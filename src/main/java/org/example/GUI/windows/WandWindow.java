package org.example.GUI.windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiDragDropFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.type.ImBoolean;
import org.example.GUI.DragPayloads;
import org.example.GUI.DragPayloads.SlotsPayload;
import org.example.GUI.DragPayloads.SpellPayload;
import org.example.GUI.SlotPainter;
import org.example.GUI.SlotPainter.Frame;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandEditorState;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The old {@code WandComponent}: an orange-bordered scrollable grid of inventory slots. On top of
 * the old behavior: click/ctrl/shift to multi-select, drag to place from the picker or to move a
 * selection as one block, Delete to clear the selection. Right-click opens the old context menu
 * (Edit stats, copy to clipboard, export) plus clear actions.
 */
public final class WandWindow{
    public static final String TITLE = "Wand";
    private static final int BORDER_COLOR = imgui.ImColor.rgba(255, 174, 62, 255);
    private static final int SELECTION_COLOR = imgui.ImColor.rgba(255, 174, 62, 255);

    private final WandEditorState state;
    private final UndoManager undoManager;
    private final WandStatDialog statDialog;
    private final SlotGrid grid = new SlotGrid(BORDER_COLOR);
    private final ImBoolean open = new ImBoolean(true);

    public WandWindow(WandEditorState state, UndoManager undoManager, WandStatDialog statDialog){
        this.state = state;
        this.undoManager = undoManager;
        this.statDialog = statDialog;
    }

    public ImBoolean getOpen(){
        return this.open;
    }

    /** Draws the dockable "Wand" panel. */
    public void render(){
        if(DockPanel.begin(TITLE, this.open)){
            render(DockPanel.contentX(), DockPanel.contentY(), DockPanel.contentWidth(), DockPanel.contentHeight());
            DockPanel.end();
        }
    }

    /** Draws the wand grid in the screen rectangle (x, y, width, height) of the current window. */
    public void render(float x, float y, float width, float height){
        Wand wand = this.state.getWand();
        Spell[] spells = wand.getSpells(true);

        this.grid.begin("##wandGrid", x, y, width, height, spells.length);
        boolean windowFocused = ImGui.isWindowFocused();

        for(int i=0; i < spells.length; i++){
            ImGui.pushID(i);
            renderSlot(wand, spells, i);
            ImGui.popID();
        }
        this.grid.endSlots(-1, -1);

        if(ImGui.isWindowHovered() && ImGui.isMouseClicked(ImGuiMouseButton.Left) && !ImGui.isAnyItemHovered()){
            this.state.clearSelection();
        }

        if(windowFocused && ImGui.isKeyPressed(ImGuiKey.Delete) && !this.state.getSelectedSlots().isEmpty()){
            clearSlots(wand, this.state.getSelectedSlots());
        }

        renderContextMenu(wand);
        this.grid.endChild();
    }

    private void renderSlot(Wand wand, Spell[] spells, int index){
        Spell spell = spells[index];
        float x = this.grid.slotX(index);
        float y = this.grid.slotY(index);
        int size = this.grid.slotPixels();

        ImGui.setCursorScreenPos(x, y);
        boolean clicked = DragPayloads.isClick(ImGui.invisibleButton("##slot", size, size));

        if(clicked){
            this.state.clickSlot(index, ImGui.getIO().getKeyCtrl(), ImGui.getIO().getKeyShift());
        }

        if(spell != null && ImGui.beginDragDropSource(ImGuiDragDropFlags.SourceNoPreviewTooltip)){
            DragPayloads.markDragStarted();
            if(!this.state.isSelected(index)){
                this.state.clickSlot(index, false, false);
            }
            ImGui.setDragDropPayload(DragPayloads.SLOTS_FROM_WAND, new SlotsPayload(sortedSelection(), index));
            ImGui.endDragDropSource();
        }

        boolean dropHover = false;
        if(ImGui.beginDragDropTarget()){
            int peek = ImGuiDragDropFlags.AcceptBeforeDelivery | ImGuiDragDropFlags.AcceptNoDrawDefaultRect;
            int deliver = ImGuiDragDropFlags.AcceptNoDrawDefaultRect;

            SpellPayload hoveringSpell = ImGui.acceptDragDropPayload(DragPayloads.SPELL_FROM_PICKER, peek, SpellPayload.class);
            SlotsPayload hoveringSlots = ImGui.acceptDragDropPayload(DragPayloads.SLOTS_FROM_WAND, peek, SlotsPayload.class);
            dropHover = hoveringSpell != null || hoveringSlots != null;

            SpellPayload spellPayload = ImGui.acceptDragDropPayload(DragPayloads.SPELL_FROM_PICKER, deliver, SpellPayload.class);
            if(spellPayload != null){
                DragPayloads.markDropHandled();
                this.undoManager.push(wand);
                wand.putSpell(spellPayload.spell().clone(), index);
                this.state.markDirty();
            }
            SlotsPayload slotsPayload = ImGui.acceptDragDropPayload(DragPayloads.SLOTS_FROM_WAND, deliver, SlotsPayload.class);
            if(slotsPayload != null){
                DragPayloads.markDropHandled();
                moveBlock(wand, slotsPayload.slots(), slotsPayload.grabbed(), index);
            }
            ImGui.endDragDropTarget();
        }

        SlotsPayload dragged = ImGui.getDragDropPayload(DragPayloads.SLOTS_FROM_WAND);
        boolean grayScale = dragged != null && Arrays.stream(dragged.slots()).anyMatch(slot -> slot == index);

        // released back onto one of the dragged slots (not a drop target for itself): keep it, like the old GUI
        if(grayScale && ImGui.isMouseReleased(ImGuiMouseButton.Left) && ImGui.isMouseHoveringRect(x, y, x + size, y + size)){
            DragPayloads.markDropHandled();
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        SlotPainter.draw(drawList, x, y, spell, dropHover ? Frame.HIGHLIGHT : Frame.SLOT, grayScale);

        if(this.state.isSelected(index)){
            int s = Global.getScaleFactor();
            drawList.addRectFilled(x, y, x + size, y + s, SELECTION_COLOR);
            drawList.addRectFilled(x, y + size - s, x + size, y + size, SELECTION_COLOR);
            drawList.addRectFilled(x, y, x + s, y + size, SELECTION_COLOR);
            drawList.addRectFilled(x + size - s, y, x + size, y + size, SELECTION_COLOR);
        }
    }

    /**
     * Old GUI behavior: wand spells dragged and released anywhere that isn't a wand slot (picker,
     * images, empty space, outside the window) are removed from the wand. Called once per frame after
     * every panel had its chance to accept the drop.
     */
    public void removeIfDroppedOutside(){
        if(!ImGui.isMouseReleased(ImGuiMouseButton.Left) || DragPayloads.isDropHandled()){
            return;
        }
        SlotsPayload payload = ImGui.getDragDropPayload(DragPayloads.SLOTS_FROM_WAND);
        if(payload == null){
            return;
        }

        Set<Integer> slots = new TreeSet<>();
        for(int slot : payload.slots()){
            slots.add(slot);
        }
        clearSlots(this.state.getWand(), slots);
        this.state.clearSelection();
    }

    private int[] sortedSelection(){
        Set<Integer> sorted = new TreeSet<>(this.state.getSelectedSlots());
        int[] result = new int[sorted.size()];
        int i = 0;
        for(int slot : sorted){
            result[i++] = slot;
        }
        return result;
    }

    /** Moves the block of {@code sourceIndices} so it starts at {@code targetIndex}, preserving relative order, shifting the rest. */
    /**
     * Moves the selected slots as one contiguous block so the grabbed slot lands on {@code targetIndex}
     * (the others keep their order around it), shifting the rest. Dropping onto another slot of the
     * selection itself is allowed: that's how a block is nudged by one slot.
     */
    private void moveBlock(Wand wand, int[] sourceIndices, int grabbedIndex, int targetIndex){
        if(sourceIndices.length == 0){
            return;
        }

        Spell[] spells = wand.getSpells(true).clone();
        boolean[] isSource = new boolean[spells.length];
        for(int idx : sourceIndices){
            if(idx >= 0 && idx < spells.length){
                isSource[idx] = true;
            }
        }
        List<Spell> moved = new ArrayList<>();
        int grabbedOffset = 0;
        for(int idx : sourceIndices){
            if(idx >= 0 && idx < spells.length){
                if(idx == grabbedIndex){
                    grabbedOffset = moved.size();
                }
                moved.add(spells[idx]);
            }
        }

        List<Spell> remaining = new ArrayList<>();
        for(int i=0; i < spells.length; i++){
            if(!isSource[i]){
                remaining.add(spells[i]);
            }
        }

        // the grabbed spell lands exactly on the slot it was dropped on (block clamped to the wand's ends)
        int insertPos = Math.max(0, Math.min(targetIndex - grabbedOffset, remaining.size()));
        remaining.addAll(insertPos, moved);

        boolean changed = false;
        for(int i=0; i < spells.length; i++){
            changed |= remaining.get(i) != spells[i];
        }
        if(!changed){
            return;
        }

        this.undoManager.push(wand);
        wand.setSpells(remaining.toArray(new Spell[0]));
        this.state.markDirty();

        this.state.clearSelection();
        for(int i=0; i < moved.size(); i++){
            this.state.clickSlot(insertPos + i, true, false);
        }
    }

    private void clearSlots(Wand wand, Set<Integer> slots){
        this.undoManager.push(wand);
        Spell[] spells = wand.getSpells(true).clone();
        for(int slot : slots){
            if(slot >= 0 && slot < spells.length){
                spells[slot] = null;
            }
        }
        wand.setSpells(spells);
        this.state.markDirty();
    }

    private void renderContextMenu(Wand wand){
        if(!ImGui.beginPopupContextWindow("##wandContext")){
            return;
        }

        if(ImGui.menuItem("Edit")){
            this.statDialog.open();
        }
        if(ImGui.beginMenu("Copy to clipboard")){
            if(ImGui.menuItem("Spells")){
                Global.toClipboard(spellsToString(wand));
            }
            if(ImGui.beginMenu("Wand image")){
                if(ImGui.menuItem("as PNG")){
                    Global.toClipboard(wand.getSpellsImage().toPNG());
                }
                if(ImGui.menuItem("as SVG")){
                    Global.toClipboard(wand.getSpellsImage().toBytes(true), Global.MimeType.SVG);
                }
                ImGui.endMenu();
            }
            if(ImGui.beginMenu("Wand stat image")){
                if(ImGui.menuItem("as PNG")){
                    Global.toClipboard(wand.getStatImage().toPNG());
                }
                if(ImGui.menuItem("as SVG")){
                    Global.toClipboard(wand.getStatImage().toBytes(true), Global.MimeType.SVG);
                }
                ImGui.endMenu();
            }
            ImGui.endMenu();
        }

        if(ImGui.beginMenu("Export")){
            if(ImGui.beginMenu("Wand image")){
                if(ImGui.menuItem("as PNG")){
                    wand.getSpellsImage().saveAsPNG(Global.getPathOutput() + "SpellImage.png");
                }
                if(ImGui.menuItem("as SVG")){
                    wand.getSpellsImage().saveAsSVG(Global.getPathOutput() + "SpellImage.svg", true);
                }
                ImGui.endMenu();
            }
            if(ImGui.beginMenu("Wand stat image")){
                if(ImGui.menuItem("as PNG")){
                    wand.getStatImage().saveAsPNG(Global.getPathOutput() + "StatImage.png");
                }
                if(ImGui.menuItem("as SVG")){
                    wand.getStatImage().saveAsSVG(Global.getPathOutput() + "StatImage.svg", true);
                }
                ImGui.endMenu();
            }
            ImGui.endMenu();
        }

        ImGui.separator();
        if(ImGui.menuItem("Clear selected slots", "Del", false, !this.state.getSelectedSlots().isEmpty())){
            clearSlots(wand, this.state.getSelectedSlots());
        }
        if(ImGui.menuItem("Quick clear wand")){
            this.undoManager.push(wand);
            wand.removeAllSpell();
            this.state.clearSelection();
            this.state.markDirty();
        }

        ImGui.endPopup();
    }

    private static String spellsToString(Wand wand){
        Spell[] spells = wand.getSpells(false);
        StringBuilder result = new StringBuilder();
        Spell lastSpell = null;
        String lastCharge = "";
        int count = 1;

        for(Spell spell : spells){
            String currentCharge = spell.getHasCharges()
                ? (spell.getMaxCharges() == spell.getChargesLeft() ? (spell.getNeverUnlimited() ? "" : "max:") : spell.getChargesLeft() + ":")
                : (spell.getMaxCharges() == 0 ? "" : "inf:");

            if(lastSpell != null && spell.getClass() == lastSpell.getClass() && lastCharge.equals(currentCharge)){
                count++;
                continue;
            }

            if(lastSpell != null){
                result.append(result.isEmpty() ? "" : ", ").append(lastCharge).append(lastSpell.getShortestAlias()).append(count == 1 ? "" : ":" + count);
            }
            count = 1;
            lastSpell = spell;
            lastCharge = currentCharge;
        }
        if(lastSpell != null){
            result.append(result.isEmpty() ? "" : ", ").append(lastCharge).append(lastSpell.getShortestAlias()).append(count == 1 ? "" : ":" + count);
        }

        return result.toString();
    }
}
