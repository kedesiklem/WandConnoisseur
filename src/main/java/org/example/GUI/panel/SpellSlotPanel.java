package org.example.GUI.panel;

import org.example.main.Global;
import org.example.spells.Spell;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class SpellSlotPanel extends JComponent{
    public enum SlotState{EMPTY, SLOT, HIGHLIGHT};
    private static final BufferedImage slotImage = Global.loadImage("./src/main/java/org/example/image/wand/full_inventory_box.png");
    private static final BufferedImage slotImageHighlight = Global.loadImage("./src/main/java/org/example/image/wand/full_inventory_box_highlight.png");
    private SlotState slotState;
    private int scaleFactor;
    private Spell spell;
    private BufferedImage scaledSlotImage = null;
    private BufferedImage scaledSlotImageHighlight = null;
    private BufferedImage scaledSpellImage = null;
    private BufferedImage scaledSpellImageGrayScale = null;
    private boolean grayScale = false;

    public SpellSlotPanel(Spell spell, int scaleFactor, SlotState slotState){
        this.setBackground(Color.BLACK);
        this.spell = spell;
        this.scaleFactor = Math.max(scaleFactor, 1);
        this.slotState = slotState;
    }

    public SpellSlotPanel(Spell spell, SlotState slotState){
        this(spell, 1, slotState);
    }

    // getters
    public Spell getSpell(){
        return this.spell;
    }

    public boolean getGrayScale(){
        return this.grayScale;
    }

    public SlotState getSlotState(){
        return this.slotState;
    }

    // setters
    public void setSpell(Spell spell){
        if(this.spell == spell){
            return;
        }

        this.spell = spell;
        this.scaledSpellImage = null;
        this.scaledSpellImageGrayScale = null;
    }

    public void setScaleFactor(int scaleFactor){
        scaleFactor = Math.max(scaleFactor, 1);

        if(this.scaleFactor == scaleFactor){
            return;
        }

        this.scaledSlotImage = null;
        this.scaledSlotImageHighlight = null;
        this.scaledSpellImage = null;
        this.scaledSpellImageGrayScale = null;
        this.scaleFactor = scaleFactor;
    }

    public void setGrayScale(boolean grayScale){
        this.grayScale = grayScale;
    }

    public void setSlotState(SlotState slotState){
        this.slotState = slotState;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(this.getBackground() != null){
            g.setColor(this.getBackground());
            g.fillRect(0, 0, 20*this.scaleFactor, 20*this.scaleFactor);
        }

        switch(this.slotState){
            case SLOT -> {
                if(this.scaledSlotImage == null){
                    this.scaledSlotImage = Global.scaleImage(slotImage, this.scaleFactor);
                }
                g.drawImage(this.scaledSlotImage, 0, 0, this);
            }
            case HIGHLIGHT -> {
                if(this.scaledSlotImageHighlight == null){
                    this.scaledSlotImageHighlight = Global.scaleImage(slotImageHighlight, this.scaleFactor);
                }
                g.drawImage(this.scaledSlotImageHighlight, 0, 0, this);
            }
            case EMPTY -> {}
        }

        if(this.spell == null){
            return;
        }

        if(this.scaledSpellImage == null){
            this.scaledSpellImage = Global.scaleImage(this.spell.getImage(), this.scaleFactor);
        }
        if(this.grayScale && this.scaledSpellImageGrayScale == null){
            this.scaledSpellImageGrayScale = Global.grayscaleImage(this.scaledSpellImage, true);
        }

        g.drawImage(this.grayScale ? this.scaledSpellImageGrayScale : this.scaledSpellImage, 0, 0, this);
    }
}