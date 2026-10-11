package org.example.GUI.component;

import org.example.GUI.*;
import org.example.GUI.component.SpellSlotComponent.SlotType;
import org.example.GUI.HoleBorder.Hole;
import org.example.spells.*;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class SpellSelectorComponent extends AdaptiveComponent{
    private static final int slotSize = 20;
    private final AdaptiveComponent[] tabs = new AdaptiveComponent[Spell.SpellType.values().length];
    private final SpellHolderComponent[] tabComponents = new SpellHolderComponent[Spell.SpellType.values().length];
    private final HoleBorder tabBorder;
    private final Hole tabBorderHole;
    private WandComponent relatedWand;
    private int currentTab = 0;

    public SpellSelectorComponent(Spell[] spells, LayoutConstraints layoutConstraints){
        super(new JPanel(), layoutConstraints);
        this.component.setOpaque(false);
        LinkedList<Spell>[] tempLists = new LinkedList[Spell.SpellType.values().length];
        for(int i = 0; i < tempLists.length; i++){
            tempLists[i] = new LinkedList<>();
        }
        for(Spell spell : spells){
            tempLists[spell.getType().ordinal()].add(spell.clone());
        }

        this.tabBorder = new HoleBorder(Color.BLUE, this.scaleFactor);
        this.tabBorderHole = this.tabBorder.addHole(HoleBorder.Side.LEFT, this.scaleFactor, slotSize*this.scaleFactor);
        for(int i=0; i < Spell.SpellType.values().length; i++){
            int tabIndex = i;
            SpellSlotComponent currentSlotComponent = new SpellSlotComponent(switch(Spell.SpellType.values()[i]){
                case projectile -> new LIGHT_BULLET();
                case static_projectile -> new REGENERATION_FIELD();
                case passif -> new ENERGY_SHIELD();
                case utility -> new RESET();
                case modifier -> new DAMAGE();
                case material -> new MATERIAL_WATER();
                case multicast -> new BURST_2();
                default -> new ALPHA();
            }, SlotType.INERT, this.scaleFactor, new LayoutConstraints(new Fraction(0), new Fraction(i*this.scaleFactor*slotSize), new Fraction(this.scaleFactor*slotSize), new Fraction(this.scaleFactor*slotSize))){
                @Override
                public void onClick(){
                    SpellSelectorComponent.this.switchTab(tabIndex);
                }
            };
            currentSlotComponent.setClickable(true);
            currentSlotComponent.component.setBorder(BorderFactory.createMatteBorder(this.scaleFactor,this.scaleFactor, i == Spell.SpellType.values().length - 1 ? this.scaleFactor : 0,0, Color.BLUE));
            this.tabs[i] = currentSlotComponent;
            this.addPanel(currentSlotComponent);
            this.tabComponents[i] = new SpellHolderComponent(tempLists[i].toArray(new Spell[0]), SlotType.SPELL_PICKER, new LayoutConstraints(new Fraction(this.scaleFactor*slotSize), new Fraction(0), this.layoutConstraints.getWidth().clone().subtract((long)this.scaleFactor*slotSize), this.layoutConstraints.getHeight().clone(), new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1)), spellSlotComponent -> {
                if(this.relatedWand != null){
                    this.relatedWand.putSpellFirstAvailable(spellSlotComponent.getSpellSlotPanel().getSpell());
                }
            });
            this.tabComponents[i].getScrollPane().setViewportBorder(this.tabBorder);
            if(i != 0){
                this.tabComponents[i].component.setVisible(false);
                currentSlotComponent.setBackground(new Color(54, 43, 39));
            }else{
                currentSlotComponent.setBackground(Color.BLACK);
            }
            this.addPanel(this.tabComponents[i]);
        }
    }

    public SpellHolderComponent[] getTabComponents(){
        return this.tabComponents;
    }

    public void setRelatedWand(WandComponent wandComponent){
        this.relatedWand = wandComponent;
    }

    public void switchTab(int tabID){
        if(this.currentTab == tabID || tabID < 0 || tabID >= this.tabComponents.length){
            return;
        }
        this.tabBorderHole.setRange(tabID*this.scaleFactor*slotSize + this.scaleFactor, (tabID + 1)*this.scaleFactor*slotSize);
        this.tabs[this.currentTab].setBackground(new Color(54, 43, 39));
        this.tabComponents[this.currentTab].component.setVisible(false);

        this.tabs[tabID].setBackground(Color.BLACK);
        this.tabComponents[tabID].component.setVisible(true);
        this.tabComponents[tabID].updateLayout();
        this.tabComponents[tabID].updateBounds();
        
        this.currentTab = tabID;
    }
}