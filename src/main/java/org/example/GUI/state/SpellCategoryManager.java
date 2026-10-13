package org.example.GUI.state;

import org.example.main.Global;
import org.example.spells.Spell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * User-defined spell categories, shown as extra tabs in the spell picker alongside the
 * built-in {@link Spell.SpellType} tabs. A spell's membership is keyed by its class' simple
 * name (stable across clones/locales), not by alias string or instance.
 */
public final class SpellCategoryManager{
    public static final class Category{
        private String name;
        private float[] color;
        private final LinkedHashSet<String> spellKeys = new LinkedHashSet<>();

        private Category(String name, float[] color){
            this.name = name;
            this.color = color;
        }

        public String getName(){
            return this.name;
        }

        public float[] getColor(){
            return this.color;
        }

        public LinkedHashSet<String> getSpellKeys(){
            return this.spellKeys;
        }

        public boolean contains(Spell spell){
            return this.spellKeys.contains(keyOf(spell));
        }
    }

    private static final String FILE = Global.getPathConfig() + "spell_categories.json";
    private final List<Category> categories = new ArrayList<>();

    public SpellCategoryManager(){
        load();
    }

    public static String keyOf(Spell spell){
        return SpellRegistry.keyOf(spell);
    }

    public List<Category> getCategories(){
        return this.categories;
    }

    public Category create(String name){
        Category category = new Category(name, new float[]{0.6f, 0.6f, 0.9f, 1f});
        this.categories.add(category);
        save();
        return category;
    }

    public void rename(Category category, String newName){
        category.name = newName;
        save();
    }

    public void setColor(Category category, float r, float g, float b){
        category.color = new float[]{r, g, b, 1f};
        save();
    }

    public void delete(Category category){
        this.categories.remove(category);
        save();
    }

    public void moveUp(Category category){
        int index = this.categories.indexOf(category);
        if(index > 0){
            this.categories.set(index, this.categories.set(index - 1, category));
            save();
        }
    }

    public void moveDown(Category category){
        int index = this.categories.indexOf(category);
        if(index != -1 && index < this.categories.size() - 1){
            this.categories.set(index, this.categories.set(index + 1, category));
            save();
        }
    }

    public void addSpell(Category category, Spell spell){
        if(category.spellKeys.add(keyOf(spell))){
            save();
        }
    }

    public void removeSpell(Category category, Spell spell){
        if(category.spellKeys.remove(keyOf(spell))){
            save();
        }
    }

    public void toggleSpell(Category category, Spell spell){
        if(category.contains(spell)){
            removeSpell(category, spell);
        }else{
            addSpell(category, spell);
        }
    }

    private void load(){
        this.categories.clear();
        try{
            String text = Files.readString(Path.of(FILE), StandardCharsets.UTF_8);
            Object parsed = MiniJson.parse(text);
            if(!(parsed instanceof List<?> list)){
                return;
            }
            for(Object entry : list){
                if(!(entry instanceof Map<?, ?> map)){
                    continue;
                }
                String name = String.valueOf(map.get("name"));
                float[] color = {0.6f, 0.6f, 0.9f, 1f};
                if(map.get("color") instanceof List<?> colorList && colorList.size() == 3){
                    for(int i=0; i < 3; i++){
                        color[i] = ((Number)colorList.get(i)).floatValue();
                    }
                }
                Category category = new Category(name, color);
                if(map.get("spells") instanceof List<?> spells){
                    for(Object spellKey : spells){
                        category.spellKeys.add(String.valueOf(spellKey));
                    }
                }
                this.categories.add(category);
            }
        }catch(IOException e){
            // no categories saved yet, start empty
        }
    }

    private void save(){
        List<Object> list = new ArrayList<>();
        for(Category category : this.categories){
            Map<String, Object> entry = new java.util.LinkedHashMap<>();
            entry.put("name", category.name);
            entry.put("color", List.of((double)category.color[0], (double)category.color[1], (double)category.color[2]));
            entry.put("spells", new ArrayList<Object>(category.spellKeys));
            list.add(entry);
        }

        try{
            File file = new File(FILE);
            if(file.getParentFile() != null){
                file.getParentFile().mkdirs();
            }
            Files.writeString(file.toPath(), MiniJson.write(list), StandardCharsets.UTF_8);
        }catch(IOException e){
            System.out.println("Failed to save spell categories: " + e.getMessage());
        }
    }
}
