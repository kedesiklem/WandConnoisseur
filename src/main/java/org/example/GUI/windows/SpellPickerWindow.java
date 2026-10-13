package org.example.GUI.windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDragDropFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.example.GUI.DragPayloads;
import org.example.GUI.SlotPainter;
import org.example.GUI.SlotPainter.Frame;
import org.example.GUI.state.SpellCategoryManager;
import org.example.GUI.state.SpellCategoryManager.Category;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandEditorState;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The old {@code SpellSelectorComponent}: a column of square icon tabs (one per
 * {@link Spell.SpellType}, then one per user category) on the left, blue-bordered, the active
 * tab black and merging into the content, inactive ones brown; next to it a scrollable grid of
 * spells in slot frames. Click a spell to put it in the wand's first free slot, drag it onto a
 * wand slot to place it there, right-click it to add/remove it from custom categories. The "+" tab
 * creates a category, right-clicking a category tab edits it; the search field shows matches from all spells.
 */
public final class SpellPickerWindow{
    public static final String TITLE = "Spell Picker";
    private static final String NEW_CATEGORY_POPUP = "##newCategoryPopup";
    private static final int PLUS_COLOR = imgui.ImColor.rgba(220, 220, 220, 255);
    private static final int BORDER_COLOR = imgui.ImColor.rgba(0, 0, 255, 255);
    private static final int TAB_ACTIVE = imgui.ImColor.rgba(0, 0, 0, 255);
    private static final int TAB_INACTIVE = imgui.ImColor.rgba(54, 43, 39, 255);

    private final WandEditorState state;
    private final UndoManager undoManager;
    private final SpellCategoryManager categoryManager;
    private final Spell[] allSpells;
    private final List<List<Spell>> spellsByType = new ArrayList<>();
    private final Spell[] typeIcons;
    private final SlotGrid grid = new SlotGrid(BORDER_COLOR);
    private final CategoryManagerWindow categoryManagerWindow;
    private final ImString newCategoryName = new ImString(32);
    private final ImString renameBuffer = new ImString(64);
    private final ImString search = new ImString(64);
    private final ImBoolean open = new ImBoolean(true);
    private int currentTab = 0;
    private Runnable pendingCategoryChange;

    public SpellPickerWindow(WandEditorState state, UndoManager undoManager, SpellCategoryManager categoryManager, CategoryManagerWindow categoryManagerWindow){
        this.state = state;
        this.undoManager = undoManager;
        this.categoryManager = categoryManager;
        this.categoryManagerWindow = categoryManagerWindow;
        this.allSpells = Global.getSpellList().getSpells(false);

        Spell.SpellType[] types = Spell.SpellType.values();
        this.typeIcons = new Spell[types.length];
        for(int i=0; i < types.length; i++){
            this.typeIcons[i] = switch(types[i]){
                case projectile -> new LIGHT_BULLET();
                case static_projectile -> new REGENERATION_FIELD();
                case passif -> new ENERGY_SHIELD();
                case utility -> new RESET();
                case modifier -> new DAMAGE();
                case material -> new MATERIAL_WATER();
                case multicast -> new BURST_2();
                default -> new ALPHA();
            };

            List<Spell> spells = new ArrayList<>();
            for(Spell spell : this.allSpells){
                if(spell.getType() == types[i]){
                    spells.add(spell);
                }
            }
            this.spellsByType.add(spells);
        }
    }

    public ImBoolean getOpen(){
        return this.open;
    }

    /** Draws the dockable "Spell Picker" panel: a search field above the old picker. */
    public void render(){
        if(!DockPanel.begin(TITLE, this.open)){
            return;
        }

        ImGui.setNextItemWidth(DockPanel.contentWidth());
        ImGui.inputTextWithHint("##spellSearch", "Search spells (name or alias)", this.search);
        ImGui.setCursorScreenPos(DockPanel.contentX(), DockPanel.contentY() + DockPanel.HALF_GAP);

        render(DockPanel.contentX(), DockPanel.contentY(), DockPanel.contentWidth(), DockPanel.contentHeight());
        DockPanel.end();
    }

    /** Draws the picker in the screen rectangle (x, y, width, height) of the current window. */
    public void render(float x, float y, float width, float height){
        List<Category> categories = this.categoryManager.getCategories();
        int tabCount = this.typeIcons.length + categories.size();
        this.currentTab = Math.min(this.currentTab, tabCount - 1);

        String query = this.search.get().trim().toLowerCase(Locale.ROOT);
        boolean searching = !query.isEmpty();
        int activeTab = searching ? -1 : this.currentTab;

        int s = Global.getScaleFactor();
        int tabSize = this.grid.slotPixels();

        // tab column: its own child so a long list of categories scrolls with the wheel instead of overflowing
        ImGui.setCursorScreenPos(x, y);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
        ImGui.beginChild("##pickerTabs", tabSize, height, false, ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoBackground | ImGuiWindowFlags.NoNav);
        ImGui.popStyleVar();
        float tabsY = ImGui.getCursorScreenPosY();
        ImDrawList drawList = ImGui.getWindowDrawList();

        for(int tab=0; tab <= tabCount; tab++){
            float tabY = tabsY + tab*tabSize;
            boolean addTab = tab == tabCount;
            Category category = addTab || tab < this.typeIcons.length ? null : categories.get(tab - this.typeIcons.length);

            ImGui.setCursorScreenPos(x, tabY);
            if(DragPayloads.isClick(ImGui.invisibleButton("##pickerTab" + tab, tabSize, tabSize))){
                if(addTab){
                    this.newCategoryName.set("");
                    ImGui.openPopup(NEW_CATEGORY_POPUP);
                }else{
                    this.currentTab = tab;
                    this.search.set("");
                }
            }
            if(category != null){
                renderCategoryTabMenu(category);
            }

            drawList.addRectFilled(x, tabY, x + tabSize, tabY + tabSize, tab == activeTab ? TAB_ACTIVE : TAB_INACTIVE);
            if(addTab){
                float center = tabSize/2f;
                drawList.addRectFilled(x + center - s, tabY + 5*s, x + center + s, tabY + tabSize - 5*s, PLUS_COLOR);
                drawList.addRectFilled(x + 5*s, tabY + center - s, x + tabSize - 5*s, tabY + center + s, PLUS_COLOR);
            }else if(category == null){
                SlotPainter.drawTexture(drawList, SlotPainter.spellTexture(this.typeIcons[tab], false), x, tabY);
            }else{
                float[] color = category.getColor();
                drawList.addRectFilled(x + 4*s, tabY + 4*s, x + tabSize - 3*s, tabY + tabSize - 3*s,
                    imgui.ImColor.rgba(color[0], color[1], color[2], 1f));
                List<Spell> spells = spellsOfCategory(category);
                if(!spells.isEmpty()){
                    SlotPainter.drawTexture(drawList, SlotPainter.spellTexture(spells.get(0), false), x, tabY);
                }
            }

            drawList.addRectFilled(x, tabY, x + tabSize, tabY + s, BORDER_COLOR);
            drawList.addRectFilled(x, tabY, x + s, tabY + tabSize, BORDER_COLOR);
            if(addTab){
                drawList.addRectFilled(x, tabY + tabSize - s, x + tabSize, tabY + tabSize, BORDER_COLOR);
            }
        }
        float tabsScroll = ImGui.getScrollY();
        renderNewCategoryPopup();
        ImGui.endChild();

        if(this.pendingCategoryChange != null){
            this.pendingCategoryChange.run();
            this.pendingCategoryChange = null;
        }
        this.currentTab = Math.max(0, Math.min(this.currentTab, this.typeIcons.length + categories.size() - 1));

        List<Spell> spells;
        if(searching){
            spells = new ArrayList<>();
            for(Spell spell : this.allSpells){
                if(matches(spell, query)){
                    spells.add(spell);
                }
            }
        }else{
            spells = this.currentTab < this.typeIcons.length
                ? this.spellsByType.get(this.currentTab)
                : spellsOfCategory(categories.get(this.currentTab - this.typeIcons.length));
        }

        this.grid.begin(searching ? "##pickerSearch" : "##pickerGrid" + this.currentTab, x + tabSize, y, width - tabSize, height, spells.size());
        for(int i=0; i < spells.size(); i++){
            ImGui.pushID(i);
            renderSpell(spells.get(i), this.grid.slotX(i), this.grid.slotY(i));
            ImGui.popID();
        }
        if(searching){
            this.grid.endSlots(-1, -1);
        }else{
            this.grid.endSlots(this.currentTab*tabSize + s - tabsScroll, (this.currentTab + 1)*tabSize - tabsScroll);
        }
        this.grid.endChild();
    }

    private static boolean matches(Spell spell, String query){
        if(spell.getName() != null && spell.getName().toLowerCase(Locale.ROOT).contains(query)){
            return true;
        }
        if(spell.getAlias() != null){
            for(String alias : spell.getAlias()){
                if(alias != null && alias.toLowerCase(Locale.ROOT).contains(query)){
                    return true;
                }
            }
        }
        return false;
    }

    private void renderNewCategoryPopup(){
        if(!ImGui.beginPopup(NEW_CATEGORY_POPUP)){
            return;
        }

        if(ImGui.isWindowAppearing()){
            ImGui.setKeyboardFocusHere();
        }
        ImGui.setNextItemWidth(180);
        boolean submitted = ImGui.inputTextWithHint("##newCategoryTabName", "New category name", this.newCategoryName, ImGuiInputTextFlags.EnterReturnsTrue);
        ImGui.sameLine();
        submitted |= ImGui.button("Create");

        String name = this.newCategoryName.get().trim();
        if(submitted && !name.isEmpty()){
            this.categoryManager.create(name);
            this.currentTab = this.typeIcons.length + this.categoryManager.getCategories().size() - 1;
            this.search.set("");
            this.newCategoryName.set("");
            ImGui.closeCurrentPopup();
        }
        ImGui.textDisabled("Right-click a spell to add it to a category.");

        ImGui.endPopup();
    }

    /** Deletes a category, keeping the same tab selected when it was after the deleted one. */
    private void deleteCategory(Category category){
        int tab = this.typeIcons.length + this.categoryManager.getCategories().indexOf(category);
        this.categoryManager.delete(category);
        if(this.currentTab > tab){
            this.currentTab--;
        }
    }

    /** Moves a category one place, the selection following it when it is the selected tab. */
    private void moveCategory(Category category, boolean up){
        List<Category> categories = this.categoryManager.getCategories();
        boolean selected = this.currentTab == this.typeIcons.length + categories.indexOf(category);
        if(up){
            this.categoryManager.moveUp(category);
        }else{
            this.categoryManager.moveDown(category);
        }
        if(selected){
            this.currentTab = this.typeIcons.length + categories.indexOf(category);
        }
    }

    private void renderCategoryTabMenu(Category category){
        if(!ImGui.beginPopupContextItem()){
            return;
        }

        if(ImGui.isWindowAppearing()){
            this.renameBuffer.set(category.getName());
        }
        ImGui.setNextItemWidth(160);
        if(ImGui.inputText("Name", this.renameBuffer, ImGuiInputTextFlags.EnterReturnsTrue) && !this.renameBuffer.get().isBlank()){
            this.categoryManager.rename(category, this.renameBuffer.get().trim());
        }
        float[] color = category.getColor();
        if(ImGui.colorEdit3("Color", color, ImGuiColorEditFlags.NoInputs)){
            this.categoryManager.setColor(category, color[0], color[1], color[2]);
        }
        ImGui.separator();
        // structural changes are deferred: this runs inside the loop over the (live) category list
        if(ImGui.menuItem("Move up")){
            this.pendingCategoryChange = () -> moveCategory(category, true);
        }
        if(ImGui.menuItem("Move down")){
            this.pendingCategoryChange = () -> moveCategory(category, false);
        }
        if(ImGui.menuItem("Delete")){
            this.pendingCategoryChange = () -> deleteCategory(category);
        }
        ImGui.separator();
        if(ImGui.menuItem("Manage categories...")){
            this.categoryManagerWindow.open();
        }

        ImGui.endPopup();
    }

    private List<Spell> spellsOfCategory(Category category){
        List<Spell> result = new ArrayList<>();
        for(Spell spell : this.allSpells){
            if(category.contains(spell)){
                result.add(spell);
            }
        }
        return result;
    }

    private void renderSpell(Spell spell, float x, float y){
        int size = this.grid.slotPixels();

        ImGui.setCursorScreenPos(x, y);
        if(DragPayloads.isClick(ImGui.invisibleButton("##spell", size, size))){
            Wand wand = this.state.getWand();
            if(Arrays.asList(wand.getSpells(true)).contains(null)){
                this.undoManager.push(wand);
                wand.putSpellFirstAvailable(spell.clone());
                this.state.markDirty();
            }
        }

        if(ImGui.beginDragDropSource(ImGuiDragDropFlags.SourceNoPreviewTooltip)){
            DragPayloads.markDragStarted();
            ImGui.setDragDropPayload(DragPayloads.SPELL_FROM_PICKER, new DragPayloads.SpellPayload(spell));
            ImGui.endDragDropSource();
        }

        renderCategoryContextMenu(spell);

        DragPayloads.SpellPayload dragged = ImGui.getDragDropPayload(DragPayloads.SPELL_FROM_PICKER);
        SlotPainter.draw(ImGui.getWindowDrawList(), x, y, spell, Frame.SLOT, dragged != null && dragged.spell() == spell);
    }

    private void renderCategoryContextMenu(Spell spell){
        if(!ImGui.beginPopupContextItem()){
            return;
        }

        for(Category category : this.categoryManager.getCategories()){
            boolean inCategory = category.contains(spell);
            if(ImGui.menuItem(category.getName(), "", inCategory)){
                this.categoryManager.toggleSpell(category, spell);
            }
        }

        if(!this.categoryManager.getCategories().isEmpty()){
            ImGui.separator();
        }

        ImGui.setNextItemWidth(150);
        ImGui.inputTextWithHint("##newCategory", "New category name", this.newCategoryName);
        ImGui.sameLine();
        if(ImGui.smallButton("Add") && !this.newCategoryName.get().isBlank()){
            Category created = this.categoryManager.create(this.newCategoryName.get().trim());
            this.categoryManager.addSpell(created, spell);
            this.newCategoryName.set("");
        }

        ImGui.endPopup();
    }
}
