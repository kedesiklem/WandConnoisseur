package org.example.GUI.windows;

import imgui.type.ImBoolean;
import org.example.GUI.state.WandEditorState;

/** Panel showing the wand's cast-state image, refreshed whenever the wand changes. */
public final class CastStateWindow{
    public static final String TITLE = "Cast State";

    private final WandEditorState state;
    private final ImageWindow panel = new ImageWindow(TITLE, TITLE);
    private final ImBoolean open = new ImBoolean(true);

    public CastStateWindow(WandEditorState state){
        this.state = state;
        refresh();
    }

    /** Called from MainWindow whenever the wand's dirty flag is consumed. */
    public void refresh(){
        this.panel.refresh(this.state.getWand().getCastStateImage(true));
    }

    public ImBoolean getOpen(){
        return this.open;
    }

    /** Draws the dockable panel. */
    public void render(){
        if(DockPanel.begin(TITLE, this.open)){
            this.panel.render(DockPanel.contentX(), DockPanel.contentY(), DockPanel.contentWidth(), DockPanel.contentHeight());
            DockPanel.end();
        }
    }
}
