package org.example.GUI;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiMouseButton;

/**
 * Vertical scrollbar drawn pixel for pixel like Swing's Metal (Ocean theme) one the old GUI had:
 * arrow buttons at both ends, gradient thumb with a grip, behaving like {@code JScrollBar}
 * (arrows scroll by a unit, the track pages by the visible extent, both auto-repeat while held,
 * the thumb drags). Always {@link #WIDTH} pixels wide.
 */
public final class MetalScrollbar{
    public static final int WIDTH = 17;
    private static final int TOP_BUTTON = 15;
    private static final int BOTTOM_BUTTON = 17;
    private static final int MIN_THUMB = 8;
    private static final double REPEAT_DELAY = 0.3;
    private static final double REPEAT_INTERVAL = 0.06;

    private static final int BORDER = rgb(0x7a8a99);
    private static final int WHITE = rgb(0xffffff);
    private static final int FACE = rgb(0xeeeeee);
    private static final int PRESSED = rgb(0xb8cfe5);
    private static final int ARROW = rgb(0x333333);
    private static final int THUMB_BORDER = rgb(0x6382bf);
    private static final int SHADOW = rgb(0xb8cfe5);
    private static final int[] THUMB_GRADIENT = {
        rgb(0xe3ecf5), rgb(0xeef3f9), rgb(0xf9fbfd), rgb(0xf9fbfd), rgb(0xeef3f9), rgb(0xe3ecf5), rgb(0xdae6f2),
        rgb(0xd5e3f0), rgb(0xd0dfee), rgb(0xcadbec), rgb(0xc5d8ea), rgb(0xc0d4e8), rgb(0xbbd1e6)
    };

    private enum Part{NONE, UP, DOWN, TRACK, THUMB}

    private Part activePart = Part.NONE;
    private double nextRepeat;
    private float grabOffset;

    /**
     * Handles input and draws the scrollbar at screen position (x, y) with the given height.
     * {@code extent} is the visible height, {@code total} the full content height; returns the new scroll value.
     */
    public float render(String id, float x, float y, float height, float scroll, float extent, float total, float unitIncrement){
        float maxScroll = Math.max(total - extent, 0f);
        int track = (int)height - TOP_BUTTON - BOTTOM_BUTTON + 1; // the bottom button's top edge row belongs to the track, as in Swing
        int thumb = Math.min(track, Math.max(MIN_THUMB, (int)(track*extent/Math.max(total, 1f))));
        int thumbTop = TOP_BUTTON + (maxScroll <= 0f ? 0 : Math.round(scroll*(track - thumb)/maxScroll));

        ImGui.setCursorScreenPos(x, y);
        ImGui.invisibleButton(id, WIDTH, height);
        float mouseY = ImGui.getMousePosY() - y;

        if(ImGui.isItemActivated()){
            if(mouseY < TOP_BUTTON){
                this.activePart = Part.UP;
            }else if(mouseY >= height - BOTTOM_BUTTON){
                this.activePart = Part.DOWN;
            }else if(mouseY >= thumbTop && mouseY < thumbTop + thumb){
                this.activePart = Part.THUMB;
                this.grabOffset = mouseY - thumbTop;
            }else{
                this.activePart = Part.TRACK;
            }
            scroll = step(scroll, mouseY, thumbTop, thumb, extent, unitIncrement);
            this.nextRepeat = ImGui.getTime() + REPEAT_DELAY;
        }else if(ImGui.isItemActive()){
            if(this.activePart == Part.THUMB){
                if(track > thumb){
                    scroll = (mouseY - this.grabOffset - TOP_BUTTON)*maxScroll/(track - thumb);
                }
            }else if(ImGui.getTime() >= this.nextRepeat){
                scroll = step(scroll, mouseY, thumbTop, thumb, extent, unitIncrement);
                this.nextRepeat = ImGui.getTime() + REPEAT_INTERVAL;
            }
        }else{
            this.activePart = Part.NONE;
        }

        scroll = Math.max(0f, Math.min(scroll, maxScroll));
        thumbTop = TOP_BUTTON + (maxScroll <= 0f ? 0 : Math.round(scroll*(track - thumb)/maxScroll));
        draw(ImGui.getWindowDrawList(), x, y, (int)height, thumbTop, thumb);
        return scroll;
    }

    private float step(float scroll, float mouseY, int thumbTop, int thumb, float extent, float unitIncrement){
        return switch(this.activePart){
            case UP -> scroll - unitIncrement;
            case DOWN -> scroll + unitIncrement;
            case TRACK -> mouseY < thumbTop ? scroll - extent : (mouseY >= thumbTop + thumb ? scroll + extent : scroll);
            default -> scroll;
        };
    }

    private void draw(ImDrawList drawList, float x, float y, int height, int thumbTop, int thumb){
        int bottom = height - BOTTOM_BUTTON;

        // track
        fill(drawList, x, y, 0, TOP_BUTTON, WIDTH, bottom, FACE);
        fill(drawList, x, y, 0, TOP_BUTTON, 1, bottom, BORDER);
        fill(drawList, x, y, 1, TOP_BUTTON, 2, bottom, SHADOW);
        fill(drawList, x, y, 15, TOP_BUTTON, 16, bottom, BORDER);
        fill(drawList, x, y, 16, TOP_BUTTON, 17, bottom, WHITE);

        // top button
        int topFace = this.activePart == Part.UP ? PRESSED : FACE;
        fill(drawList, x, y, 0, 0, 16, 1, BORDER);
        fill(drawList, x, y, 16, 0, 17, 1, FACE);
        fill(drawList, x, y, 0, 1, 1, TOP_BUTTON, BORDER);
        fill(drawList, x, y, 1, 1, 15, 2, WHITE);
        fill(drawList, x, y, 15, 1, 16, 2, FACE);
        fill(drawList, x, y, 1, 2, 2, TOP_BUTTON, WHITE);
        fill(drawList, x, y, 2, 2, 15, TOP_BUTTON, topFace);
        fill(drawList, x, y, 15, 2, 16, TOP_BUTTON, BORDER);
        fill(drawList, x, y, 16, 1, 17, TOP_BUTTON, WHITE);
        for(int i=0; i < 4; i++){
            fill(drawList, x, y, 8 - i, 6 + i, 9 + i, 7 + i, ARROW);
        }

        // bottom button
        int bottomFace = this.activePart == Part.DOWN ? PRESSED : FACE;
        fill(drawList, x, y, 0, bottom, 1, height - 1, BORDER);
        fill(drawList, x, y, 1, bottom, 2, bottom + 1, FACE);
        fill(drawList, x, y, 2, bottom, 16, bottom + 1, BORDER);
        fill(drawList, x, y, 1, bottom + 1, 15, bottom + 2, WHITE);
        fill(drawList, x, y, 15, bottom + 1, 16, height - 2, BORDER);
        fill(drawList, x, y, 1, bottom + 2, 2, height - 2, WHITE);
        fill(drawList, x, y, 2, bottom + 2, 15, height - 2, bottomFace);
        fill(drawList, x, y, 1, height - 2, 2, height - 1, FACE);
        fill(drawList, x, y, 2, height - 2, 16, height - 1, BORDER);
        fill(drawList, x, y, 16, bottom, 17, height, WHITE);
        fill(drawList, x, y, 0, height - 1, 1, height, FACE);
        fill(drawList, x, y, 1, height - 1, 16, height, WHITE);
        for(int i=0; i < 4; i++){
            fill(drawList, x, y, 4 + i, bottom + 6 + i, 13 - i, bottom + 7 + i, ARROW);
        }

        // thumb
        int thumbBottom = thumbTop + thumb - 1;
        fill(drawList, x, y, 0, thumbTop, 16, thumbTop + thumb, THUMB_BORDER);
        fill(drawList, x, y, 1, thumbTop + 1, 15, thumbTop + 2, SHADOW);
        fill(drawList, x, y, 1, thumbTop + 2, 2, thumbBottom, SHADOW);
        for(int i=0; i < THUMB_GRADIENT.length; i++){
            fill(drawList, x, y, 2 + i, thumbTop + 2, 3 + i, thumbBottom, THUMB_GRADIENT[i]);
        }
        if(thumbBottom + 1 < bottom){
            fill(drawList, x, y, 1, thumbBottom + 1, 16, thumbBottom + 2, SHADOW);
        }
        if(thumb >= 12){
            int grip = thumbTop + thumb/2 - 2;
            for(int i=0; i < 3; i++){
                fill(drawList, x, y, 4, grip + 2*i, 13, grip + 2*i + 1, THUMB_BORDER);
                fill(drawList, x, y, 5, grip + 2*i + 1, 14, grip + 2*i + 2, WHITE);
            }
        }
    }

    private static void fill(ImDrawList drawList, float x, float y, int x0, int y0, int x1, int y1, int color){
        if(x1 > x0 && y1 > y0){
            drawList.addRectFilled(x + x0, y + y0, x + x1, y + y1, color);
        }
    }

    private static int rgb(int rgb){
        return imgui.ImColor.rgba((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 255);
    }
}
