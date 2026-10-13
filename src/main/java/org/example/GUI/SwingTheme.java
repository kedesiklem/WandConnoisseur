package org.example.GUI;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;

/**
 * ImGui style approximating the Swing Metal/Ocean look the old GUI had (colors sampled from it):
 * light gray surfaces, #333 text, square corners, 1px blue borders, pale blue buttons and selection.
 */
public final class SwingTheme{
    private SwingTheme(){}

    public static void apply(){
        ImGui.styleColorsLight();
        ImGuiStyle style = ImGui.getStyle();

        style.setWindowRounding(0f);
        style.setChildRounding(0f);
        style.setFrameRounding(0f);
        style.setPopupRounding(0f);
        style.setScrollbarRounding(0f);
        style.setGrabRounding(0f);
        style.setTabRounding(0f);
        style.setWindowBorderSize(1f);
        style.setChildBorderSize(0f);
        style.setPopupBorderSize(1f);
        style.setFrameBorderSize(1f);
        style.setScrollbarSize(17f);
        style.setItemSpacing(8f, 7f); // with the 12px font: 21px menu item pitch, like Swing menus
        style.setWindowTitleAlign(0f, 0.5f);

        set(style, ImGuiCol.Text, 0x33, 0x33, 0x33);
        set(style, ImGuiCol.TextDisabled, 153, 153, 153);
        set(style, ImGuiCol.WindowBg, 238, 238, 238);
        set(style, ImGuiCol.PopupBg, 238, 238, 238);
        set(style, ImGuiCol.MenuBarBg, 238, 238, 238);
        set(style, ImGuiCol.Border, 0x63, 0x82, 0xbf);
        set(style, ImGuiCol.FrameBg, 255, 255, 255);
        set(style, ImGuiCol.FrameBgHovered, 255, 255, 255);
        set(style, ImGuiCol.FrameBgActive, 255, 255, 255);
        set(style, ImGuiCol.TitleBg, 238, 238, 238);
        set(style, ImGuiCol.TitleBgActive, 221, 232, 243);
        set(style, ImGuiCol.TitleBgCollapsed, 238, 238, 238);
        set(style, ImGuiCol.Button, 221, 232, 243);
        set(style, ImGuiCol.ButtonHovered, 238, 244, 250);
        set(style, ImGuiCol.ButtonActive, 184, 207, 229);
        set(style, ImGuiCol.Header, 184, 207, 229);
        set(style, ImGuiCol.HeaderHovered, 0xa3, 0xb8, 0xcc);
        set(style, ImGuiCol.HeaderActive, 0xa3, 0xb8, 0xcc);
        set(style, ImGuiCol.CheckMark, 0, 0, 0);
        set(style, ImGuiCol.ScrollbarBg, 238, 238, 238);
        set(style, ImGuiCol.ScrollbarGrab, 200, 221, 242);
        set(style, ImGuiCol.ScrollbarGrabHovered, 184, 207, 229);
        set(style, ImGuiCol.ScrollbarGrabActive, 163, 184, 204);
        set(style, ImGuiCol.Separator, 0x7a, 0x8a, 0x99);
        set(style, ImGuiCol.TextSelectedBg, 184, 207, 229);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0, 0, 0, 0);
    }

    private static void set(ImGuiStyle style, int col, int r, int g, int b){
        style.setColor(col, r, g, b, 255);
    }
}
