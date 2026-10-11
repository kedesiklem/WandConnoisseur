package org.example.GUI.component;

import org.example.GUI.component.SpellSlotComponent.SlotType;
import org.example.GUI.dialog.WandStatDialog;
import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.GUI.panel.ImageAreaPanel;
import org.example.GUI.RelativeLayoutConstraints;
import org.example.main.CastState;
import org.example.main.Global;
import org.example.main.ImageBuilder;
import org.example.main.Wand;
import org.example.main.Wand.InfoResult;
import org.example.spells.Spell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class WandComponent extends AdaptiveComponent{
    private final Wand wand;
    private final SpellHolderComponent spellHolder;
    private ImageAreaPanel relatedFlowchart = null;
    private ImageAreaPanel relatedCastState = null;

    public WandComponent(Wand wand, LayoutConstraints layoutConstraints){
        super(new JPanel(), layoutConstraints);
        this.wand = wand == null ? new Wand() : wand;
        this.spellHolder = new SpellHolderComponent(this.wand.getSpells(true), SlotType.SLOT, new RelativeLayoutConstraints(new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1)));
        if(this.spellHolder.component instanceof JScrollPane scrollPane){
            scrollPane.setViewportBorder(BorderFactory.createLineBorder(new Color(255, 174, 62), this.scaleFactor));
        }
        this.addPanel(this.spellHolder);

        JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem itemEdit = new JMenuItem("Edit");
            popupMenu.add(itemEdit);
            itemEdit.addActionListener(e -> {
                AtomicBoolean updateWand = new WandStatDialog(SwingUtilities.getWindowAncestor(this.component), "wand stats").run(this.wand);
                if(updateWand != null && updateWand.get()){
                    this.updateSlots();
                    this.updateSpellsFromComponent();
                }
            });
            JMenu menuClipboard = new JMenu("Copy to clipboard");
            popupMenu.add(menuClipboard);
                JMenuItem itemSpellString = new JMenuItem("Spells");
                menuClipboard.add(itemSpellString);
                itemSpellString.addActionListener(e -> {
                    Spell[] spells = this.wand.getSpells(false);
                    StringBuilder result = new StringBuilder();
                    Spell lastSpell = null;
                    String lastCharge = "";
                    int count = 1;

                    for(Spell spell : spells){
                        String currentCharge = spell.getHasCharges() ? (spell.getMaxCharges() == spell.getChargesLeft() ? (spell.getNeverUnlimited() ? "" : "max:") : spell.getChargesLeft() + ":") : (spell.getMaxCharges() == 0 ? "" : "inf:") ;
                        if(lastSpell != null && spell.getClass() == lastSpell.getClass() && lastCharge.equals(currentCharge)){
                            count++;
                            continue;
                        }

                        if(lastSpell != null){
                            result.append(result.isEmpty() ? "" : ", ").append(lastCharge).append(lastSpell.getShortestAlias()).append(count == 1 ? "" : ":" + count);
                        }
                        count = 1;
                        lastSpell = spell;
                        lastCharge = currentCharge;
                    }
                    if(lastSpell != null){
                        result.append(result.isEmpty() ? "" : ", ").append(lastCharge).append(lastSpell.getShortestAlias()).append(count == 1 ? "" : ":" + count);
                    }

                    Global.toClipboard(result.toString());
                });
                JMenu menuWand_1 = new JMenu("Wand image");
                menuClipboard.add(menuWand_1);
                    JMenuItem itemWandPng_1 = new JMenuItem("as PNG");
                    menuWand_1.add(itemWandPng_1);
                    itemWandPng_1.addActionListener(e -> Global.toClipboard(this.wand.getSpellsImage().toPNG()));
                    JMenuItem itemWandSvg_1 = new JMenuItem("as SVG");
                    menuWand_1.add(itemWandSvg_1);
                    itemWandSvg_1.addActionListener(e -> Global.toClipboard(this.wand.getSpellsImage().toBytes(true), Global.MimeType.SVG));
                JMenu menuWandStat_1 = new JMenu("Wand stat image");
                menuClipboard.add(menuWandStat_1);
                    JMenuItem itemWandStatPng_1 = new JMenuItem("as PNG");
                    menuWandStat_1.add(itemWandStatPng_1);
                    itemWandStatPng_1.addActionListener(e -> Global.toClipboard(this.wand.getStatImage().toPNG()));
                    JMenuItem itemWandStatSvg_1 = new JMenuItem("as SVG");
                    menuWandStat_1.add(itemWandStatSvg_1);
                    itemWandStatSvg_1.addActionListener(e -> Global.toClipboard(this.wand.getStatImage().toBytes(true), Global.MimeType.SVG));
            JMenu menuExport = new JMenu("Export");
            popupMenu.add(menuExport);
                JMenu menuWand_2 = new JMenu("Wand image");
                menuExport.add(menuWand_2);
                    JMenuItem itemWandPng_2 = new JMenuItem("as PNG");
                    menuWand_2.add(itemWandPng_2);
                    itemWandPng_2.addActionListener(e -> this.wand.getSpellsImage().saveAsPNG(Global.getPathOutput() + "SpellImage.png"));
                    JMenuItem itemWandSvg_2 = new JMenuItem("as SVG");
                    menuWand_2.add(itemWandSvg_2);
                    itemWandSvg_2.addActionListener(e -> this.wand.getSpellsImage().saveAsSVG(Global.getPathOutput() + "SpellImage.svg", true));
                JMenu menuWandStat_2 = new JMenu("Wand stat image");
                menuExport.add(menuWandStat_2);
                    JMenuItem itemWandStatPng_2 = new JMenuItem("as PNG");
                    menuWandStat_2.add(itemWandStatPng_2);
                    itemWandStatPng_2.addActionListener(e -> this.wand.getStatImage().saveAsPNG(Global.getPathOutput() + "StatImage.png"));
                    JMenuItem itemWandStatSvg_2 = new JMenuItem("as SVG");
                    menuWandStat_2.add(itemWandStatSvg_2);
                    itemWandStatSvg_2.addActionListener(e -> this.wand.getStatImage().saveAsSVG(Global.getPathOutput() + "StatImage.svg", true));

        MouseAdapter mouseHandler = new MouseAdapter(){
            private void showPopup(MouseEvent e){
                if(e.isPopupTrigger()){
                    popupMenu.show(component, e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e){
                if(e.isPopupTrigger()){
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                if(e.isPopupTrigger()){
                    showPopup(e);
                }
            }
        };

        this.spellHolder.component.addMouseListener(mouseHandler);
    }

    public void setFlowchart(ImageAreaPanel flowchart){
        this.relatedFlowchart = flowchart;
    }

    public void setCastState(ImageAreaPanel castState){
        this.relatedCastState = castState;
    }

    public void setSpell(Spell spell, int id){
        if(id < 0 || id >= this.wand.getNbSlot()){
            return;
        }

        SpellSlotComponent[] spellSlotComponents = this.spellHolder.getSpellSlotComponents();

        if(id >= spellSlotComponents.length){
            return;
        }

        this.wand.putSpell(spell, id);
        spellSlotComponents[id].setSpell(spell);
        this.updateAllRelatedInfo();
    }

    public void putSpellFirstAvailable(Spell spell){
        int id = this.wand.putSpellFirstAvailable(spell);
        if(id != -1){
            SpellSlotComponent[] spellSlotComponents = this.spellHolder.getSpellSlotComponents();
            spellSlotComponents[id].setSpell(spell);
            spellSlotComponents[id].component.repaint();
            this.updateAllRelatedInfo();
        }
    }

    private void updateFlowchart(){
        if(this.relatedFlowchart != null){
            this.relatedFlowchart.setImage(this.wand.getFlowchartImage(true));
        }
    }

    private void updateCastState(){
        if(this.relatedCastState != null){
            this.relatedCastState.setImage(this.wand.getCastStateImage(true));
        }
    }

    public void updateSlots(){
        this.spellHolder.updateSlots(this.wand.getSpells(true));
    }

    public void updateAllRelatedInfo(){
        InfoResult result = new InfoResult(this.relatedCastState == null ? null : new CastState[0], this.relatedFlowchart == null ? null : new ImageBuilder(new Color(0, 0, 0)), this.relatedCastState == null ? null : new ImageBuilder(new Color(0, 0, 0)), null, null);
        this.wand.getAllInfo(true, result, false);

        if(result.flowchartImage != null){
            this.relatedFlowchart.setImage(result.flowchartImage.toPNG());
        }
        if(result.castStateImage != null){
            this.relatedCastState.setImage(result.castStateImage.toPNG());
        }
    }

    public void updateSpellsFromComponent(){
        this.wand.setSpells(this.spellHolder.getSpells());
        this.updateAllRelatedInfo();
    }
}