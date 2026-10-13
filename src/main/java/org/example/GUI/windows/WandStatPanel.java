package org.example.GUI.windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImString;
import org.example.GUI.Fonts;
import org.example.GUI.TextureCache;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandEditorState;
import org.example.main.Global;
import org.example.main.Wand;

/**
 * The old {@code WandStatComponent}: a black panel with the 9 wand stats in the pixel font,
 * editable in place (shuffle toggles on click), and the rotated wand sprite on the right.
 * Holds the edit buffers; {@link #load} fills them from a wand, {@link #apply} writes them back.
 * Used by the modal {@link WandStatDialog} and the dockable {@link WandStatWindow}.
 */
final class WandStatPanel{
    private static final String[] LABELS = {"shuffle", "Spell/Cast", "Cast delay", "Rechrg time", "Mana max", "Mana chg. Spd", "Capacity", "Spread", "Speed"};
    private static final boolean[] UNSIGNED = {true, true, false, false, true, true, true, false, true};
    static final float MARGIN = 10f;
    private static final int PANEL_BACKGROUND = imgui.ImColor.rgba(0, 0, 0, 255);
    private static final int WHITE = imgui.ImColor.rgba(255, 255, 255, 255);

    private final String spriteKey;
    private final ImString[] fields = new ImString[LABELS.length];
    private final String[] initialTexts = new String[LABELS.length];
    private boolean shuffle;
    private float width;
    private float height;

    WandStatPanel(String spriteKey){
        this.spriteKey = spriteKey;
        for(int i=1; i < LABELS.length; i++){
            this.fields[i] = new ImString(32);
        }
    }

    void load(Wand wand){
        this.shuffle = wand.getShuffle();
        this.initialTexts[0] = Boolean.toString(this.shuffle);
        this.initialTexts[1] = Integer.toString(wand.getNbDraw());
        this.initialTexts[2] = Integer.toString(wand.getCastDelay());
        this.initialTexts[3] = Integer.toString(wand.getRechargeTime());
        this.initialTexts[4] = Integer.toString(wand.getMaxMana());
        this.initialTexts[5] = Integer.toString(wand.getRegenMana());
        this.initialTexts[6] = Integer.toString(wand.getNbSlot());
        this.initialTexts[7] = Double.toString(wand.getSpread());
        this.initialTexts[8] = Double.toString(wand.getSpeed());
        for(int i=1; i < LABELS.length; i++){
            this.fields[i].set(this.initialTexts[i]);
        }

        TextureCache.refresh(this.spriteKey, Global.rotateImageByDegrees(
            Global.scaleImage(Global.loadImage(Global.getWandList().getSprite(wand)), 2*Global.getScaleFactor()), -90));
    }

    /** Natural size of the panel, valid after the first {@link #render}. */
    float getWidth(){
        return this.width;
    }

    float getHeight(){
        return this.height;
    }

    /**
     * Draws the panel at the cursor, at least {@code minWidth} x {@code minHeight} (black fill), and
     * leaves the cursor below it. Returns true when an edit was just committed (a field lost focus
     * after being changed, Enter in a field, or shuffle clicked).
     */
    boolean render(float minWidth, float minHeight){
        TextureCache.Texture sprite = TextureCache.get(this.spriteKey, null);
        boolean committed = false;

        ImGui.pushFont(Fonts.pixel());
        float rowHeight = ImGui.getTextLineHeight();
        float labelWidth = 0f;
        float valueWidth = 0f;
        for(int i=0; i < LABELS.length; i++){
            labelWidth = Math.max(labelWidth, ImGui.calcTextSize(LABELS[i]).x);
            valueWidth = Math.max(valueWidth, ImGui.calcTextSize(this.initialTexts[i]).x);
        }
        labelWidth += 20f;
        valueWidth += 20f;

        this.width = labelWidth + valueWidth + sprite.width() + 2*MARGIN;
        this.height = MARGIN + LABELS.length*(rowHeight + MARGIN);
        float drawWidth = Math.max(this.width, minWidth);
        float drawHeight = Math.max(this.height, minHeight);
        float startX = ImGui.getCursorScreenPosX();
        float startY = ImGui.getCursorScreenPosY();
        ImDrawList drawList = ImGui.getWindowDrawList();

        drawList.addRectFilled(startX, startY, startX + drawWidth, startY + drawHeight, PANEL_BACKGROUND);

        ImGui.pushStyleColor(ImGuiCol.Text, WHITE);
        ImGui.pushStyleColor(ImGuiCol.FrameBg, 0);
        ImGui.pushStyleColor(ImGuiCol.FrameBgHovered, 0);
        ImGui.pushStyleColor(ImGuiCol.FrameBgActive, 0);
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0f, 0f);
        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 0f);

        for(int i=0; i < LABELS.length; i++){
            float rowY = startY + MARGIN + i*(rowHeight + MARGIN);

            ImGui.setCursorScreenPos(startX + MARGIN, rowY);
            ImGui.textUnformatted(LABELS[i]);

            ImGui.setCursorScreenPos(startX + MARGIN + labelWidth, rowY);
            if(i == 0){
                if(ImGui.invisibleButton("##shuffle", valueWidth, rowHeight)){
                    this.shuffle = !this.shuffle;
                    committed = true;
                }
                drawList.addText(startX + MARGIN + labelWidth, rowY, WHITE, Boolean.toString(this.shuffle));
            }else{
                ImGui.setNextItemWidth(Math.max(valueWidth, drawWidth - this.width + valueWidth));
                committed |= ImGui.inputText("##stat" + i, this.fields[i], ImGuiInputTextFlags.CharsDecimal | ImGuiInputTextFlags.EnterReturnsTrue);
                committed |= ImGui.isItemDeactivatedAfterEdit();
            }
        }

        ImGui.popStyleVar(2);
        ImGui.popStyleColor(4);
        ImGui.popFont();

        float spriteX = startX + drawWidth - sprite.width();
        float spriteY = startY + (drawHeight - sprite.height())/2f;
        drawList.addImage(sprite.id(), spriteX, spriteY, spriteX + sprite.width(), spriteY + sprite.height());

        ImGui.setCursorScreenPos(startX, startY);
        ImGui.dummy(drawWidth, drawHeight);
        return committed;
    }

    /** Writes the buffers to the wand as one undo step; does nothing if no value changed. */
    void apply(WandEditorState state, UndoManager undoManager){
        Wand wand = state.getWand();

        int nbDraw = parseInt(1, wand.getNbDraw());
        int castDelay = parseInt(2, wand.getCastDelay());
        int rechargeTime = parseInt(3, wand.getRechargeTime());
        int maxMana = parseInt(4, wand.getMaxMana());
        int regenMana = parseInt(5, wand.getRegenMana());
        int nbSlot = parseInt(6, wand.getNbSlot());
        double spread = parseDouble(7, wand.getSpread());
        double speed = parseDouble(8, wand.getSpeed());

        boolean slotChanged = nbSlot != wand.getNbSlot();
        boolean changed = slotChanged || this.shuffle != wand.getShuffle() || nbDraw != wand.getNbDraw()
            || castDelay != wand.getCastDelay() || rechargeTime != wand.getRechargeTime() || maxMana != wand.getMaxMana()
            || regenMana != wand.getRegenMana() || spread != wand.getSpread() || speed != wand.getSpeed();
        if(!changed){
            return;
        }

        undoManager.push(wand);
        wand.setShuffle(this.shuffle, false);
        wand.setNbDraw(nbDraw, false);
        wand.setCastDelay(castDelay, false);
        wand.setRechargeTime(rechargeTime, false);
        wand.setMaxMana(maxMana, false);
        wand.setRegenMana(regenMana, false);
        wand.setNbSlot(nbSlot, false);
        wand.setSpread(spread, false);
        wand.setSpeed(speed, false);

        if(slotChanged){
            wand.resetAll();
            state.onSlotCountChanged(wand.getNbSlot());
        }else{
            wand.reset();
        }
        state.markDirty();
    }

    private int parseInt(int field, int fallback){
        String text = this.fields[field].get().trim();
        int value;
        if(text.isEmpty()){
            value = 0;
        }else{
            try{
                value = Integer.parseInt(text);
            }catch(NumberFormatException e){
                return fallback;
            }
        }
        return UNSIGNED[field] ? Math.max(value, 0) : value;
    }

    private double parseDouble(int field, double fallback){
        String text = this.fields[field].get().trim();
        double value;
        if(text.isEmpty()){
            value = 0.0;
        }else{
            try{
                value = Double.parseDouble(text);
            }catch(NumberFormatException e){
                return fallback;
            }
        }
        return UNSIGNED[field] ? Math.max(value, 0.0) : value;
    }
}
