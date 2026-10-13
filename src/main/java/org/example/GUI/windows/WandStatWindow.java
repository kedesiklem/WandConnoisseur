package org.example.GUI.windows;

import imgui.type.ImBoolean;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandEditorState;

/**
 * Dockable, always-visible version of the wand stats dialog: the same {@link WandStatPanel},
 * but each edit is applied as soon as it is committed (field loses focus, Enter, shuffle click),
 * each as its own undo step. Hidden by default; View menu toggles it.
 */
public final class WandStatWindow{
    public static final String TITLE = "Wand Stats";

    private final WandEditorState state;
    private final UndoManager undoManager;
    private final WandStatPanel panel = new WandStatPanel("wandStatWindowSprite");
    private final ImBoolean open = new ImBoolean(false);

    public WandStatWindow(WandEditorState state, UndoManager undoManager){
        this.state = state;
        this.undoManager = undoManager;
        syncFromWand();
    }

    public ImBoolean getOpen(){
        return this.open;
    }

    /** Called from MainWindow whenever the wand changed (edits, undo/redo, build loads). */
    public void syncFromWand(){
        this.panel.load(this.state.getWand());
    }

    public void render(){
        if(!DockPanel.begin(TITLE, this.open)){
            return;
        }

        if(this.panel.render(DockPanel.contentWidth(), DockPanel.contentHeight())){
            this.panel.apply(this.state, this.undoManager);
        }

        DockPanel.end();
    }
}
