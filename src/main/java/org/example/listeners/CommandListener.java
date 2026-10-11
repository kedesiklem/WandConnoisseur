package org.example.listeners;

import org.example.commands.*;
import org.example.localization.LanguageManager;
import static org.example.localization.LanguageManager.Language;
import org.example.localization.LocalizedText;
import org.example.main.Global;
import org.example.WandConnoisseur;

import java.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandListener extends ListenerAdapter{
    private static final LocalizedText ERROR_UNKNOWN_COMMAND = Global.getLanguageManager().get("ERROR_UNKNOWN_COMMAND");
    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);
    private static final Map<String, CommandLocal> commands = createCommandMap();

    public CommandListener(){
        // nothing
    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        SelfUser user = event.getJDA().getSelfUser();

        logger.info("JDA is ready! Logged in as " + user.getName() + "#" + user.getDiscriminator());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String commandName = event.getName();
        CommandLocal commandLocal = commands.get(commandName);

        if(commandLocal != null){
            long startTime = System.nanoTime();
            logger.debug("User \033[0;31m" + event.getUser().getName() + "\u001b[0;0m executing slash command \"" + event.getInteraction().getCommandString() + "\"");
            commandLocal.executeSlash(event);
            logger.debug("Executed slash command in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getInteraction().getCommandString() + "\"");
        }else{
            logger.warn("Unknown slash command: " + commandName + " from user: \033[0;31m" + event.getUser().getName() + "\u001b[0;0m");
            event.reply(ERROR_UNKNOWN_COMMAND.get(event, commandName)).setEphemeral(true).queue();
        }
    }

    private static Map<String, CommandLocal> createCommandMap(){
        Map<String, CommandLocal> map = new HashMap<>();

        for(Class<? extends CommandLocal> clazz : new Reflections("org.example.commands").getSubTypesOf(CommandLocal.class)){
            try{
                CommandLocal commandLocal = clazz.getDeclaredConstructor().newInstance();
                map.put(commandLocal.getName().get(Language.en), commandLocal);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        logger.info("Registered " + map.size() + " commands.");

        return map;
    }

    public static CommandLocal[] createCommandArray(){
        List<CommandLocal> list = new ArrayList<>();

        for(Class<? extends CommandLocal> clazz : new Reflections("org.example.commands").getSubTypesOf(CommandLocal.class)){
            try{
                CommandLocal commandLocal = clazz.getDeclaredConstructor().newInstance();
                list.add(commandLocal);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        CommandLocal[] result = list.toArray(new CommandLocal[0]);
        Arrays.sort(result, Comparator.comparing(obj -> obj.getName().get(Language.en)));

        return result;
    }

    public static void registerSlashCommands(){
        JDA jda = WandConnoisseur.jda;

        if(jda == null){
            logger.error("JDA instance is not initialized. Cannot register slash commands.");
            return;
        }

        CommandListUpdateAction commandsList = jda.updateCommands();
        SlashCommandData currentCommand;
        OptionData currentOption;

        logger.info("Registering Slash Commands...");

        for(CommandLocal commandLocal : commands.values()){
            currentCommand = Commands.slash(commandLocal.getName().get(Language.en), commandLocal.getDescription().get(Language.en));
            for(Language language : Language.values()){
                currentCommand.setNameLocalization(LanguageManager.languageToDiscordLocale(language), commandLocal.getName().get(language));
                currentCommand.setDescriptionLocalization(LanguageManager.languageToDiscordLocale(language), commandLocal.getDescription().get(language));
            }
            for(CommandOption option : commandLocal.getCommandOptions()){
                currentOption = new OptionData(option.optionType(), option.name().get(Language.en), option.description().get(Language.en), option.required(), option.autoComplete() && (option.staticChoices() == null || option.staticChoices().length == 0));
                for(Language language : Language.values()){
                    currentOption.setNameLocalization(LanguageManager.languageToDiscordLocale(language), option.name().get(language));
                    currentOption.setDescriptionLocalization(LanguageManager.languageToDiscordLocale(language), option.description().get(language));
                }

                if(option.staticChoices() != null){
                    for(String choice : option.staticChoices()){
                        currentOption.addChoice(choice, choice);
                    }
                }

                currentCommand.addOptions(currentOption);
            }
            commandsList = commandsList.addCommands(currentCommand);
        }
        commandsList.queue(success -> logger.info("Slash commands registered successfully!"), failure -> logger.error("Failed to register slash commands: ", failure));
    }
}