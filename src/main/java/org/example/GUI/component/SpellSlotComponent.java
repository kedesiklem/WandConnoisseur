package org.example.GUI.component;

import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.GUI.panel.SpellSlotPanel;
import org.example.GUI.panel.SpellSlotPanel.SlotState;
import org.example.spells.Spell;

public class SpellSlotComponent extends AdaptiveComponent{
    public enum SlotType{INERT, SLOT, SPELL_PICKER}
    private static final int slotSize = 20;
    private SlotType slotType;
    private final SpellSlotPanel spellSlotPanel;

    public SpellSlotComponent(Spell spell, SlotType slotType, int scaleFactor, LayoutConstraints layoutConstraints){
        super(layoutConstraints);
        this.slotType = slotType;
        this.spellSlotPanel = new SpellSlotPanel(spell, scaleFactor, this.slotType == SlotType.INERT ? SlotState.EMPTY : SlotState.SLOT);
        this.component = this.spellSlotPanel;
        this.scaleFactor = Math.max(scaleFactor, 1);
        this.updateDragAndDrop();
    }

    public SpellSlotComponent(Spell spell, SlotType slotType, int scaleFactor, Fraction x, Fraction y, Fraction xGrowthRate, Fraction yGrowthRate){
        this(spell, slotType, scaleFactor, new LayoutConstraints(x, y, new Fraction(slotSize*scaleFactor), new Fraction(slotSize*scaleFactor), slotSize*scaleFactor, slotSize*scaleFactor, xGrowthRate, yGrowthRate, new Fraction(0), new Fraction(0)));
    }

    public SpellSlotComponent(Spell spell, SlotType slotType, int scaleFactor, int x, int y){
        this(spell, slotType, scaleFactor, new LayoutConstraints(new Fraction(x), new Fraction(y), new Fraction(slotSize*scaleFactor), new Fraction(slotSize*scaleFactor)));
    }

    public SpellSlotPanel getSpellSlotPanel(){
        return this.spellSlotPanel;
    }

    public void setSpell(Spell spell){
        this.spellSlotPanel.setSpell(spell);
        this.updateDragAndDrop();
    }

    private void updateDragAndDrop(){
        switch(this.slotType){
            case INERT -> {
                this.draggable = false;
                this.droppable = false;
                this.clickable = false;
            }
            case SLOT -> {
                this.draggable = this.spellSlotPanel.getSpell() != null;
                this.droppable = true;
                this.clickable = false;
            }
            case SPELL_PICKER -> {
                this.draggable = true;
                this.droppable = false;
                this.clickable = true;
            }
        }
    }

    public void setScaleFactor(int scaleFactor){
        scaleFactor = Math.max(scaleFactor, 1);

        if(this.scaleFactor == scaleFactor){
            return;
        }

        this.scaleFactor = scaleFactor;
        this.spellSlotPanel.setScaleFactor(scaleFactor);
    }

    private static WandComponent getFirstWandParent(AdaptiveComponent adaptiveComponent){
        if(adaptiveComponent.parent == null){
            return null;
        }

        if(adaptiveComponent.parent instanceof WandComponent wandComponent){
            return wandComponent;
        }

        return getFirstWandParent(adaptiveComponent.parent);
    }

    @Override
    public void onDrop(AdaptiveComponent from, AdaptiveComponent to){
        if(!(from instanceof SpellSlotComponent fromSlot)){
            return;
        }

        if(!(to instanceof SpellSlotComponent toSlot)){
            if(fromSlot.spellSlotPanel.getSpell() != null && fromSlot.slotType != SlotType.SPELL_PICKER){
                WandComponent wandComponent = getFirstWandParent(fromSlot);
                fromSlot.spellSlotPanel.setSpell(null);
                fromSlot.updateDragAndDrop();
                if(wandComponent != null){
                    wandComponent.updateSpellsFromComponent();
                }
                fromSlot.spellSlotPanel.repaint();
            }
            return;
        }

        SpellSlotPanel fromSpellPanel = fromSlot.spellSlotPanel;
        SpellSlotPanel toSpellPanel = toSlot.spellSlotPanel;

        if(fromSpellPanel.getSpell() == null){
            return;
        }

        if(fromSlot.slotType == SlotType.SPELL_PICKER){
            toSpellPanel.setSpell(fromSpellPanel.getSpell().clone());
            toSpellPanel.setSlotState(SlotState.SLOT);
            toSpellPanel.repaint();
        }else{
            Spell currentSpell = fromSpellPanel.getSpell();
            fromSpellPanel.setSpell(toSpellPanel.getSpell());
            toSpellPanel.setSpell(currentSpell);
            toSpellPanel.setSlotState(SlotState.SLOT);
        }

        fromSlot.updateDragAndDrop();
        toSlot.updateDragAndDrop();

        WandComponent fromWandComponent = getFirstWandParent(fromSlot);
        WandComponent towWandComponent = getFirstWandParent(toSlot);
        if(fromWandComponent != null){
            fromWandComponent.updateSpellsFromComponent();
        }
        if(towWandComponent != null && towWandComponent != fromWandComponent){
            towWandComponent.updateSpellsFromComponent();
        }

        fromSpellPanel.repaint();
        toSpellPanel.repaint();
    }

    @Override
    public void startOnDrag(){
        if(this.spellSlotPanel.getSpell() == null){
            return;
        }
        this.spellSlotPanel.setGrayScale(true);
        this.spellSlotPanel.repaint();
    }

    @Override
    public void stopOnDrag(){
        if(this.spellSlotPanel.getSpell() == null || !this.spellSlotPanel.getGrayScale()){
            return;
        }
        this.spellSlotPanel.setGrayScale(false);
        this.spellSlotPanel.repaint();
    }

    @Override
    public void onDragEnter(AdaptiveComponent draggedComponent){
        if(!(draggedComponent.component instanceof SpellSlotPanel draggedSpellPanel) || draggedSpellPanel.getSpell() == null){
            return;
        }
        this.spellSlotPanel.setSlotState(SlotState.HIGHLIGHT);
        this.spellSlotPanel.repaint();
    }

    @Override
    public void onDragExit(AdaptiveComponent draggedComponent){
        if(!(draggedComponent.component instanceof SpellSlotPanel draggedSpellPanel) || draggedSpellPanel.getSpell() == null){
            return;
        }
        this.spellSlotPanel.setSlotState(SlotState.SLOT);
        this.spellSlotPanel.repaint();
    }

    @Override
    public void updateLayout(boolean forceUpdate){
        if(!forceUpdate && this.component != null && !this.component.isVisible()){
            return;
        }
        if(this.layoutConstraints.update(this.parent == null ? null : this.parent.layoutConstraints) || forceUpdate){
            this.layoutUpdated = true;
            for(AdaptiveComponent child : this.children){
                child.updateLayout(forceUpdate);
            }
        }
    }
}