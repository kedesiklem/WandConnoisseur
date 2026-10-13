package org.example.GUI.windows;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;
import org.example.GUI.TextureCache;
import org.example.main.Global;

import java.awt.image.BufferedImage;

/**
 * Shared behavior for the Flowchart and Cast State panels, a port of the old {@code ImageAreaPanel}:
 * black area showing a {@link BufferedImage} that the owner pushes in via {@link #refresh},
 * wheel zooms around the cursor (never smaller than "fit", never above 10x), left-drag pans,
 * double-click resets the zoom, right-click offers "Save image" / "Copy to clipboard".
 */
final class ImageWindow{
    private static final double MAX_ZOOM = 10.0;
    private static final int BACKGROUND = imgui.ImColor.rgba(0, 0, 0, 255);

    private final String id;
    private final Object textureKey;
    private TextureCache.Texture texture;
    private BufferedImage currentImage;
    private double zoom = 1.0;
    private double minZoom = 1.0;
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    private float width;
    private float height;

    ImageWindow(String id, Object textureKey){
        this.id = id;
        this.textureKey = textureKey;
    }

    void refresh(BufferedImage image){
        this.currentImage = image;
        this.texture = TextureCache.refresh(this.textureKey, image);
        updateMinZoom();
        clampPosition();
    }

    /** Draws the panel in the screen rectangle (x, y, width, height) of the current window. */
    void render(float x, float y, float width, float height){
        if(this.width != width || this.height != height){
            this.width = width;
            this.height = height;
            updateMinZoom();
        }

        ImGui.setCursorScreenPos(x, y);
        ImGui.invisibleButton("##" + this.id, width, height, ImGuiButtonFlags.MouseButtonLeft | ImGuiButtonFlags.MouseButtonRight);

        if(this.texture != null){
            if(ImGui.isItemActive() && ImGui.isMouseDown(ImGuiMouseButton.Left)){
                float dx = ImGui.getIO().getMouseDeltaX();
                float dy = ImGui.getIO().getMouseDeltaY();
                if(dx != 0f || dy != 0f){
                    this.offsetX += dx;
                    this.offsetY += dy;
                    clampPosition();
                }
            }
            if(ImGui.isItemHovered()){
                float wheel = ImGui.getIO().getMouseWheel();
                if(wheel != 0f){
                    zoomAt(ImGui.getMousePosX() - x, ImGui.getMousePosY() - y, wheel);
                }
                if(ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)){
                    resetZoom();
                }
            }
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.pushClipRect(x, y, x + width, y + height, true);
        drawList.addRectFilled(x, y, x + width, y + height, BACKGROUND);
        if(this.texture != null){
            float imageX = x + (int)this.offsetX;
            float imageY = y + (int)this.offsetY;
            drawList.addImage(this.texture.id(), imageX, imageY,
                imageX + (int)(this.texture.width()*this.zoom), imageY + (int)(this.texture.height()*this.zoom));
        }
        drawList.popClipRect();

        if(ImGui.beginPopupContextItem("##" + this.id + "Context")){
            if(ImGui.menuItem("Save image") && this.currentImage != null){
                Global.saveBufferedImage(this.currentImage, Global.getPathOutput() + "test.png");
            }
            if(ImGui.menuItem("Copy to clipboard") && this.currentImage != null){
                Global.toClipboard(this.currentImage);
            }
            ImGui.endPopup();
        }
    }

    private void zoomAt(double mouseX, double mouseY, float wheel){
        double newZoom = Math.max(this.minZoom, Math.min(MAX_ZOOM, this.zoom*Math.pow(1.1, wheel)));
        if(newZoom == this.zoom){
            return;
        }

        this.offsetX = mouseX - newZoom*(mouseX - this.offsetX)/this.zoom;
        this.offsetY = mouseY - newZoom*(mouseY - this.offsetY)/this.zoom;
        this.zoom = newZoom;
        clampPosition();
    }

    private void resetZoom(){
        this.zoom = Math.min(Math.max(1.0, this.minZoom), MAX_ZOOM);
        clampPosition();
    }

    private void clampPosition(){
        if(this.texture == null){
            return;
        }
        double widthDiff = this.width - this.texture.width()*this.zoom;
        double heightDiff = this.height - this.texture.height()*this.zoom;

        this.offsetX = Math.max(Math.min(this.offsetX, Math.max(widthDiff, 0.0)), Math.min(widthDiff, 0.0));
        this.offsetY = Math.max(Math.min(this.offsetY, Math.max(heightDiff, 0.0)), Math.min(heightDiff, 0.0));
    }

    private void updateMinZoom(){
        if(this.texture == null){
            return;
        }
        double newMinZoom = Double.MAX_VALUE;
        if(this.width != 0){
            newMinZoom = Math.min((double)this.width/this.texture.width(), newMinZoom);
        }
        if(this.height != 0){
            newMinZoom = Math.min((double)this.height/this.texture.height(), newMinZoom);
        }
        this.minZoom = Math.min(newMinZoom, 1.0);
        this.zoom = Math.max(Math.min(this.zoom, MAX_ZOOM), this.minZoom);
        clampPosition();
    }
}
