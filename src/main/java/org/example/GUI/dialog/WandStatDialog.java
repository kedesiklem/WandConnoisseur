package org.example.GUI.dialog;

import org.example.GUI.component.AdaptiveComponent;
import org.example.GUI.component.WandStatComponent;
import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.GUI.panel.BooleanFieldPanel;
import org.example.GUI.panel.DoubleFieldPanel;
import org.example.GUI.panel.IntFieldPanel;
import org.example.main.Wand;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.LinkedList;
import javax.swing.*;

public class WandStatDialog extends AdaptiveDialog<AtomicBoolean>{
    public WandStatDialog(Window owner, String title){
        super(owner, title);
    }

    public AtomicBoolean run(Wand wand){
        LinkedList<AdaptiveComponent> componentList = new LinkedList<>();

        WandStatComponent wandComponent = new WandStatComponent(wand, new Fraction(10), new Fraction(10), new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1));

        LayoutConstraints[] confirmLayouts = new LayoutConstraints[2];
        LayoutConstraints.createLayoutHorizontal(confirmLayouts, new Fraction(10), new Fraction(0), wandComponent.getLayoutConstraints().getStartY().clone().add(wandComponent.getLayoutConstraints().getStartHeight()), wandComponent.getLayoutConstraints().getStartWidth().clone().add(20L), new Fraction(30 + 2*10), new Fraction(0), new Fraction(1), true, true, false);

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> {
            boolean isWandUpdated = false;
            Wand wandToUpdate = wandComponent.getWand();
            BooleanFieldPanel shuffleComponent = wandComponent.getShuffleComponent();
            IntFieldPanel drawComponent = wandComponent.getDrawComponent();
            IntFieldPanel castDelayComponent = wandComponent.getCastDelayComponent();
            IntFieldPanel rechargeTimeComponent = wandComponent.getRechargeTimeComponent();
            IntFieldPanel maxManaComponent = wandComponent.getMaxManaComponent();
            IntFieldPanel regenManaComponent = wandComponent.getRegenManaComponent();
            IntFieldPanel slotComponent = wandComponent.getSlotComponent();
            DoubleFieldPanel spreadComponent = wandComponent.getSpreadComponent();
            DoubleFieldPanel speedComponent = wandComponent.getSpeedComponent();

            boolean oldShuffle = shuffleComponent.getInitialValue();
            int oldNbDraw = drawComponent.getInitialValue();
            int oldCastDelay = castDelayComponent.getInitialValue();
            int oldRechargeTime = rechargeTimeComponent.getInitialValue();
            int oldMaxMana = maxManaComponent.getInitialValue();
            int oldRegenMana = regenManaComponent.getInitialValue();
            int oldNbSlot = slotComponent.getInitialValue();
            double oldSpread = spreadComponent.getInitialValue();
            double oldSpeed = speedComponent.getInitialValue();

            boolean shuffle = shuffleComponent.getValue();
            int nbDraw = drawComponent.getValue();
            int castDelay = castDelayComponent.getValue();
            int rechargeTime = rechargeTimeComponent.getValue();
            int maxMana = maxManaComponent.getValue();
            int regenMana = regenManaComponent.getValue();
            int nbSlot = slotComponent.getValue();
            double spread = spreadComponent.getValue();
            double speed = speedComponent.getValue();

            if(oldShuffle != shuffle){
                wandToUpdate.setShuffle(shuffle, false);
                isWandUpdated = true;
            }
            if(oldNbDraw != nbDraw){
                wandToUpdate.setNbDraw(nbDraw, false);
                isWandUpdated = true;
            }
            if(oldCastDelay != castDelay){
                wandToUpdate.setCastDelay(castDelay, false);
                isWandUpdated = true;
            }
            if(oldRechargeTime != rechargeTime){
                wandToUpdate.setRechargeTime(rechargeTime, false);
                isWandUpdated = true;
            }
            if(oldMaxMana != maxMana){
                wandToUpdate.setMaxMana(maxMana, false);
                isWandUpdated = true;
            }
            if(oldRegenMana != regenMana){
                wandToUpdate.setRegenMana(regenMana, false);
                isWandUpdated = true;
            }
            if(oldNbSlot != nbSlot){
                wandToUpdate.setNbSlot(nbSlot, false);
                isWandUpdated = true;
            }
            if(oldSpread != spread){
                wandToUpdate.setSpread(spread, false);
                isWandUpdated = true;
            }
            if(oldSpeed != speed){
                wandToUpdate.setSpeed(speed, false);
                isWandUpdated = true;
            }

            if(isWandUpdated){
                if(oldNbSlot != nbSlot){
                    wandToUpdate.resetAll();
                }else{
                    wandToUpdate.reset();
                }
            }

            WandStatDialog.this.close(new AtomicBoolean(isWandUpdated));
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> WandStatDialog.this.close(new AtomicBoolean(false)));

        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "OK");
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CANCEL");
        this.getRootPane().getActionMap().put("OK", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                buttonOK.doClick();
            }
        });
        this.getRootPane().getActionMap().put("CANCEL", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                buttonCancel.doClick();
            }
        });

        componentList.add(wandComponent);
        componentList.add(new AdaptiveComponent(buttonOK, confirmLayouts[0]));
        componentList.add(new AdaptiveComponent(buttonCancel, confirmLayouts[1]));

        this.startDimension.setSize(confirmLayouts[1].getStartX().clone().add(confirmLayouts[1].getStartWidth()).toLong() + 10, confirmLayouts[1].getStartY().clone().add(confirmLayouts[1].getStartHeight()).toLong() + 10);

        return this.endRun(componentList);
    }
}