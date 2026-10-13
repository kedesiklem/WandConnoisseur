package org.example.GUI.windows;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.example.GUI.state.SpellCategoryManager;
import org.example.GUI.state.SpellCategoryManager.Category;

import java.util.List;

/** CRUD window for user-defined spell categories: create, rename, recolor, reorder, delete. */
public final class CategoryManagerWindow{
    public static final String TITLE = "Spell Categories";

    private final SpellCategoryManager categoryManager;
    private final ImBoolean visible = new ImBoolean(false);
    private final ImString newCategoryName = new ImString(32);

    public CategoryManagerWindow(SpellCategoryManager categoryManager){
        this.categoryManager = categoryManager;
    }

    public ImBoolean getOpen(){
        return this.visible;
    }

    public void open(){
        this.visible.set(true);
    }

    public void render(){
        if(!this.visible.get()){
            return;
        }

        if(!ImGui.begin(TITLE, this.visible)){
            ImGui.end();
            return;
        }

        List<Category> categories = this.categoryManager.getCategories();
        Category toDelete = null;

        for(int i=0; i < categories.size(); i++){
            Category category = categories.get(i);
            ImGui.pushID(i);

            ImString nameBuffer = new ImString(category.getName(), 64);
            ImGui.setNextItemWidth(160);
            ImGui.inputText("##name", nameBuffer);
            if(ImGui.isItemDeactivatedAfterEdit()){
                this.categoryManager.rename(category, nameBuffer.get());
            }

            ImGui.sameLine();
            float[] color = category.getColor();
            if(ImGui.colorEdit3("##color", color, imgui.flag.ImGuiColorEditFlags.NoInputs)){
                this.categoryManager.setColor(category, color[0], color[1], color[2]);
            }

            ImGui.sameLine();
            if(ImGui.smallButton("Up")){
                this.categoryManager.moveUp(category);
            }
            ImGui.sameLine();
            if(ImGui.smallButton("Down")){
                this.categoryManager.moveDown(category);
            }
            ImGui.sameLine();
            if(ImGui.smallButton("Delete")){
                toDelete = category;
            }
            ImGui.sameLine();
            ImGui.text("(" + category.getSpellKeys().size() + " spells)");

            ImGui.popID();
        }

        if(toDelete != null){
            this.categoryManager.delete(toDelete);
        }

        ImGui.separator();
        ImGui.setNextItemWidth(160);
        ImGui.inputTextWithHint("##newCategoryName", "New category name", this.newCategoryName);
        ImGui.sameLine();
        if(ImGui.button("Create") && !this.newCategoryName.get().isBlank()){
            this.categoryManager.create(this.newCategoryName.get().trim());
            this.newCategoryName.set("");
        }

        ImGui.textWrapped("Tip: right-click a spell icon in the Spell Picker to add or remove it from a category.");

        ImGui.end();
    }
}
