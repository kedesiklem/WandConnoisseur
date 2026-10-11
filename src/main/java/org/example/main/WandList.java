package org.example.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

enum RangeType{BETWEEN, GREATER_OR_EQUAL, LESS_OR_EQUAL}

class IntRange{
    private final int min;
    private final int max;
    private final RangeType type;

    public IntRange(int min, int max){
        this.min = min;
        this.max = max;
        this.type = RangeType.BETWEEN;
    }

    public IntRange(int min, boolean greater){
        this.min = min;
        this.max = 0;
        if(greater){
            this.type = RangeType.GREATER_OR_EQUAL;
        }else{
            this.type = RangeType.LESS_OR_EQUAL;
        }
    }

    public boolean contains(double value){
        return switch(type){
            case BETWEEN -> value >= this.min && value <= this.max;
            case GREATER_OR_EQUAL -> value >= this.min;
            case LESS_OR_EQUAL -> value <= this.min;
        };
    }

    public int getMin(){
        return this.min;
    }

    public int getMax(){
        return this.max;
    }

    public RangeType getType(){
        return this.type;
    }

    public String toString(String format, double multiplier, boolean ensembleFormat, String start, String end){
        String stringMin = "";
        String stringMax = "";

        if(format.equals("")){
            stringMin = String.valueOf(this.min*multiplier);
            stringMax = String.valueOf(this.max*multiplier);
        }else{
            try{
                stringMin = String.format(format, this.min*multiplier);
                stringMax = String.format(format, this.max*multiplier);
            }catch(IllegalFormatException e){
                stringMin = String.valueOf(this.min*multiplier);
                stringMax = String.valueOf(this.max*multiplier);
            }
        }

        if(ensembleFormat){
            return switch(this.type){
                case GREATER_OR_EQUAL -> start + "[" + stringMin + "; \u221e[" + end;
                case LESS_OR_EQUAL -> start + "]\u221e; " + stringMin + "]" + end;
                default -> stringMin.equals(stringMax) ? start + stringMin + end : start + "[" + stringMin + "; " + stringMax + "]" + end;
            };
        }else{
            return switch(this.type){
                case GREATER_OR_EQUAL -> start + ">" + stringMin + end;
                case LESS_OR_EQUAL -> start + "<" + stringMin + end;
                default -> stringMin.equals(stringMax) ? start + stringMin + end : start + stringMin + " - " + stringMax + end;
            };
        }
    }

    public String toString(){
        return this.toString("%.0f", 1, false, "", "");
    }
}

class WandStat{
    private String filePath = "./src/main/java/org/example/image/wand/";
    private String fileName;
    private boolean shuffle;
    private IntRange nbDraw;
    private IntRange castDelay;
    private IntRange rechargeTime;
    private IntRange nbSlot;
    private IntRange spread;

    public WandStat(String filePath, String fileName, boolean shuffle, IntRange nbDraw, IntRange castDelay, IntRange rechargeTime, IntRange nbSlot, IntRange spread){
        if(!filePath.equals("")){
            this.filePath = filePath;
        }
        this.fileName = fileName;
        this.shuffle = shuffle;
        this.nbDraw = nbDraw;
        this.castDelay = castDelay;
        this.rechargeTime = rechargeTime;
        this.nbSlot = nbSlot;
        this.spread = spread;
    }

    public WandStat(String fileName, boolean shuffle, IntRange nbDraw, IntRange castDelay, IntRange rechargeTime, IntRange nbSlot, IntRange spread){
        this.fileName = fileName;
        this.shuffle = shuffle;
        this.nbDraw = nbDraw;
        this.castDelay = castDelay;
        this.rechargeTime = rechargeTime;
        this.nbSlot = nbSlot;
        this.spread = spread;
    }

    public String getSprite(){
        return this.filePath + this.fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

    public String toString(){
        String result = "\"" + this.fileName + "\" Shuffle: ";

        if(this.shuffle){
            result += "yes";
        }else{
            result += "no";
        }
        result += ", draw \u2208 " + this.nbDraw.toString() + ", slot \u2208 " + this.nbSlot.toString() + ", recharge time \u2208 " + this.rechargeTime.toString() + ", cast delay \u2208 " + this.castDelay.toString() + ", spread \u2208 " + this.spread.toString();
        return result;
    }

    public boolean isValid(boolean shuffle, int nbDraw, int castDelay, int rechargeTime, int nbSlot, double spread){
        if(this.shuffle != shuffle){
            return false;
        }
        if(!this.nbDraw.contains(nbDraw)){
            return false;
        }
        if(!this.nbSlot.contains(nbSlot)){
            return false;
        }
        if(!this.rechargeTime.contains(rechargeTime)){
            return false;
        }
        if(!this.castDelay.contains(castDelay)){
            return false;
        }
        if(!this.spread.contains(spread)){
            return false;
        }
        return true;
    }

    // order: good -> bad
    // 1 = good order
    // 0 = same
    // -1 = invert
    public static int compare(WandStat wand1, WandStat wand2){
        // shuffle
        if(!wand1.shuffle && wand2.shuffle){
            return 6;
        }else if(wand1.shuffle && !wand2.shuffle){
            return -6;
        }

        // nbDraw <
        if(wand1.nbDraw.getType() == RangeType.LESS_OR_EQUAL && wand2.nbDraw.getType() != RangeType.LESS_OR_EQUAL){
            return 5;
        }else if(wand1.nbDraw.getType() != RangeType.LESS_OR_EQUAL && wand2.nbDraw.getType() == RangeType.LESS_OR_EQUAL){
            return -5;
        }else if(wand1.nbDraw.getMin() < wand2.nbDraw.getMin()){
            return 5;
        }else if(wand1.nbDraw.getMin() > wand2.nbDraw.getMin()){
            return -5;
        }else if(wand1.nbDraw.getType() != RangeType.GREATER_OR_EQUAL && wand2.nbDraw.getType() == RangeType.GREATER_OR_EQUAL){
            return 5;
        }else if(wand1.nbDraw.getType() == RangeType.GREATER_OR_EQUAL && wand2.nbDraw.getType() != RangeType.GREATER_OR_EQUAL){
            return -5;
        }else if(wand1.nbDraw.getMax() < wand2.nbDraw.getMax()){
            return 5;
        }else if(wand1.nbDraw.getMax() > wand2.nbDraw.getMax()){
            return -5;
        }

        // nbSlot >
        if(wand1.nbSlot.getType() == RangeType.GREATER_OR_EQUAL && wand2.nbSlot.getType() != RangeType.GREATER_OR_EQUAL){
            return 4;
        }else if(wand1.nbSlot.getType() != RangeType.GREATER_OR_EQUAL && wand2.nbSlot.getType() == RangeType.GREATER_OR_EQUAL){
            return -4;
        }else if(wand1.nbSlot.getMax() > wand2.nbSlot.getMax()){
            return 4;
        }else if(wand1.nbSlot.getMax() < wand2.nbSlot.getMax()){
            return -4;
        }else if(wand1.nbSlot.getType() != RangeType.LESS_OR_EQUAL && wand2.nbSlot.getType() == RangeType.LESS_OR_EQUAL){
            return 4;
        }else if(wand1.nbSlot.getType() == RangeType.LESS_OR_EQUAL && wand2.nbSlot.getType() != RangeType.LESS_OR_EQUAL){
            return -4;
        }else if(wand1.nbSlot.getMin() > wand2.nbSlot.getMin()){
            return 4;
        }else if(wand1.nbSlot.getMin() < wand2.nbSlot.getMin()){
            return -4;
        }

        // rechargeTime <
        if(wand1.rechargeTime.getType() == RangeType.LESS_OR_EQUAL && wand2.rechargeTime.getType() != RangeType.LESS_OR_EQUAL){
            return 3;
        }else if(wand1.rechargeTime.getType() != RangeType.LESS_OR_EQUAL && wand2.rechargeTime.getType() == RangeType.LESS_OR_EQUAL){
            return -3;
        }else if(wand1.rechargeTime.getMin() < wand2.rechargeTime.getMin()){
            return 3;
        }else if(wand1.rechargeTime.getMin() > wand2.rechargeTime.getMin()){
            return -3;
        }else if(wand1.rechargeTime.getType() != RangeType.GREATER_OR_EQUAL && wand2.rechargeTime.getType() == RangeType.GREATER_OR_EQUAL){
            return 3;
        }else if(wand1.rechargeTime.getType() == RangeType.GREATER_OR_EQUAL && wand2.rechargeTime.getType() != RangeType.GREATER_OR_EQUAL){
            return -3;
        }else if(wand1.rechargeTime.getMax() < wand2.rechargeTime.getMax()){
            return 3;
        }else if(wand1.rechargeTime.getMax() > wand2.rechargeTime.getMax()){
            return -3;
        }

        // castDelay <
        if(wand1.castDelay.getType() == RangeType.LESS_OR_EQUAL && wand2.castDelay.getType() != RangeType.LESS_OR_EQUAL){
            return 2;
        }else if(wand1.castDelay.getType() != RangeType.LESS_OR_EQUAL && wand2.castDelay.getType() == RangeType.LESS_OR_EQUAL){
            return -2;
        }else if(wand1.castDelay.getMin() < wand2.castDelay.getMin()){
            return 2;
        }else if(wand1.castDelay.getMin() > wand2.castDelay.getMin()){
            return -2;
        }else if(wand1.castDelay.getType() != RangeType.GREATER_OR_EQUAL && wand2.castDelay.getType() == RangeType.GREATER_OR_EQUAL){
            return 2;
        }else if(wand1.castDelay.getType() == RangeType.GREATER_OR_EQUAL && wand2.castDelay.getType() != RangeType.GREATER_OR_EQUAL){
            return -2;
        }else if(wand1.castDelay.getMax() < wand2.castDelay.getMax()){
            return 2;
        }else if(wand1.castDelay.getMax() > wand2.castDelay.getMax()){
            return -2;
        }

        // spread <
        if(wand1.spread.getType() == RangeType.LESS_OR_EQUAL && wand2.spread.getType() != RangeType.LESS_OR_EQUAL){
            return 1;
        }else if(wand1.spread.getType() != RangeType.LESS_OR_EQUAL && wand2.spread.getType() == RangeType.LESS_OR_EQUAL){
            return -1;
        }else if(wand1.spread.getMin() < wand2.spread.getMin()){
            return 1;
        }else if(wand1.spread.getMin() > wand2.spread.getMin()){
            return -1;
        }else if(wand1.spread.getType() != RangeType.GREATER_OR_EQUAL && wand2.spread.getType() == RangeType.GREATER_OR_EQUAL){
            return 1;
        }else if(wand1.spread.getType() == RangeType.GREATER_OR_EQUAL && wand2.spread.getType() != RangeType.GREATER_OR_EQUAL){
            return -1;
        }else if(wand1.spread.getMax() < wand2.spread.getMax()){
            return 1;
        }else if(wand1.spread.getMax() > wand2.spread.getMax()){
            return -1;
        }

        return 0;
    }

    public static int compare(Wand wand, WandStat wandStat){
        // shuffle
        if(!wand.getShuffle() && wandStat.shuffle){
            return 6;
        }else if(wand.getShuffle() && !wandStat.shuffle){
            return -6;
        }

        // nbDraw <
        switch(wandStat.nbDraw.getType()){
            case GREATER_OR_EQUAL -> {
                if(wand.getNbDraw() < wandStat.nbDraw.getMin()){
                    return 5;
                }
            }
            case LESS_OR_EQUAL -> {
                if(wand.getNbDraw() > wandStat.nbDraw.getMin()){
                    return -5;
                }
            }
            default -> {
                if(wand.getNbDraw() < wandStat.nbDraw.getMin()){
                    return 5;
                }else if(wand.getNbDraw() > wandStat.nbDraw.getMax()){
                    return -5;
                }
            }
        }

        // nbSlot >
        switch(wandStat.nbSlot.getType()){
            case LESS_OR_EQUAL -> {
                if(wand.getNbSlot() > wandStat.nbSlot.getMin()){
                    return 4;
                }
            }
            case GREATER_OR_EQUAL -> {
                if(wand.getNbSlot() < wandStat.nbSlot.getMin()){
                    return -4;
                }
            }
            default -> {
                if(wand.getNbSlot() > wandStat.nbSlot.getMin()){
                    return 4;
                }else if(wand.getNbSlot() < wandStat.nbSlot.getMax()){
                    return -4;
                }
            }
        }

        // rechargeTime <
        switch(wandStat.rechargeTime.getType()){
            case GREATER_OR_EQUAL -> {
                if(wand.getRechargeTime() < wandStat.rechargeTime.getMin()){
                    return 3;
                }
            }
            case LESS_OR_EQUAL -> {
                if(wand.getRechargeTime() > wandStat.rechargeTime.getMin()){
                    return -3;
                }
            }
            default -> {
                if(wand.getRechargeTime() < wandStat.rechargeTime.getMin()){
                    return 3;
                }else if(wand.getRechargeTime() > wandStat.rechargeTime.getMax()){
                    return -3;
                }
            }
        }

        // castDelay <
        switch(wandStat.castDelay.getType()){
            case GREATER_OR_EQUAL -> {
                if(wand.getCastDelay() < wandStat.castDelay.getMin()){
                    return 2;
                }
            }
            case LESS_OR_EQUAL -> {
                if(wand.getCastDelay() > wandStat.castDelay.getMin()){
                    return -2;
                }
            }
            default -> {
                if(wand.getCastDelay() < wandStat.castDelay.getMin()){
                    return 2;
                }else if(wand.getCastDelay() > wandStat.castDelay.getMax()){
                    return -2;
                }
            }
        }

        // spread <
        switch(wandStat.spread.getType()){
            case GREATER_OR_EQUAL -> {
                if(wand.getSpread() < wandStat.spread.getMin()){
                    return 1;
                }
            }
            case LESS_OR_EQUAL -> {
                if(wand.getSpread() > wandStat.spread.getMin()){
                    return -1;
                }
            }
            default -> {
                if(wand.getSpread() < wandStat.spread.getMin()){
                    return 1;
                }else if(wand.getSpread() > wandStat.spread.getMax()){
                    return -1;
                }
            }
        }

        return 0;
    }

    public ImageBuilder getStatImage(){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        String[] stats = new String[]{"Shuffle", "Spells/Cast", "Cast delay", "Rechrg. Time", "Capacity", "Spread"};
        int[] statId = new int[]{0, 1, 2, 3, 6, 7};
        String[] statValues = new String[stats.length];
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        int margin = 15;
        int outsideMargin = 18;
        int textScaling = 3;
        int wandScaling = 6;
        BufferedImage sprite = Global.rotateImageByDegrees(Global.scaleImage(Global.loadImage(this.getSprite()), wandScaling), -90);

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
        statValues[1] = this.nbDraw.toString();
        statValues[2] = this.castDelay.toString("%1$3.2f", 1.0/60.0, false, "", " s");
        statValues[3] = this.rechargeTime.toString("%1$3.2f", 1.0/60.0, false, "", " s");
        statValues[4] = this.nbSlot.toString();
        statValues[5] = this.spread.toString("%1$3.2f", 1, false, "", " DEG");

        Point currentPoint = imageBuilder.drawTextFixedSpacing(stats, textScaling*Global.getBaseIconSize() + margin + outsideMargin, outsideMargin, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE);
        currentPoint = imageBuilder.drawTextFixedSpacing(statValues, currentPoint.x + margin + outsideMargin, outsideMargin, 0, textScaling*(Global.getBaseIconSize() + 1), Color.WHITE);
        currentPoint.x += (sprite == null ? 0 : 2*margin);
        currentPoint.y += outsideMargin;
        currentPoint.y = Math.max(currentPoint.y, sprite == null ? 0 : sprite.getHeight());
        imageBuilder.setCanvasHeight(currentPoint.y);

        for(int i=0; i < stats.length; i++){
            imageBuilder.addImage(Wand.statImages[statId[i]], outsideMargin, outsideMargin + i*(textScaling*Global.getBaseIconSize() + textScaling), stats[i] + "_" + textScaling);
        }

        if(sprite != null){
            imageBuilder.addImage(sprite, currentPoint.x, (currentPoint.y - sprite.getHeight())/2, this.getFileName());
            currentPoint.x += sprite.getWidth();
        }
        imageBuilder.setCanvasWidth(imageBuilder.getCanvasWidth() + outsideMargin);

        return imageBuilder;
    }
}

// https://wondible.com/noita-know-your-wand/
public class WandList{
    static final WandStat[] wandList = readFile();

    private static WandStat[] readFile(){
        ArrayList<WandStat> wands = new ArrayList<>();
        File file = new File(Global.getPathConfig() + "wands.txt");

        try(Scanner myReader = new Scanner(file)){
            while(myReader.hasNextLine()){
                String[] data = myReader.nextLine().split(";");

                wands.add(new WandStat(
                    data[0], // folder
                    data[1], // file
                    data[2].equals("true"), // shuffle
                    switch(data[4]){ // draw
                        case "true" -> new IntRange(Integer.parseInt(data[3]), true);
                        case "false" -> new IntRange(Integer.parseInt(data[3]), false);
                        default -> new IntRange(Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                    }, switch(data[6]){ // cast delay
                        case "true" -> new IntRange(Integer.parseInt(data[5]), true);
                        case "false" -> new IntRange(Integer.parseInt(data[5]), false);
                        default -> new IntRange(Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                    }, switch(data[8]){ // recharge time
                        case "true" -> new IntRange(Integer.parseInt(data[7]), true);
                        case "false" -> new IntRange(Integer.parseInt(data[7]), false);
                        default -> new IntRange(Integer.parseInt(data[7]), Integer.parseInt(data[8]));
                    }, switch(data[10]){ // slot
                        case "true" -> new IntRange(Integer.parseInt(data[9]), true);
                        case "false" -> new IntRange(Integer.parseInt(data[9]), false);
                        default -> new IntRange(Integer.parseInt(data[9]), Integer.parseInt(data[10]));
                    }, switch(data[12]){ // spread
                        case "true" -> new IntRange(Integer.parseInt(data[11]), true);
                        case "false" -> new IntRange(Integer.parseInt(data[11]), false);
                        default -> new IntRange(Integer.parseInt(data[11]), Integer.parseInt(data[12]));
                    }
                ));
            }
        }catch(Exception e){
            System.out.println("Error reading wand config: " + e.getMessage());
            return null;
        }

        // sort
        WandStat[] result = wands.toArray(new WandStat[0]);
        WandStat tmp;

        for(int i=0; i < result.length; i++){
            for(int j=1; j < result.length; j++){
                if(WandStat.compare(result[j - 1], result[j]) < 0){
                    tmp = result[j - 1];
                    result[j - 1] = result[j];
                    result[j] = tmp;
                }
            }
        }

        /*for(WandStat wand : result){
            System.out.println(wand.toString());
        }*/

        return result;
    }

    public WandList(){
        // empty
    }

    public void generateAllSprites(boolean svg){
        String path;

        if(wandList == null){
            System.out.println("error wand file not loaded at \"" + Global.getPathConfig() + "wands.txt" + "\", can't generate sprites");
            return;
        }

        String outputPath = Global.getPathOutput();
        for(WandStat wandStat : wandList){
            path = outputPath + "wandStat_" + wandStat.getFileName();
            if(svg){
                int dot = path.lastIndexOf('.');
                path = dot >= 0 ? path.substring(0, dot) + ".svg" : path + ".svg";
            }

            if(!(svg ? wandStat.getStatImage().saveAsSVG(path, true) : Global.saveBufferedImage(wandStat.getStatImage().toPNG(), path))){
                System.out.println("error while saving image \"" + outputPath + "wandStat_" + wandStat.getFileName() + "\"");
            }
        }
    }

    public String getSprite(Wand wand){
        if(wandList == null){
            System.out.println("error wand file not loaded at \"" + Global.getPathConfig() + "wands.txt" + "\", returning default sprite");
            return "./src/main/java/org/example/image/wand/wand_0832.png";
        }

        int min = 0;
        int max = wandList.length - 1;
        int current;
        int comparaisonResult;
        boolean stop = false;

        while(min < max && !stop){
            current = min + (max - min)/2;
            comparaisonResult = WandStat.compare(wand, wandList[current]);
            if(comparaisonResult > 0){
                max = current;
            }else if(comparaisonResult < 0){
                min = current;
            }else{
                min = current;
                max = current;
                while(min - 1 >= 0 && WandStat.compare(wand, wandList[min - 1]) == 0){
                    min--;
                }
                while(max + 1 < wandList.length && WandStat.compare(wand, wandList[max + 1]) == 0){
                    max++;
                }
            }
            if(min + 1 == max){
                comparaisonResult = Math.abs(WandStat.compare(wand, wandList[min])) - Math.abs(WandStat.compare(wand, wandList[max]));
                if(comparaisonResult > 0){
                    min = max;
                }else if(comparaisonResult < 0){
                    max = min;
                }else{
                    stop = true;
                }
            }
            //System.out.println("range found: [" + min + ";" + max + "]\n ->" + wandList[min].toString() + "\n ->" + wandList[max].toString() + "\n");
        }

        /*System.out.println("valid wands (" + min + ", " + max + "):");
        for(int i=min; i <= max; i++){
            System.out.println(wandList[i].toString());
        }*/

        if(min == max){
            Random random = new Random();
            int randomNumber = random.nextInt(max + 1 - min) + min;
            //System.out.println("wand N°" + randomNumber + " " + wandList[randomNumber].getSprite());
            return wandList[randomNumber].getSprite();
        }else{
            return wandList[min].getSprite();
        }
    }
}