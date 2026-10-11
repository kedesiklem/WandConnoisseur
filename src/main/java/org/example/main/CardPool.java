package org.example.main;

import org.example.spells.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.StringBuilder;
import java.util.*;
import java.util.List;

public class CardPool{
    private List<Spell> startingDeck;
    private List<Spell> deck;
    private List<Spell> hand;
    private List<Spell> discard;
    private boolean drawEnabled = true;
    private boolean wrappedThisCast = false;
    private boolean forceStopDraws = false;
    private boolean playingPermanentCard = false;
    private boolean autoFlowchart = false;
    private boolean autoCardHistory = false;
    private Flowchart flowchart = new Flowchart();
    private CardHistory cardHistory;
    private List<Flowchart> callStack = new ArrayList<>();
    private int maxMana = 0;
    private int manaUsage = 0;
    private boolean shuffle;
    private int rechargeTime = 0;
    private double recoil = 0.0;

    public CardPool(){
        this.startingDeck = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
        this.cardHistory = new CardHistory(new Spell[0]);
        this.shuffle = false;
    }

    public CardPool(Spell[] spells, boolean shuffle){
        this.startingDeck = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
        for(Spell spell : spells){
            if(spell != null){
                this.deck.add(spell);
                this.startingDeck.add(spell);
            }
        }
        this.cardHistory = new CardHistory(this.deck.toArray(new Spell[0]));
        this.shuffle = shuffle;
    }

    // setters
    public void setShuffle(boolean shuffle){
        this.shuffle = shuffle;
    }

    public void setManaUsage(int manaUsage){
        this.manaUsage = manaUsage;
    }

    public void setAutoFlowchart(boolean autoFlowchart){
        if(!autoFlowchart && this.autoFlowchart){
            this.flowchart.reset();
        }
        this.autoFlowchart = autoFlowchart;
    }

    public void setAutoCardHistory(boolean autoCardHistory){
        if(!autoCardHistory && this.autoCardHistory){
            this.cardHistory.reset();
        }
        this.autoCardHistory = autoCardHistory;
    }

    private void updateCardHistory(boolean callStack){
        if(!this.autoCardHistory){
            return;
        }

        if(callStack){
            Spell[] callStackSpell = new Spell[this.callStack.size()];
            for(int i=0; i < callStackSpell.length; i++){
                callStackSpell[i] = this.callStack.get(i).getSelf();
            }
            this.cardHistory.addCallStack(callStackSpell);
        }else{
            this.cardHistory.addStep(this.discard.toArray(new Spell[0]), this.hand.toArray(new Spell[0]), this.deck.toArray(new Spell[0]));
        }
    }

    public void addCallStack(Flowchart currentNode, Spell spell, boolean alwaysCast, boolean isCopy, boolean copyFailed){
        if(!this.autoFlowchart && !this.autoCardHistory){
            return;
        }

        Flowchart newNode = currentNode.add(spell);
        if(alwaysCast){
            newNode.setAlwaysCast(true);
        }else if(isCopy){
            newNode.setIsCopy(true);
            if(copyFailed){
                newNode.setCopyFailed(true);
            }
        }
        if(!copyFailed){
            this.callStack.add(newNode);
            this.updateCardHistory(true);
        }
    }

    public void addCallStackCopy(Spell spell, boolean copyFailed){
        this.addCallStack(this.getCallStack(), spell, false, true, copyFailed);
    }

    public void addCallStackSpell(Flowchart node, Spell spell){
        this.addCallStack(node, spell, false, false, false);
    }

    public void addCallStackAC(Flowchart node, Spell spell){
        this.addCallStack(node, spell, true, false, false);
    }

    public void removeCallStack(){
        if(!this.autoFlowchart && !this.autoCardHistory){
            return;
        }

        if(this.callStack.size() > 0){
            this.callStack.remove(this.callStack.size() - 1);
            this.updateCardHistory(true);
        }
    }

    private void addEmptyFlowchart(Flowchart node){
        if(this.autoFlowchart){
            node.add(null);
        }
    }

    public Flowchart getCallStack(){
        Flowchart[] callStackArray = this.callStack.toArray(new Flowchart[0]);

        if(callStackArray.length == 0){
            return this.flowchart;
        }
        return callStackArray[callStackArray.length - 1];
    }

    public CardHistory getCardHistory(){
        return this.cardHistory;
    }

    public void setStartingStats(int maxMana){
        this.maxMana = maxMana;
        this.manaUsage = 0;
        this.recoil = 0;
        if(this.shuffle && this.shuffleDeck()){
            this.updateCardHistory(false);
        }
    }

    public int getMaxMana(){
        return this.maxMana;
    }

    public void addManaUsage(int manaUsage){
        this.manaUsage += manaUsage;
    }

    public void setRechargeTime(int rechargeTime){
        this.rechargeTime = rechargeTime;
    }

    public void addRechargeTime(int rechargeTime){
        this.rechargeTime += rechargeTime;
    }

    public void addRecoil(double recoil){
        this.recoil += recoil;
    }

    public void setRecoil(double recoil){
        this.recoil = recoil;
    }

    // getters
    public boolean getAutoFlowchart(){
        return this.autoFlowchart;
    }

    public boolean getAutoCardHistory(){
        return this.autoCardHistory;
    }

    public String getFlowchartString(boolean formatting){
        this.flowchart.refactor();
        return this.flowchart.toString(formatting);
    }

    public String getHistoryString(){
        return this.cardHistory.toString();
    }

    public String getCallStackString(){
        return this.cardHistory.callStackToString();
    }

    public String getCardPoolString(){
        return this.cardHistory.cardPoolToString();
    }

    public BufferedImage getFlowchartImage(){
        this.flowchart.refactor();
        return this.flowchart.toImage();
    }

    public void saveFlowchartImage(String filename){
        this.flowchart.refactor();
        this.flowchart.saveToImage(filename);
    }

    public Point computeFlowchartImage(ImageBuilder image, int x, int y){
        this.flowchart.refactor();
        return this.flowchart.toImage(image, x, y);
    }

    public int getManaUsage(){
        return this.manaUsage;
    }

    public int getRechargeTime(){
        return this.rechargeTime;
    }

    public boolean getWrappedThisCast(){
        return this.wrappedThisCast;
    }

    public Spell getDiscardSpell(int id){
        if(id >= 0 && id < this.discard.size()){
            return this.discard.get(id);
        }else{
            return null;
        }
    }

    public Spell getHandSpell(int id){
        if(id >= 0 && id < this.hand.size()){
            return this.hand.get(id);
        }else{
            return null;
        }
    }

    public Spell getDeckSpell(int id){
        if(id >= 0 && id < this.deck.size()){
            return this.deck.get(id);
        }else{
            return null;
        }
    }

    public Spell[] getDiscard(){
        return this.discard.toArray(new Spell[0]);
    }

    public Spell[] getHand(){
        return this.hand.toArray(new Spell[0]);
    }

    public Spell[] getDeck(){
        return this.deck.toArray(new Spell[0]);
    }

    public List<Spell> getDiscardList(){
        return this.discard;
    }

    public List<Spell> getHandList(){
        return this.hand;
    }

    public List<Spell> getDeckList(){
        return this.deck;
    }

    public int getDiscardSize(){
        return this.discard.size();
    }

    public int getHandSize(){
        return this.hand.size();
    }

    public int getDeckSize(){
        return this.deck.size();
    }

    public void addDeck(Spell spell){
        this.deck.add(spell);
    }

    public void addHand(Spell spell){
        this.hand.add(spell);
    }

    public void addDiscard(Spell spell){
        this.discard.add(spell);
    }

    public void addDeck(Spell[] spells){
        this.deck.addAll(Arrays.asList(spells));
    }

    public void addHand(Spell[] spells){
        this.hand.addAll(Arrays.asList(spells));
    }

    public void addDiscard(Spell[] spells){
        this.discard.addAll(Arrays.asList(spells));
    }

    public void clear(){
        this.startingDeck.clear();
        this.deck.clear();
        this.hand.clear();
        this.discard.clear();
        this.drawEnabled = true;
        this.flowchart.reset();
        this.cardHistory.reset();
        this.forceStopDraws = false;
        this.wrappedThisCast = false;
    }

    public void reset(){
        this.deck = new ArrayList<>(this.startingDeck);
        this.hand.clear();
        this.discard.clear();
        this.drawEnabled = true;
        this.flowchart.reset();
        this.cardHistory.reset();
        this.forceStopDraws = false;
        this.wrappedThisCast = false;
    }

    public void reset(Spell[] spells){
        this.startingDeck.clear();
        this.deck.clear();
        for(Spell spell : spells){
            if(spell != null){
                this.deck.add(spell);
                this.startingDeck.add(spell);
            }
        }
        this.hand.clear();
        this.discard.clear();
        this.drawEnabled = true;
        this.flowchart.reset();
        this.cardHistory = new CardHistory(this.startingDeck.toArray(new Spell[0]));
        this.forceStopDraws = false;
        this.wrappedThisCast = false;
    }

    public void resetFlowchart(){
        this.flowchart.reset();
    }

    public Flowchart newFlowchart(){
        Flowchart oldFlowchart = this.flowchart;
        this.flowchart = new Flowchart();
        return oldFlowchart;
    }

    public void resetCardHistory(){
        this.cardHistory.reset(this.discard.toArray(new Spell[0]), this.hand.toArray(new Spell[0]), this.deck.toArray(new Spell[0]));
    }

    private boolean shuffleDeck(){
        List<Spell> newDeck = new ArrayList<>();
        long frame = Global.getCurrentFrame();
        RandomGenerator random = Global.getRandomGenerator();
        random.setSeed(frame, frame);
        boolean result = false;

        for(int i=this.deck.size(); i > 0; i--){
            int rnd = random.random(1, i);
            if(rnd != 1){
                result = true;
            }
            newDeck.add(this.deck.remove(rnd -1));
        }

        this.deck = newDeck;
        return result;
    }

    private boolean sortDeck(){
        if(this.shuffle){
            return this.shuffleDeck();
        }

        List<Spell> original = new ArrayList<>(this.deck);
        Sorter.sort(this.deck, this.startingDeck);

        return !this.deck.equals(original);
    }

    public void endCast(){
        Spell currentSpell;
        List<Spell> conditionalChargeSpells = new ArrayList<>();
        boolean containsProjectile = false;

        while(!this.hand.isEmpty()){
            currentSpell = this.hand.remove(0);
            if(!currentSpell.getAlwaysCast() && currentSpell.getType() == Spell.SpellType.modifier || currentSpell.getType() == Spell.SpellType.multicast){
                conditionalChargeSpells.add(currentSpell);
            }else{
                currentSpell.removeCharge();
            }
            if(!containsProjectile && (currentSpell.getType() == Spell.SpellType.projectile || currentSpell.getType() == Spell.SpellType.static_projectile || currentSpell.getType() == Spell.SpellType.material)){
                containsProjectile = true;
            }
            if(!currentSpell.getAlwaysCast()){
                this.discard.add(currentSpell);
            }
            this.updateCardHistory(false);
        }
        if(containsProjectile){
            for(Spell spell : conditionalChargeSpells){
                spell.removeCharge();
            }
        }
        if(this.wrappedThisCast || this.deck.isEmpty()){
            // recharge
            this.discardToDeck();
            this.sortDeck();
        }

        this.drawEnabled = true;
        this.forceStopDraws = false;
        this.wrappedThisCast = false;
    }

    public void clearDeck(){
        this.deck.clear();
    }

    public void clearHand(){
        this.hand.clear();
    }

    public void clearDiscard(){
        this.discard.clear();
    }

    public void deckFullClear(){
        this.deck = new ArrayList<>();
    }

    public void handFullClear(){
        this.hand = new ArrayList<>();
    }

    public void discardFullClear(){
        this.discard = new ArrayList<>();
    }

    public void refreshWrap(){
        this.discardToDeck();
        if(this.sortDeck()){
            this.updateCardHistory(false);
        }
    }

    private void wrap(){
        if(this.forceStopDraws){
            return;
        }

        this.discardToDeck();
        if(this.sortDeck()){
            this.updateCardHistory(false);
        }
        this.wrappedThisCast = true;
    }

    public void draw(int nbDraw, CastState castState, boolean wrapAllowed, Flowchart currentNode){
        Spell currentSpell;
        boolean spellSkipped;
        boolean drawConsumed;

        if(this.playingPermanentCard && nbDraw == 1){
            this.addEmptyFlowchart(currentNode);
            return;
        }

        for(int i=0; i < nbDraw; i++){
            if(this.drawEnabled && this.deck.isEmpty()){
                if(wrapAllowed && !this.discard.isEmpty()){
                    this.wrap();
                }
            }
            if(this.drawEnabled && !this.deck.isEmpty()){
                drawConsumed = true;
                currentSpell = this.deck.remove(0);
                if(this.manaUsage + currentSpell.getManaCost() <= this.maxMana && (currentSpell.getAlwaysCast() || !currentSpell.getHasCharges() || currentSpell.getChargesLeft() > 0)){
                    this.hand.add(currentSpell);
                    spellSkipped = false;
                }else{
                    this.discard.add(currentSpell);
                    spellSkipped = true;
                    if(!this.deck.isEmpty()){
                        // draw not consumed
                        drawConsumed = false;
                        i--;
                    }
                    this.updateCardHistory(false);
                }

                if(!spellSkipped){
                    this.updateCardHistory(false);
                    this.addCallStackSpell(currentNode, currentSpell);
                    this.manaUsage += currentSpell.getManaCost();
                    currentSpell.doAction(this, castState);
                    this.removeCallStack();
                }else if(drawConsumed && i + 1 == nbDraw){
                    this.addEmptyFlowchart(currentNode);
                }
            }else{
                this.addEmptyFlowchart(currentNode);
                break;
            }
        }
    }

    public void draw(int nbDraw, boolean wrapAllowed, CastState castState){
        draw(nbDraw, castState, wrapAllowed, this.getCallStack());
    }

    public void alwaysCast(Spell spell, CastState castState, Flowchart currentNode){
        this.playingPermanentCard = true;
        Spell AC = spell.clone();
        AC.setAlwaysCast(true);
        if(AC.getManaCost() < 0){
            this.manaUsage += AC.getManaCost();
        }
        this.hand.add(AC);
        this.updateCardHistory(false);
        this.addCallStackAC(currentNode, spell);
        AC.doAction(this, castState);
        this.removeCallStack();
        this.playingPermanentCard = false;
    }

    public void alwaysCast(Spell spell, CastState castState){
        alwaysCast(spell, castState, this.getCallStack());
    }

    public void discardNext(){
        if(!this.deck.isEmpty()){
            this.discard.add(this.deck.remove(0));
            this.updateCardHistory(false);
        }
    }

    public void discardNext(int nbDiscard){
        for(int i=0; i < nbDiscard; i++){
            if(!this.deck.isEmpty()){
                this.discard.add(this.deck.remove(0));
                this.updateCardHistory(false);
            }else{
                return;
            }
        }
    }

    public void discardId(int id){
        if(id < 0 || id > this.deck.size()){
            return;
        }
        this.discard.add(this.deck.remove(id));
        this.updateCardHistory(false);
    }

    public void discardId(int fromId, int toId){
        int id = fromId;
        for(int i=fromId; i <= toId; i++){
            if(id >= 0 && id < this.deck.size()){
                this.discard.add(this.deck.remove(id));
                this.updateCardHistory(false);
            }else{
                id++;
            }
        }
    }

    public void discardAllDeck(){
        while(!this.deck.isEmpty()){
            this.discard.add(this.deck.remove(0));
            this.updateCardHistory(false);
        }
    }

    public void discardAllHand(){
        while(!this.hand.isEmpty()){
            this.discard.add(this.hand.remove(0));
            this.updateCardHistory(false);
        }
    }

    public void discardToDeck(){
        while(!this.discard.isEmpty()){
            this.deck.add(this.discard.remove(0));
            this.updateCardHistory(false);
        }
    }

    public void copyHandToDiscard(){
        for(Spell spell : this.hand){
            this.discard.add(spell);
            this.updateCardHistory(false);
        }
    }

    public void copyDeckToDiscard(){
        for(Spell spell : this.deck){
            this.discard.add(spell);
            this.updateCardHistory(false);
        }
    }

    public void enableDraw(){
        this.drawEnabled = true;
    }

    public void disableDraw(){
        this.drawEnabled = false;
    }

    public boolean getForceStopDraws(){
        return this.forceStopDraws;
    }

    public void setForceStopDraws(boolean forceStopDraws){
        this.forceStopDraws = forceStopDraws;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        ListIterator<Spell> deckIterator = this.deck.listIterator();
        ListIterator<Spell> handIterator = this.hand.listIterator();
        ListIterator<Spell> discardIterator = this.discard.listIterator();

        str.append("[");
        while(discardIterator.hasNext()){
            str.append(discardIterator.next().getName());
            if(discardIterator.hasNext()){
                str.append(", ");
            }
        }
        str.append("] [");
        while(handIterator.hasNext()){
            str.append(handIterator.next().getName());
            if(handIterator.hasNext()){
                str.append(", ");
            }
        }
        str.append("] [");
        while(deckIterator.hasNext()){
            str.append(deckIterator.next().getName());
            if(deckIterator.hasNext()){
                str.append(", ");
            }
        }
        str.append("]");

        return str.toString();
    }

    public String infoToString(){
        StringBuilder result = new StringBuilder();

        result.append("Mana cost: ").append(this.manaUsage);
        if(this.rechargeTime != 0){result.append("\n").append("Recharge time: ").append(Global.delayFormat(this.rechargeTime));}
        if(this.recoil != 0){result.append("\n").append("Recoil: ").append(this.recoil);}

        return result.toString();
    }

    public String CastInfoToString(boolean formatting){
        StringBuilder result = new StringBuilder();

        if(formatting){
            result.append("Mana cost: ").append(this.manaUsage);
            if(this.recoil != 0){result.append("\n").append("Recoil: ").append(this.recoil);}
        }else{
            result.append(this.manaUsage).append(";").append(this.recoil);
        }

        return result.toString();
    }

    public boolean saveToImage(String fileName){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        imageBuilder.setFont(Global.getPixelFont().deriveFont((float)15));

        this.toImageNode(imageBuilder, 0, 0);

        return imageBuilder.saveAsPNG(fileName);
    }

    public BufferedImage toImage(){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        imageBuilder.setFont(Global.getPixelFont().deriveFont((float)15));

        this.toImageNode(imageBuilder, 0, 0);

        return imageBuilder.toPNG();
    }

    public Point addToImage(ImageBuilder image, int x, int y){
        if(image == null){
            return new Point(x, y);
        }

        return this.toImageNode(image, x, y);
    }

    private Point toImageNode(ImageBuilder image, int x, int y){
        String[] castInfo = this.infoToString().split("\\r?\\n");

        if(castInfo.length == 1 && castInfo[0].equals("")){
            return new Point(x, y);
        }

        return image.drawTextRectangle(castInfo, x, y, 5, Color.WHITE, Color.WHITE);
    }
}