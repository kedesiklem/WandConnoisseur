package org.example.main;

import org.example.spells.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.lang.StringBuilder;

// TODO maybe one day optimize using recursive patterns
class StructHistory{
    private int[][] history;
    private ArrayList<int[]> callStackHistory;

    public StructHistory(int[] discard, int[] hand, int[] deck, int[] callStack){
        int[] newCallStack = new int[callStack.length];
        for(int i=0; i < callStack.length; i++){
            newCallStack[i] = callStack[i];
        }
        this.callStackHistory = new ArrayList<>();
        this.callStackHistory.add(newCallStack);

        this.history = new int[3][];
        this.history[0] = new int[discard.length];
        for(int i=0; i < discard.length; i++){
            this.history[0][i] = discard[i];
        }
        this.history[1] = new int[hand.length];
        for(int i=0; i < hand.length; i++){
            this.history[1][i] = hand[i];
        }
        this.history[2] = new int[deck.length];
        for(int i=0; i < deck.length; i++){
            this.history[2][i] = deck[i];
        }
    }

    public StructHistory(int[] discard, int[] hand, int[] deck){
        this.callStackHistory = new ArrayList<>();
        this.history = new int[3][];
        this.history[0] = new int[discard.length];
        for(int i=0; i < discard.length; i++){
            this.history[0][i] = discard[i];
        }
        this.history[1] = new int[hand.length];
        for(int i=0; i < hand.length; i++){
            this.history[1][i] = hand[i];
        }
        this.history[2] = new int[deck.length];
        for(int i=0; i < deck.length; i++){
            this.history[2][i] = deck[i];
        }
    }

    public int getCallStackSize(){
        return this.callStackHistory.size();
    }

    public int[][] getCallStack(){
        int[][] result = new int[this.callStackHistory.size()][];
        int[] currentCallStack;
        int id;

        for(int i=0; i < result.length; i++){
            currentCallStack = this.callStackHistory.get(i);
            if(currentCallStack.length == 1 && currentCallStack[0] < 0){
                //id = -(currentCallStack[0] + 1);
                currentCallStack = this.callStackHistory.get(-(currentCallStack[0] + 1));
            }/*else{
                id = i;
            }*/
            result[i] = new int[currentCallStack.length];
            for(int j=0; j < result[i].length; j++){
                result[i][j] = currentCallStack[j];
            }
        }
        return result;
    }

    public int[][] getHistory(){
        return this.history;
    }

    public int[] getCallStackHistory(int id){
        if(id < 0 || id >= this.callStackHistory.size()){
            return new int[0];
        }else if(this.callStackHistory.get(id).length == 1 && this.callStackHistory.get(id)[0] < 0){
            return this.callStackHistory.get(-(this.callStackHistory.get(id)[0] + 1));
        }else{
            return this.callStackHistory.get(id);
        }
    }

    public void addCallStack(int[] callStack){
        int[] newCallStack;
        int[] currentCallStack;
        boolean same;

        for(int i=0; i < callStackHistory.size(); i++){
            currentCallStack = callStackHistory.get(i);
            if(callStack.length == currentCallStack.length && callStack.length > 1){
                same = true;
                for(int j=0; j < currentCallStack.length && same; j++){
                    if(callStack[j] != currentCallStack[j]){
                        same = false;
                    }
                }
                if(same){
                    newCallStack = new int[]{-(i + 1)};
                    this.callStackHistory.add(newCallStack);
                    return;
                }
            }
        }

        newCallStack = new int[callStack.length];
        for(int i=0; i < callStack.length; i++){
            newCallStack[i] = callStack[i];
        }
        this.callStackHistory.add(newCallStack);
    }

    public void cardPoolToIdString(StringBuilder sb){
        for(int i=0; i < this.history.length; i++){
            switch(i){
                case 0 -> sb.append("\033[0;31mDiscard\u001B[0m: ");
                case 1 -> sb.append(" \033[0;31mHand\u001B[0m: ");
                case 2 -> sb.append(" \033[0;31mDeck\u001B[0m: ");
                default -> sb.append(" ");
            }
            sb.append("[");
            for(int j=0; j < this.history[i].length; j++){
                sb.append(this.history[i][j]);
                if(j + 1 < this.history[i].length){
                    sb.append(", ");
                }
            }
            sb.append("]");
        }
        sb.append("\n");
    }

    public void callStackToIdString(StringBuilder sb){
        int[] currentCallStackHistory;

        for(int i=0; i < this.callStackHistory.size(); i++){
            currentCallStackHistory = this.callStackHistory.get(i);
            if(currentCallStackHistory.length == 0){
                sb.append("<empty>");
            }else{
                for(int j=0; j < currentCallStackHistory.length; j++){
                    sb.append(currentCallStackHistory[j]);
                    if(j + 1 < currentCallStackHistory.length){
                        sb.append(" -> ");
                    }
                }
            }
            sb.append("\n");
        }
    }

    public void toIdString(StringBuilder sb){
        cardPoolToIdString(sb);
        callStackToIdString(sb);
    }

    public String toIdString(){
        StringBuilder sb = new StringBuilder();
        this.toIdString(sb);
        return sb.toString();
    }
}

public class CardHistory{
    private Spell[] spellID = new Spell[0];
    private Spell[] startingSpells;
    private ArrayList<StructHistory> history;
    private int totalCallStackSize = 1;
    private int currentCardPoolStep = 0;
    private int currentCardPoolStep2 = 0;
    private int currentCallStackStep = 0;

    public CardHistory(Spell[] startingSpells){
        this.startingSpells = Arrays.stream(startingSpells).filter(Objects::nonNull).toArray(Spell[]::new);
        this.history = new ArrayList<>();
        this.history.add(new StructHistory(spellToID(new Spell[0]), spellToID(new Spell[0]), spellToID(startingSpells), spellToID(new Spell[0])));
    }

    public int getSize(){
        return this.totalCallStackSize;
    }

    public int getCardPoolSize(){
        return this.history.size();
    }

    public int getCurrentCallStackStepAbsolute(){
        if(this.currentCardPoolStep == this.currentCardPoolStep2){
            return this.currentCallStackStep;
        }else{
            return 0;
        }
    }

    public int getCurrentCardPoolSize(){
        return this.history.get(this.currentCardPoolStep).getCallStackSize();
    }

    public int getStep(){
        int result = 0;

        for(int i=0; i < Math.min(currentCardPoolStep + 1, this.history.size()); i++){
            if(i < this.currentCardPoolStep){
                result += Math.max(this.history.get(i).getCallStackSize(), 1);
            }else{
                if(this.currentCardPoolStep2 == this.currentCardPoolStep){
                    result += this.currentCallStackStep;
                }
                return result;
            }
        }

        return result;
    }

    public int getCardOnlyStep(){
        return this.currentCardPoolStep;
    }

    private int spellToID(Spell spell){
        for(int i=0; i < this.spellID.length; i++){
            if(spell.getClass() == this.spellID[i].getClass()){
                return i;
            }
        }
        Spell[] newSpellID = new Spell[this.spellID.length + 1];
        for(int i=0; i < this.spellID.length; i++){
            newSpellID[i] = this.spellID[i];
        }
        newSpellID[newSpellID.length - 1] = spell;
        this.spellID = newSpellID;
        return this.spellID.length - 1;
    }

    private int[] spellToID(Spell[] spell){
        int[] result = new int[spell.length];

        for(int i=0; i < result.length; i++){
            result[i] = spellToID(spell[i]);
        }
        return result;
    }

    private Spell[] IDToSpell(int[] IDs){
        Spell[] result = new Spell[IDs.length];

        for(int i=0; i < result.length; i++){
            result[i] = this.spellID[IDs[i]];
        }
        return result;
    }

    private Spell[][] IDToSpell(int[][] IDs){
        Spell[][] result = new Spell[IDs.length][];

        for(int i=0; i < result.length; i++){
            result[i] = IDToSpell(IDs[i]);
        }
        return result;
    }

    public void add(CardHistory cardHistory, boolean destroy){
        StructHistory structHistory;
        Spell[][] history;
        Spell[][] callStack;
        int maxSize = cardHistory.history.size(); // avoid infinite loop if (this == cardHistory) but still duplicates it

        for(int i=0; i < maxSize; i++){
            structHistory = cardHistory.history.get(i);
            history = cardHistory.IDToSpell(structHistory.getHistory());
            this.addStep(history[0], history[1], history[2]);
            callStack = cardHistory.IDToSpell(structHistory.getCallStack());
            for(Spell[] spells : callStack){
                this.addCallStack(spells);
            }
            if(destroy){
                cardHistory.history.set(i, null);
            }
        }
        this.totalCallStackSize += cardHistory.getSize();
    }

    public void addStep(Spell[] discard, Spell[] hand, Spell[] deck, Spell[] callStack){
        this.history.add(new StructHistory(spellToID(discard), spellToID(hand), spellToID(deck), spellToID(callStack)));
        this.totalCallStackSize += 1;
    }

    public void addStep(Spell[] discard, Spell[] hand, Spell[] deck){
        this.history.add(new StructHistory(spellToID(discard), spellToID(hand), spellToID(deck)));
        this.totalCallStackSize += 1;
    }

    public void reset(){
        this.history = new ArrayList<>();
        this.history.add(new StructHistory(spellToID(new Spell[0]), spellToID(new Spell[0]), spellToID(this.startingSpells), spellToID(new Spell[0])));
        this.totalCallStackSize = 1;
    }

    public void reset(Spell[] discard, Spell[] hand, Spell[] deck){
        this.history = new ArrayList<>();
        this.history.add(new StructHistory(spellToID(discard), spellToID(hand), spellToID(deck)));
        this.totalCallStackSize = 1;
    }

    public void addCallStack(Spell[] callStack){
        if(this.history.get(this.history.size() - 1).getCallStackSize() != 0){
            this.totalCallStackSize += 1;
        }
        this.history.get(this.history.size() - 1).addCallStack(spellToID(callStack));
    }

    public boolean goToStep(int callStackStep){
        int currentTotalStep = 0;

        if(callStackStep < 0 || callStackStep >= this.totalCallStackSize){
            return false;
        }

        this.currentCardPoolStep = 0;

        for(StructHistory history : this.history){
            if(currentTotalStep + Math.max(history.getCallStackSize(), 1) <= callStackStep){
                currentTotalStep += Math.max(history.getCallStackSize(), 1);
                this.currentCardPoolStep++;
            }else{
                break;
            }
        }

        this.currentCardPoolStep2 = this.currentCardPoolStep;
        this.currentCallStackStep = callStackStep - currentTotalStep;

        if(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
            while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                this.currentCardPoolStep2 -= 1;
            }
            this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
        }

        return true;
    }

    public boolean goToStep(int cardPoolStep, int callStackStep){
        StructHistory currentStructHistory;

        if(cardPoolStep < 0 || cardPoolStep >= this.history.size()){
            return false;
        }

        currentStructHistory = this.history.get(cardPoolStep);
        if(callStackStep < 0 || (callStackStep != 0 && callStackStep >= currentStructHistory.getCallStackSize())){
            return false;
        }

        this.currentCardPoolStep = cardPoolStep;
        this.currentCardPoolStep2 = cardPoolStep;
        this.currentCallStackStep = callStackStep;

        if(this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
            while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                this.currentCardPoolStep2 -= 1;
            }
            this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
        }

        return true;
    }

    public Spell[][] getStep(int callStackStep){
        Spell[][] result = new Spell[4][0];
        Spell[][] currentCardPool;
        int currentTotalStep = 0;
        int currentCardPoolStep = 0;
        int currentCardPoolStep2 = 0;
        int currentCallStackStep = 0;

        if(callStackStep < 0 || callStackStep >= this.totalCallStackSize){
            return result;
        }

        for(StructHistory history : this.history){
            if(currentTotalStep + Math.max(history.getCallStackSize(), 1) <= callStackStep){
                currentTotalStep += Math.max(history.getCallStackSize(), 1);
                currentCardPoolStep++;
            }else{
                break;
            }
        }

        currentCardPoolStep2 = currentCardPoolStep;
        currentCallStackStep = callStackStep - currentTotalStep;

        if(currentCardPoolStep2 >= 0 && this.history.get(currentCardPoolStep2).getCallStackSize() == 0){
            while(currentCardPoolStep2 >= 0 && this.history.get(currentCardPoolStep2).getCallStackSize() == 0){
                currentCardPoolStep2 -= 1;
            }
            currentCallStackStep = this.history.get(currentCardPoolStep2).getCallStackSize() - 1;
        }

        currentCardPool = IDToSpell(this.history.get(currentCardPoolStep).getHistory());
        for(int i=0; i < currentCardPool.length && i < 3; i++){
            result[i] = currentCardPool[i];
        }
        result[3] = IDToSpell(this.history.get(currentCardPoolStep2).getCallStackHistory(currentCallStackStep));

        return result;
    }

    public Spell[][] getStep(int cardPoolStep, int callStackStep){
        Spell[][] result = new Spell[4][0];
        Spell[][] currentCardPool;
        StructHistory currentStructHistory;
        int currentCardPoolStep2 = cardPoolStep;
        int currentCallStackStep = callStackStep;

        if(cardPoolStep < 0 || cardPoolStep >= this.history.size()){
            return result;
        }

        currentStructHistory = this.history.get(cardPoolStep);
        if(callStackStep < 0 || (callStackStep != 0 && callStackStep >= currentStructHistory.getCallStackSize())){
            return result;
        }

        if(this.history.get(currentCardPoolStep2).getCallStackSize() == 0){
            while(currentCardPoolStep2 >= 0 && this.history.get(currentCardPoolStep2).getCallStackSize() == 0){
                currentCardPoolStep2 -= 1;
            }
            currentCallStackStep = this.history.get(currentCardPoolStep2).getCallStackSize() - 1;
        }

        currentCardPool = IDToSpell(this.history.get(cardPoolStep).getHistory());
        for(int i=0; i < currentCardPool.length && i < 3; i++){
            result[i] = currentCardPool[i];
        }
        result[3] = IDToSpell(this.history.get(currentCardPoolStep2).getCallStackHistory(currentCallStackStep));

        return result;
    }

    public Spell[][] getCurrentStep(){
        Spell[][] currentCardPool;
        Spell[][] result = new Spell[4][0];

        if(this.currentCardPoolStep >= 0 && this.currentCardPoolStep < this.history.size()){
            currentCardPool = IDToSpell(this.history.get(this.currentCardPoolStep).getHistory());
            for(int i=0; i < currentCardPool.length && i < 3; i++){
                result[i] = currentCardPool[i];
            }
        }
        if(this.currentCardPoolStep2 >= 0 && this.currentCardPoolStep2 < this.history.size() && this.currentCallStackStep >= 0 && this.currentCallStackStep < this.history.get(this.currentCardPoolStep2).getCallStackSize()){
            result[3] = IDToSpell(this.history.get(this.currentCardPoolStep2).getCallStackHistory(this.currentCallStackStep));
        }

        return result;
    }

    public void nextStep(){
        if(this.history.size() == 0 || this.currentCardPoolStep < 0 || this.currentCardPoolStep >= this.history.size()){
            this.currentCardPoolStep = 0;
            this.currentCardPoolStep2 = 0;
            this.currentCallStackStep = 0;
            return;
        }
        //System.out.println("(1) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        if(this.currentCardPoolStep2 < this.currentCardPoolStep){
            this.currentCallStackStep = 0;
        }
        this.currentCardPoolStep2 = this.currentCardPoolStep;
        this.currentCallStackStep += 1;
        //System.out.println("(2) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);

        if(this.currentCallStackStep >= this.history.get(this.currentCardPoolStep).getCallStackSize()){
            this.currentCardPoolStep += 1;
            this.currentCardPoolStep2 += 1;
            this.currentCallStackStep = 0;
            //System.out.println("(3) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
            if(this.currentCardPoolStep >= this.history.size()){
                this.currentCardPoolStep = 0;
                this.currentCardPoolStep2 = 0;
                //System.out.println("(4) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
                return;
            }
            if(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                    this.currentCardPoolStep2 -= 1;
                    //System.out.println("(5) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
                }
                if(this.currentCardPoolStep2 >= 0){
                    this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
                }else{
                    this.currentCardPoolStep2 = 0;
                    this.currentCallStackStep = 0;
                }
            }
            //System.out.println("(6) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
        }
    }

    public void previousStep(){
        if(this.history.size() == 0 || this.currentCardPoolStep < 0 || this.currentCardPoolStep >= this.history.size()){
            this.currentCardPoolStep = 0;
            this.currentCardPoolStep2 = 0;
            this.currentCallStackStep = 0;
            return;
        }
        //System.out.println("(1) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        if(this.currentCardPoolStep2 < this.currentCardPoolStep){
            this.currentCallStackStep = 0;
        }
        this.currentCardPoolStep2 = this.currentCardPoolStep;
        this.currentCallStackStep -= 1;
        //System.out.println("(2) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");

        if(this.currentCallStackStep < 0){
            this.currentCardPoolStep -= 1;
            this.currentCardPoolStep2 -= 1;
            if(this.currentCardPoolStep < 0){
                this.currentCardPoolStep = this.history.size() - 1;
                this.currentCardPoolStep2 = this.history.size() - 1;
            }
            this.currentCallStackStep = this.history.get(this.currentCardPoolStep).getCallStackSize() - 1;
            //if(this.currentCardPoolStep > 0){
            //    System.out.println("(3) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
            //}else{
            //    System.out.println("(3) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
            //}
            if(this.currentCardPoolStep < 0){
                this.currentCardPoolStep = this.history.size() - 1;
                this.currentCardPoolStep2 = this.history.size() - 1;
                this.currentCallStackStep = this.history.get(this.currentCardPoolStep).getCallStackSize() - 1;
                //System.out.println("(4) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
            }
            if(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                    this.currentCardPoolStep2 -= 1;
                    //System.out.println("(5) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
                }
                if(this.currentCardPoolStep2 >= 0){
                    this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
                }else{
                    this.currentCardPoolStep2 = 0;
                    this.currentCallStackStep = 0;
                }
            }
            //System.out.println("(6) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        }
    }

    public void nextCardPoolStep(){
        if(this.history.size() == 0 || this.currentCardPoolStep < 0 || this.currentCardPoolStep >= this.history.size()){
            this.currentCardPoolStep = 0;
            this.currentCardPoolStep2 = 0;
            this.currentCallStackStep = 0;
            return;
        }
        //System.out.println("(1) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        this.currentCardPoolStep += 1;
        this.currentCardPoolStep2 = this.currentCardPoolStep;
        this.currentCallStackStep = 0;
        //if(this.currentCardPoolStep < this.history.size()){
        //    System.out.println("(2) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        //}else{
        //    System.out.println("(2) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep);
        //}
        if(this.currentCardPoolStep >= this.history.size()){
            this.currentCardPoolStep = 0;
            this.currentCardPoolStep2 = 0;
            //System.out.println("(3) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
            return;
        }
        if(this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
            while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                this.currentCardPoolStep2 -= 1;
                //System.out.println("(4) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
            }
            if(this.currentCardPoolStep2 >= 0){
                this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
            }else{
                this.currentCardPoolStep2 = 0;
                this.currentCallStackStep = 0;
            }
        }
        //System.out.println("(5) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
    }

    public void previousCardPoolStep(){
        if(this.history.size() == 0 || this.currentCardPoolStep < 0 || this.currentCardPoolStep >= this.history.size()){
            this.currentCardPoolStep = 0;
            this.currentCardPoolStep2 = 0;
            this.currentCallStackStep = 0;
            return;
        }
        //System.out.println("(1) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        if(this.currentCardPoolStep == this.currentCardPoolStep2 && this.currentCallStackStep > 0){
            this.currentCallStackStep = 0;
        }else{
            this.currentCardPoolStep -= 1;
            if(this.currentCardPoolStep < 0){
                this.currentCardPoolStep = this.history.size() - 1;
            }
            this.currentCardPoolStep2 = this.currentCardPoolStep;
            this.currentCallStackStep = 0;
        }
        //System.out.println("(2) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
        if(this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
            while(this.currentCardPoolStep2 >= 0 && this.history.get(this.currentCardPoolStep2).getCallStackSize() == 0){
                this.currentCardPoolStep2 -= 1;
                //System.out.println("(3) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
            }
            if(this.currentCardPoolStep2 >= 0){
                this.currentCallStackStep = this.history.get(this.currentCardPoolStep2).getCallStackSize() - 1;
            }else{
                this.currentCardPoolStep2 = 0;
                this.currentCallStackStep = 0;
            }
        }
        //System.out.println("(4) " + this.currentCardPoolStep + " " + this.currentCardPoolStep2 + " " + this.currentCallStackStep + "/[" + this.history.get(this.currentCardPoolStep).getCallStackSize() + ", " + this.totalCallStackSize + "]");
    }

    private String toString(boolean cardPool, boolean callStack){
        StringBuilder sb = new StringBuilder();
        Spell[][] currentHistory;
        boolean first = true;

        if(!cardPool && !callStack){
            return "";
        }

        for(StructHistory structHistory : this.history){
            if(!first && cardPool && callStack){
                sb.append("\n");
            }

            if(cardPool){
                if(!first){
                    sb.append("\n");
                }
                currentHistory = IDToSpell(structHistory.getHistory());
                for(int i=0; i < 3; i++){
                    switch(i){
                        case 0 -> sb.append("\033[0;31mDiscard\u001B[0m: ");
                        case 1 -> sb.append(" \033[0;31mHand\u001B[0m: ");
                        case 2 -> sb.append(" \033[0;31mDeck\u001B[0m: ");
                        default -> sb.append(" ");
                    }
                    sb.append("[");
                    for(int j=0; j < currentHistory[i].length; j++){
                        sb.append(currentHistory[i][j].getName());
                        if(j + 1 < currentHistory[i].length){
                            sb.append(", ");
                        }
                    }
                    sb.append("]");
                }
            }
            if(callStack){
                if(cardPool || !first){
                    sb.append("\n");
                }
                currentHistory = IDToSpell(structHistory.getCallStack());
                if(currentHistory.length == 0){
                    sb.append("<empty>");
                }
                for(int i=0; i < currentHistory.length; i++){
                    if(currentHistory[i].length == 0){
                        sb.append("<empty>");
                    }
                    for(int j=0; j < currentHistory[i].length; j++){
                        sb.append(currentHistory[i][j].getName());
                        if(j + 1 < currentHistory[i].length){
                            sb.append(" -> ");
                        }
                    }
                    if(i + 1 < currentHistory.length){
                        sb.append("\n");
                    }
                }
            }
            if(first){
                first = false;
            }
        }

        return sb.toString();
    }

    public String toString(){
        return this.toString(true, true);
    }

    public String cardPoolToString(){
        return this.toString(true, false);
    }

    public String callStackToString(){
        return this.toString(false, true);
    }

    public String getCurrentStepString(){
        StringBuilder sb = new StringBuilder();
        Spell[][] currentHistory;
        Spell[] currentCallStack;

        if(this.currentCardPoolStep >= 0 && this.currentCardPoolStep < this.history.size()){
            currentHistory = IDToSpell(this.history.get(this.currentCardPoolStep).getHistory());
            for(int i=0; i < 3; i++){
                switch(i){
                    case 0 -> sb.append("\033[0;31mDiscard\u001B[0m: ");
                    case 1 -> sb.append(" \033[0;31mHand\u001B[0m: ");
                    case 2 -> sb.append(" \033[0;31mDeck\u001B[0m: ");
                    default -> sb.append(" ");
                }
                sb.append("[");
                for(int j=0; j < currentHistory[i].length; j++){
                    sb.append(currentHistory[i][j].getName());
                    if(j + 1 < currentHistory[i].length){
                        sb.append(", ");
                    }
                }
                sb.append("]");
            }
            sb.append("\n");

            if(this.currentCardPoolStep2 >= 0 && this.currentCardPoolStep2 < this.history.size() && this.currentCallStackStep >= 0 && this.currentCallStackStep < this.history.get(this.currentCardPoolStep2).getCallStackSize()){
                currentCallStack = IDToSpell(this.history.get(this.currentCardPoolStep2).getCallStackHistory(this.currentCallStackStep));
                if(currentCallStack.length == 0){
                    sb.append("<empty>");
                }
                for(int i=0; i < currentCallStack.length; i++){
                    sb.append(currentCallStack[i].getName());
                    if(i + 1 < currentCallStack.length){
                        sb.append(" -> ");
                    }
                }
            }
        }

        return sb.toString();
    }
}