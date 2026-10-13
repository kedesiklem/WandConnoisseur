package org.example.GUI;

import imgui.ImGui;
import imgui.flag.ImGuiMouseButton;
import org.example.spells.Spell;

/** Named drag & drop payload shapes shared between the spell picker and the wand grid. */
public final class DragPayloads{
    public static final String SPELL_FROM_PICKER = "SPELL_FROM_PICKER";
    public static final String SLOTS_FROM_WAND = "SLOTS_FROM_WAND";

    private static boolean dragStarted = false;
    private static boolean dropHandled = false;

    private DragPayloads(){}

    /** Dragged from the spell picker: the template spell to clone into whatever slot accepts it. */
    public record SpellPayload(Spell spell){}

    /** Dragged from the wand grid: every currently-selected slot index (sorted), moved as one block, and the slot the drag started from. */
    public record SlotsPayload(int[] slots, int grabbed){}

    /** Call when a drag source starts, so the button release that ends the drag isn't also treated as a click. */
    public static void markDragStarted(){
        dragStarted = true;
    }

    /** Filters a button's "clicked" result: releases that end a drag don't count as clicks. */
    public static boolean isClick(boolean clicked){
        return clicked && !dragStarted;
    }

    /** Call when a drop was consumed (delivered to a target, or released back onto its own source). */
    public static void markDropHandled(){
        dropHandled = true;
    }

    public static boolean isDropHandled(){
        return dropHandled;
    }

    /** Called once at the end of every frame. */
    public static void endFrame(){
        dropHandled = false;
        if(!ImGui.isMouseDown(ImGuiMouseButton.Left)){
            dragStarted = false;
        }
    }
}
