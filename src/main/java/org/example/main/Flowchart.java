package org.example.main;

import org.example.spells.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.List;

public class Flowchart{
    private static final BufferedImage copyFailedImage = Global.loadImage("./src/main/java/org/example/image/other/failed_20.png");
    private static final String copyColor = "\u001b[0;33m"; // yellow
    private static final String failedColor = "\u001b[0;31m"; // red
    private static final String ACColor = "\u001b[0;34m"; // blue
    private static final String resetColor = "\u001b[0;0m";
    private Spell myself;
    //private int chargesLeft = 0;
    private int count = 1;
    private List<Flowchart> spells = new ArrayList<>();
    private boolean isRoot;
    private boolean isCopy = false;
    private boolean alwaysCast = false;
    private boolean copyFailed = false;
    private String rootName = "wand";
    private int width = 3;
    private Flowchart root;
    private final Hasher hasher = new Hasher();
    private boolean hashUpdated = false;

    public Spell getSelf(){
        return this.myself;
    }

    public Flowchart(){
        this.isRoot = true;
        this.myself = null;
        this.root = this;
    }

    public Flowchart(String rootName){
        this.isRoot = true;
        this.myself = null;
        this.rootName = rootName;
        this.root = this;
    }

    private Flowchart(Spell spell){
        this.isRoot = false;
        this.myself = spell;
        this.rootName = null;
        this.width = 0;
        //if(spell != null && spell.getHasCharges()){
        //    this.chargesLeft = spell.getChargesLeft();
        //}
    }

    public boolean equals(Flowchart flowchart){
        if(flowchart == null){
            return false;
        }
        if(this.spells.size() != flowchart.spells.size()){
            return false;
        }
        if((this.myself == null) != (flowchart.myself == null)){
            return false;
        }
        if(this.myself != null && this.myself.getClass() != flowchart.myself.getClass()){
            return false;
        }
        //if(this.chargesLeft != flowchart.chargesLeft){
        //    return false;
        //}
        if(this.count != flowchart.count){
            return false;
        }
        if(this.isRoot != flowchart.isRoot){
            return false;
        }
        if(this.isCopy != flowchart.isCopy){
            return false;
        }
        if(this.alwaysCast != flowchart.alwaysCast){
            return false;
        }
        if(this.copyFailed != flowchart.copyFailed){
            return false;
        }
        if(this.rootName == null ? flowchart.rootName != null : !this.rootName.equals(flowchart.rootName)){
            return false;
        }
        for(int i=0; i < this.spells.size(); i++){
            if(!this.spells.get(i).equals(flowchart.spells.get(i))){
                return false;
            }
        }

        return this.width == flowchart.width;
    }

    public static boolean staticEquals(Flowchart first, Flowchart second){
        if(first == null && second == null){
            return true;
        }
        if(first == null){
            return false;
        }

        return first.equals(second);
    }

    public long getHash(){
        if(!this.getHashUpdated()){
            this.computeHash();
        }

        return this.hasher.getValue();
    }

    public static long staticHash(Flowchart flowchart){
        return flowchart.getHash();
    }

    private void computeHash(){
        this.hasher.reset();

        if(this.myself == null){
            this.hasher.add(0);
        }else{
            this.hasher.add(this.myself.getClass());
        }
        //this.hasher.add(this.chargesLeft);
        this.hasher.add(this.count);
        this.hasher.add(this.isRoot);
        this.hasher.add(this.isCopy);
        this.hasher.add(this.alwaysCast);
        this.hasher.add(this.copyFailed);
        this.hasher.add(this.rootName);
        this.hasher.add(this.width);
        for(Flowchart child : this.spells){
            this.hasher.add(child.getHash());
        }

        this.hashUpdated = true;
    }

    public boolean getHashUpdated(){
        if(!this.hashUpdated){
            return false;
        }
        for(Flowchart child : this.spells){
            if(!child.getHashUpdated()){
                return false;
            }
        }
        return true;
    }

    public Flowchart add(Spell spell){
        this.hashUpdated = false;
        Flowchart currentNode = new Flowchart(spell);
        currentNode.root = this.root;
        this.spells.add(currentNode);
        return currentNode;
    }

    public void refactor(){
        this.hashUpdated = false;
        Flowchart[] subNodes = this.spells.toArray(new Flowchart[0]);

        for(Flowchart subNode : subNodes){
            subNode.refactor();
        }

        for(int i=0; i < subNodes.length - 1; i++){
            for(int j=i+1; j < subNodes.length; j++){
                if(subNodes[i] != null && subNodes[j] != null && canFuse(subNodes[i], subNodes[j])){
                    subNodes[i].count += subNodes[j].count;
                    this.spells.remove(subNodes[j]);
                    subNodes[j] = null;
                }else{
                    break;
                }
            }
        }
    }

    public boolean canFuse(Flowchart node1, Flowchart node2){
        if(node1.spells.size() != node2.spells.size()){
            return false;
        }
        if((node1.myself == null) != (node2.myself == null) || (node1.myself != null && node1.myself.getClass() != node2.myself.getClass())){
            return false;
        }
        if(node1.copyFailed != node2.copyFailed){
            return false;
        }
        if(node1.isCopy != node2.isCopy){
            return false;
        }
        if(node1.alwaysCast != node2.alwaysCast){
            return false;
        }
        if(node1.spells.isEmpty()){
            return true;
        }

        for(int i=0; i < node1.spells.size(); i++){
            if(!canFuse(node1.spells.get(i), node2.spells.get(i))){
                return false;
            }
        }

        return true;
    }

    // getters
    public int getWidth(){
        if(this.isRoot){
            return this.width;
        }else{
            return this.root.getWidth();
        }
    }

    public boolean getIsCopy(){
        return this.isCopy;
    }

    public boolean getAlwaysCast(){
        return this.alwaysCast;
    }

    public boolean getCopyFailed(){
        return this.copyFailed;
    }

    public String getRootName(){
        if(this.isRoot){
            return this.rootName;
        }else{
            return this.root.getRootName();
        }
    }

    // setters
    public void setWidth(int width){
        this.hashUpdated = false;
        if(this.isRoot){
            this.width = width;
        }else{
            this.root.setWidth(width);
        }
    }

    public void setIsCopy(boolean isCopy){
        this.hashUpdated = false;
        this.isCopy = isCopy;
        if(this.isCopy){
            this.alwaysCast = false;
        }
    }

    public void setAlwaysCast(boolean alwaysCast){
        this.hashUpdated = false;
        this.alwaysCast = alwaysCast;
        if(this.alwaysCast){
            this.isCopy = false;
        }
    }

    public void setCopyFailed(boolean copyFailed){
        this.hashUpdated = false;
        this.copyFailed = copyFailed;
    }

    public void setRootName(String rootName){
        this.hashUpdated = false;
        if(this.isRoot){
            this.rootName = rootName;
        }else{
            this.root.setRootName(rootName);
        }
    }

    public void reset(){
        this.hashUpdated = false;
        this.spells = new ArrayList<>();
    }

    public String toString(boolean formatting){
        if(this.isRoot){
            StringBuilder flowchartString = new StringBuilder(this.rootName + "\n");
            this.toString(flowchartString, "", formatting);
            return flowchartString.toString();
        }else{
            return this.root.toString(formatting);
        }
    }

    private void toString(StringBuilder sb, String currentFlow, boolean formatting){
        Flowchart[] flowchartArray = this.spells.toArray(new Flowchart[0]);
        Flowchart currentFlowchart;
        boolean last;
        boolean failed;

        for(int i=0; i < flowchartArray.length; i++){
            // is last
            last = i + 1 == flowchartArray.length;
            currentFlowchart = flowchartArray[i];
            failed = currentFlowchart.myself == null;

            sb.append(currentFlow);
            if(formatting){
                if(failed){
                    sb.append(failedColor);
                }else if(currentFlowchart.isCopy){
                    sb.append(copyColor);
                }else if(currentFlowchart.alwaysCast){
                    sb.append(ACColor);
                }
            }
            sb.append(last ? "\u2514" : "\u251c");
            sb.append("\u2500".repeat(this.getWidth()));
            if(formatting && (currentFlowchart.isCopy || currentFlowchart.alwaysCast || failed)){
                sb.append(resetColor);
            }
            if(currentFlowchart.myself != null){
                if(currentFlowchart.copyFailed){
                    if(formatting){
                        sb.append(failedColor).append(currentFlowchart.myself.getName()).append(resetColor);
                    }else{
                        sb.append("(").append(currentFlowchart.myself.getName()).append(")");
                    }
                }else{
                    sb.append(currentFlowchart.myself.getName());
                }
                /*if(currentFlowchart.myself.getHasCharges() && !currentFlowchart.isCopy){
                    sb.append(" (" + currentFlowchart.chargesLeft + ")");
                }*/
                if(currentFlowchart.count > 1){
                    sb.append(" (x").append(currentFlowchart.count).append(")");
                }
                sb.append("\n");
            }else{
                sb.append("X\n");
            }
            if(last){
                currentFlowchart.toString(sb, currentFlow + " ".repeat(this.getWidth() + 1), formatting);
            }else{
                if(formatting && flowchartArray[i+1].myself == null){
                    currentFlowchart.toString(sb, currentFlow + failedColor + "\u2502" + resetColor + " ".repeat(this.getWidth()), formatting);
                }else if(formatting && flowchartArray[i+1].isCopy){
                    currentFlowchart.toString(sb, currentFlow + copyColor + "\u2502" + resetColor + " ".repeat(this.getWidth()), formatting);
                }else if(formatting && flowchartArray[i+1].alwaysCast){
                    currentFlowchart.toString(sb, currentFlow + ACColor + "\u2502" + resetColor + " ".repeat(this.getWidth()), formatting);
                }else{
                    currentFlowchart.toString(sb, currentFlow + "\u2502" + " ".repeat(this.getWidth()), formatting);
                }
            }
        }
    }

    public boolean saveToImage(String filename){
        if(this.isRoot){
            ImageBuilder image = new ImageBuilder(new Color(0, 0, 0));
            image.setFont(Global.getPixelFont().deriveFont((float)15));
            this.toImageNode(image, 0, 0);
            return image.saveAsPNG(filename);
        }else{
            return this.root.saveToImage(filename);
        }
    }

    public BufferedImage toImage(){
        if(this.isRoot){
            ImageBuilder image = new ImageBuilder(new Color(0, 0, 0));
            image.setFont(Global.getPixelFont().deriveFont((float)15));
            this.toImageNode(image, 0, 0);
            return image.toPNG();
        }else{
            return this.root.toImage();
        }
    }

    public Point toImage(ImageBuilder image, int x, int y){
        if(this.isRoot){
            image.setFont(Global.getPixelFont().deriveFont((float)15));
            Point tempPoint = this.toImageNode(image, x, y);
            tempPoint.y += 40;
            return tempPoint;
        }else{
            return this.root.toImage(image, x, y);
        }
    }

    public static void addToImage(int x, int y, ImageBuilder imageBuilder, Flowchart[] flowcharts){
        imageBuilder.setFont(Global.getPixelFont().deriveFont((float)15));
        AtomicInteger nextY = new AtomicInteger(y);
        int[][] maxPoints = new int[flowcharts.length][3]; // maxX, minY, maxY

        Consumer<Integer> print = index -> {
            maxPoints[index][1] = nextY.get();

            Point maxPoint = imageBuilder.drawText(((index + 1) + ")"), x, nextY.get(), Color.WHITE);

            maxPoints[index][0] = maxPoint.x;

            maxPoint = flowcharts[index].toImageNode(imageBuilder, x, maxPoint.y);

            maxPoints[index][0] = Math.max(maxPoints[index][0], maxPoint.x);
            nextY.set(maxPoint.y + 40);
            maxPoints[index][2] = maxPoint.y;
        };

        BiConsumer<int[], Integer> repeat = (range, count) -> {
            int maxX = maxPoints[range[0]][0];
            for(int i=range[0] + 1; i <= range[1]; i++){
                maxX = Math.max(maxX, maxPoints[i][0]);
            }
            maxX += 10;
            maxPoints[range[0]][0] = imageBuilder.drawCurlyBrackets(maxX, maxPoints[range[0]][1], maxX + 20, maxPoints[range[1]][2], "x" + count, Color.WHITE, false, true).x;
        };

        RecursivePatternMatcher<Flowchart> recursivePatternMatcher = new RecursivePatternMatcher<>(flowcharts, print, repeat, Flowchart::staticHash, Flowchart::staticEquals);
        recursivePatternMatcher.process();
    }

    private Point toImageNode(ImageBuilder image, int x, int y){
        Flowchart[] flowchartArray = this.spells.toArray(new Flowchart[0]);
        Flowchart currentFlowchart;
        BufferedImage currentImage;
        int imageSize = 16;
        int arrowSizeX = 40;
        int arrowSizeY = 24;
        int arrowColor = 0;
        int nextY = y;
        boolean drawUnder = false;
        Point maxPoint = new Point(x, y + imageSize);

        for(int i=0; i < flowchartArray.length; i++){
            currentFlowchart = flowchartArray[i];

            if(currentFlowchart.myself == null){
                drawUnder = drawUnder || arrowColor != 0;
                image.drawArrow(x, y + imageSize/2, x + arrowSizeX - 1, nextY + imageSize/2, Color.RED, true, drawUnder);
            }else if(currentFlowchart.alwaysCast){
                if(i == 0){arrowColor = 1;}
                drawUnder = drawUnder || arrowColor != 1;
                image.drawArrow(x, y + imageSize/2, x + arrowSizeX - 1, nextY + imageSize/2, new Color(105, 154, 251), true, drawUnder);
            }else if(currentFlowchart.isCopy){
                if(i == 0){arrowColor = 2;}
                drawUnder = drawUnder || arrowColor != 2;
                image.drawArrow(x, y + imageSize/2, x + arrowSizeX - 1, nextY + imageSize/2, Color.YELLOW, true, drawUnder);
            }else{
                if(i == 0){arrowColor = 3;}
                drawUnder = drawUnder || arrowColor != 3;
                image.drawArrow(x, y + imageSize/2, x + arrowSizeX - 1, nextY + imageSize/2, Color.WHITE, true, drawUnder);
            }
            if(currentFlowchart.myself != null){
                currentImage = currentFlowchart.myself.getImage();
                if(currentImage != null){
                    image.addImage(currentImage, x + arrowSizeX + (imageSize - currentImage.getWidth())/2, nextY + (imageSize - currentImage.getHeight())/2, currentFlowchart.myself.getClass().getSimpleName());
                }
            }
            if(copyFailedImage != null && (currentFlowchart.copyFailed || currentFlowchart.myself == null)){
                image.addImage(copyFailedImage, x + arrowSizeX + (imageSize - copyFailedImage.getWidth())/2, nextY + (imageSize - copyFailedImage.getHeight())/2, "FAILED_20");
            }

            Point currentPoint = currentFlowchart.toImageNode(image, x + arrowSizeX + imageSize, nextY);
            maxPoint.x = Math.max(maxPoint.x, currentPoint.x);
            maxPoint.y = currentPoint.y;
            nextY = currentPoint.y;

            if(currentFlowchart.count > 1){
                nextY = image.drawCurlyBrackets(x + arrowSizeX, nextY, currentPoint.x, nextY + 20, "x" + currentFlowchart.count, Color.WHITE, false, false).y;
                maxPoint.y = nextY;
            }
            nextY += arrowSizeY;
        }

        return maxPoint;
    }
}