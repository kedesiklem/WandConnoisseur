package org.example.GUI.windows;

import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandEditorState;

/**
 * The old "wand stats" modal dialog ({@code WandStatDialog}): the {@link WandStatPanel} with
 * OK / Cancel below. Nothing is applied until OK (or Enter); an accepted edit is one undo step.
 */
public final class WandStatDialog{
    private static final String TITLE = "wand stats";
    private static final float BUTTON_HEIGHT = 30f;

    private final WandEditorState state;
    private final UndoManager undoManager;
    private final WandStatPanel panel = new WandStatPanel("wandStatDialogSprite");
    private boolean openRequested = false;

    public WandStatDialog(WandEditorState state, UndoManager undoManager){
        this.state = state;
        this.undoManager = undoManager;
    }

    public void open(){
        this.panel.load(this.state.getWand());
        this.openRequested = true;
    }

    /** Must be called every frame from the same place in the ID stack (popups are keyed by it). */
    public void render(){
        if(this.openRequested){
            ImGui.openPopup(TITLE);
            this.openRequested = false;
        }

        ImGui.setNextWindowPos(ImGui.getMainViewport().getCenterX(), ImGui.getMainViewport().getCenterY(), ImGuiCond.Appearing, 0.5f, 0.5f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, WandStatPanel.MARGIN, WandStatPanel.MARGIN);
        boolean open = ImGui.beginPopupModal(TITLE, ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings);
        ImGui.popStyleVar();
        if(!open){
            return;
        }

        this.panel.render(0f, 0f);

        float buttonWidth = (this.panel.getWidth() - WandStatPanel.MARGIN)/2f;
        ImGui.setCursorPosY(ImGui.getCursorPosY() + WandStatPanel.MARGIN - ImGui.getStyle().getItemSpacingY());
        boolean ok = ImGui.button("OK", buttonWidth, BUTTON_HEIGHT);
        ImGui.sameLine(0f, WandStatPanel.MARGIN);
        boolean cancel = ImGui.button("Cancel", buttonWidth, BUTTON_HEIGHT);

        ok |= ImGui.isKeyPressed(ImGuiKey.Enter) || ImGui.isKeyPressed(ImGuiKey.KeypadEnter);
        cancel |= ImGui.isKeyPressed(ImGuiKey.Escape);

        if(ok){
            this.panel.apply(this.state, this.undoManager);
            ImGui.closeCurrentPopup();
        }else if(cancel){
            ImGui.closeCurrentPopup();
        }

        ImGui.endPopup();
    }
}
