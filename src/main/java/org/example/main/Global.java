package org.example.main;

import org.example.commands.CommandLocal;
import static org.example.listeners.CommandListener.createCommandArray;
import org.example.localization.LanguageManager;
import static org.example.localization.LanguageManager.Language;
import org.example.localization.LocalizedText;
import org.example.menu.MenuManager;
import org.example.spells.*;
import static org.example.WandConnoisseur.jda;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Global{
    private static final LanguageManager languageManager = new LanguageManager();
    private static final LocalizedText ERROR_INVALID_DELAY = Global.getLanguageManager().get("ERROR_INVALID_DELAY");
    private static final LocalizedText ERROR_NOT_INTEGER = Global.getLanguageManager().get("ERROR_NOT_INTEGER");
    private static final LocalizedText ERROR_UNKNOWN_SPELL = Global.getLanguageManager().get("ERROR_UNKNOWN_SPELL");
    private static final LocalizedText ERROR_UNKNOWN_SPELLS = Global.getLanguageManager().get("ERROR_UNKNOWN_SPELLS");
    private static final LocalizedText ERROR_NO_SPELL = Global.getLanguageManager().get("ERROR_NO_SPELL");
    private static final LocalizedText ERROR_MESSAGE_TOO_LONG = Global.getLanguageManager().get("ERROR_MESSAGE_TOO_LONG");
    private static final LocalizedText ERROR_READING_FILE = Global.getLanguageManager().get("ERROR_READING_FILE");
    private static final LocalizedText ERROR_XML_NO_WAND = Global.getLanguageManager().get("ERROR_XML_NO_WAND");
    private static final LocalizedText ERROR_WRONG_FILE_TYPE = Global.getLanguageManager().get("ERROR_WRONG_FILE_TYPE");
    public enum MimeType{TEXT("text/plain"), CSV("text/csv"), HTML("text/html"), CSS("text/css"), JAVASCRIPT("text/javascript"), JSON("application/json"), XML("application/xml"), SVG("image/svg+xml"), PNG("image/png"), JPEG("image/jpeg"), GIF("image/gif"), WEBP("image/webp"), PDF("application/pdf"), ZIP("application/zip"), BINARY("application/octet-stream"), DEFAULT("application/octet-stream");
        private final String value;

        MimeType(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }
    public enum DamageType{PROJECTILE, MELEE, EXPLOSION, ELECTRICITY, FIRE, DRILL, SLICE, ICE, HEALING, PHYSICS_HIT, RADIOACTIVE, POISON, OVEREATING, CURSE, HOLY;
        public String getDisplayName(){
            String result = name().toLowerCase().replace('_', ' ');
            return Character.toUpperCase(result.charAt(0)) + result.substring(1);
        }
    }
    public record DamageTypeBooleanPair(boolean value, DamageType damageType){
        public static DamageTypeBooleanPair of(boolean value, DamageType damageType){
            return new DamageTypeBooleanPair(value, damageType);
        }
    }
    public record DamageTypeDoublePair(double value, DamageType damageType){
        public static DamageTypeDoublePair of(double value, DamageType damageType){
            return new DamageTypeDoublePair(value, damageType);
        }
    }
    private static final Player player = new Player(Double.MAX_VALUE, Integer.MAX_VALUE, true);
    private static Wand lastWand = null;
    private static int projectileCount = 0;
    private static int enemyCount = 0;
    private static final DecimalFormat DF = createDecimalFormat();
    private static final String pathOutput = "./src/main/java/org/example/fileOutput/";
    private static final String pathAutoDelete = "./src/main/java/org/example/fileOutput/autoDelete/";
    private static final String pathConfig = "./src/main/java/org/example/configFiles/";
    private static final SpellFilter spellFilter = new SpellFilter();
    private static final String[] spellProperties = SpellFilter.PROPERTY_RESOLVERS.keySet().stream().sorted().toArray(String[]::new);
    private static final String[] spellStringProperties = SpellFilter.STRING_PROPERTY_RESOLVERS.keySet().stream().sorted().toArray(String[]::new);
    public static boolean modded = false;
    private static final ProjectileList projectileList = new ProjectileList();
    private static final ScriptList scriptList = new ScriptList();
    private static final SpellList spellList = new SpellList(spell -> modded || !spell.getIsModded());
    private static final SpellList spellListNonRecursive = new SpellList(spell -> !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListProjectileType = new SpellList(spell -> spell.getType() == Spell.SpellType.projectile && (modded || !spell.getIsModded()));
    private static final SpellList spellListProjectileTypeNonRecursive = new SpellList(spell -> spell.getType() == Spell.SpellType.projectile && !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListRelatedProjectile = new SpellList(spell -> spell.getRelatedProjectile() != null && (modded || !spell.getIsModded()));
    private static final SpellList spellListRelatedProjectileNonRecursive = new SpellList(spell -> spell.getRelatedProjectile() != null && !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListProjectileComponent = new SpellList(spell -> spell.getRelatedProjectile() != null && spell.getRelatedProjectile().getProjectileComponent() != null && (modded || !spell.getIsModded()));
    private static final SpellList spellListProjectileComponentNonRecursive = new SpellList(spell -> spell.getRelatedProjectile() != null && spell.getRelatedProjectile().getProjectileComponent() != null && !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListModifier = new SpellList(spell -> spell.getType() == Spell.SpellType.modifier && (modded || !spell.getIsModded()));
    private static final SpellList spellListModifierNonRecursive = new SpellList(spell -> spell.getType() == Spell.SpellType.modifier && !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListStaticProjectile = new SpellList(spell -> spell.getType() == Spell.SpellType.static_projectile && (modded || !spell.getIsModded()));
    private static final SpellList spellListStaticProjectileNonRecursive = new SpellList(spell -> spell.getType() == Spell.SpellType.static_projectile && !spell.getRecursive() && (modded || !spell.getIsModded()));
    private static final SpellList spellListLifetimeModifier = new SpellList(spell -> spell.getLifetime() != 0 && (modded || !spell.getIsModded()), Comparator.comparing(Spell::getLifetime).thenComparing(Spell::getName));
    private static final String[] aliasList = spellList.getAllAlias();
    private static final String[] aliasListRelatedProjectile = spellListRelatedProjectile.getAllAlias();
    private static final String[] aliasListProjectileComponent = spellListProjectileComponent.getAllAlias();
    private static final String[] aliasListLifetimeModifier = spellListLifetimeModifier.getAllAlias();
    private static Command[] commandList = null;
    private static final CommandLocal[] commandLocalList = createCommandArray();
    private static final WandList wandList = new WandList();
    private static final int baseImageSize = 16;
    private static final int baseIconSize = 7;
    private static final int scaleFactor = 5;
    private static final int margin = 4*scaleFactor;
    private static final Font titleFont = loadFont("./src/main/java/org/example/TitleFont.ttf");
    private static final Font pixelFont = loadFont("./src/main/java/org/example/PixelFont.ttf");
    private static final Font pixelFontSmall = loadFont("./src/main/java/org/example/PixelFontSmall.ttf");
    private static final Font glyphFont = loadFont("./src/main/java/org/example/GlyphFont.ttf");
    private static final Pattern delayPattern = Pattern.compile("^ *([+-]?[0-9]+(|\\.[0-9]+)?)(?: *(|[fs]))? *$");
    private static final Pattern spellPattern = Pattern.compile("^(?:(inf|max|[0-9]+):)?([^:]*)(?::([0-9]+))?$");
    private static final Pattern alwaysCastPattern = Pattern.compile("^([^:]*)(?::([0-9]+))?$");
    private static final Pattern wandSpritePattern = Pattern.compile("^wand_[0-9]{4}$");
    public static final MenuManager menuManager = new MenuManager();
    private static long currentFrame = 0;
    private static final RandomGenerator randomGenerator = new RandomGenerator();
    private static boolean reqOEState = false;

    // getters
    public static Player getPlayer(){
        return player;
    }

    public static int getProjectileCount(){
        return projectileCount;
    }

    public static Wand getLastWand(){
        return lastWand;
    }

    public static int getEnemyCount(){
        return enemyCount;
    }

    public static LanguageManager getLanguageManager(){
        return languageManager;
    }

    public static String getPathOutput(){
        return pathOutput;
    }

    public static String getPathAutoDelete(){
        return pathAutoDelete;
    }

    public static String getPathConfig(){
        return pathConfig;
    }

    public static SpellFilter getSpellFilter(){
        return spellFilter;
    }

    public static String[] getSpellProperties(){
        return spellProperties;
    }

    public static String[] getSpellStringProperties(){
        return spellStringProperties;
    }

    public static ProjectileList getProjectileList(){
        return projectileList;
    }

    public static ScriptList getScriptList(){
        return scriptList;
    }

    public static SpellList getSpellList(){
        return spellList;
    }

    public static SpellList getSpellListProjectileType(){
        return spellListProjectileType;
    }

    public static SpellList getSpellListProjectileTypeNonRecursive(){
        return spellListProjectileTypeNonRecursive;
    }

    public static SpellList getSpellListRelatedProjectile(){
        return spellListRelatedProjectile;
    }

    public static SpellList getSpellListRelatedProjectileNonRecursive(){
        return spellListRelatedProjectileNonRecursive;
    }

    public static SpellList getSpellListProjectileComponent(){
        return spellListProjectileComponent;
    }

    public static SpellList getSpellListProjectileComponentNonRecursive(){
        return spellListProjectileComponentNonRecursive;
    }

    public static SpellList getSpellListModifier(){
        return spellListModifier;
    }

    public static SpellList getSpellListStaticProjectile(){
        return spellListStaticProjectile;
    }

    public static SpellList getSpellListLifetimeModifier(){
        return spellListLifetimeModifier;
    }

    public static SpellList getSpellListNonRecursive(){
        return spellListNonRecursive;
    }

    public static SpellList getSpellListModifierNonRecursive(){
        return spellListModifierNonRecursive;
    }

    public static SpellList getSpellListStaticProjectileNonRecursive(){
        return spellListStaticProjectileNonRecursive;
    }

    public static String[] getAliasList(){
        return aliasList;
    }

    public static String[] getAliasListRelatedProjectile(){
        return aliasListRelatedProjectile;
    }

    public static String[] getAliasListProjectileComponent(){
        return aliasListProjectileComponent;
    }

    public static String[] getAliasListLifetimeModifier(){
        return aliasListLifetimeModifier;
    }

    public static Command[] getCommandList(){
        if(commandList == null){
            commandList = jda.retrieveCommands().complete().toArray(new Command[0]);
            Arrays.sort(commandList, Comparator.comparing(Command::getName));
        }

        return commandList;
    }

    public static Command[] getCommandList(String name){
        return Arrays.stream(getCommandList()).filter(command -> command.getName().contains(name)).toArray(Command[]::new);
    }

    public static Command getCommand(String name){
        Command[] commandListTemp = getCommandList();

        for(Command command : commandListTemp){
            if(command.getName().equals(name)){
                return command;
            }
        }

        return null;
    }

    public static CommandLocal[] getCommandLocalList(){
        return commandLocalList;
    }

    public static CommandLocal[] getCommandLocalList(String name, Language language){
        return Arrays.stream(getCommandLocalList()).filter(commandLocal -> commandLocal.getName().get(language).contains(name)).toArray(CommandLocal[]::new);
    }

    public static CommandLocal[] getCommandLocalList(String name, Locale language){
        return Arrays.stream(getCommandLocalList()).filter(commandLocal -> commandLocal.getName().get(language).contains(name)).toArray(CommandLocal[]::new);
    }

    public static CommandLocal getCommandLocal(String name, Language language){
        for(CommandLocal commandLocal : commandLocalList){
            if(commandLocal.getName().get(language).equals(name)){
                return commandLocal;
            }
        }

        return null;
    }

    public static CommandLocal getCommandLocal(String name, Locale language){
        for(CommandLocal commandLocal : commandLocalList){
            if(commandLocal.getName().get(language).equals(name)){
                return commandLocal;
            }
        }

        return null;
    }

    public static WandList getWandList(){
        return wandList;
    }

    public static int getBaseImageSize(){
        return baseImageSize;
    }

    public static int getBaseIconSize(){
        return baseIconSize;
    }

    public static int getScaleFactor(){
        return scaleFactor;
    }

    public static int getMargin(){
        return margin;
    }

    public static Font getTitleFont(){
        return titleFont;
    }

    public static Font getPixelFont(){
        return pixelFont;
    }

    public static Font getPixelFontSmall(){
        return pixelFontSmall;
    }

    public static Font getGlyphFont(){
        return glyphFont;
    }

    public static Pattern getDelayPattern(){
        return delayPattern;
    }

    public static Pattern getSpellPattern(){
        return spellPattern;
    }

    public static Pattern getAlwaysCastPattern(){
        return alwaysCastPattern;
    }

    public static long getCurrentFrame(){
        return currentFrame;
    }

    public static void nextFrame(){
        if(currentFrame == Long.MAX_VALUE){
            currentFrame = 0;
        }else{
            currentFrame++;
        }
    }

    public static void nextFrame(int nb){
        if(nb < 0){
            nb = -nb;
        }

        if(currentFrame > Long.MAX_VALUE - nb){
            // overflow
            currentFrame = - Long.MAX_VALUE + currentFrame + nb;
        }else{
            currentFrame += nb;
        }
    }

    public static RandomGenerator getRandomGenerator(){
        return randomGenerator;
    }

    public static boolean getReqOEState(){
        return reqOEState;
    }

    public static void switchReqEOState(){
        reqOEState = !reqOEState;
    }

    // setters
    public static void setLastWand(Wand wand){
        lastWand = wand;
    }

    private Global(){
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    private static DecimalFormat createDecimalFormat(){
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(' ');

        return new DecimalFormat("0.##", dfs);
    }

    public static String format(double value){
        synchronized(DF){
            return DF.format(value);
        }
    }

    public static String delayFormat(int value){
        return String.format("%1$df (%2$3.2fs)", value, value/60.0).replace(",", ".");
    }

    public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle){
        if(img == null){
            return null;
        }
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int)Math.floor(w*cos + h*sin);
        int newHeight = (int)Math.floor(h*cos + w*sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w)/2.0, (newHeight - h)/2.0);

        at.rotate(rads, w/2.0, h/2.0);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    public static BufferedImage scaleImage(BufferedImage image, int scale){
        if(image == null){
            return null;
        }
        if(scale <= 1){
            return image;
        }

        int type = image.getType();
        return switch(type){
            case 1, 2, 3, 4 -> scaleInt(image, scale, type);
            case 6, 7 -> scale4BYTE(image, scale, type);
            default -> scaleImageGeneric(image, scale);
        };
    }

    private static BufferedImage scaleImageGeneric(BufferedImage originalImage, int scaleFactor){
        int newWidth = originalImage.getWidth()*scaleFactor;
        int newHeight = originalImage.getHeight()*scaleFactor;
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    private static BufferedImage scaleInt(BufferedImage image, int scale, int type){
        int width = image.getWidth();
        int height = image.getHeight();
        int scaledWidth = width*scale;
        BufferedImage newImage = new BufferedImage(scaledWidth, height*scale, type);
        int[] src = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[] dst = ((DataBufferInt) newImage.getRaster().getDataBuffer()).getData();
        int[] expandedRow = new int[scaledWidth];

        for(int y=0; y < height; y++){
            int srcIndex = y*width;
            int dstIndex = 0;

            for(int x=0; x < width; x++){
                int pixel = src[srcIndex + x];
                for(int sx=0; sx < scale; sx++){
                    expandedRow[dstIndex++] = pixel;
                }
            }

            int dstRow = y*scale*scaledWidth;
            for(int sy=0; sy < scale; sy++){
                System.arraycopy(expandedRow, 0, dst, dstRow + sy*scaledWidth, scaledWidth);
            }
        }

        return newImage;
    }

    public static BufferedImage scale4BYTE(BufferedImage src, int scale, int type){
        final int w = src.getWidth();
        final int h = src.getHeight();
        final int dw = w*scale;
        final int dh = h*scale;
        final BufferedImage dst = new BufferedImage(dw, dh, type);
        final byte[] s = ((java.awt.image.DataBufferByte) src.getRaster().getDataBuffer()).getData();
        final byte[] d = ((java.awt.image.DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        final int srcStride = w << 2;
        final int dstStride = dw << 2;

        for(int y=0; y < h; y++){
            final int sRow = y*srcStride;
            final int dRow = y*scale*dstStride;

            for (int sy=0; sy < scale; sy++){
                int sp = sRow;
                int dp = dRow + sy*dstStride;

                for (int x = 0; x < w; x++){
                    final byte a = s[sp++];
                    final byte b = s[sp++];
                    final byte g = s[sp++];
                    final byte r = s[sp++];

                    for (int sx = 0; sx < scale; sx++){
                        d[dp++] = a;
                        d[dp++] = b;
                        d[dp++] = g;
                        d[dp++] = r;
                    }
                }
            }
        }

        return dst;
    }

    public static BufferedImage grayscaleImage(BufferedImage originalImage, boolean luminance){
        if(originalImage == null){
            return null;
        }

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int argb = originalImage.getRGB(x, y);

                int alpha = (argb >> 24) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8)  & 0xFF;
                int blue = argb & 0xFF;

                int gray = luminance ? (int)(0.299*red + 0.587*green + 0.114*blue) : (red + green + blue)/3;
                int newArgb = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                grayscaleImage.setRGB(x, y, newArgb);
            }
        }

        return grayscaleImage;
    }

    public static BufferedImage loadImage(String path){
        BufferedImage result = null;
        try{
            result = ImageIO.read(new File(path));
        }catch(IOException e){
            System.out.println("image not found: \"" + path + "\"");
        }
        return result;
    }

    public static void sendMessage(String message, SlashCommandInteractionEvent event, boolean replyEvent, boolean ansiFormatting){
        int chunkSize = 1970;
        String chunk = "";
        String[] lines = message.split("\\r?\\n");

        for(int i=0; i < lines.length; i++){
            if(chunk.length() + lines[i].length() + 1 > chunkSize){
                if(chunk.length() <= chunkSize){
                    if(replyEvent){
                        event.getHook().editOriginal(ansiFormatting ? "```ansi\n" + chunk + "```" : chunk).queue();
                        replyEvent = false;
                    }else{
                        event.getChannel().sendMessage(ansiFormatting ? "```ansi\n" + chunk + "```" : chunk).queue();
                    }
                }else{
                    if(replyEvent){
                        event.getHook().editOriginal(ansiFormatting ? "```ansi\n" + ERROR_MESSAGE_TOO_LONG.get(event) + "```" : ERROR_MESSAGE_TOO_LONG.get(event)).queue();
                        replyEvent = false;
                    }else{
                        event.getChannel().sendMessage(ansiFormatting ? "```ansi\n" + ERROR_MESSAGE_TOO_LONG.get(event) + "```" : ERROR_MESSAGE_TOO_LONG.get(event)).queue();
                    }
                }
                chunk = lines[i];
            }else{
                chunk += chunk.isEmpty() ? lines[i] : "\n" + lines[i];
            }
            if(i + 1 == lines.length && !chunk.equals("")){
                if(replyEvent){
                    event.getHook().editOriginal(ansiFormatting ? "```ansi\n" + chunk + "```" : chunk).queue();
                    replyEvent = false;
                }else{
                    event.getChannel().sendMessage(ansiFormatting ? "```ansi\n" + chunk + "```" : chunk).queue();
                }
            }
        }
    }

    public static Node getWandNode(Document doc){
        return getWandNode(doc.getChildNodes());
    }

    public static Node getWandNode(NodeList children){
        for(int i=0; i < children.getLength(); i++){
            Node currentNode = children.item(i);
            if(currentNode.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            Element currentElement = (Element)currentNode;
            if(currentElement.getTagName().equals("Entity") && Arrays.asList(currentElement.getAttribute("tags").split(",")).contains("wand")){
                return currentNode;
            }
            Node subNode = getWandNode(currentNode.getChildNodes());
            if(subNode != null){
                return subNode;
            }
        }

        return null;
    }

    public static Spell getOrNull(ArrayList<Spell> list, int index){
        return index >= 0 && index < list.size() ? list.get(index) : null;
    }

    public static void setOrExpand(ArrayList<Spell> list, int index, Spell spell) {
        while(list.size() <= index){
            list.add(null);
        }
        list.set(index, spell);
    }

    public static Wand slashInteractionToWand(SlashCommandInteractionEvent event){
        SpellList spellList = Global.getSpellList();
        OptionMapping spellsOption = event.getOption("spells");
        OptionMapping alwaysCastOption = event.getOption("always_cast");
        OptionMapping shuffleOption = event.getOption("shuffle");
        OptionMapping drawOption = event.getOption("draw");
        OptionMapping castDelayOption = event.getOption("cast_delay");
        OptionMapping rechargeTimeOption = event.getOption("recharge_time");
        OptionMapping manaMaxOption = event.getOption("mana_max");
        OptionMapping manaRegenOption = event.getOption("mana_regen");
        OptionMapping spreadOption = event.getOption("spread");
        OptionMapping speedOption = event.getOption("speed");
        OptionMapping fileOption = event.getOption("file");
        String spellsInput = "";
        StringBuilder unknownSpells = new StringBuilder();
        boolean severalUnknown = false;
        boolean shuffle = false;
        int draw = 1;
        int castDelay = 0;
        int rechargeTime = 0;
        int manaMax = 1000000;
        int manaRegen = 1000000;
        int nb_slot = 0;
        double spread = 0.0;
        double speed = 1.0;
        String sprite = "";
        Spell currentSpell;
        int currentSpellCount = 1;
        ArrayList<Spell> alwaysCasts = new ArrayList<>();
        ArrayList<Spell> spells = new ArrayList<>();
        String[] spellsString;
        Wand wand;
        Matcher m;

        if(fileOption != null){
            try{
                Message.Attachment attachment = fileOption.getAsAttachment();
                String currentString = attachment.getFileExtension();

                if(currentString == null || !currentString.equalsIgnoreCase("xml")){
                    event.reply(ERROR_WRONG_FILE_TYPE.get(event, new String[]{currentString == null ? "" : currentString, "xml"})).setEphemeral(true).queue();
                    return null;
                }

                Node wandNode = getWandNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(attachment.getProxy().download().join()));
                if(wandNode == null){
                    event.reply(ERROR_XML_NO_WAND.get(event)).setEphemeral(true).queue();
                    return null;
                }

                NodeList children = wandNode.getChildNodes();
                for(int i=0; i < children.getLength(); i++){
                    Node currentNode = children.item(i);
                    if(currentNode.getNodeType() != Node.ELEMENT_NODE){
                        continue;
                    }
                    Element element = (Element)currentNode;
                    String indent = "    ";
                    switch(element.getTagName()){
                        case "AbilityComponent" -> {
                            currentString = element.getAttribute("mana_max");
                            if(!currentString.equals("")){
                                try{manaMax = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                            }
                            currentString = element.getAttribute("mana_charge_speed");
                            if(!currentString.equals("")){
                                try{manaRegen = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                            }
                            currentString = element.getAttribute("sprite_file");
                            if(!currentString.equals("")){
                                sprite = currentString.replaceFirst("^.*[/\\\\]", "").replaceFirst("\\.[^.]*$", "");
                                sprite = "./src/main/java/org/example/image/wand/" + (wandSpritePattern.matcher(sprite).matches() ? "" : "custom/") + sprite + ".png";
                            }

                            NodeList statNodes = currentNode.getChildNodes();
                            for(int j=0; j < statNodes.getLength(); j++){
                                Node statNode = statNodes.item(j);
                                if(statNode.getNodeType() != Node.ELEMENT_NODE){
                                    continue;
                                }
                                Element statElement = (Element) statNode;
                                switch(statElement.getTagName()){
                                    case "gunaction_config" -> {
                                        currentString = statElement.getAttribute("spread_degrees");
                                        if(!currentString.equals("")){
                                            try{spread = Double.parseDouble(currentString);}catch(Exception ignored){}
                                        }
                                        currentString = statElement.getAttribute("speed_multiplier");
                                        if(!currentString.equals("")){
                                            try{speed = Double.parseDouble(currentString);}catch(Exception ignored){}
                                        }
                                        currentString = statElement.getAttribute("fire_rate_wait");
                                        if(!currentString.equals("")){
                                            try{castDelay = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                                        }
                                    }
                                    case "gun_config" -> {
                                        currentString = statElement.getAttribute("shuffle_deck_when_empty");
                                        if(!currentString.equals("")){
                                            try{shuffle = Integer.parseInt(currentString) == 1;}catch(Exception ignored){}
                                        }
                                        currentString = statElement.getAttribute("actions_per_round");
                                        if(!currentString.equals("")){
                                            try{draw = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                                        }
                                        currentString = statElement.getAttribute("reload_time");
                                        if(!currentString.equals("")){
                                            try{rechargeTime = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                                        }
                                        currentString = statElement.getAttribute("deck_capacity");
                                        if(!currentString.equals("")){
                                            try{nb_slot = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                                        }
                                    }
                                }
                            }
                        }
                        case "Entity" -> {
                            if(spellsOption != null || !Arrays.asList(element.getAttribute("tags").split(",")).contains("card_action")){
                                break;
                            }

                            NodeList spellNodes = currentNode.getChildNodes();
                            boolean alwaysCast = false;
                            int slot = 0;
                            currentSpell = null;

                            for(int j=0; j < spellNodes.getLength(); j++){
                                Node spellNode = spellNodes.item(j);
                                if(spellNode.getNodeType() != Node.ELEMENT_NODE){
                                    continue;
                                }
                                Element spellElement = (Element) spellNode;
                                switch(spellElement.getTagName()){
                                    case "ItemActionComponent" -> {
                                        currentString = spellElement.getAttribute("action_id");
                                        currentSpell = spellList.getSpell(currentString);
                                        if(currentSpell == null){
                                            System.out.println("unknown spell: " + currentString);
                                        }
                                    }
                                    case "ItemComponent" -> {
                                        alwaysCast = spellElement.getAttribute("permanently_attached").equals("1");
                                        currentString = spellElement.getAttribute("inventory_slot.x");
                                        if(!currentString.equals("")){
                                            try{slot = (int)Math.floor(Double.parseDouble(currentString));}catch(Exception ignored){}
                                        }
                                    }
                                }
                            }
                            if(currentSpell != null){
                                if(alwaysCast){
                                    alwaysCasts.add(currentSpell);
                                }else{
                                    while(getOrNull(spells, slot) != null){
                                        slot++;
                                    }
                                    setOrExpand(spells, slot, currentSpell);
                                }
                            }
                        }
                    }
                }

                for(int i=0; i < spells.size() && spells.size() > nb_slot; i++){
                    if(spells.get(i) == null){
                        spells.remove(i);
                        i--;
                    }
                }
                while(spells.size() < nb_slot){
                    spells.add(null);
                }
            }catch(Exception e){
                System.out.println("Error reading xml: " + e.getMessage() + "\n");
                e.printStackTrace();
                event.reply(ERROR_READING_FILE.get(event)).setEphemeral(true).queue();
                return null;
            }
        }

        if(shuffleOption != null){
            shuffle = shuffleOption.getAsBoolean();
        }
        if(drawOption != null){
            draw = Math.max(drawOption.getAsInt(), 1);
        }
        if(castDelayOption != null){
            try{
                castDelay = Global.stringToDelay(event.getGuildLocale().toLocale(), castDelayOption.getAsString());
            }catch(Exception e){
                event.reply("cast_delay: " + e.getMessage()).setEphemeral(true).queue();
                return null;
            }
        }
        if(rechargeTimeOption != null){
            try{
                rechargeTime = Global.stringToDelay(event.getGuildLocale().toLocale(), rechargeTimeOption.getAsString());
            }catch(Exception e){
                event.reply("recharge_time: " + e.getMessage()).setEphemeral(true).queue();
                return null;
            }
        }
        if(manaMaxOption != null){
            manaMax = manaMaxOption.getAsInt();
        }
        if(manaRegenOption != null){
            manaRegen = manaRegenOption.getAsInt();
        }
        if(spreadOption != null){
            spread = spreadOption.getAsDouble();
        }
        if(speedOption != null){
            speed = speedOption.getAsDouble();
        }

        if(spellsOption != null){
            spellsInput = spellsOption.getAsString();
            spellsString = spellsInput.split(",", -1);

            for(int i=0; i < spellsString.length; i++){
                spellsString[i] = spellsString[i].trim().toLowerCase();
            }

            for(String s : spellsString){
                if(s.equals("")){
                    spells.add(null);
                    continue;
                }
                m = spellPattern.matcher(s);
                if(m.find()){
                    currentSpell = spellList.getSpell(m.group(2));
                    if(currentSpell != null){
                        switch((m.group(1) != null) ? m.group(1) : "inf"){
                            case "inf" -> currentSpell.makeInfinite();
                            case "max" -> currentSpell.refillCharges();
                            default -> currentSpell.setCharges(Integer.parseInt(m.group(1)));
                        }
                    }
                    currentSpellCount = (m.group(3) != null) ? Integer.parseInt(m.group(3)) : 1;
                }else{
                    currentSpell = null;
                }
                if(currentSpell != null){
                    spells.add(currentSpell);
                    for(int j=1; j < currentSpellCount; j++){
                        spells.add(currentSpell.clone());
                    }
                }else{
                    if(unknownSpells.isEmpty()){
                        unknownSpells.append(s);
                    }else{
                        if(!severalUnknown){
                            unknownSpells.insert(0, "\"").append("\"");
                        }
                        severalUnknown = true;
                        unknownSpells.append(", \"").append(s).append("\"");
                    }
                }
            }
        }

        if(alwaysCastOption != null){
            spellsInput = alwaysCastOption.getAsString();
            spellsString = spellsInput.split(",", -1);

            for(int i=0; i < spellsString.length; i++){
                spellsString[i] = spellsString[i].trim().toLowerCase();
            }

            for(String s : spellsString){
                m = alwaysCastPattern.matcher(s);
                if(m.find()){
                    currentSpell = spellList.getSpell(m.group(1));
                    currentSpellCount = (m.group(2) != null) ? Integer.parseInt(m.group(2)) : 1;
                }else{
                    currentSpell = null;
                }
                if(currentSpell != null){
                    alwaysCasts.add(currentSpell);
                    for(int j=1; j < currentSpellCount; j++){
                        alwaysCasts.add(currentSpell.clone());
                    }
                }else{
                    if(unknownSpells.isEmpty()){
                        unknownSpells.append(s);
                    }else{
                        if(!severalUnknown){
                            unknownSpells.insert(0, "\"").append("\"");
                        }
                        severalUnknown = true;
                        unknownSpells.append(", \"").append(s).append("\"");
                    }
                }
            }
        }

        if(!unknownSpells.isEmpty()){
            if(severalUnknown){
                event.reply(ERROR_UNKNOWN_SPELLS.get(event, unknownSpells.toString())).setEphemeral(true).queue();
            }else{
                event.reply(ERROR_UNKNOWN_SPELL.get(event, unknownSpells.toString())).setEphemeral(true).queue();
            }
            return null;
        }

        if(fileOption == null && spells.size() == 0){
            event.reply(ERROR_NO_SPELL.get(event)).setEphemeral(true).queue();
            return null;
        }

        wand = new Wand(shuffle, draw, castDelay, rechargeTime, manaMax, manaRegen, spells.size(), spread, speed);
        if(!sprite.equals("")){
            wand.setSprite(sprite);
        }
        wand.setAlwaysCasts(alwaysCasts);
        wand.setSpells(spells.toArray(new Spell[0]));

        return wand;
    }

    public static int stringToDelay(Locale language, String input){
        Matcher m = delayPattern.matcher(input);
        int result;

        if(m.find()){
            switch(m.group(3)){
                case "s" -> result = (int)Math.round(Double.parseDouble(m.group(1))*60.0);
                case "f" -> {
                    if(!m.group(2).equals("")){
                        throw new IllegalArgumentException(ERROR_NOT_INTEGER.get(language, input));
                    }
                    result = Integer.parseInt(m.group(1));
                }
                default -> {
                    if(m.group(2).equals("")){
                        result = Integer.parseInt(m.group(1));
                    }else{
                        result = (int)Math.round(Double.parseDouble(m.group(1))*60.0);
                    }
                }
            }
        }else{
            throw new IllegalArgumentException(ERROR_INVALID_DELAY.get(language, input));
        }

        return result;
    }

    public static void autoDeleteFiles(){
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathAutoDelete))){
            for(Path file : stream){
                if(Files.isRegularFile(file)){
                    Files.deleteIfExists(file);
                }
            }
        }catch(Exception e){
            System.out.println("failed to auto delete folder content \"" + pathAutoDelete + "\" " + e.getMessage());
        }
    }

    private static Font loadFont(String filePath){
        try{
            InputStream myStream = new FileInputStream(filePath);
            return Font.createFont(Font.TRUETYPE_FONT, myStream);
        }catch(Exception e){
            return new Font("Arial", Font.PLAIN, scaleFactor*7);
        }
    }

    public static File jPanelToFile(JPanel jPanel, String filePath){
        if(jPanel == null || jPanel.getSize().width <= 0 || jPanel.getSize().height <= 0){
            return null;
        }

        BufferedImage bi = new BufferedImage(jPanel.getWidth(), jPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        jPanel.paint(g);
        g.dispose();

        try{
            ImageIO.write(bi,"png", new File(filePath));
        }catch(Exception e){
            System.out.println("Error writing file \"" + filePath + "\" " + e.getMessage());
            return null;
        }

        return new File(filePath);
    }

    public static File bufferedImageToFile(BufferedImage image, String filePath){
        if(saveBufferedImage(image, filePath)){
            return new File(filePath);
        }

        return null;
    }

    public static byte[] bufferedImageToBytes(BufferedImage bufferedImage){
        if(bufferedImage == null){
            return null;
        }

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        }catch(IOException e){
            System.out.println("Error writing image: " + e.getMessage());
            return null;
        }
    }

    public static byte[] JPanelToBytes(JPanel jPanel){
        if(jPanel == null || jPanel.getWidth() <= 0 || jPanel.getHeight() <= 0){
            return null;
        }

        BufferedImage bi = new BufferedImage(jPanel.getWidth(), jPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        jPanel.paint(g);
        g.dispose();

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            javax.imageio.ImageIO.write(bi, "png", baos);
            return baos.toByteArray();
        }catch(IOException e){
            System.out.println("Error writing image: " + e.getMessage());
            return null;
        }
    }

    public static FileUpload byteToUpload(byte[] data, String name){
        try{
            return data != null ? FileUpload.fromData(data, name) : null;
        }catch(Exception e){
            return null;
        }
    }

    public static boolean saveByteFile(byte[] data, String path){
        try{
            Files.write(Path.of(path), data);
        }catch(Exception e){
            return false;
        }

        return true;
    }

    public static boolean saveBufferedImage(BufferedImage image, String path){
        try{
            ImageIO.write(image, "png", Path.of(path).toFile());
        }catch(Exception e){
            return false;
        }

        return true;
    }

    public static void toClipboard(String text){
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }

    public static void toClipboard(BufferedImage image){
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new Transferable(){
            @Override
            public DataFlavor[] getTransferDataFlavors(){
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor){
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException{
                if(!isDataFlavorSupported(flavor)){
                    throw new UnsupportedFlavorException(flavor);
                }
                return image;
            }
        }, null);
    }

    public static void toClipboard(byte[] bytes, MimeType mimeType){
        if(mimeType == null){
            mimeType = MimeType.DEFAULT;
        }

        final MimeType type = mimeType;

        DataFlavor flavor;

        try{
            flavor = new DataFlavor(type.getValue() + ";class=java.io.InputStream");
        }catch(ClassNotFoundException e){
            throw new IllegalArgumentException(e);
        }

        final DataFlavor contentFlavor = flavor;

        Transferable transferable = new Transferable(){
            @Override
            public DataFlavor[] getTransferDataFlavors(){
                return new DataFlavor[]{contentFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor){
                return contentFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException{
                if(!isDataFlavorSupported(flavor)){
                    throw new UnsupportedFlavorException(flavor);
                }

                return new java.io.ByteArrayInputStream(bytes);
            }
        };

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
    }

    public static FileUpload JPanelToUpload(JPanel panel, String name){
        return byteToUpload(JPanelToBytes(panel), name);
    }

    public static FileUpload bufferedImageToUpload(BufferedImage bufferedImage, String name){
        return byteToUpload(bufferedImageToBytes(bufferedImage), name);
    }

    public static FileUpload ImageBuilderToSVGUpload(ImageBuilder image, String name, boolean embed){
        return byteToUpload(image.toBytes(embed), name);
    }

    public static FileUpload ImageBuilderToPNGUpload(ImageBuilder image, String name){
        return bufferedImageToUpload(image.toPNG(), name);
    }

    public static int longToInt(Long nb){
        return (int)(nb & 0xFFFFFFFFL);
    }

    public static long intToLong(int lower, int higher){
        return ((long) higher << 32) | (lower & 0xffffffffL);
    }

    public static BufferedImage cloneBufferedImage(BufferedImage image){
        if(image == null){
            return null;
        }

        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }

    public static String truncate(String text, int length){
        if(text.length() <= length){
            return text;
        }else{
            return text.substring(0, length);
        }
    }

    public static String toDisplayName(String value){
        if(value == null || value.isEmpty()){
            return value;
        }

        String result = value.toLowerCase().replace('_', ' ');
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }
}