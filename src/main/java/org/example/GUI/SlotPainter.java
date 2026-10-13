package org.example.GUI;

import imgui.ImDrawList;
import org.example.main.Global;
import org.example.spells.Spell;

import java.util.List;

/**
 * Draws one spell slot the way the old {@code SpellSlotPanel} painted it: black background, the
 * inventory box frame (or its highlighted variant while something is dragged over it), then the
 * spell icon at the top-left, all scaled by {@link Global#getScaleFactor()} with nearest filtering.
 */
public final class SlotPainter{
    public enum Frame{NONE, SLOT, HIGHLIGHT}

    private static final String BOX_KEY = "slotBox";
    private static final String BOX_HIGHLIGHT_KEY = "slotBoxHighlight";
    private static final int BACKGROUND = imgui.ImColor.rgba(0, 0, 0, 255);

    private SlotPainter(){}

    public static void draw(ImDrawList drawList, float x, float y, Spell spell, Frame frame, boolean grayScale){
        float size = Global.getScaleFactor()*20f;
        drawList.addRectFilled(x, y, x + size, y + size, BACKGROUND);

        switch(frame){
            case SLOT -> drawTexture(drawList, box(false), x, y);
            case HIGHLIGHT -> drawTexture(drawList, box(true), x, y);
            case NONE -> {}
        }

        if(spell != null){
            drawTexture(drawList, spellTexture(spell, grayScale), x, y);
        }
    }

    public static TextureCache.Texture spellTexture(Spell spell, boolean grayScale){
        if(grayScale){
            return TextureCache.get(List.of("gray", spell.getClass()), Global.grayscaleImage(spell.getImage(), true));
        }
        return TextureCache.get(spell.getClass(), spell.getImage());
    }

    public static void drawTexture(ImDrawList drawList, TextureCache.Texture texture, float x, float y){
        int scale = Global.getScaleFactor();
        drawList.addImage(texture.id(), x, y, x + texture.width()*scale, y + texture.height()*scale);
    }

    private static TextureCache.Texture box(boolean highlight){
        if(highlight){
            return TextureCache.get(BOX_HIGHLIGHT_KEY, Global.loadImage("./src/main/java/org/example/image/wand/full_inventory_box_highlight.png"));
        }
        return TextureCache.get(BOX_KEY, Global.loadImage("./src/main/java/org/example/image/wand/full_inventory_box.png"));
    }
}
