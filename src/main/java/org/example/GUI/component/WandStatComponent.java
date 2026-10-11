package org.example.GUI.component;

import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.GUI.panel.BooleanFieldPanel;
import org.example.GUI.panel.DoubleFieldPanel;
import org.example.GUI.panel.IntFieldPanel;
import org.example.main.Global;
import org.example.main.Wand;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class WandStatComponent extends AdaptiveComponent{
    private final Wand wand;
    private final BooleanFieldPanel shuffleComponent;
    private final IntFieldPanel drawComponent;
    private final IntFieldPanel castDelayComponent;
    private final IntFieldPanel rechargeTimeComponent;
    private final IntFieldPanel maxManaComponent;
    private final IntFieldPanel regenManaComponent;
    private final IntFieldPanel slotComponent;
    private final DoubleFieldPanel spreadComponent;
    private final DoubleFieldPanel speedComponent;

    public WandStatComponent(Wand wand, LayoutConstraints layoutConstraints){
        super(new JPanel(), layoutConstraints);
        String[] stats = new String[]{"shuffle", "Spell/Cast", "Cast delay", "Rechrg time", "Mana max", "Mana chg. Spd", "Capacity", "Spread", "Speed"};
        LayoutConstraints[] panelConstraints = new LayoutConstraints[stats.length];
        Font font = Global.getPixelFont().deriveFont(((float)7.5*Global.getScaleFactor()));

        this.wand = wand == null ? new Wand() : wand;
        this.component.setBackground(Color.BLACK);
        this.shuffleComponent = new BooleanFieldPanel(this.wand.getShuffle());
        this.drawComponent = new IntFieldPanel(this.wand.getNbDraw(), true);
        this.castDelayComponent = new IntFieldPanel(this.wand.getCastDelay(), false);
        this.rechargeTimeComponent = new IntFieldPanel(this.wand.getRechargeTime(), false);
        this.maxManaComponent = new IntFieldPanel(this.wand.getMaxMana(), true);
        this.regenManaComponent = new IntFieldPanel(this.wand.getRegenMana(), true);
        this.slotComponent = new IntFieldPanel(this.wand.getNbSlot(), true);
        this.spreadComponent = new DoubleFieldPanel(this.wand.getSpread(), false);
        this.speedComponent = new DoubleFieldPanel(this.wand.getSpeed(), true);

        FontMetrics fm = this.shuffleComponent.getFontMetrics(font);
        int maxTextWidth = 0;

        for(String stat : stats){
            maxTextWidth = Math.max(maxTextWidth, fm.stringWidth(stat));
        }
        maxTextWidth += 20;

        BufferedImage wandImage = Global.rotateImageByDegrees(Global.scaleImage(Global.loadImage(Global.getWandList().getSprite(this.wand)), 2*this.scaleFactor), -90);
        LayoutConstraints.createLayoutVertical(panelConstraints, new Fraction(10), new Fraction(0), new Fraction(0), this.getLayoutConstraints().getStartWidth().clone().subtract((long)wandImage.getWidth()), this.getLayoutConstraints().getHeight(), new Fraction(0), new Fraction(0), true, true, true);
        Fraction textHeight = panelConstraints[0].getStartHeight();

        for(int i=0; i < panelConstraints.length; i++){
            JPanel currentPanel = new JPanel();
            JLabel currentLabel = new JLabel(stats[i]);
            JComponent currentText = switch(i){
                case 0 -> this.shuffleComponent;
                case 1 -> this.drawComponent;
                case 2 -> this.castDelayComponent;
                case 3 -> this.rechargeTimeComponent;
                case 4 -> this.maxManaComponent;
                case 5 -> this.regenManaComponent;
                case 6 -> this.slotComponent;
                case 7 -> this.spreadComponent;
                default -> this.speedComponent;
            };

            currentPanel.setBorder(null);
            currentPanel.setBackground(null);

            currentLabel.setBorder(null);
            currentLabel.setBackground(null);
            currentLabel.setForeground(Color.WHITE);
            currentLabel.setFont(Global.getPixelFont().deriveFont((float)7.5*Global.getScaleFactor()));

            currentText.setBorder(null);
            currentText.setBackground(null);
            currentText.setForeground(Color.WHITE);
            if(currentText instanceof JTextField jTextField){
                jTextField.setCaretColor(Color.WHITE);
            }
            currentText.setFont(Global.getPixelFont().deriveFont((float)7.5*Global.getScaleFactor()));

            AdaptiveComponent currentPanelComponent = new AdaptiveComponent(currentPanel, panelConstraints[i]);
            AdaptiveComponent currentLabelComponent = new AdaptiveComponent(currentLabel, new LayoutConstraints(new Fraction(0), new Fraction(0), new Fraction(maxTextWidth), textHeight.clone(), new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(1)));
            AdaptiveComponent currentTextComponent = new AdaptiveComponent(currentText, new LayoutConstraints(new Fraction(maxTextWidth), new Fraction(0), this.getLayoutConstraints().getWidth().clone().subtract((long)maxTextWidth + wandImage.getWidth()), textHeight.clone(), new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1)));

            this.addPanel(currentPanelComponent);
            currentPanelComponent.addPanel(currentLabelComponent);
            currentPanelComponent.addPanel(currentTextComponent);
        }

        JLabel wandSpriteLabel = new JLabel(new ImageIcon(Global.rotateImageByDegrees(Global.scaleImage(Global.loadImage(Global.getWandList().getSprite(this.wand)), 2*this.scaleFactor), -90)));
        wandSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wandSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        wandSpriteLabel.setBackground(null);
        wandSpriteLabel.setOpaque(false);
        wandSpriteLabel.setBorder(null);
        AdaptiveComponent wandSpriteComponent = new AdaptiveComponent(wandSpriteLabel, new LayoutConstraints(this.getLayoutConstraints().getStartWidth().clone().subtract((long)wandImage.getWidth()), new Fraction(0), new Fraction(wandImage.getWidth()), this.getLayoutConstraints().getHeight().clone(), new Fraction(1), new Fraction(0), new Fraction(0), new Fraction(1)));
        this.addPanel(wandSpriteComponent);
    }

    public WandStatComponent(Wand wand, Fraction x, Fraction y, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        super(new JPanel(), new LayoutConstraints(x, y, new Fraction(0), new Fraction(0), xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate));
        String[] stats = new String[]{"shuffle", "Spell/Cast", "Cast delay", "Rechrg time", "Mana max", "Mana chg. Spd", "Capacity", "Spread", "Speed"};
        LayoutConstraints[] panelConstraints = new LayoutConstraints[stats.length];
        Font font = Global.getPixelFont().deriveFont(((float)7.5*Global.getScaleFactor()));

        this.wand = wand == null ? new Wand() : wand;
        this.component.setBackground(Color.BLACK);
        this.shuffleComponent = new BooleanFieldPanel(this.wand.getShuffle());
        this.drawComponent = new IntFieldPanel(this.wand.getNbDraw(), true);
        this.castDelayComponent = new IntFieldPanel(this.wand.getCastDelay(), false);
        this.rechargeTimeComponent = new IntFieldPanel(this.wand.getRechargeTime(), false);
        this.maxManaComponent = new IntFieldPanel(this.wand.getMaxMana(), true);
        this.regenManaComponent = new IntFieldPanel(this.wand.getRegenMana(), true);
        this.slotComponent = new IntFieldPanel(this.wand.getNbSlot(), true);
        this.spreadComponent = new DoubleFieldPanel(this.wand.getSpread(), false);
        this.speedComponent = new DoubleFieldPanel(this.wand.getSpeed(), true);

        FontMetrics fm = this.shuffleComponent.getFontMetrics(font);
        int maxTextWidth = 0;
        int maxTextWidth2 = 0;

        for(String stat : stats){
            maxTextWidth = Math.max(maxTextWidth, fm.stringWidth(stat));
        }
        maxTextWidth += 20;

        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.shuffleComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.drawComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.castDelayComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.rechargeTimeComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.maxManaComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.regenManaComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.slotComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.spreadComponent.getText()));
        maxTextWidth2 = Math.max(maxTextWidth2, fm.stringWidth(this.speedComponent.getText()));
        maxTextWidth2 += 20;

        BufferedImage wandImage = Global.rotateImageByDegrees(Global.scaleImage(Global.loadImage(Global.getWandList().getSprite(this.wand)), 2*this.scaleFactor), -90);
        LayoutConstraints.createLayoutVerticalUnbound(panelConstraints, new Fraction(10), new Fraction(0), new Fraction(0), new Fraction(maxTextWidth + maxTextWidth2 + 2*10), new Fraction(fm.getHeight()), new Fraction(0), new Fraction(0), true, true, true);
        Fraction textHeight = panelConstraints[0].getStartHeight();

        for(int i=0; i < panelConstraints.length; i++){
            JPanel currentPanel = new JPanel();
            JLabel currentLabel = new JLabel(stats[i]);
            JComponent currentText = switch(i){
                case 0 -> this.shuffleComponent;
                case 1 -> this.drawComponent;
                case 2 -> this.castDelayComponent;
                case 3 -> this.rechargeTimeComponent;
                case 4 -> this.maxManaComponent;
                case 5 -> this.regenManaComponent;
                case 6 -> this.slotComponent;
                case 7 -> this.spreadComponent;
                default -> this.speedComponent;
            };

            currentPanel.setBorder(null);
            currentPanel.setBackground(null);

            currentLabel.setBorder(null);
            currentLabel.setBackground(null);
            currentLabel.setForeground(Color.WHITE);
            currentLabel.setFont(Global.getPixelFont().deriveFont((float)7.5*Global.getScaleFactor()));

            currentText.setBorder(null);
            currentText.setBackground(null);
            currentText.setForeground(Color.WHITE);
            if(currentText instanceof JTextField jTextField){
                jTextField.setCaretColor(Color.WHITE);
            }
            currentText.setFont(Global.getPixelFont().deriveFont((float)7.5*Global.getScaleFactor()));

            AdaptiveComponent currentPanelComponent = new AdaptiveComponent(currentPanel, panelConstraints[i]);
            AdaptiveComponent currentLabelComponent = new AdaptiveComponent(currentLabel, new LayoutConstraints(new Fraction(0), new Fraction(0), new Fraction(maxTextWidth), textHeight.clone(), new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(1)));
            AdaptiveComponent currentTextComponent = new AdaptiveComponent(currentText, new LayoutConstraints(new Fraction(maxTextWidth), new Fraction(0), new Fraction(maxTextWidth2), textHeight.clone(), new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1)));

            this.addPanel(currentPanelComponent);
            currentPanelComponent.addPanel(currentLabelComponent);
            currentPanelComponent.addPanel(currentTextComponent);
        }

        JLabel wandSpriteLabel = new JLabel(new ImageIcon(Global.rotateImageByDegrees(Global.scaleImage(Global.loadImage(Global.getWandList().getSprite(this.wand)), 2*this.scaleFactor), -90)));
        wandSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wandSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        wandSpriteLabel.setBackground(null);
        wandSpriteLabel.setBorder(null);

        this.layoutConstraints.setStartWidth(maxTextWidth + maxTextWidth2 + wandImage.getWidth() + 2*10);
        this.layoutConstraints.setStartHeight(panelConstraints[panelConstraints.length - 1].getStartY().clone().add(panelConstraints[panelConstraints.length - 1].getStartHeight()).add(10L));

        AdaptiveComponent wandSpriteComponent = new AdaptiveComponent(wandSpriteLabel, new Fraction(maxTextWidth + maxTextWidth2 + 2*10), new Fraction(0), new Fraction(wandImage.getWidth()), this.getLayoutConstraints().getStartHeight().clone(), new Fraction(1), new Fraction(0), new Fraction(0), new Fraction(1));
        this.addPanel(wandSpriteComponent);
    }

    // getters
    public Wand getWand(){
        return this.wand;
    }

    public BooleanFieldPanel getShuffleComponent(){
        return this.shuffleComponent;
    }

    public IntFieldPanel getDrawComponent(){
        return this.drawComponent;
    }

    public IntFieldPanel getCastDelayComponent(){
        return this.castDelayComponent;
    }

    public IntFieldPanel getRechargeTimeComponent(){
        return this.rechargeTimeComponent;
    }

    public IntFieldPanel getMaxManaComponent(){
        return this.maxManaComponent;
    }

    public IntFieldPanel getRegenManaComponent(){
        return this.regenManaComponent;
    }

    public IntFieldPanel getSlotComponent(){
        return this.slotComponent;
    }

    public DoubleFieldPanel getSpreadComponent(){
        return this.spreadComponent;
    }

    public DoubleFieldPanel getSpeedComponent(){
        return this.speedComponent;
    }
}