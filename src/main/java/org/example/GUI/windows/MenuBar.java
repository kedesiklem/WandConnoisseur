package org.example.GUI.windows;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.example.GUI.state.UndoManager;
import org.example.GUI.state.WandBuildManager;
import org.example.GUI.state.WandEditorState;
import org.example.main.Global;
import org.example.main.Wand;

import java.util.List;
import java.util.Map;

/** The main window's menu bar: File (builds, quick clear, export), Edit (undo/redo, wand stats), Categories, View (panels, layout). */
public final class MenuBar{
    private static final String SAVE_POPUP = "Save wand build";

    private final WandEditorState state;
    private final UndoManager undoManager;
    private final WandBuildManager buildManager;
    private final CategoryManagerWindow categoryManagerWindow;
    private final WandStatDialog statDialog;
    private final Map<String, ImBoolean> panels;
    private final Runnable resetLayout;
    private final ImString saveNameBuffer = new ImString(64);

    public MenuBar(WandEditorState state, UndoManager undoManager, WandBuildManager buildManager,
                    CategoryManagerWindow categoryManagerWindow, WandStatDialog statDialog,
                    Map<String, ImBoolean> panels, Runnable resetLayout){
        this.state = state;
        this.undoManager = undoManager;
        this.buildManager = buildManager;
        this.categoryManagerWindow = categoryManagerWindow;
        this.statDialog = statDialog;
        this.panels = panels;
        this.resetLayout = resetLayout;
    }

    public void render(){
        Wand wand = this.state.getWand();

        if(ImGui.beginMenuBar()){
            if(ImGui.beginMenu("File")){
                if(ImGui.menuItem("Save build...")){
                    this.saveNameBuffer.set("");
                    ImGui.openPopup(SAVE_POPUP);
                }
                if(ImGui.beginMenu("Load build", !this.buildManager.listBuilds().isEmpty())){
                    for(String name : this.buildManager.listBuilds()){
                        if(ImGui.menuItem(name)){
                            this.undoManager.push(wand);
                            if(this.buildManager.load(name, wand)){
                                this.state.onSlotCountChanged(wand.getNbSlot());
                                this.state.clearSelection();
                                this.state.markDirty();
                            }
                        }
                    }
                    ImGui.endMenu();
                }
                if(ImGui.beginMenu("Delete build", !this.buildManager.listBuilds().isEmpty())){
                    for(String name : this.buildManager.listBuilds()){
                        if(ImGui.menuItem(name)){
                            this.buildManager.delete(name);
                        }
                    }
                    ImGui.endMenu();
                }
                ImGui.separator();
                if(ImGui.menuItem("Quick clear wand")){
                    this.undoManager.push(wand);
                    wand.removeAllSpell();
                    this.state.clearSelection();
                    this.state.markDirty();
                }
                ImGui.separator();
                if(ImGui.beginMenu("Export wand image")){
                    if(ImGui.menuItem("as PNG")){
                        wand.getSpellsImage().saveAsPNG(Global.getPathOutput() + "SpellImage.png");
                    }
                    if(ImGui.menuItem("as SVG")){
                        wand.getSpellsImage().saveAsSVG(Global.getPathOutput() + "SpellImage.svg", true);
                    }
                    ImGui.endMenu();
                }
                if(ImGui.beginMenu("Export wand stat image")){
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

            if(ImGui.beginMenu("Edit")){
                if(ImGui.menuItem("Undo", "Ctrl+Z", false, this.undoManager.canUndo())){
                    if(this.undoManager.undo(wand)){
                        this.state.markDirty();
                    }
                }
                if(ImGui.menuItem("Redo", "Ctrl+Y", false, this.undoManager.canRedo())){
                    if(this.undoManager.redo(wand)){
                        this.state.markDirty();
                    }
                }
                ImGui.separator();
                if(ImGui.menuItem("Wand stats...")){
                    this.statDialog.open();
                }
                ImGui.endMenu();
            }

            if(ImGui.beginMenu("Categories")){
                if(ImGui.menuItem("Manage categories...")){
                    this.categoryManagerWindow.open();
                }
                ImGui.endMenu();
            }

            if(ImGui.beginMenu("View")){
                for(Map.Entry<String, ImBoolean> panel : this.panels.entrySet()){
                    ImGui.menuItem(panel.getKey(), "", panel.getValue());
                }
                ImGui.separator();
                ImGui.menuItem("Edit layout (show panel tabs)", "", DockPanel.SHOW_LAYOUT_TABS);
                if(ImGui.menuItem("Reset layout")){
                    this.resetLayout.run();
                }
                ImGui.endMenu();
            }

            ImGui.endMenuBar();
        }

        renderSavePopup(wand);
    }

    private void renderSavePopup(Wand wand){
        if(ImGui.beginPopupModal(SAVE_POPUP)){
            ImGui.inputText("Name", this.saveNameBuffer);

            String name = this.saveNameBuffer.get().trim();
            if(!name.isEmpty() && this.buildManager.exists(name)){
                ImGui.textColored(1f, 0.6f, 0.2f, 1f, "A build named \"" + name + "\" already exists and will be overwritten.");
            }

            boolean canSave = !name.isEmpty();
            if(!canSave){
                ImGui.beginDisabled();
            }
            if(ImGui.button("Save") && canSave){
                this.buildManager.save(name, wand);
                ImGui.closeCurrentPopup();
            }
            if(!canSave){
                ImGui.endDisabled();
            }

            ImGui.sameLine();
            if(ImGui.button("Cancel")){
                ImGui.closeCurrentPopup();
            }

            ImGui.endPopup();
        }
    }
}
