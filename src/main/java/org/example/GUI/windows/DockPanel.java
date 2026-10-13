package org.example.GUI.windows;

import imgui.ImGui;
import imgui.ImGuiWindowClass;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.flag.ImGuiDockNodeFlags;
import imgui.type.ImBoolean;

/**
 * Common frame of the dockable panels: a closable ImGui window whose background is the old
 * green and whose padding is half the old 10px gap, so docked side by side the panels show the
 * same 10px green gaps the Swing window had. The panel content is drawn by the caller in the
 * rectangle returned by {@link #contentX()}/{@link #contentY()}/{@link #contentWidth()}/{@link #contentHeight()}.
 *
 * Docked panels have no tab bar unless {@link #SHOW_LAYOUT_TABS} is on (View menu), so by default
 * docking leaves the old look untouched; turning it on shows the tabs used to drag, tab and undock panels.
 */
public final class DockPanel{
    static final float HALF_GAP = 5f;
    static final int BACKGROUND = imgui.ImColor.rgba(0, 255, 0, 255);
    public static final ImBoolean SHOW_LAYOUT_TABS = new ImBoolean(false);

    private static final ImGuiWindowClass HIDDEN_TABS = new ImGuiWindowClass();
    private static final ImGuiWindowClass VISIBLE_TABS = new ImGuiWindowClass();
    static{
        HIDDEN_TABS.setDockNodeFlagsOverrideSet(ImGuiDockNodeFlags.NoTabBar);
    }

    private DockPanel(){}

    /** Begins the panel window; when it returns true, draw the content then call {@link #end()}. When false, nothing to do. */
    static boolean begin(String title, ImBoolean open){
        if(!open.get()){
            return false;
        }

        // always set: a window keeps its last class when none is given
        ImGui.setNextWindowClass(SHOW_LAYOUT_TABS.get() ? VISIBLE_TABS : HIDDEN_TABS);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, HALF_GAP, HALF_GAP);
        ImGui.pushStyleColor(ImGuiCol.WindowBg, BACKGROUND);
        boolean visible = ImGui.begin(title, open, ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoCollapse);
        ImGui.popStyleColor();
        ImGui.popStyleVar();

        if(!visible){
            ImGui.end();
        }
        return visible;
    }

    static void end(){
        ImGui.end();
    }

    static float contentX(){
        return (float)Math.round(ImGui.getCursorScreenPosX());
    }

    static float contentY(){
        return (float)Math.round(ImGui.getCursorScreenPosY());
    }

    static float contentWidth(){
        return (float)Math.round(Math.max(ImGui.getContentRegionAvailX(), 1f));
    }

    static float contentHeight(){
        return (float)Math.round(Math.max(ImGui.getContentRegionAvailY(), 1f));
    }
}
