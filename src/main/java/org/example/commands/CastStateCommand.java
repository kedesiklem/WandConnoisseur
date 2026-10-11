package org.example.commands;

import org.example.localization.LocalizedText;
import org.example.main.CastState;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.menu.MenuTree;
import org.example.spells.Spell;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.utils.FileUpload;

public class CastStateCommand extends CommandLocal{
    private static final LocalizedText COMMAND_CAST_STATE = Global.getLanguageManager().get("COMMAND_CAST_STATE");
    private static final LocalizedText COMMAND_CAST_STATE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_CAST_STATE_DESCRIPTION");
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
    private static final LocalizedText COMMAND_CAST_STATE_TYPE = Global.getLanguageManager().get("COMMAND_CAST_STATE_TYPE");
    private static final LocalizedText COMMAND_CAST_STATE_TYPE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_CAST_STATE_TYPE_DESCRIPTION");
    private static final LocalizedText ERROR_GENERATING_IMAGE = Global.getLanguageManager().get("ERROR_GENERATING_IMAGE");
    private static final LocalizedText ERROR_INVALID_TYPE = Global.getLanguageManager().get("ERROR_INVALID_TYPE");

    public CastStateCommand(){
        this.name = COMMAND_CAST_STATE;
        this.description = COMMAND_CAST_STATE_DESCRIPTION;
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
            new CommandOption(OptionType.STRING, COMMAND_CAST_STATE_TYPE, COMMAND_CAST_STATE_TYPE_DESCRIPTION, false, false, new String[]{"png", "svg", "svg_light", "menu"}),
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
                case "menu" -> type = 3;
                default -> {
                    event.reply(ERROR_INVALID_TYPE.get(event, typeOption.getAsString())).setEphemeral(true).queue();
                    return;
                }
            }
        }

        String fileName = event.getId() + ".png";
        String[] statOptions = new String[]{"always_cast", "cast_delay", "recharge_time", "mana_max", "mana_regen", "spread", "speed", "file"};
        Wand wand = Global.slashInteractionToWand(event);
        boolean eventReplied = false;
        boolean statChanged = false;

        if(wand == null){
            return;
        }

        event.deferReply(false).queue();

        for(String statOption : statOptions){
            if(event.getOption(statOption) != null){
                statChanged = true;
                break;
            }
        }

        if(type == 3){
            CastState[] castStates = wand.getCastState(true);
            Spell[] spells = wand.getSpells(false);
            StringBuilder spellsEmote = new StringBuilder();

            for(Spell spell : spells){
                spellsEmote.append(spell.getEmote());
                wand.putSpellEnd(spell);
            }

            MenuTree menu = new MenuTree(event.getId(), "Cast state", "", Global.bufferedImageToFile(statChanged ? wand.getStatImage().toPNG() : wand.getSpellsImage().toPNG(), Global.getPathAutoDelete() + "wand_" + fileName), spellsEmote.toString());
            for(int i=0; i < castStates.length; i++){
                menu.addChild(castStates[i].toMenuTree("",(i + 1) + ") Initial cast state"));
            }

            Global.menuManager.add(menu);
            menu.replyHookEvent(event);
        }else{
            FileUpload spellsImage = statChanged ? Global.bufferedImageToUpload(wand.getStatImage().toPNG(), "wandstats.png") : Global.bufferedImageToUpload(wand.getSpellsImage().toPNG(), "wand.png");
            FileUpload castStateImage = switch(type){
                case 0 -> Global.bufferedImageToUpload(wand.getCastStateImage(true), "caststate.png");
                case 1 -> Global.byteToUpload(wand.getCastStateImageSVG(true, true), "caststate.svg");
                case 2 -> Global.byteToUpload(wand.getCastStateImageSVG(true, false), "caststate.svg");
                default -> null;
            };

            if(spellsImage != null){
                event.getHook().editOriginal("").setFiles(spellsImage).queue();
                eventReplied = true;
            }
            if(castStateImage != null){
                if(eventReplied){
                    event.getChannel().sendFiles(castStateImage).queue();
                }else{
                    event.getHook().editOriginal("").setFiles(castStateImage).queue();
                    eventReplied = true;
                }
            }

            if(!eventReplied){
                event.getHook().editOriginal(ERROR_GENERATING_IMAGE.get(event)).queue();
            }
        }

        Global.setLastWand(wand);
    }
}