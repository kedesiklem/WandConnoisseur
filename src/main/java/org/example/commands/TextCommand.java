package org.example.commands;

import org.example.localization.LocalizedText;
import org.example.main.Global;
import org.example.main.ImageBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class TextCommand extends CommandLocal{
    private static final LocalizedText COMMAND_TEXT = Global.getLanguageManager().get("COMMAND_TEXT");
    private static final LocalizedText COMMAND_TEXT_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_DESCRIPTION");
    private static final LocalizedText COMMAND_TEXT_TEXT = Global.getLanguageManager().get("COMMAND_TEXT_TEXT");
    private static final LocalizedText COMMAND_TEXT_TEXT_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_TEXT_DESCRIPTION");
    private static final LocalizedText COMMAND_TEXT_FONT = Global.getLanguageManager().get("COMMAND_TEXT_FONT");
    private static final LocalizedText COMMAND_TEXT_FONT_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_FONT_DESCRIPTION");
    private static final LocalizedText COMMAND_TEXT_SIZE = Global.getLanguageManager().get("COMMAND_TEXT_SIZE");
    private static final LocalizedText COMMAND_TEXT_SIZE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_SIZE_DESCRIPTION");
    private static final LocalizedText COMMAND_TEXT_COLOR = Global.getLanguageManager().get("COMMAND_TEXT_COLOR");
    private static final LocalizedText COMMAND_TEXT_COLOR_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_COLOR_DESCRIPTION");
    private static final LocalizedText COMMAND_TEXT_TYPE = Global.getLanguageManager().get("COMMAND_TEXT_TYPE");
    private static final LocalizedText COMMAND_TEXT_TYPE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_TEXT_TYPE_DESCRIPTION");
    private static final LocalizedText ERROR_GENERATING_IMAGE = Global.getLanguageManager().get("ERROR_GENERATING_IMAGE");
    private static final LocalizedText ERROR_GRADIENT_IMPLEMENTATION = Global.getLanguageManager().get("ERROR_GRADIENT_IMPLEMENTATION");
    private static final LocalizedText ERROR_INVALID_TYPE = Global.getLanguageManager().get("ERROR_INVALID_TYPE");
    private static final Pattern colorPattern = Pattern.compile("^([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})?$");

    public TextCommand(){
        this.name = COMMAND_TEXT;
        this.description = COMMAND_TEXT_DESCRIPTION;
        this.commandOptions = new CommandOption[]{
            new CommandOption(OptionType.STRING, COMMAND_TEXT_TEXT, COMMAND_TEXT_TEXT_DESCRIPTION, true, false),
            new CommandOption(OptionType.STRING, COMMAND_TEXT_FONT, COMMAND_TEXT_FONT_DESCRIPTION, false, false, new String[]{"pixel", "title", "glyph"}),
            new CommandOption(OptionType.NUMBER, COMMAND_TEXT_SIZE, COMMAND_TEXT_SIZE_DESCRIPTION, false, false),
            new CommandOption(OptionType.STRING, COMMAND_TEXT_COLOR, COMMAND_TEXT_COLOR_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_TEXT_TYPE, COMMAND_TEXT_TYPE_DESCRIPTION, false, false, new String[]{"png", "svg"})
        };
    }

    private static BufferedImage scaleGradient(BufferedImage originalImage, int newHeight){
        if(originalImage == null){
            return null;
        }
        int newWidth = (int)Math.ceil((double)originalImage.getWidth()*(double)newHeight/(double)originalImage.getHeight());

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event){
        OptionMapping textOption = event.getOption("text");
        OptionMapping fontOption = event.getOption("font");
        OptionMapping sizeOption = event.getOption("size");
        OptionMapping colorOption = event.getOption("color");
        OptionMapping typeOption = event.getOption("type");
        String texte = "";
        String fontTexte = "title";
        float taille = 100;
        Color color = null;
        int type = 0;
        Font font;

        if(typeOption != null){
            switch(typeOption.getAsString()){
                case "png" -> {}
                case "svg" -> type = 1;
                default -> {
                    event.reply(ERROR_INVALID_TYPE.get(event, typeOption.getAsString())).setEphemeral(true).queue();
                    return;
                }
            }
        }
        if(textOption != null){
            texte = textOption.getAsString();
        }
        if(fontOption != null){
            fontTexte = fontOption.getAsString();
        }
        if(sizeOption != null){
            taille = Math.max((float)sizeOption.getAsDouble(), 1);
        }
        if(colorOption != null){
            if(colorOption.getAsString().equalsIgnoreCase("gradient")){
                if(type == 1){
                    event.reply(ERROR_GRADIENT_IMPLEMENTATION.get(event)).setEphemeral(true).queue();
                    return;
                }
            }else{
                Matcher m = colorPattern.matcher(colorOption.getAsString().toLowerCase());
                if(m.find()){
                    if(m.group(4) != null){ // RGBA
                        color = new Color(Integer.parseInt(m.group(1), 16), Integer.parseInt(m.group(2), 16), Integer.parseInt(m.group(3), 16), Integer.parseInt(m.group(4), 16));
                    }else{ // RGB
                        color = new Color(Integer.parseInt(m.group(1), 16), Integer.parseInt(m.group(2), 16), Integer.parseInt(m.group(3), 16), 255);
                    }
                }
            }
        }else if(!fontTexte.equals("title") || type == 1){
            color = new Color(255, 255, 255);
        }

        try{
            switch(fontTexte){
                case "pixel" -> font = Global.getPixelFont().deriveFont(Font.PLAIN, taille);
                case "glyph" -> {
                    font = Global.getGlyphFont().deriveFont(Font.PLAIN, taille);;
                    texte = texte.replace(" ", "   ");
                }
                default -> font = Global.getTitleFont().deriveFont(Font.PLAIN, taille);
            }

            if(color != null){
                ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0, 0));
                imageBuilder.setFont(font);
                imageBuilder.drawText(texte, 0, 0, color);
                if(type == 0){
                    event.replyFiles(Global.bufferedImageToUpload(imageBuilder.toPNG(), "text.png")).queue();
                }else{
                    event.replyFiles(Global.byteToUpload(imageBuilder.toBytes(true), "text.svg")).queue();
                }
            }else{ // gradient
                BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                Graphics2D tempG2d = image.createGraphics();
                tempG2d.setFont(font);
                FontMetrics fm = tempG2d.getFontMetrics();
                int width = fm.stringWidth(texte);
                int height = fm.getHeight() + fm.getMaxDescent()*2;
                tempG2d.dispose();

                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                BufferedImage gradientImage = scaleGradient(Global.loadImage("./src/main/java/org/example/image/other/gradient.png"), height);

                BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D maskG2d = mask.createGraphics();
                maskG2d.setFont(font);
                maskG2d.setColor(Color.BLACK);
                maskG2d.drawString(texte, 0, fm.getAscent());
                maskG2d.dispose();

                Graphics2D resultG2d = image.createGraphics();
                Rectangle gradientRect = new Rectangle(0, 0, gradientImage.getWidth(), gradientImage.getHeight());
                TexturePaint texture = new TexturePaint(gradientImage, gradientRect);
                resultG2d.setPaint(texture);
                resultG2d.fillRect(0, 0, width, height);
                resultG2d.setComposite(AlphaComposite.DstIn);
                resultG2d.drawImage(mask, 0, 0, null);
                resultG2d.dispose();

                event.replyFiles(Global.bufferedImageToUpload(image, "text.png")).queue();
            }
        }catch(Exception e){
            event.reply(ERROR_GENERATING_IMAGE.get(event)).setEphemeral(true).queue();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}