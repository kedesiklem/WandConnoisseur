package org.example.GUI.state;

import org.example.main.Wand;
import org.example.spells.Spell;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Ctrl+Z/Ctrl+Y for the wand editor. Rather than modeling every mutation as its own undoable
 * command, each commit point ({@link #push}) snapshots the *whole* wand (every stat field plus
 * a deep-cloned spell array). Restoring just replays that snapshot through the same setters the
 * UI already uses. One mechanism correctly covers every kind of edit (slot drag, group move,
 * clear, stat field, build load) instead of needing a bespoke undo command per feature.
 */
public final class UndoManager{
    private record Snapshot(boolean shuffle, int nbDraw, int castDelay, int rechargeTime, int maxMana,
                             int regenMana, int nbSlot, double spread, double speed, Spell[] spells){}

    private static final int MAX_DEPTH = 200;
    private final Deque<Snapshot> undoStack = new ArrayDeque<>();
    private final Deque<Snapshot> redoStack = new ArrayDeque<>();

    /** Call BEFORE mutating the wand, to record what it looked like before the upcoming change. */
    public void push(Wand wand){
        this.undoStack.push(capture(wand));
        if(this.undoStack.size() > MAX_DEPTH){
            this.undoStack.removeLast();
        }
        this.redoStack.clear();
    }

    public boolean canUndo(){
        return !this.undoStack.isEmpty();
    }

    public boolean canRedo(){
        return !this.redoStack.isEmpty();
    }

    public boolean undo(Wand wand){
        if(this.undoStack.isEmpty()){
            return false;
        }
        this.redoStack.push(capture(wand));
        restore(wand, this.undoStack.pop());
        return true;
    }

    public boolean redo(Wand wand){
        if(this.redoStack.isEmpty()){
            return false;
        }
        this.undoStack.push(capture(wand));
        restore(wand, this.redoStack.pop());
        return true;
    }

    private static Snapshot capture(Wand wand){
        Spell[] source = wand.getSpells(true);
        Spell[] spells = new Spell[source.length];
        for(int i=0; i < source.length; i++){
            spells[i] = source[i] == null ? null : source[i].clone();
        }

        return new Snapshot(wand.getShuffle(), wand.getNbDraw(), wand.getCastDelay(), wand.getRechargeTime(),
            wand.getMaxMana(), wand.getRegenMana(), wand.getNbSlot(), wand.getSpread(), wand.getSpeed(), spells);
    }

    private static void restore(Wand wand, Snapshot snapshot){
        wand.setShuffle(snapshot.shuffle(), false);
        wand.setNbDraw(snapshot.nbDraw(), false);
        wand.setCastDelay(snapshot.castDelay(), false);
        wand.setRechargeTime(snapshot.rechargeTime(), false);
        wand.setMaxMana(snapshot.maxMana(), false);
        wand.setRegenMana(snapshot.regenMana(), false);
        wand.setNbSlot(snapshot.nbSlot(), false);
        wand.setSpread(snapshot.spread(), false);
        wand.setSpeed(snapshot.speed(), false);
        wand.setSpells(snapshot.spells());
        wand.resetAll();
    }
}
