package org.example.GUI.component;

import org.example.GUI.component.SpellSlotComponent.SlotType;
import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.GUI.RelativeLayoutConstraints;
import org.example.main.Global;
import org.example.spells.Spell;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.Border;

public class SpellHolderComponent extends AdaptiveComponent{
    private static final int slotSize = 20;
    private static final int marginSize = 3;
    private final JScrollPane scrollPane;
    private final JPanel insidePanel;
    private SpellSlotComponent[] spellSlotComponents;
    private final SlotType slotType;
    private final Consumer<SpellSlotComponent> spellConsumer;
    private int nbX = -1;
    private int nbY = -1;
    private int lastHorizontalMargin = -1;

    public SpellHolderComponent(Spell[] spells, SlotType slotType, LayoutConstraints LayoutConstraints, Consumer<SpellSlotComponent> spellConsumer){
        super(new JScrollPane(), LayoutConstraints);
        this.slotType = slotType;
        this.spellConsumer = spellConsumer;
        this.scaleFactor = Math.max(Global.getScaleFactor(), 1);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        this.scrollPane.setBorder(null);
        this.component = this.scrollPane;
        this.insidePanel = new JPanel(null){
            @Override
            public Dimension getPreferredSize(){
                int maxY = marginSize*SpellHolderComponent.this.scaleFactor;

                for(Component child : getComponents()){
                    maxY = Math.max(maxY, child.getY() + child.getHeight() + marginSize*SpellHolderComponent.this.scaleFactor);
                }
                return new Dimension(SpellHolderComponent.this.layoutConstraints.getWidthInt(), maxY);
            }
        };

        this.spellSlotComponents = new SpellSlotComponent[spells.length];
        for(int i=0; i < spells.length; i++){
            SpellSlotComponent spellSlotComponent = new SpellSlotComponent(spells[i], this.slotType, this.scaleFactor, new RelativeLayoutConstraints(new Fraction(0), new Fraction(0), new Fraction(this.scaleFactor*slotSize), new Fraction(this.scaleFactor*slotSize), new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(0))){
                @Override
                public void onClick(){
                    if(SpellHolderComponent.this.spellConsumer != null){
                        SpellHolderComponent.this.spellConsumer.accept(this);
                    }
                }
            };
            spellSlotComponent.parent = this;
            this.spellSlotComponents[i] = spellSlotComponent;
            this.children.add(spellSlotComponent);
            this.insidePanel.add(spellSlotComponent.component);
        }

        this.insidePanel.setBackground(Color.BLACK);
        this.scrollPane.setViewportView(this.insidePanel);
    }

    public SpellHolderComponent(Spell[] spells, SlotType slotType, LayoutConstraints LayoutConstraints){
        this(spells, slotType, LayoutConstraints, null);
    }

    public JScrollPane getScrollPane(){
        return this.scrollPane;
    }

    public JPanel getInsidePanel(){
        return this.insidePanel;
    }

    public Spell[] getSpells(){
        Spell[] spells = new Spell[this.spellSlotComponents.length];

        for(int i=0; i < this.spellSlotComponents.length; i++){
            spells[i] = this.spellSlotComponents[i].getSpellSlotPanel().getSpell();
        }

        return spells;
    }

    public void updateSlots(Spell[] spells){
        if(spells.length != this.spellSlotComponents.length){
            SpellSlotComponent[] newSpellSlotComponents = new SpellSlotComponent[spells.length];

            for(int i=0; i < Math.max(newSpellSlotComponents.length, this.spellSlotComponents.length); i++){
                if(i < newSpellSlotComponents.length && i < this.spellSlotComponents.length){
                    newSpellSlotComponents[i] = this.spellSlotComponents[i];
                }else if(i < newSpellSlotComponents.length){
                    newSpellSlotComponents[i] = new SpellSlotComponent(spells[i], this.slotType, this.scaleFactor, new RelativeLayoutConstraints(new Fraction(0), new Fraction(0), new Fraction(this.scaleFactor*slotSize), new Fraction(this.scaleFactor*slotSize), new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(0))){
                        @Override
                        public void onClick(){
                            if(SpellHolderComponent.this.spellConsumer != null){
                                SpellHolderComponent.this.spellConsumer.accept(this);
                            }
                        }
                    };
                    this.addPanel(newSpellSlotComponents[i]);
                }else{
                    this.removePanel(this.spellSlotComponents[i]);
                }
            }
            this.spellSlotComponents = newSpellSlotComponents;

            this.nbX = -1;
            this.nbY = -1;
            this.updateSpellPanels();
            this.updateLayout();
            this.updateBounds();
            this.component.revalidate();
            this.component.repaint();
        }else{
            for(int i=0; i < this.spellSlotComponents.length; i++){
                this.spellSlotComponents[i].getSpellSlotPanel().setSpell(spells[i]);
                this.spellSlotComponents[i].getSpellSlotPanel().repaint();
            }
        }
    }

    private void updateSpellPanels(){
        int verticalBarWidth = 0;

        Border border = this.scrollPane.getViewportBorder();
        int topBottom = 0;
        int leftRight = 0;
        if(border != null){
            Insets insets = border.getBorderInsets(this.scrollPane.getViewport());
            topBottom = insets.top + insets.bottom;
            leftRight = insets.left + insets.right;
        }

        int nbX = (int)Math.max(Math.floor(this.layoutConstraints.getWidth().clone().subtract((long)leftRight).subtract((long)this.scaleFactor*marginSize).divide((long)this.scaleFactor*(marginSize + slotSize)).toDouble()), 1);
        int nbY = (int)Math.ceil(this.spellSlotComponents.length/(double)nbX);

        if(this.scaleFactor*(nbY*slotSize + (nbY + 1)*marginSize) + topBottom > this.layoutConstraints.getHeightInt()){
            JScrollBar verticalBar = this.scrollPane.getVerticalScrollBar();
            verticalBarWidth = verticalBar.isVisible() ? verticalBar.getWidth() : 0;

            if(verticalBarWidth == 0){
                verticalBarWidth = verticalBar.getPreferredSize().width;
            }
            nbX = (int)Math.max(Math.floor(this.layoutConstraints.getWidth().clone().subtract((long)verticalBarWidth + leftRight).subtract((long)this.scaleFactor*marginSize).divide((long)this.scaleFactor*(marginSize + slotSize)).toDouble()), 1);
            nbY = (int)Math.ceil(this.spellSlotComponents.length/(double)nbX);
        }

        if(this.nbX == nbX && this.nbY == nbY && verticalBarWidth + leftRight == this.lastHorizontalMargin){
            return;
        }
        this.nbX = nbX;
        this.nbY = nbY;
        this.lastHorizontalMargin = verticalBarWidth + leftRight;

        int x = 0;
        int y = 0;
        int margin = this.scaleFactor*slotSize*nbX + verticalBarWidth + leftRight;
        for(SpellSlotComponent spellSlotComponent : this.spellSlotComponents){
            if(spellSlotComponent.layoutConstraints instanceof RelativeLayoutConstraints relativeLayoutConstraints){
                relativeLayoutConstraints.setMargin(margin);
            }
            spellSlotComponent.layoutConstraints.setStartX((long) x*this.scaleFactor*slotSize);
            spellSlotComponent.layoutConstraints.setXGrowthRate(x + 1, nbX + 1);
            spellSlotComponent.layoutConstraints.setStartY(new Fraction(this.scaleFactor*(y*slotSize + (y + 1)*marginSize)));

            spellSlotComponent.layoutUpdated = true;
            x++;
            if(x >= nbX){
                x = 0;
                y++;
            }
        }
    }

    public SpellSlotComponent[] getSpellSlotComponents(){
        return this.spellSlotComponents;
    }

    @Override
    public void addPanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = this;
        this.children.add(adaptiveComponent);

        if(adaptiveComponent.component != null){
            this.insidePanel.add(adaptiveComponent.component);
            this.childUpdated = true;
        }
    }

    @Override
    public void addPanel(AdaptiveComponent[] adaptiveComponents){
        for(AdaptiveComponent adaptiveComponent : adaptiveComponents){
            adaptiveComponent.parent = this;
            this.children.add(adaptiveComponent);
            if(adaptiveComponent.component != null){
                this.insidePanel.add(adaptiveComponent.component);
                this.childUpdated = true;
            }
        }
    }

    @Override
    public void removePanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = null;
        this.children.remove(adaptiveComponent);

        if(adaptiveComponent.component != null){
            this.insidePanel.remove(adaptiveComponent.component);
            this.childUpdated = true;
        }
    }

    @Override
    public void removePanel(AdaptiveComponent[] adaptiveComponents){
        for(AdaptiveComponent adaptiveComponent : adaptiveComponents){
            adaptiveComponent.parent = null;
            this.children.remove(adaptiveComponent);
            if(adaptiveComponent.component != null){
                this.insidePanel.remove(adaptiveComponent.component);
                this.childUpdated = true;
            }
        }
    }

    @Override
    public void updateLayout(boolean forceUpdate){
        if(!forceUpdate && this.component != null && !this.component.isVisible()){
            return;
        }
        if(this.layoutConstraints.update(this.parent == null ? null : this.parent.layoutConstraints) || forceUpdate){
            this.layoutUpdated = true;
            this.scrollPane.revalidate();
            this.updateSpellPanels();
            for(AdaptiveComponent child : this.children){
                child.updateLayout(forceUpdate);
            }
        }
    }
}