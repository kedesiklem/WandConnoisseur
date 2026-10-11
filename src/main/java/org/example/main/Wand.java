package org.example.main;

import org.example.spells.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Wand{
    public static class InfoResult{
        public CastState[] castStates;
        public Flowchart[] flowcharts;
        public ImageBuilder flowchartImage;
        public ImageBuilder castStateImage;
        public StringBuilder flowchartString;
        public CardHistory cardHistory;

        public InfoResult(CastState[] castStates, ImageBuilder flowchartImage, ImageBuilder castStateImage, StringBuilder flowchartString, CardHistory cardHistory){
            this.castStates = castStates;
            this.flowchartImage = flowchartImage;
            this.castStateImage = castStateImage;
            this.flowchartString = flowchartString;
            this.cardHistory = cardHistory;
        }
    }
    private static final BufferedImage slotImage = Global.loadImage("./src/main/java/org/example/image/wand/full_inventory_box.png");
    public static final BufferedImage[] statImages = loadStatImages();
    private BufferedImage sprite = null;
    private boolean shuffle;
    private int nbDraw;
    private int castDelay;
    private int rechargeTime;
    private int currentCastDelay = 0;
    private int currentRechargeTime = 0;
    private int waitingTime = 0;
    private int maxMana;
    private int regenMana;
    private int currentMana;
    private int nbSlot;
    private double spread;
    private double speed;
    private final ArrayList<Spell> alwaysCasts = new ArrayList<>();
    private Spell[] spells;
    private CardPool cardPool = new CardPool();

    public Wand(){
        this.shuffle = false;
        this.nbDraw = 1;
        this.castDelay = 0;
        this.rechargeTime = 0;
        this.maxMana = 1000000;
        this.regenMana = 1000000;
        this.currentMana = this.maxMana*60;
        this.nbSlot = 1;
        this.spread = 0.0;
        this.speed = 1.0;
        this.spells = new Spell[this.nbSlot];
        this.cardPool.setRechargeTime(this.rechargeTime);
    }

    public Wand(boolean shuffle, int nbDraw, int castDelay, int rechargeTime, int maxMana, int regenMana, int nbSlot, double spread, double speed){
        this.shuffle = shuffle;
        this.nbDraw = nbDraw;
        this.castDelay = castDelay;
        this.rechargeTime = rechargeTime;
        this.maxMana = maxMana;
        this.regenMana = regenMana;
        this.currentMana = this.maxMana*60;
        this.nbSlot = nbSlot;
        this.spread = spread;
        this.speed = speed;
        this.spells = new Spell[nbSlot];
        this.cardPool.setRechargeTime(this.rechargeTime);
    }

    // setters
    public void setShuffle(boolean shuffle, boolean reset){
        this.shuffle = shuffle;
        this.cardPool.setShuffle(shuffle);
        if(reset){
            this.reset();
        }
    }

    public void setShuffle(boolean shuffle){
        this.setShuffle(shuffle, true);
    }

    public void setNbDraw(int nbDraw, boolean reset){
        this.nbDraw = nbDraw;
        if(reset){
            this.reset();
        }
    }

    public void setNbDraw(int nbDraw){
        this.setNbDraw(nbDraw, true);
    }

    public void setCastDelay(int castDelay, boolean reset){
        this.castDelay = castDelay;
        if(reset){
            this.reset();
        }
    }

    public void setCastDelay(int castDelay){
        this.setCastDelay(castDelay, true);
    }

    public void setRechargeTime(int rechargeTime, boolean reset){
        this.rechargeTime = rechargeTime;
        if(reset){
            this.reset();
        }
    }

    public void setRechargeTime(int rechargeTime){
        this.setRechargeTime(rechargeTime, true);
    }

    public void setMaxMana(int maxMana, boolean reset){
        this.maxMana = maxMana;
        if(reset){
            this.reset();
        }
    }

    public void setMaxMana(int maxMana){
        this.setMaxMana(maxMana, true);
    }

    public void setRegenMana(int regenMana, boolean reset){
        this.regenMana = regenMana;
        if(reset){
            this.reset();
        }
    }

    public void setRegenMana(int regenMana){
        this.setRegenMana(regenMana, true);
    }

    public void setNbSlot(int nbSlot, boolean reset){
        this.nbSlot = nbSlot;
        if(this.nbSlot != this.spells.length){
            Spell[] newSpells = new Spell[this.nbSlot];
            for(int i=0; i < newSpells.length; i++){
                if(i < this.spells.length){
                    newSpells[i] = this.spells[i];
                }
            }
            this.spells = newSpells;
        }
        if(reset){
            this.resetAll();
        }
    }

    public void setNbSlot(int nbSlot){
        this.setNbSlot(nbSlot, true);
    }

    public void setSpread(double spread, boolean reset){
        this.spread = spread;
        if(reset){
            this.reset();
        }
    }

    public void setSpread(int spread){
        this.setSpread(spread, true);
    }

    public void setSpeed(double speed, boolean reset){
        this.speed = speed;
        if(reset){
            this.reset();
        }
    }

    public void setSpeed(int speed){
        this.setSpeed(speed, true);
    }

    public void setAutoFlowchart(boolean autoFlowchart){
        this.cardPool.setAutoFlowchart(autoFlowchart);
    }

    public void setAutoCardHistory(boolean autoCardHistory){
        this.cardPool.setAutoCardHistory(autoCardHistory);
    }

    // getters
    public boolean getShuffle(){
        return this.shuffle;
    }

    public int getNbDraw(){
        return this.nbDraw;
    }

    public int getCastDelay(){
        return this.castDelay;
    }

    public int getRechargeTime(){
        return this.rechargeTime;
    }

    public int getMaxMana(){
        return this.maxMana;
    }

    public int getRegenMana(){
        return this.regenMana;
    }

    public int getNbSlot(){
        return this.nbSlot;
    }

    public double getSpread(){
        return this.spread;
    }

    public double getSpeed(){
        return this.speed;
    }

    public Spell[] getSpells(boolean includeNull){
        if(includeNull){
            return this.spells;
        }

        ArrayList<Spell> spellList = new ArrayList<>();

        for(Spell spell : this.spells){
            if(spell != null){
                spellList.add(spell);
            }
        }

        return spellList.toArray(new Spell[0]);
    }

    public Spell[] getAlwaysCasts(){
        return this.alwaysCasts.toArray(new Spell[0]);
    }

    public BufferedImage getSprite(){
        if(this.sprite == null){
            this.sprite = Global.loadImage(Global.getWandList().getSprite(this));
        }
        return this.sprite;
    }

    private static BufferedImage[] loadStatImages(){
        String path = "./src/main/java/org/example/image/other/";
        String[] statImagesString = new String[]{"shuffle.png", "spell_cast.png", "cast_delay.png", "recharge_time.png", "max_mana.png", "mana_regen.png", "slot.png", "spread.png", "speed.png", "always_cast.png"};
        BufferedImage[] result = new BufferedImage[statImagesString.length];
        int imageScaling = 3;

        for(int i=0; i < result.length; i++){
            result[i] = Global.scaleImage(Global.loadImage(path + statImagesString[i]), imageScaling);
        }

        return result;
    }

    public ImageBuilder getSpellsImage(){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        String[] stats = new String[]{"Shuffle", "Spells/Cast"};
        String[] statValues = new String[2];
        int slotSize = slotImage.getWidth();
        Point currentPoint = new Point(0, 0);
        int margin = 20;
        int textScaling = 3;
        int wandScaling = 6;
        int slotScaling = 3;
        boolean chargeFontLoaded = false;

        statValues[0] = this.shuffle ? "Yes" : "No";
        statValues[1] = String.valueOf(this.nbDraw);

        imageBuilder.setFont(Global.getPixelFont().deriveFont(Font.PLAIN, textScaling*7.5F));
        imageBuilder.setMargin(21);
        imageBuilder.setBorderThickness(3);
        imageBuilder.setBorderColor(new Color(201, 137, 48));

        BufferedImage sprite = Global.scaleImage(this.getSprite(), wandScaling);
        if(sprite != null){
            imageBuilder.addImage(sprite, 0, 0, "wand_sprite");
            currentPoint.x = sprite.getWidth() + margin;
            currentPoint.y = sprite.getHeight() + margin;
        }

        currentPoint.x = imageBuilder.drawTextFixedSpacing(stats, currentPoint.x, wandScaling, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE).x + margin;
        currentPoint.y = Math.max(currentPoint.y, imageBuilder.drawTextFixedSpacing(statValues, currentPoint.x, wandScaling, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE).y + margin);

        BufferedImage scaledSlotImage = Global.scaleImage(slotImage, slotScaling);
        Map<Class<?>, BufferedImage> spellImages = new HashMap<>();
        int maxX = Math.max((int)Math.ceil(Math.sqrt(this.spells.length)), 26);
        int x = 0;
        int y = 0;
        for(Spell spell : this.spells){
            imageBuilder.addImage(scaledSlotImage, x*slotScaling*slotSize, currentPoint.y + y*slotScaling*slotSize, "full_inventory_box");
            if(spell != null){
                Class<?> clazz = spell.getClass();
                BufferedImage scaledImage = spellImages.get(clazz);
                if(scaledImage == null){
                    scaledImage = Global.scaleImage(spell.getImage(), slotScaling);
                    spellImages.put(clazz, scaledImage);
                }

                imageBuilder.addImage(scaledImage, x*slotScaling*slotSize, currentPoint.y + y*slotScaling*slotSize, spell.getClass().getSimpleName());
                if(spell.getHasCharges()){
                    if(!chargeFontLoaded){
                        imageBuilder.setFont(Global.getPixelFontSmall().deriveFont(Font.PLAIN, slotScaling*7.5F));
                        chargeFontLoaded = true;
                    }
                    imageBuilder.drawText(Integer.toString(spell.getChargesLeft()), (int) (slotScaling*(x*slotSize + 1.46)), (int) (currentPoint.y + 1.96*slotScaling) + y*slotScaling*slotSize, Color.WHITE);
                }
            }
            x++;
            if(x >= maxX){
                x = 0;
                y++;
            }
        }

        return imageBuilder;
    }

    public ImageBuilder getStatImage(){
        Map<Class<?>, BufferedImage> spellImages = new HashMap<>();
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        String[] stats = new String[]{"Shuffle", "Spells/Cast", "Cast delay", "Rechrg. Time", "Mana max", "Mana chg. Spd", "Capacity", "Spread", "Speed"};
        String[] statValues = new String[9];
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        int slotSize = slotImage.getWidth();
        int margin = 15;
        int outsideMargin = 18;
        int textScaling = 3;
        int wandScaling = 6;
        int slotScaling = 3;
        BufferedImage sprite = Global.rotateImageByDegrees(Global.scaleImage(this.getSprite(), wandScaling), -90);
        boolean chargeFontLoaded = false;

        imageBuilder.setFont(Global.getPixelFont().deriveFont(Font.PLAIN, textScaling*7.5F));
        imageBuilder.setMargin(3);
        imageBuilder.setBorderThickness(3);
        imageBuilder.setBorderColor(new Color(201, 137, 48));
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("0.##", dfs);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(340);

        statValues[0] = this.shuffle ? "Yes" : "No";
        statValues[1] = String.valueOf(this.nbDraw);
        statValues[2] = Global.delayFormat(this.castDelay);
        statValues[3] = Global.delayFormat(this.rechargeTime);
        statValues[4] = String.valueOf(this.maxMana);
        statValues[5] = String.valueOf(this.regenMana);
        statValues[6] = String.valueOf(this.nbSlot);
        statValues[7] = df.format(this.spread) + " DEG";
        statValues[8] = "x" + df.format(this.speed);

        Point currentPoint = imageBuilder.drawText("WAND", outsideMargin, outsideMargin, Color.WHITE);
        int startY = currentPoint.y + margin;
        currentPoint = imageBuilder.drawTextFixedSpacing(stats, textScaling*Global.getBaseIconSize() + margin + outsideMargin, startY, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE);
        currentPoint = imageBuilder.drawTextFixedSpacing(statValues, currentPoint.x + margin + outsideMargin, startY, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE);
        currentPoint.x += (sprite == null ? 0 : 2*margin);
        currentPoint.y = Math.max(currentPoint.y, sprite == null ? 0 : sprite.getHeight());
        int wandAvailableHeight = currentPoint.y + margin;

        for(int i=0; i < stats.length; i++){
            imageBuilder.addImage(statImages[i], outsideMargin, startY + i*(textScaling*Global.getBaseIconSize() + textScaling), stats[i] + "_" + textScaling);
        }

        if(this.alwaysCasts.size() > 0){
            currentPoint.y += margin;
            imageBuilder.addImage(statImages[statImages.length - 1], outsideMargin, currentPoint.y + (slotScaling*slotSize - Global.getBaseIconSize()*textScaling)/2, "always_cast_" + textScaling);
            int newX = imageBuilder.drawText("Always casts", textScaling*Global.getBaseIconSize() + margin + outsideMargin, currentPoint.y + (slotScaling*slotSize - Global.getBaseIconSize()*textScaling)/2, Color.WHITE).x + 9;
            for(Spell alwaysCast : this.alwaysCasts){
                Class<?> clazz = alwaysCast.getClass();
                BufferedImage scaledImage = spellImages.get(clazz);
                if(scaledImage == null){
                    scaledImage = Global.scaleImage(alwaysCast.getImage(), slotScaling);
                    spellImages.put(clazz, scaledImage);
                }

                imageBuilder.addImage(scaledImage, newX, currentPoint.y, alwaysCast.getClass().getSimpleName());
                newX += slotScaling*(slotSize - 1);
            }
            currentPoint.x = Math.max(currentPoint.x, newX - (sprite == null ? 0 : sprite.getWidth()));
            currentPoint.y += slotScaling*slotSize + margin;
        }

        if(sprite != null){
            imageBuilder.addImage(sprite, currentPoint.x, (wandAvailableHeight - sprite.getHeight())/2, "wand_sprite");
            currentPoint.x += sprite.getWidth();
        }
        imageBuilder.setCanvasWidth(imageBuilder.getCanvasWidth() + outsideMargin);

        BufferedImage scaledSlotImage = Global.scaleImage(slotImage, slotScaling);
        int nbSlotHorizontal = (currentPoint.x - outsideMargin + 2*slotScaling)/(slotScaling*slotSize);
        currentPoint.x = this.nbSlot < nbSlotHorizontal ? outsideMargin - slotScaling : (imageBuilder.getCanvasWidth() - nbSlotHorizontal*slotScaling*slotSize)/2;
        currentPoint.y += margin;
        int x = 0;
        int y = 0;
        for(Spell spell : this.spells){
            imageBuilder.addImage(scaledSlotImage, currentPoint.x + x*slotScaling*slotSize, currentPoint.y + y*slotScaling*slotSize, "full_inventory_box");
            if(spell != null){
                Class<?> clazz = spell.getClass();
                BufferedImage scaledImage = spellImages.get(clazz);
                if(scaledImage == null){
                    scaledImage = Global.scaleImage(spell.getImage(), slotScaling);
                    spellImages.put(clazz, scaledImage);
                }

                imageBuilder.addImage(scaledImage, currentPoint.x + x*slotScaling*slotSize, currentPoint.y + y*slotScaling*slotSize, spell.getClass().getSimpleName());
                if(spell.getHasCharges()){
                    if(!chargeFontLoaded){
                        imageBuilder.setFont(Global.getPixelFontSmall().deriveFont(Font.PLAIN, slotScaling*7.5F));
                        chargeFontLoaded = true;
                    }
                    imageBuilder.drawText(Integer.toString(spell.getChargesLeft()), (int)(currentPoint.x + slotScaling*(x*slotSize + 1.46)), (int)(currentPoint.y + slotScaling*(y*slotSize + 1.96)), Color.WHITE);
                }
            }
            x++;
            if(x >= nbSlotHorizontal){
                x = 0;
                y++;
            }
        }
        imageBuilder.setCanvasHeight(imageBuilder.getCanvasHeight() + outsideMargin);

        return imageBuilder;
    }

    public CardPool getCardPool(){
        return this.cardPool;
    }

    public String getCurrentFlowchartString(boolean formatting){
        return this.cardPool.getFlowchartString(formatting);
    }

    public BufferedImage getCurrentFlowchartImage(){
        return this.cardPool.getFlowchartImage();
    }

    public void saveCurrentFlowchartImage(String filename){
        this.cardPool.saveFlowchartImage(filename);
    }

    public void addAlwaysCast(Spell spell){
        this.alwaysCasts.add(spell);
    }

    public void addAlwaysCast(Spell[] spells){
        this.alwaysCasts.addAll(Arrays.asList(spells));
    }

    public void addAlwaysCast(ArrayList<Spell> spells){
        this.alwaysCasts.addAll(spells);
    }

    public void setAlwaysCasts(Spell[] spells){
        this.alwaysCasts.clear();
        this.alwaysCasts.addAll(Arrays.asList(spells));
    }

    public void setAlwaysCasts(ArrayList<Spell> spells){
        this.alwaysCasts.clear();
        this.alwaysCasts.addAll(spells);
    }

    public boolean putSpell(Spell spell, int slot){
        if(slot >= 0 && slot < this.nbSlot){
            if(spell == null){
                return true;
            }
            this.spells[slot] = spell;
            this.setCardPool();
            return true;
        }else{
            return false;
        }
    }

    public int putSpellEnd(Spell spell){
        int slot = -1;

        for(int i=this.nbSlot - 1; i >= 0; i--){
            if(this.spells[i] != null){
                break;
            }else{
                slot = i;
            }
        }

        if(slot == -1){
            return -1;
        }

        if(putSpell(spell, slot)){
            return slot;
        }else{
            return -1;
        }
    }

    public int putSpellFirstAvailable(Spell spell){
        for(int i=0; i < this.nbSlot; i++){
            if(this.spells[i] == null){
                this.spells[i] = spell;
                return i;
            }
        }

        return -1;
    }

    public void setSpells(Spell[] spells){
        for(int i=0; i < this.spells.length; i++){
            if(i < spells.length){
                this.spells[i] = spells[i];
            }else{
                this.spells[i] = null;
            }
        }
    }

    public boolean removeSpell(int slot){
        if(slot < 0 || slot >= this.nbSlot){
            return false;
        }
        if(this.spells[slot] == null){
            return false;
        }

        this.spells[slot] = null;
        this.setCardPool();
        return true;
    }

    public void removeAllSpell(){
        for(int i=0; i < this.spells.length; i++){
            if(this.spells[i] != null){
                this.spells[i] = null;
            }
        }
        this.setCardPool();
    }

    public boolean swapSpell(int slot1, int slot2){
        if(slot1 < 0 || slot1 >= this.nbSlot){
            return false;
        }
        if(slot2 < 0 || slot2 >= this.nbSlot){
            return false;
        }

        Spell currentSpell1 = this.spells[slot1];
        Spell currentSpell2 = this.spells[slot2];

        this.spells[slot2] = currentSpell1;
        this.spells[slot1] = currentSpell2;

        this.setCardPool();
        return true;
    }

    private void setCardPool(){
        this.cardPool.reset(this.spells);
    }

    public void getAllInfo(boolean allCasts, InfoResult infoResult, boolean formatting){
        ArrayList<CastState> castStateList = infoResult.castStates == null && infoResult.castStateImage == null ? null : new ArrayList<>();
        ArrayList<Flowchart> flowchartList = infoResult.flowchartString == null && infoResult.flowchartImage == null ? null : new ArrayList<>();
        ArrayList<String> castInfo = castStateList == null && flowchartList == null ? null : new ArrayList<>();
        CardPool cardPoolCopy = new CardPool(Arrays.stream(this.spells).map(spell -> spell != null ? spell.clone() : null).toArray(Spell[]::new), this.shuffle);
        int deckSize = cardPoolCopy.getDeckSize();
        boolean historyCreated = false;
        CastState primaryCastState;
        int currentMana = this.maxMana*60;
        int waitingTime = 0;
        int currentCastDelay = 0;
        int currentRechargeTime = 0;
        int nbCast = 0;
        boolean recharged = false;
        int totalDelay = 0;
        int totalMana = 0;

        if(infoResult.flowchartImage != null || infoResult.flowchartString != null){
            cardPoolCopy.setAutoFlowchart(true);
        }
        if(infoResult.cardHistory != null){
            cardPoolCopy.setAutoCardHistory(true);
        }
        cardPoolCopy.setRechargeTime(this.rechargeTime);

        while((!recharged || cardPoolCopy.getDeckSize() != deckSize) && nbCast < deckSize){
            currentMana = Math.min(currentMana + this.regenMana*waitingTime, this.maxMana*60);
            waitingTime = 0;
            nbCast++;
            cardPoolCopy.setStartingStats((int)Math.floor(currentMana/60.0));
            cardPoolCopy.resetFlowchart();
            primaryCastState = new CastState();
            primaryCastState.setCastDelay(this.castDelay);
            primaryCastState.setSpread(this.spread);
            primaryCastState.setSpeedMultiplier(this.speed);
            for(Spell AC : this.alwaysCasts){
                cardPoolCopy.alwaysCast(AC, primaryCastState);
            }
            cardPoolCopy.draw(this.nbDraw, false, primaryCastState);
            currentRechargeTime = cardPoolCopy.getRechargeTime();
            currentCastDelay += primaryCastState.getCastDelay();
            currentMana -= cardPoolCopy.getManaUsage()*60;
            totalMana += cardPoolCopy.getManaUsage();
            if(cardPoolCopy.getWrappedThisCast() || cardPoolCopy.getDeckSize() <= 0){
                if(infoResult.flowchartString != null){
                    infoResult.flowchartString.append("Mana cost: ").append(cardPoolCopy.getManaUsage()).append("\n").append("Recharge time").append(currentCastDelay > currentRechargeTime ? " (cast delay): " : ": ").append(Global.delayFormat(currentRechargeTime)).append("\n").append(nbCast).append(")\n").append(cardPoolCopy.getFlowchartString(formatting));
                }
                waitingTime += Math.max(Math.max(currentCastDelay, currentRechargeTime), 0);
                totalDelay += Math.max(Math.max(currentCastDelay, currentRechargeTime), 1);
                recharged = true;
            }else{
                if(infoResult.flowchartString != null){
                    infoResult.flowchartString.append("Mana cost: ").append(cardPoolCopy.getManaUsage()).append("\n").append("Cast delay: ").append(Global.delayFormat(currentCastDelay)).append("\n").append(nbCast).append(")\n").append(cardPoolCopy.getFlowchartString(formatting)).append(allCasts ? "\n" : "");
                }
                waitingTime += Math.max(currentCastDelay, 0);
                totalDelay += Math.max(currentCastDelay, 1);
                currentCastDelay = 0;
            }
            Global.nextFrame(Math.max(waitingTime, 1));
            cardPoolCopy.endCast();
            if(!allCasts){
                recharged = true;
            }
            primaryCastState.setGlobalValues(cardPoolCopy.CastInfoToString(false));
            if(castStateList != null){
                castStateList.add(primaryCastState);
            }
            if(flowchartList != null){
                Flowchart currentFlowchart = cardPoolCopy.newFlowchart();
                currentFlowchart.refactor();
                flowchartList.add(currentFlowchart);
            }
            if(castInfo != null){
                castInfo.add(cardPoolCopy.CastInfoToString(true));
            }
            if(infoResult.cardHistory != null && !historyCreated){
                infoResult.cardHistory = cardPoolCopy.getCardHistory();
                historyCreated = true;
            }
        }

        if(castStateList != null){
            if(infoResult.castStates != null){
                infoResult.castStates = castStateList.toArray(new CastState[0]);
            }
            if(infoResult.castStateImage != null){
                CastState.addToImage(0, 0, infoResult.castStateImage, "Recharge time: " + Global.delayFormat(currentRechargeTime) + "\nAverage mana cost: " + Global.format((double)totalMana/totalDelay) + "/f (" + (int)Math.max(Math.ceil(60.0*totalMana/totalDelay), 0) + " regen)", castInfo.toArray(new String[0]), castStateList.toArray(new CastState[0]));
            }
        }
        if(flowchartList != null){
            if(infoResult.flowcharts != null){
                infoResult.flowcharts = flowchartList.toArray(new Flowchart[0]);
            }
            if(infoResult.flowchartImage != null){
                Flowchart.addToImage(0, 0, infoResult.flowchartImage, flowchartList.toArray(new Flowchart[0]));
            }
        }
    }

    public CardHistory getCardHistory(boolean allCasts){
        InfoResult result = new InfoResult(null, null, null, null, new CardHistory(this.spells));
        this.getAllInfo(allCasts, result, false);
        return result.cardHistory;
    }

    public String getHistoryString(boolean allCasts){
        return this.getCardHistory(allCasts).toString();
    }

    public String getCallStackString(boolean allCasts){
        return this.getCardHistory(allCasts).callStackToString();
    }

    public String getcardPoolString(boolean allCasts){
        return this.getCardHistory(allCasts).cardPoolToString();
    }

    public String getFlowchartString(boolean allCasts, boolean formatting){
        InfoResult result = new InfoResult(null, null, null, new StringBuilder(), null);
        this.getAllInfo(allCasts, result, formatting);
        return result.flowchartString.toString();
    }

    public CastState[] getCastState(boolean allCasts){
        InfoResult result = new InfoResult(new CastState[0], null, null, null, null);
        this.getAllInfo(allCasts, result, false);
        return result.castStates;
    }

    public BufferedImage getCastStateImage(boolean allCasts){
        InfoResult result = new InfoResult(new CastState[0], null, new ImageBuilder(new Color(0, 0, 0)), null, null);
        this.getAllInfo(allCasts, result, false);
        return result.castStateImage.toPNG();
    }

    public byte[] getCastStateImageSVG(boolean allCasts, boolean embed){
        InfoResult result = new InfoResult(new CastState[0], null, new ImageBuilder(new Color(0, 0, 0)), null, null);
        this.getAllInfo(allCasts, result, false);
        return result.castStateImage.toBytes(embed);
    }

    public boolean saveCastStateImage(String filename, boolean allCasts){
        InfoResult result = new InfoResult(new CastState[0], null, new ImageBuilder(new Color(0, 0, 0)), null, null);
        this.getAllInfo(allCasts, result, false);
        return result.castStateImage.saveAsPNG(filename);
    }

    public BufferedImage getFlowchartImage(boolean allCasts){
        InfoResult result = new InfoResult(null, new ImageBuilder(new Color(0, 0, 0)), null, null, null);
        this.getAllInfo(allCasts, result, false);
        return result.flowchartImage.toPNG();
    }

    public boolean saveFlowchartImage(String filename, boolean allCasts){
        InfoResult result = new InfoResult(null, new ImageBuilder(new Color(0, 0, 0)), null, null, null);
        this.getAllInfo(allCasts, result, false);
        return result.flowchartImage.saveAsPNG(filename);
    }

    public byte[] getFlowchartImageSVG(boolean allCasts, boolean embed){
        InfoResult result = new InfoResult(null, new ImageBuilder(new Color(0, 0, 0)), null, null, null);
        this.getAllInfo(allCasts, result, false);
        return result.flowchartImage.toBytes(embed);
    }

    public boolean saveFlowchartImageSVG(String filename, boolean allCasts, boolean embed){
        InfoResult result = new InfoResult(null, new ImageBuilder(new Color(0, 0, 0)), null, null, null);
        this.getAllInfo(allCasts, result, false);
        return result.flowchartImage.saveAsSVG(filename, embed);
    }

    public void setSprite(BufferedImage image){
        this.sprite = image;
    }

    public boolean setSprite(String path){
        BufferedImage image = Global.loadImage(path);

        if(image != null){
            this.sprite = image;
            return true;
        }

        return false;
    }

    public void reset(){
        this.cardPool.reset();
    }

    public void resetAll(){
        this.cardPool.reset(this.getSpells(false));
    }

    public boolean cast(){
        CastState castState = new CastState();

        if(this.waitingTime <= 0){
            this.cardPool.setStartingStats((int)Math.floor(currentMana/60.0));
            this.cardPool.resetFlowchart();
            castState.setCastDelay(this.castDelay);
            castState.setSpread(this.spread);
            castState.setSpeedMultiplier(this.speed);
            for(Spell AC : this.alwaysCasts){
                this.cardPool.alwaysCast(AC, castState);
            }
            this.cardPool.draw(this.nbDraw, false, castState);
            this.currentRechargeTime = this.cardPool.getRechargeTime();
            this.currentCastDelay += castState.getCastDelay();
            this.currentMana -= this.cardPool.getManaUsage()*60;
            if(this.cardPool.getWrappedThisCast() || this.cardPool.getDeckSize() <= 0){
                this.waitingTime += Math.max(Math.max(this.currentCastDelay, this.currentRechargeTime), 0);
                this.currentCastDelay = 0;
                this.currentRechargeTime = 0;
                this.cardPool.setRechargeTime(this.rechargeTime);
            }else{
                this.waitingTime += Math.max(this.currentCastDelay, 0);
                this.currentCastDelay = 0;
            }
            this.cardPool.endCast();
            return true;
        }else{
            System.out.println("WAITING " + this.waitingTime);
            return false;
        }
    }

    public void nextFrame(){
        if(this.waitingTime > 0){
            this.waitingTime--;
        }
        Global.nextFrame();

        this.currentMana += this.regenMana;
        if(this.currentMana > this.maxMana*60){
            this.currentMana = this.maxMana*60;
        }
    }

    public void nextFrame(int nbFrame){
        if(nbFrame < 1){
            return;
        }
        Global.nextFrame(nbFrame);
        this.waitingTime = Math.max(this.waitingTime - nbFrame, 0);

        this.currentMana += this.regenMana*nbFrame;
        if(this.currentMana > this.maxMana*60){
            this.currentMana = this.maxMana*60;
        }
    }
}