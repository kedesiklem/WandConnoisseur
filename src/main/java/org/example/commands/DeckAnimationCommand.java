package org.example.commands;

import org.example.localization.LocalizedText;
import org.example.main.CardHistory;
import org.example.main.Global;
import org.example.main.Wand;
import org.example.menu.MenuDeckAnimation;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;


public class DeckAnimationCommand extends CommandLocal{
    private static final LocalizedText COMMAND_DECK_ANIMATION = Global.getLanguageManager().get("COMMAND_DECK_ANIMATION");
    private static final LocalizedText COMMAND_DECK_ANIMATION_DESCRIPTION = Global.getLanguageManager().get("COMMAND_DECK_ANIMATION_DESCRIPTION");
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
    public DeckAnimationCommand(){
        this.name = COMMAND_DECK_ANIMATION;
        this.description = COMMAND_DECK_ANIMATION_DESCRIPTION;
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
            new CommandOption(OptionType.ATTACHMENT, COMMAND_GENERAL_FILE, COMMAND_GENERAL_FILE_DESCRIPTION, false, false)
        };
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event){
        Wand wand = Global.slashInteractionToWand(event);
        CardHistory cardHistory;

        if(wand == null){
            return;
        }

        cardHistory = wand.getCardHistory(true);
        event.deferReply(false).queue();
        MenuDeckAnimation menu = new MenuDeckAnimation(event.getId(), "titre", "description", cardHistory);

        Global.menuManager.add(menu);
        menu.replyHookEvent(event);
        Global.setLastWand(wand);
    }
}