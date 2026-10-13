package org.example.GUI;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiPopupFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import org.example.GUI.state.SpellCategoryManager;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandBuildManager;
import org.example.GUI.state.WandEditorState;
import org.example.GUI.windows.CastStateWindow;
import org.example.GUI.windows.CategoryManagerWindow;
import org.example.GUI.windows.DockPanel;
import org.example.GUI.windows.FlowchartWindow;
import org.example.GUI.windows.MenuBar;
import org.example.GUI.windows.SpellPickerWindow;
import org.example.GUI.windows.WandStatDialog;
import org.example.GUI.windows.WandStatWindow;
import org.example.GUI.windows.WandWindow;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.Spell;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Entry point and main loop for the local GUI, drawn with Dear ImGui. Every panel (spell picker,
 * wand, flowchart, cast state, wand stats, categories) is a dockable window that can be
 * rearranged, tabbed together, closed (View menu reopens it) or dragged out of the main window
 * into its own OS window. The default arrangement reproduces the old Swing window: picker on the
 * left, wand on top right, flowchart and cast state below it, 10px green gaps. The arrangement is
 * saved to {@link #LAYOUT_INI} on exit.
 *
 * The loop is event driven: it sleeps in {@code glfwWaitEvents} until input arrives (on any of its
 * OS windows), then renders a few frames so ImGui can settle (hover state, popups opening on the
 * next frame, docking previews) and goes back to sleep. It only keeps rendering while a mouse
 * button is held, and wakes twice a second while a text field is focused so the caret blinks.
 */
public class MainWindow{
    private static final String LAYOUT_INI = "imgui_layout.ini";
    private static final int GAP = 10;
    private static final int SLOT_SIZE = 20;
    private static final int SLOT_MARGIN = 3;
    private static final int BACKGROUND = imgui.ImColor.rgba(0, 255, 0, 255);
    /** Frames rendered after the last event: ImGui reacts to some inputs one or two frames late. */
    private static final int SETTLE_FRAMES = 3;
    private static final double CARET_BLINK_SECONDS = 0.5;
    /** Gap ImGui 1.88 leaves between two split dock nodes (DOCKING_SPLITTER_SIZE). */
    private static final int DOCK_SPLITTER = 2;

    private static MainWindow instance;

    private long windowHandle;
    private final ImGuiImplGlfw imGuiGlfw = new ActivityTrackingGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private final Set<Long> platformWindowsWithRefresh = new HashSet<>();
    private boolean running = false;
    private int framesToRender = SETTLE_FRAMES;
    private boolean layoutBuilt = false;

    // old Swing starting sizes of each panel, used for the initial window size and the default dock layout
    private float pickerWidth;
    private float wandWidth;
    private float wandHeight;
    private float imageHeight;
    private float contentWidth;
    private float contentHeight;

    private WandEditorState state;
    private UndoManager undoManager;
    private SpellCategoryManager categoryManager;
    private WandBuildManager buildManager;

    private SpellPickerWindow spellPickerWindow;
    private WandWindow wandWindow;
    private WandStatDialog statDialog;
    private WandStatWindow statWindow;
    private FlowchartWindow flowchartWindow;
    private CastStateWindow castStateWindow;
    private CategoryManagerWindow categoryManagerWindow;
    private MenuBar menuBar;

    private MainWindow(){}

    public static MainWindow getInstance(){
        if(instance == null){
            instance = new MainWindow();
        }
        return instance;
    }

    public void run(){
        if(this.running){
            return;
        }
        this.running = true;

        computeStartLayout();
        initGlfw();
        initImGui();
        initState();
        showWindow();

        while(!glfwWindowShouldClose(this.windowHandle)){
            waitForWork();
            renderFrame();
        }

        ImGui.saveIniSettingsToDisk(LAYOUT_INI);
        TextureCache.clear();
        this.imGuiGl3.shutdown();
        this.imGuiGlfw.shutdown();
        ImGui.destroyContext();

        Callbacks.glfwFreeCallbacks(this.windowHandle);
        glfwDestroyWindow(this.windowHandle);
        glfwTerminate();
        GLFWErrorCallback previous = glfwSetErrorCallback(null);
        if(previous != null){
            previous.free();
        }

        this.running = false;
    }

    /** Schedules a few frames; called from every input callback and whenever displayed data changes. */
    private void requestFrames(){
        this.framesToRender = SETTLE_FRAMES;
    }

    private void waitForWork(){
        if(this.framesToRender > 0){
            glfwPollEvents();
            return;
        }

        if(ImGui.getIO().getWantTextInput()){
            glfwWaitEventsTimeout(CARET_BLINK_SECONDS);
        }else{
            glfwWaitEvents();
        }
        this.framesToRender = Math.max(this.framesToRender, 1);
    }

    private void renderFrame(){
        this.imGuiGlfw.newFrame();
        this.imGuiGl3.newFrame();
        ImGui.newFrame();

        handleGlobalShortcuts();
        renderRoot();
        this.spellPickerWindow.render();
        this.wandWindow.render();
        this.flowchartWindow.render();
        this.castStateWindow.render();
        this.statWindow.render();
        this.categoryManagerWindow.render();
        this.wandWindow.removeIfDroppedOutside();
        DragPayloads.endFrame();

        ImGui.render();
        glClearColor(0f, 1f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);
        this.imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)){
            long backupContext = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupContext);
            watchPlatformWindows();
        }

        glfwSwapBuffers(this.windowHandle);

        // refreshed after the frame so this frame's draw lists never point at a deleted texture
        if(this.state.consumeDirty()){
            this.flowchartWindow.refresh();
            this.castStateWindow.refresh();
            this.statWindow.syncFromWand();
            requestFrames();
        }

        this.framesToRender--;
        if(ImGui.isAnyMouseDown()){
            this.framesToRender = Math.max(this.framesToRender, 1);
        }
    }

    /** Panels dragged out of the main window get their own GLFW window: redraw those too when the OS asks (uncovered, etc.). */
    private void watchPlatformWindows(){
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        for(int i=0; i < platformIO.getViewportsSize(); i++){
            long handle = platformIO.getViewports(i).getPlatformHandle();
            if(handle != 0 && handle != this.windowHandle && this.platformWindowsWithRefresh.add(handle)){
                glfwSetWindowRefreshCallback(handle, window -> requestFrames());
                glfwSetFramebufferSizeCallback(handle, (window, width, height) -> requestFrames());
            }
        }
    }

    private void computeStartLayout(){
        int s = Global.getScaleFactor();

        this.pickerWidth = 4*s*(SLOT_SIZE + SLOT_MARGIN + 2) + MetalScrollbar.WIDTH;
        float pickerHeight = s*Spell.SpellType.values().length*SLOT_SIZE;
        this.wandWidth = s*(6*SLOT_SIZE + 7*SLOT_MARGIN + 2) + MetalScrollbar.WIDTH;
        this.wandHeight = s*(2*SLOT_SIZE + 3*SLOT_MARGIN + 2);
        float imageWidth = (this.wandWidth - GAP)/2f;
        this.imageHeight = pickerHeight - this.wandHeight - GAP;
        this.contentWidth = GAP + this.pickerWidth + GAP + imageWidth + GAP + imageWidth + GAP;
        this.contentHeight = GAP + this.wandHeight + GAP + this.imageHeight + GAP;
    }

    private void initGlfw(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        this.windowHandle = glfwCreateWindow((int)this.contentWidth, (int)this.contentHeight, "Wand Connoisseur", 0, 0);
        if(this.windowHandle == 0){
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(this.windowHandle);
        glfwSwapInterval(1);
        GL.createCapabilities();

        // the ImGui backend installs its own input callbacks (see ActivityTrackingGlfw); these cover the rest
        glfwSetFramebufferSizeCallback(this.windowHandle, (window, width, height) -> requestFrames());
        glfwSetWindowRefreshCallback(this.windowHandle, window -> requestFrames());
        glfwSetWindowIconifyCallback(this.windowHandle, (window, iconified) -> requestFrames());
    }

    private void initImGui(){
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(LAYOUT_INI);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable | ImGuiConfigFlags.ViewportsEnable);
        this.layoutBuilt = new File(LAYOUT_INI).isFile();

        Fonts.load();
        SwingTheme.apply();

        this.imGuiGlfw.init(this.windowHandle, true);
        this.imGuiGl3.init("#version 150");
    }

    private void initState(){
        this.undoManager = new UndoManager();
        this.categoryManager = new SpellCategoryManager();
        this.buildManager = new WandBuildManager();
        this.state = new WandEditorState(new Wand(false, 1, 0, 0, 1000000, 1000000, 26, 0.0, 1.0));

        this.statDialog = new WandStatDialog(this.state, this.undoManager);
        this.statWindow = new WandStatWindow(this.state, this.undoManager);
        this.categoryManagerWindow = new CategoryManagerWindow(this.categoryManager);
        this.spellPickerWindow = new SpellPickerWindow(this.state, this.undoManager, this.categoryManager, this.categoryManagerWindow);
        this.wandWindow = new WandWindow(this.state, this.undoManager, this.statDialog);
        this.flowchartWindow = new FlowchartWindow(this.state);
        this.castStateWindow = new CastStateWindow(this.state);

        Map<String, ImBoolean> panels = new LinkedHashMap<>();
        panels.put(SpellPickerWindow.TITLE, this.spellPickerWindow.getOpen());
        panels.put(WandWindow.TITLE, this.wandWindow.getOpen());
        panels.put(WandStatWindow.TITLE, this.statWindow.getOpen());
        panels.put(FlowchartWindow.TITLE, this.flowchartWindow.getOpen());
        panels.put(CastStateWindow.TITLE, this.castStateWindow.getOpen());
        panels.put(CategoryManagerWindow.TITLE, this.categoryManagerWindow.getOpen());
        this.menuBar = new MenuBar(this.state, this.undoManager, this.buildManager, this.categoryManagerWindow, this.statDialog,
            panels, this::resetLayout);
    }

    /** Sizes the window to the old starting layout plus menu bar (and tab bars when shown), centers it and shows it. */
    private void showWindow(){
        int width = (int)Math.ceil(this.contentWidth) + DOCK_SPLITTER;
        int tabBars = DockPanel.SHOW_LAYOUT_TABS.get() ? 2 : 0; // right column stacks two panels
        int height = (int)Math.ceil(this.contentHeight + (1 + tabBars)*frameHeight()) + DOCK_SPLITTER;

        glfwSetWindowSize(this.windowHandle, width, height);

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if(videoMode != null){
            glfwSetWindowPos(this.windowHandle, Math.max((videoMode.width() - width)/2, 0), Math.max((videoMode.height() - height)/2, 0));
        }

        glfwShowWindow(this.windowHandle);
        requestFrames();
    }

    /** Height of a menu bar or a dock tab bar with the UI font. */
    private static float frameHeight(){
        return Fonts.uiFontSize() + 2*ImGui.getStyle().getFramePaddingY();
    }

    private void handleGlobalShortcuts(){
        ImGuiIO io = ImGui.getIO();
        if(io.getWantTextInput() || ImGui.isPopupOpen("", ImGuiPopupFlags.AnyPopupId)){
            return;
        }

        if(io.getKeyCtrl() && ImGui.isKeyPressed(ImGuiKey.Z)){
            if(this.undoManager.undo(this.state.getWand())){
                this.state.markDirty();
            }
        }else if(io.getKeyCtrl() && ImGui.isKeyPressed(ImGuiKey.Y)){
            if(this.undoManager.redo(this.state.getWand())){
                this.state.markDirty();
            }
        }
    }

    /** Full-window host: menu bar and the dockspace every panel docks into, on the old green background. */
    private void renderRoot(){
        ImGuiViewport viewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY());
        ImGui.setNextWindowSize(viewport.getSizeX(), viewport.getSizeY());
        ImGui.setNextWindowViewport(viewport.getID());

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, GAP/2f, GAP/2f);
        ImGui.pushStyleColor(ImGuiCol.WindowBg, BACKGROUND);

        int flags = ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoSavedSettings
            | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoScrollWithMouse
            | ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.MenuBar;
        ImGui.begin("##root", flags);
        ImGui.popStyleColor();
        ImGui.popStyleVar(3);

        this.menuBar.render();

        int dockspaceId = ImGui.getID("MainDockspace");
        if(!this.layoutBuilt){
            buildDefaultLayout(dockspaceId, ImGui.getContentRegionAvailX(), ImGui.getContentRegionAvailY());
            this.layoutBuilt = true;
        }
        // the 2px splitters between docked panels blend into the green gaps unless the layout is being edited
        boolean hideSplitters = !DockPanel.SHOW_LAYOUT_TABS.get();
        if(hideSplitters){
            ImGui.pushStyleColor(ImGuiCol.Separator, BACKGROUND);
            ImGui.pushStyleColor(ImGuiCol.SeparatorHovered, BACKGROUND);
            ImGui.pushStyleColor(ImGuiCol.SeparatorActive, BACKGROUND);
        }
        ImGui.dockSpace(dockspaceId, 0f, 0f, ImGuiDockNodeFlags.None);
        if(hideSplitters){
            ImGui.popStyleColor(3);
        }

        this.statDialog.render();

        ImGui.end();
    }

    /** View > Reset layout: reopen the default panels and rebuild the old arrangement next frame. */
    private void resetLayout(){
        this.spellPickerWindow.getOpen().set(true);
        this.wandWindow.getOpen().set(true);
        this.flowchartWindow.getOpen().set(true);
        this.castStateWindow.getOpen().set(true);
        this.statWindow.getOpen().set(false);
        this.layoutBuilt = false;
    }

    /**
     * Old Swing arrangement as dock splits: each docked panel has a 5px green padding, so a panel's
     * dock node is its old size plus 10px (plus its tab bar unless tab bars are hidden).
     */
    private void buildDefaultLayout(int dockspaceId, float width, float height){
        float tabBar = DockPanel.SHOW_LAYOUT_TABS.get() ? frameHeight() : 0f;
        float leftWidth = this.pickerWidth + GAP;
        float topHeight = this.wandHeight + GAP + tabBar;

        imgui.internal.ImGui.dockBuilderRemoveNode(dockspaceId);
        imgui.internal.ImGui.dockBuilderAddNode(dockspaceId, imgui.internal.flag.ImGuiDockNodeFlags.DockSpace);
        imgui.internal.ImGui.dockBuilderSetNodeSize(dockspaceId, width, height);

        ImInt leftId = new ImInt();
        ImInt rightId = new ImInt();
        imgui.internal.ImGui.dockBuilderSplitNode(dockspaceId, ImGuiDir.Left, leftWidth/width, leftId, rightId);

        ImInt wandId = new ImInt();
        ImInt bottomId = new ImInt();
        imgui.internal.ImGui.dockBuilderSplitNode(rightId.get(), ImGuiDir.Up, topHeight/height, wandId, bottomId);

        ImInt flowchartId = new ImInt();
        ImInt castStateId = new ImInt();
        imgui.internal.ImGui.dockBuilderSplitNode(bottomId.get(), ImGuiDir.Left, 0.5f, flowchartId, castStateId);

        imgui.internal.ImGui.dockBuilderDockWindow(SpellPickerWindow.TITLE, leftId.get());
        imgui.internal.ImGui.dockBuilderDockWindow(WandWindow.TITLE, wandId.get());
        imgui.internal.ImGui.dockBuilderDockWindow(FlowchartWindow.TITLE, flowchartId.get());
        imgui.internal.ImGui.dockBuilderDockWindow(CastStateWindow.TITLE, castStateId.get());
        imgui.internal.ImGui.dockBuilderFinish(dockspaceId);
    }

    /**
     * ImGui's GLFW backend, also scheduling frames for every input event it receives (main and pop-out windows),
     * and fixing its shutdown: it destroys every mouse cursor it tried to create, but cursors the platform lacks
     * (e.g. resize cursors before GLFW 3.4) are null handles, which LWJGL's glfwDestroyCursor rejects with a
     * NullPointerException, crashing the app on quit.
     */
    private final class ActivityTrackingGlfw extends ImGuiImplGlfw{
        @Override
        protected Data newData(){
            return new SafeShutdownData();
        }

        @Override
        public void shutdown(){
            if(this.data instanceof SafeShutdownData safeData){
                safeData.replaceMissingCursors();
            }
            super.shutdown();
        }

        private static final String PUNCTUATION_NAMES = "`-=[]\\,;'./";
        private static final int[] PUNCTUATION_KEYS = {
            GLFW_KEY_GRAVE_ACCENT, GLFW_KEY_MINUS, GLFW_KEY_EQUAL, GLFW_KEY_LEFT_BRACKET, GLFW_KEY_RIGHT_BRACKET,
            GLFW_KEY_BACKSLASH, GLFW_KEY_COMMA, GLFW_KEY_SEMICOLON, GLFW_KEY_APOSTROPHE, GLFW_KEY_PERIOD, GLFW_KEY_SLASH
        };

        /**
         * Maps GLFW's physical (US-position) key codes to what the user's layout prints on the key, so
         * Ctrl+Z is the key labelled Z on AZERTY too. Replaces the backend's version, a C port whose
         * {@code name.length() > 2 && name.charAt(1) == 0} test can never match a Java string, so it never translated anything.
         */
        @Override
        protected int translateUntranslatedKey(int key, int scancode){
            if(key >= GLFW_KEY_KP_0 && key <= GLFW_KEY_KP_EQUAL){
                return key;
            }
            String name = glfwGetKeyName(key, scancode);
            if(name == null || name.length() != 1){
                return key;
            }

            char c = name.charAt(0);
            if(c >= '0' && c <= '9'){
                return GLFW_KEY_0 + (c - '0');
            }
            if(c >= 'a' && c <= 'z'){
                return GLFW_KEY_A + (c - 'a');
            }
            if(c >= 'A' && c <= 'Z'){
                return GLFW_KEY_A + (c - 'A');
            }
            int punctuation = PUNCTUATION_NAMES.indexOf(c);
            return punctuation >= 0 ? PUNCTUATION_KEYS[punctuation] : key;
        }

        @Override
        public void mouseButtonCallback(long windowId, int button, int action, int mods){
            super.mouseButtonCallback(windowId, button, action, mods);
            requestFrames();
        }

        @Override
        public void scrollCallback(long windowId, double xOffset, double yOffset){
            super.scrollCallback(windowId, xOffset, yOffset);
            requestFrames();
        }

        @Override
        public void keyCallback(long windowId, int key, int scancode, int action, int mods){
            super.keyCallback(windowId, key, scancode, action, mods);
            requestFrames();
        }

        @Override
        public void charCallback(long windowId, int c){
            super.charCallback(windowId, c);
            requestFrames();
        }

        @Override
        public void cursorPosCallback(long windowId, double x, double y){
            super.cursorPosCallback(windowId, x, y);
            requestFrames();
        }

        @Override
        public void cursorEnterCallback(long windowId, boolean entered){
            super.cursorEnterCallback(windowId, entered);
            requestFrames();
        }

        @Override
        public void windowFocusCallback(long windowId, boolean focused){
            super.windowFocusCallback(windowId, focused);
            requestFrames();
        }

        /** Nested here because the backend's Data type is only visible to its subclasses. */
        private final class SafeShutdownData extends Data{
            /** Swaps null cursor handles for throwaway arrow cursors so the backend's shutdown can destroy them all. */
            void replaceMissingCursors(){
                if(this.mouseCursors == null){
                    return;
                }
                for(int i=0; i < this.mouseCursors.length; i++){
                    if(this.mouseCursors[i] == 0){
                        this.mouseCursors[i] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
                    }
                }
            }
        }
    }

}
