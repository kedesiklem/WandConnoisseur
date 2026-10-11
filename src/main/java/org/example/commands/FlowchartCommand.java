package org.example.commands;

import org.example.localization.LocalizedText;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.spells.Spell;

import java.nio.charset.StandardCharsets;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class FlowchartCommand extends CommandLocal{
    private static final LocalizedText COMMAND_FLOWCHART = Global.getLanguageManager().get("COMMAND_FLOWCHART");
    private static final LocalizedText COMMAND_FLOWCHART_DESCRIPTION = Global.getLanguageManager().get("COMMAND_FLOWCHART_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_SPELLS = Global.getLanguageManager().get("COMMAND_GENERAL_SPELLS");
    private static final LocalizedText COMMAND_GENERAL_SPELLS_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_SPELLS_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_ALWAYS_CAST = Global.getLanguageManager().get("COMMAND_GENERAL_ALWAYS_CAST");
    private static final LocalizedText COMMAND_GENERAL_ALWAYS_CAST_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_ALWAYS_CAST_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_SHUFFLE = Global.getLanguageManager().get("COMMAND_GENERAL_SHUFFLE");
    private static final LocalizedText COMMAND_GENERAL_SHUFFLE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_SHUFFLE_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_DRAW = Global.getLanguageManager().get("COMMAND_GENERAL_DRAW");
    private static final LocalizedText COMMAND_GENERAL_DRAW_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_DRAW_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_CAST_DELAY = Global.getLanguageManager().get("COMMAND_GENERAL_CAST_DELAY");
    private static final LocalizedText COMMAND_GENERAL_CAST_DELAY_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_CAST_DELAY_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_RECHARGE_TIME = Global.getLanguageManager().get("COMMAND_GENERAL_RECHARGE_TIME");
    private static final LocalizedText COMMAND_GENERAL_RECHARGE_TIME_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_RECHARGE_TIME_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_MANA_MAX = Global.getLanguageManager().get("COMMAND_GENERAL_MANA_MAX");
    private static final LocalizedText COMMAND_GENERAL_MANA_MAX_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_MANA_MAX_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_MANA_REGEN = Global.getLanguageManager().get("COMMAND_GENERAL_MANA_REGEN");
    private static final LocalizedText COMMAND_GENERAL_MANA_REGEN_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_MANA_REGEN_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_SPREAD = Global.getLanguageManager().get("COMMAND_GENERAL_SPREAD");
    private static final LocalizedText COMMAND_GENERAL_SPREAD_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_SPREAD_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_SPEED = Global.getLanguageManager().get("COMMAND_GENERAL_SPEED");
    private static final LocalizedText COMMAND_GENERAL_SPEED_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_SPEED_DESCRIPTION");
    private static final LocalizedText COMMAND_GENERAL_FILE = Global.getLanguageManager().get("COMMAND_GENERAL_FILE");
    private static final LocalizedText COMMAND_GENERAL_FILE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_GENERAL_FILE_DESCRIPTION");
    private static final LocalizedText COMMAND_FLOWCHART_TYPE = Global.getLanguageManager().get("COMMAND_FLOWCHART_TYPE");
    private static final LocalizedText COMMAND_FLOWCHART_TYPE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_FLOWCHART_TYPE_DESCRIPTION");
    private static final LocalizedText ERROR_GENERATING_FILE = Global.getLanguageManager().get("ERROR_GENERATING_FILE");
    private static final LocalizedText ERROR_GENERATING_IMAGE = Global.getLanguageManager().get("ERROR_GENERATING_IMAGE");
    private static final LocalizedText ERROR_INVALID_TYPE = Global.getLanguageManager().get("ERROR_INVALID_TYPE");

    public FlowchartCommand(){
        this.name = COMMAND_FLOWCHART;
        this.description = COMMAND_FLOWCHART_DESCRIPTION;
        this.commandOptions = new CommandOption[]{
            new CommandOption(OptionType.STRING, COMMAND_GENERAL_SPELLS, COMMAND_GENERAL_SPELLS_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_GENERAL_ALWAYS_CAST, COMMAND_GENERAL_ALWAYS_CAST_DESCRIPTION, false, true),
            new CommandOption(OptionType.BOOLEAN, COMMAND_GENERAL_SHUFFLE, COMMAND_GENERAL_SHUFFLE_DESCRIPTION, false, false),
            new CommandOption(OptionType.INTEGER, COMMAND_GENERAL_DRAW, COMMAND_GENERAL_DRAW_DESCRIPTION, false, false),
            new CommandOption(OptionType.STRING, COMMAND_GENERAL_CAST_DELAY, COMMAND_GENERAL_CAST_DELAY_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_GENERAL_RECHARGE_TIME, COMMAND_GENERAL_RECHARGE_TIME_DESCRIPTION, false, true),
            new CommandOption(OptionType.INTEGER, COMMAND_GENERAL_MANA_MAX, COMMAND_GENERAL_MANA_MAX_DESCRIPTION, false, false),
            new CommandOption(OptionType.INTEGER, COMMAND_GENERAL_MANA_REGEN, COMMAND_GENERAL_MANA_REGEN_DESCRIPTION, false, false),
            new CommandOption(OptionType.NUMBER, COMMAND_GENERAL_SPREAD, COMMAND_GENERAL_SPREAD_DESCRIPTION, false, false),
            new CommandOption(OptionType.NUMBER, COMMAND_GENERAL_SPEED, COMMAND_GENERAL_SPEED_DESCRIPTION, false, false),
            new CommandOption(OptionType.STRING, COMMAND_FLOWCHART_TYPE, COMMAND_FLOWCHART_TYPE_DESCRIPTION, false, false, new String[]{"png", "svg", "svg_light", "text", "text_file"}),
            new CommandOption(OptionType.ATTACHMENT, COMMAND_GENERAL_FILE, COMMAND_GENERAL_FILE_DESCRIPTION, false, false)
        };
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event){
        OptionMapping typeOption = event.getOption("type");
        int type = 0;

        if(typeOption != null){
            switch(typeOption.getAsString()){
                case "png" -> {}
                case "svg" -> type = 1;
                case "svg_light" -> type = 2;
                case "text" -> type = 3;
                case "text_file" -> type = 4;
                default -> {
                    event.reply(ERROR_INVALID_TYPE.get(event, typeOption.getAsString())).setEphemeral(true).queue();
                    return;
                }
            }
        }

        Wand wand = Global.slashInteractionToWand(event);
        String[] statOptions = new String[]{"always_cast", "cast_delay", "recharge_time", "mana_max", "mana_regen", "spread", "speed", "file"};
        boolean statChanged = false;
        boolean eventReplied = false;

        if(wand == null){
            return;
        }
        wand.saveFlowchartImage(Global.getPathOutput() + "fl.png", true);
        for(String statOption : statOptions){
            if(event.getOption(statOption) != null){
                statChanged = true;
                break;
            }
        }

        event.deferReply(false).queue();

        if(type <= 2){
            FileUpload spellsImage = Global.bufferedImageToUpload(statChanged ? wand.getStatImage().toPNG() : wand.getSpellsImage().toPNG(), "wand.png");
            FileUpload flowchartImage = switch(type){
                case 0 -> Global.bufferedImageToUpload(wand.getFlowchartImage(true), "flowchart.png");
                case 1 -> Global.byteToUpload(wand.getFlowchartImageSVG(true, true), "flowchart.svg");
                case 2 -> Global.byteToUpload(wand.getFlowchartImageSVG(true, false), "flowchart.svg");
                default -> null;
            };

            if(spellsImage != null){
                event.getHook().editOriginal("").setFiles(spellsImage).queue();
                eventReplied = true;
            }
            if(flowchartImage != null){
                if(eventReplied){
                    event.getChannel().sendFiles(flowchartImage).queue();
                }else{
                    event.getHook().editOriginal("").setFiles(flowchartImage).queue();
                    eventReplied = true;
                }
            }

            if(!eventReplied){
                event.getHook().editOriginal(ERROR_GENERATING_IMAGE.get(event)).queue();
            }
        }else{
            StringBuilder result = new StringBuilder();
            Spell[] spells = wand.getSpells(false);

            if(statChanged){
                if(wand.getShuffle()){
                    result.append("Shuffle: Yes");
                }else{
                    result.append("Shuffle: No");
                }
                result.append(", Spells/cast: ").append(wand.getNbDraw()).append(", Cast delay: ").append(Global.delayFormat(wand.getCastDelay())).append(", Recharge time: ").append(Global.delayFormat(wand.getRechargeTime())).append(", Max mana: ").append(wand.getMaxMana()).append(", Mana regen: ").append(wand.getRegenMana()).append(", Slots: ").append(wand.getNbSlot()).append(", Spread: ").append(wand.getSpread()).append("°, Speed: x").append(wand.getSpeed()).append("\n");
            }
            result.append("Spells: ");
            for(int i=0; i < spells.length; i++){
                if(i > 0){
                    result.append(", ");
                }
                result.append(spells[i].getName());
            }
            result.append("\n\n").append(wand.getFlowchartString(true, type != 4));

            if(type == 4){
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                    baos.write(result.toString().getBytes(StandardCharsets.UTF_8));
                    event.getHook().editOriginal("").setFiles(FileUpload.fromData(baos.toByteArray(), "text_flowchart.txt")).queue();
                }catch(Exception e){
                    event.getHook().editOriginal(ERROR_GENERATING_FILE.get(event)).queue();
                    e.printStackTrace();
                }
            }else{
                Global.sendMessage(result.toString(), event, true, true);
            }
        }

        Global.setLastWand(wand);
    }
}