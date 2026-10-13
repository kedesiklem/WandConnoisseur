package org.example.GUI.windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import org.example.GUI.MetalScrollbar;
import org.example.main.Global;

import java.util.HashMap;
import java.util.Map;

/**
 * Scrollable grid of spell slots laid out exactly like the old Swing {@code SpellHolderComponent}:
 * as many 20px (times scale) columns as fit, leftover width spread evenly between them, a
 * scale-thick border around the viewport (with an optional hole on the left side, used by the
 * spell picker so the active tab merges into its content) and a Metal scrollbar outside that border.
 * Scrolling is handled here rather than by ImGui so the old scrollbar look and wheel step are kept.
 *
 * Usage per frame: {@link #begin}, then {@link #slotX}/{@link #slotY} for each slot, then
 * {@link #endSlots}, optionally popups that belong to the grid, then {@link #endChild}.
 */
final class SlotGrid{
    static final int SLOT_SIZE = 20;
    static final int MARGIN = 3;
    private static final float UNIT_INCREMENT = 30f; // old JScrollPane unit increment
    private static final float WHEEL_STEP = 3*UNIT_INCREMENT; // 3 units per wheel notch, like Swing
    private static final int BACKGROUND = imgui.ImColor.rgba(0, 0, 0, 255);

    private final int borderColor;
    private final int scale = Global.getScaleFactor();
    private final MetalScrollbar scrollbar = new MetalScrollbar();
    private final Map<String, Float> scrollById = new HashMap<>();

    private String id;
    private float windowX;
    private float windowY;
    private float width;
    private float height;
    private float scrollbarWidth;
    private float scrollY;
    private float freeWidth;
    private int nbX;

    SlotGrid(int borderColor){
        this.borderColor = borderColor;
    }

    int slotPixels(){
        return this.scale*SLOT_SIZE;
    }

    /** Opens the grid's child window at screen position (x, y) and computes the slot layout for {@code count} slots. */
    void begin(String id, float x, float y, float width, float height, int count){
        int s = this.scale;
        float border = 2f*s;

        this.id = id;
        this.width = width;
        this.height = height;
        this.scrollbarWidth = 0f;
        this.nbX = columns(width - border);
        int nbY = (count + this.nbX - 1)/this.nbX;
        float viewHeight = s*(nbY*SLOT_SIZE + (nbY + 1)*MARGIN);

        if(viewHeight + border > height){
            this.scrollbarWidth = MetalScrollbar.WIDTH;
            this.nbX = columns(width - MetalScrollbar.WIDTH - border);
            nbY = (count + this.nbX - 1)/this.nbX;
            viewHeight = s*(nbY*SLOT_SIZE + (nbY + 1)*MARGIN);
        }

        this.freeWidth = width - (s*SLOT_SIZE*this.nbX + this.scrollbarWidth + border);

        ImGui.setCursorScreenPos(x, y);
        ImGui.pushStyleColor(ImGuiCol.ChildBg, BACKGROUND);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
        ImGui.beginChild(id, width, height, false, ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoNav);
        ImGui.popStyleVar();
        ImGui.popStyleColor();

        this.windowX = ImGui.getWindowPosX();
        this.windowY = ImGui.getWindowPosY();

        float extent = height - border;
        float maxScroll = Math.max(viewHeight - extent, 0f);
        float scroll = this.scrollById.getOrDefault(id, 0f);
        if(this.scrollbarWidth > 0 && ImGui.isWindowHovered() && ImGui.getIO().getMouseWheel() != 0f){
            scroll -= ImGui.getIO().getMouseWheel()*WHEEL_STEP;
        }
        scroll = Math.max(0f, Math.min(scroll, maxScroll));
        if(this.scrollbarWidth > 0){
            scroll = this.scrollbar.render("##scrollbar", this.windowX + width - MetalScrollbar.WIDTH, this.windowY, height, scroll, extent, viewHeight, UNIT_INCREMENT);
        }
        this.scrollY = scroll;

        ImGui.pushClipRect(this.windowX + s, this.windowY + s, this.windowX + width - this.scrollbarWidth - s, this.windowY + height - s, true);
    }

    private int columns(float availableWidth){
        int s = this.scale;
        return Math.max((int)Math.floor((availableWidth - s*MARGIN)/(double)(s*(MARGIN + SLOT_SIZE))), 1);
    }

    float slotX(int index){
        int column = index % this.nbX;
        return this.windowX + this.scale + Math.round(column*slotPixels() + this.freeWidth*(column + 1)/(this.nbX + 1));
    }

    float slotY(int index){
        int row = index/this.nbX;
        return this.windowY + this.scale + this.scale*(row*SLOT_SIZE + (row + 1)*MARGIN) - (int)this.scrollY;
    }

    /** Closes the slot clip area and draws the border; {@code holeFrom}/{@code holeTo} (relative to the grid top) leave a gap in the left side, pass -1 for none. */
    void endSlots(float holeFrom, float holeTo){
        ImGui.popClipRect();
        this.scrollById.put(this.id, this.scrollY);

        int s = this.scale;
        float x0 = this.windowX;
        float y0 = this.windowY;
        float x1 = x0 + this.width - this.scrollbarWidth;
        float y1 = y0 + this.height;
        ImDrawList drawList = ImGui.getWindowDrawList();
        holeFrom = Math.max(holeFrom, 0f);
        holeTo = Math.min(holeTo, this.height);

        drawList.addRectFilled(x0, y0, x1, y0 + s, this.borderColor);
        drawList.addRectFilled(x0, y1 - s, x1, y1, this.borderColor);
        drawList.addRectFilled(x1 - s, y0, x1, y1, this.borderColor);
        if(holeFrom >= 0 && holeTo > holeFrom){
            drawList.addRectFilled(x0, y0, x0 + s, y0 + holeFrom, this.borderColor);
            drawList.addRectFilled(x0, y0 + holeTo, x0 + s, y1, this.borderColor);
        }else{
            drawList.addRectFilled(x0, y0, x0 + s, y1, this.borderColor);
        }
    }

    void endChild(){
        ImGui.endChild();
    }
}
