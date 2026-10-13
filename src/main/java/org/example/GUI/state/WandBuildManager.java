package org.example.GUI.state;

import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.Spell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Save/load named wand builds (the "File > Save build / Load build" feature). A build is a
 * small JSON file under {@code Global.getPathOutput() + "builds/"} holding every stat field
 * plus the spell list, keyed by each spell's class simple name (always one of its aliases,
 * resolvable via {@code Global.getSpellList().getSpell(...)}) with its charge state.
 */
public final class WandBuildManager{
    private static String buildsDir(){
        return Global.getPathOutput() + "builds/";
    }

    public List<String> listBuilds(){
        File dir = new File(buildsDir());
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        List<String> names = new ArrayList<>();
        if(files != null){
            for(File file : files){
                String name = file.getName();
                names.add(name.substring(0, name.length() - ".json".length()));
            }
        }
        names.sort(String::compareToIgnoreCase);
        return names;
    }

    public boolean exists(String name){
        return new File(buildsDir() + sanitize(name) + ".json").isFile();
    }

    public void save(String name, Wand wand){
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("shuffle", wand.getShuffle());
        root.put("nbDraw", (double)wand.getNbDraw());
        root.put("castDelay", (double)wand.getCastDelay());
        root.put("rechargeTime", (double)wand.getRechargeTime());
        root.put("maxMana", (double)wand.getMaxMana());
        root.put("regenMana", (double)wand.getRegenMana());
        root.put("nbSlot", (double)wand.getNbSlot());
        root.put("spread", wand.getSpread());
        root.put("speed", wand.getSpeed());

        List<Object> spells = new ArrayList<>();
        for(Spell spell : wand.getSpells(true)){
            if(spell == null){
                spells.add(null);
                continue;
            }
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("spell", SpellRegistry.keyOf(spell));
            if(!spell.getHasCharges()){
                entry.put("charge", "inf");
            }else if(spell.getChargesLeft() != spell.getMaxCharges()){
                entry.put("charge", String.valueOf(spell.getChargesLeft()));
            }
            spells.add(entry);
        }
        root.put("spells", spells);

        try{
            File dir = new File(buildsDir());
            dir.mkdirs();
            Files.writeString(new File(dir, sanitize(name) + ".json").toPath(), MiniJson.write(root), StandardCharsets.UTF_8);
        }catch(IOException e){
            System.out.println("Failed to save wand build \"" + name + "\": " + e.getMessage());
        }
    }

    /** Applies a saved build onto {@code wand} in place (never replaces the Wand instance, so undo keeps working). */
    public boolean load(String name, Wand wand){
        Map<String, Object> root;
        try{
            String text = Files.readString(Path.of(buildsDir() + sanitize(name) + ".json"), StandardCharsets.UTF_8);
            Object parsed = MiniJson.parse(text);
            if(!(parsed instanceof Map<?, ?> map)){
                return false;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> typed = (Map<String, Object>)map;
            root = typed;
        }catch(IOException e){
            System.out.println("Failed to load wand build \"" + name + "\": " + e.getMessage());
            return false;
        }

        List<?> spellsField = (List<?>)root.get("spells");
        int nbSlot = ((Number)root.get("nbSlot")).intValue();
        Spell[] spells = new Spell[nbSlot];
        if(spellsField != null){
            for(int i=0; i < spellsField.size() && i < nbSlot; i++){
                Object entry = spellsField.get(i);
                if(!(entry instanceof Map<?, ?> spellEntry)){
                    continue;
                }
                String key = String.valueOf(spellEntry.get("spell"));
                Spell spell = SpellRegistry.byKey(key);
                if(spell == null){
                    System.out.println("unknown spell in build \"" + name + "\": " + key);
                    continue;
                }
                Object charge = spellEntry.get("charge");
                if("inf".equals(charge)){
                    spell.makeInfinite();
                }else if(charge != null){
                    try{
                        spell.setCharges(Integer.parseInt(String.valueOf(charge)));
                    }catch(NumberFormatException ignored){}
                }
                spells[i] = spell;
            }
        }

        wand.setShuffle((Boolean)root.get("shuffle"), false);
        wand.setNbDraw(((Number)root.get("nbDraw")).intValue(), false);
        wand.setCastDelay(((Number)root.get("castDelay")).intValue(), false);
        wand.setRechargeTime(((Number)root.get("rechargeTime")).intValue(), false);
        wand.setMaxMana(((Number)root.get("maxMana")).intValue(), false);
        wand.setRegenMana(((Number)root.get("regenMana")).intValue(), false);
        wand.setNbSlot(nbSlot, false);
        wand.setSpread(((Number)root.get("spread")).doubleValue(), false);
        wand.setSpeed(((Number)root.get("speed")).doubleValue(), false);
        wand.setSpells(spells);
        wand.resetAll();
        return true;
    }

    public void delete(String name){
        new File(buildsDir() + sanitize(name) + ".json").delete();
    }

    private static String sanitize(String name){
        return name.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
