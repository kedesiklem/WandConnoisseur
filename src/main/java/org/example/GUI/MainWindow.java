package org.example.GUI;

import org.example.GUI.component.AdaptiveComponent;
import org.example.GUI.component.RootComponent;
import org.example.GUI.component.SpellSelectorComponent;
import org.example.GUI.component.WandComponent;
import org.example.GUI.panel.ImageAreaPanel;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow{
    private static MainWindow instance;
    private JFrame frame;
    private RootComponent root;

    private MainWindow(){}

    public static MainWindow getInstance(){
        if(instance == null){
            instance = new MainWindow();
        }
        return instance;
    }

    // TODO
    // charges see + modify
    // zeta slot
    // number projectile/enemies
    // infinite spells
    // frames
    // many parameters (color, scaling, localization)
    // other commands as windows (wisp, spell query, text?, etc)
    // deck animation
    // enter spells with text somehow
    // spell searcher/filter
    // player ?
    public void run(){
        if(this.frame != null && this.frame.isDisplayable()){
            this.frame.toFront();
            this.frame.requestFocus();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if(this.frame != null && this.frame.isDisplayable()){
                this.frame.toFront();
                this.frame.requestFocus();
                return;
            }

            this.frame = new JFrame("Wand Connoisseur");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setBackground(Color.green);
            this.frame.getContentPane().setBackground(Color.green);
            this.frame.getContentPane().setLayout(null);

            int slotSize = 20;
            int margin = 3;
            int scaleFactor = Global.getScaleFactor();
            int scrollBarWidth = UIManager.getInt("ScrollBar.width");

            SpellSelectorComponent spellSelectorComponent = new SpellSelectorComponent(Global.getSpellList().getSpells(false), new LayoutConstraints(
                new Fraction(10),
                new Fraction(10),
                new Fraction(4*scaleFactor*(slotSize + margin + 2) + scrollBarWidth),
                new Fraction(scaleFactor*Spell.SpellType.values().length*slotSize),
                new Fraction(0),
                new Fraction(0),
                new Fraction(1, 3),
                new Fraction(1)
            ));

            Wand wand  = new Wand(false, 1, 0, 0, 1000000, 1000000, 26, 0.0, 1.0);
            WandComponent wandComponent = new WandComponent(wand, new LayoutConstraints(
                new Fraction(spellSelectorComponent.getLayoutConstraints().getStartX().toLong() + spellSelectorComponent.getLayoutConstraints().getStartWidth().toLong() + 10),
                new Fraction(10), new Fraction(scaleFactor*(6*slotSize + 7*margin + 2) + scrollBarWidth),
                new Fraction(scaleFactor*(2*slotSize + 3*margin + 2)),
                new Fraction(1, 3), new Fraction(0),
                new Fraction(2, 3), new Fraction(0)
            ));
            spellSelectorComponent.setRelatedWand(wandComponent);

            ImageAreaPanel imageAreaFlowchart = new ImageAreaPanel(wand.getFlowchartImage(true));
            wandComponent.setFlowchart(imageAreaFlowchart);
            AdaptiveComponent flowchartComponent = new AdaptiveComponent(imageAreaFlowchart, new LayoutConstraints(
                wandComponent.getLayoutConstraints().getStartX().clone(),
                new Fraction(wandComponent.getLayoutConstraints().getStartY().toLong() + wandComponent.getLayoutConstraints().getStartHeight().toLong() + 10),
                wandComponent.getLayoutConstraints().getStartWidth().clone().subtract(10L).divide(2L),
                new Fraction(spellSelectorComponent.getLayoutConstraints().getStartHeight().toLong() - wandComponent.getLayoutConstraints().getStartHeight().toLong() - 10),
                new Fraction(1, 3),
                new Fraction(0),
                new Fraction(1, 3),
                new Fraction(1)
            ));

            ImageAreaPanel imageAreaCastState = new ImageAreaPanel(wand.getCastStateImage(true));
            wandComponent.setCastState(imageAreaCastState);
            AdaptiveComponent castStateComponent = new AdaptiveComponent(imageAreaCastState, new LayoutConstraints(
                new Fraction(flowchartComponent.getLayoutConstraints().getStartX().toLong() + flowchartComponent.getLayoutConstraints().getStartWidth().toLong() + 10),
                flowchartComponent.getLayoutConstraints().getStartY().clone(),
                flowchartComponent.getLayoutConstraints().getStartWidth().clone(),
                flowchartComponent.getLayoutConstraints().getStartHeight().clone(),
                new Fraction(2, 3),
                new Fraction(0),
                new Fraction(1, 3),
                new Fraction(1)
            ));

            Dimension startDimension = new Dimension((int)(10 + castStateComponent.getLayoutConstraints().getStartX().toLong() + castStateComponent.getLayoutConstraints().getStartWidth().toLong()), (int)(10 + castStateComponent.getLayoutConstraints().getStartY().toLong() + castStateComponent.getLayoutConstraints().getStartHeight().toLong()));
            this.frame.getContentPane().setPreferredSize(startDimension);
            this.frame.getContentPane().setMinimumSize(startDimension);

            this.root = new RootComponent(this.frame);
            this.root.addPanel(spellSelectorComponent);
            this.root.addPanel(wandComponent);
            this.root.addPanel(flowchartComponent);
            this.root.addPanel(castStateComponent);
            new DragAndDropManager(this.root);

            this.frame.pack();
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
            SwingUtilities.invokeLater(() -> {
                this.frame.setMinimumSize(frame.getSize());
                this.root.ready();
            });
        });
    }
}