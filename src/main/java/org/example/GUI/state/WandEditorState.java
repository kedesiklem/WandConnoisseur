package org.example.GUI.state;

import org.example.main.Wand;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Everything the editor windows need to agree on about "what's being edited right now":
 * the wand itself, which slots are multi-selected, which picker tab is active, and a dirty
 * flag so the main loop knows to re-render the flowchart/cast-state textures.
 */
public final class WandEditorState{
    private final Wand wand;
    private final Set<Integer> selectedSlots = new LinkedHashSet<>();
    private int selectionAnchor = -1;
    private boolean dirty = true;

    public WandEditorState(Wand wand){
        this.wand = wand;
    }

    public Wand getWand(){
        return this.wand;
    }

    // selection

    public Set<Integer> getSelectedSlots(){
        return this.selectedSlots;
    }

    public boolean isSelected(int slot){
        return this.selectedSlots.contains(slot);
    }

    /** Click handling for a slot: plain click selects it alone, ctrl toggles it, shift range-selects from the anchor. */
    public void clickSlot(int slot, boolean ctrl, boolean shift){
        if(shift && this.selectionAnchor != -1){
            this.selectedSlots.clear();
            int from = Math.min(this.selectionAnchor, slot);
            int to = Math.max(this.selectionAnchor, slot);
            for(int i=from; i <= to; i++){
                this.selectedSlots.add(i);
            }
            return;
        }

        if(ctrl){
            if(!this.selectedSlots.remove((Integer)slot)){
                this.selectedSlots.add(slot);
            }
            this.selectionAnchor = slot;
            return;
        }

        this.selectedSlots.clear();
        this.selectedSlots.add(slot);
        this.selectionAnchor = slot;
    }

    public void clearSelection(){
        this.selectedSlots.clear();
        this.selectionAnchor = -1;
    }

    public void onSlotCountChanged(int newNbSlot){
        this.selectedSlots.removeIf(slot -> slot >= newNbSlot);
        if(this.selectionAnchor >= newNbSlot){
            this.selectionAnchor = -1;
        }
    }

    // dirty flag, consumed once per frame by MainWindow to refresh dependent textures

    public void markDirty(){
        this.dirty = true;
    }

    public boolean consumeDirty(){
        boolean wasDirty = this.dirty;
        this.dirty = false;
        return wasDirty;
    }
}
