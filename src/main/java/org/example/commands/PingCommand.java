package org.example.commands;

import org.example.localization.LocalizedText;
import org.example.main.Global;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends CommandLocal{
    private static final LocalizedText COMMAND_PING = Global.getLanguageManager().get("COMMAND_PING");
    private static final LocalizedText COMMAND_PING_DESCRIPTION = Global.getLanguageManager().get("COMMAND_PING_DESCRIPTION");
    private static final LocalizedText MESSAGE_PING = Global.getLanguageManager().get("MESSAGE_PING");

    public PingCommand(){
        this.name = COMMAND_PING;
        this.description = COMMAND_PING_DESCRIPTION;
        this.commandOptions = new CommandOption[0];
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event){
        long ping = event.getJDA().getGatewayPing();
        event.replyFormat(MESSAGE_PING.get(event, String.valueOf(ping))).queue();
    }

}