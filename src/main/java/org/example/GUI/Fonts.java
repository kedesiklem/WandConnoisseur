package org.example.GUI;

import imgui.ImFont;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import org.example.main.Global;

import java.io.File;

/**
 * Loads the fonts into the ImGui font atlas: first (= default) a bold sans-serif close to Swing's
 * Metal "Dialog" font for menus/buttons, then the game's own pixel fonts.
 */
public final class Fonts{
    private static ImFont pixel;
    private static ImFont pixelSmall;
    private static ImFont title;
    private static float uiFontSize = 13f; // ImGui's built-in font, used if no UI font file is found

    /** Candidates for the default UI font, in order; Swing's logical "Dialog" font maps to DejaVu Sans on most Linux setups. */
    private static final String[] UI_FONTS = {
        "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf",
        "/usr/share/fonts/TTF/DejaVuSans-Bold.ttf",
        "/usr/share/fonts/dejavu/DejaVuSans-Bold.ttf",
        "C:/Windows/Fonts/tahomabd.ttf",
        "/System/Library/Fonts/Supplemental/Tahoma Bold.ttf"
    };
    private static final float UI_FONT_SIZE = 12f;

    private Fonts(){}

    public static void load(){
        ImFontAtlas fonts = ImGui.getIO().getFonts();
        int scale = Global.getScaleFactor();
        ImFontConfig config = new ImFontConfig();

        boolean uiFontLoaded = false;
        for(String path : UI_FONTS){
            if(new File(path).isFile()){
                fonts.addFontFromFileTTF(path, UI_FONT_SIZE, config);
                uiFontSize = UI_FONT_SIZE;
                uiFontLoaded = true;
                break;
            }
        }
        if(!uiFontLoaded){
            fonts.addFontDefault();
        }

        pixel = fonts.addFontFromFileTTF("./src/main/java/org/example/PixelFont.ttf", 7.5f*scale, config);
        pixelSmall = fonts.addFontFromFileTTF("./src/main/java/org/example/PixelFontSmall.ttf", 6f*scale, config);
        title = fonts.addFontFromFileTTF("./src/main/java/org/example/TitleFont.ttf", 10f*scale, config);

        config.destroy();
        fonts.build();
    }

    public static float uiFontSize(){
        return uiFontSize;
    }

    public static ImFont pixel(){
        return pixel;
    }

    public static ImFont pixelSmall(){
        return pixelSmall;
    }

    public static ImFont title(){
        return title;
    }
}
