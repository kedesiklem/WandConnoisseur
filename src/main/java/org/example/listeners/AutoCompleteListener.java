package org.example.listeners;

import org.example.commands.CommandLocal;
import org.example.main.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoCompleteListener extends ListenerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteListener.class);
    private static final Pattern colorPattern = Pattern.compile("^ *([0-9a-f]{1,2})?([0-9a-f]{1,2})?([0-9a-f]{1,2})?([0-9a-f]{1,2})? *$");
    private static final Pattern sortPattern = Pattern.compile("^ *(?i)([a-z0-9_]+) *(|ASC|DESC) *$");

    @Override
    public void onReady(@NotNull ReadyEvent event){
        logger.info("AutoCompleteListener is ready!");
    }

    public static String[] getSpellAutocomplete(String[] spellAlias, String input, int maxOutput){
        if(spellAlias == null || input == null || maxOutput <= 0){
            return new String[0];
        }

        int start = Arrays.binarySearch(spellAlias, input);
        if(start < 0){
            start = -(start + 1);
        }

        if(start >= spellAlias.length || !spellAlias[start].startsWith(input)){
            return new String[0];
        }

        int end = start;
        while(end < spellAlias.length && spellAlias[end].startsWith(input) && end - start < maxOutput){
            end++;
        }

        return Arrays.copyOfRange(spellAlias, start, end);
    }

    private List<String> getAutoCompleteValues(@NotNull CommandAutoCompleteInteractionEvent event){
        List<String> values = new LinkedList<>();
        String currentInput = event.getFocusedOption().getValue().toLowerCase();
        String preInput = "";
        String postInput = "";
        String[] validOptions;
        Matcher m;
        int maxOutput = 25;

        if(event.getFocusedOption().getType() == OptionType.BOOLEAN){
            values.add("true");
            values.add("false");
            return values;
        }

        if(currentInput.strip().equalsIgnoreCase("quoi")){
            values.add("Feur");
            return values;
        }

        if(Arrays.asList(new String[]{"spells", "property", "sort"}).contains(event.getFocusedOption().getName())){
            preInput = currentInput.substring(0, currentInput.lastIndexOf(',') + 1) + " ";
            currentInput = currentInput.substring(currentInput.lastIndexOf(',') + 1).strip();
        }

        switch(event.getFocusedOption().getName()){
            case "always_cast" -> {
                m = Global.getAlwaysCastPattern().matcher(currentInput);
                if(m.find()){
                    currentInput = m.group(1);
                    if(m.group(2) != null){
                        postInput += ":" + m.group(2);
                    }
                }
                if(currentInput.equals("")){
                    validOptions = Arrays.copyOf(Global.getAliasList(), Math.min(maxOutput, Global.getAliasList().length));
                }else{
                    validOptions = getSpellAutocomplete(Global.getAliasList(), currentInput, maxOutput);
                }

                for(String validOption : validOptions){
                    values.add(preInput + validOption + postInput);
                }
            }
            case "cast_delay", "recharge_time" -> {
                int intDelay = 0;

                if(!currentInput.equals("")){
                    try{
                        intDelay = Global.stringToDelay(event.getGuildLocale().toLocale(), currentInput);
                    }catch(Exception e){
                        break;
                    }
                }

                values.add(String.format("%1$d f", intDelay).replace(',', '.'));
                values.add(String.format("%1$3.2f s", intDelay/60.0).replace(',', '.'));
            }
            case "color" -> {
                values.add("gradient");
                m = colorPattern.matcher(currentInput);
                if(!currentInput.equals("") && m.find()){
                    StringBuilder color = new StringBuilder();
                    for(int i=1; i < 4; i++){
                        if(m.group(i) != null){
                            color.append(m.group(i).length() == 2 ? m.group(i).toUpperCase() : m.group(i).toUpperCase() + "F");
                        }else{
                            color.append("FF");
                        }
                    }
                    if(m.group(4) != null){
                        color.append(m.group(4).length() == 2 ? m.group(4).toUpperCase() : m.group(4).toUpperCase() + "F");
                        values.add(color.toString());
                    }else{
                        values.add(color.toString());
                        values.add(color + "FF");
                    }
                }else{
                    values.add("FFFFFF");
                    values.add("FFFFFFFF");
                }
            }
            case "command" -> {
                currentInput = currentInput.strip();
                Locale userLanguage = event.getUserLocale().toLocale();
                Locale guildLanguage = event.getGuildLocale().toLocale();
                CommandLocal[] commandLocalList = currentInput.equals("") ? Global.getCommandLocalList() : Global.getCommandLocalList(currentInput, userLanguage);

                if(userLanguage != guildLanguage && commandLocalList.length == 0){
                    commandLocalList = currentInput.equals("") ? Global.getCommandLocalList() : Global.getCommandLocalList(currentInput, guildLanguage);
                }

                for(CommandLocal commandLocal : commandLocalList){
                    values.add(commandLocal.getName().get(event.getUserLocale().toLocale()));
                }

                return values;
            }
            case "condition" -> {
                int charId = Math.max(Math.max(currentInput.lastIndexOf(' '), currentInput.lastIndexOf('(')), currentInput.lastIndexOf(')'));
                preInput = currentInput.substring(0, charId + 1);
                currentInput = currentInput.substring(charId + 1).strip();

                if(currentInput.equals("")){
                    validOptions = Arrays.copyOf(Global.getSpellProperties(), Math.min(maxOutput, Global.getSpellProperties().length));
                }else{
                    validOptions = getSpellAutocomplete(Global.getSpellProperties(), currentInput, maxOutput);
                }

                for(String validOption : validOptions){
                    values.add(preInput + validOption);
                }
            }
            case "name" -> {
                currentInput = currentInput.strip();
                if(currentInput.equals("")){
                    values.addAll(Arrays.asList(Arrays.copyOf(Global.getAliasList(), Math.min(maxOutput, Global.getAliasList().length))));
                }else{
                    values.addAll(Arrays.asList(getSpellAutocomplete(Global.getAliasList(), currentInput, maxOutput)));
                }
            }
            case "property" -> {
                if(currentInput.equals("")){
                    validOptions = Arrays.copyOf(Global.getSpellStringProperties(), Math.min(maxOutput, Global.getSpellStringProperties().length));
                }else{
                    validOptions = getSpellAutocomplete(Global.getSpellStringProperties(), currentInput, maxOutput);
                }

                for(String validOption : validOptions){
                    values.add(preInput + validOption);
                }
            }
            case "sort" -> {
                if(currentInput.equals("")){
                    validOptions = Arrays.copyOf(Global.getSpellProperties(), Math.min(maxOutput, Global.getSpellProperties().length));
                }else{
                    m = sortPattern.matcher(currentInput);
                    if(m.find()){
                        currentInput = m.group(1);
                        postInput += m.group(2).equalsIgnoreCase("DESC") ? " DESC" : "";
                    }
                    if(currentInput.equals("")){
                        validOptions = Arrays.copyOf(Global.getSpellProperties(), Math.min(maxOutput, Global.getSpellProperties().length));
                    }else{
                        validOptions = getSpellAutocomplete(Global.getSpellProperties(), currentInput, maxOutput);
                    }
                }

                for(String validOption : validOptions){
                    values.add(preInput + validOption + postInput);
                }
            }
            case "spells", "spell" -> {
                if(event.getName().equals("wisp")){
                    currentInput = currentInput.strip();
                    if(currentInput.equals("")){
                        values.addAll(Arrays.asList(Arrays.copyOf(Global.getAliasListProjectileComponent(), Math.min(maxOutput, Global.getAliasListProjectileComponent().length))));
                    }else{
                        values.addAll(Arrays.asList(getSpellAutocomplete(Global.getAliasListProjectileComponent(), currentInput, maxOutput)));
                    }
                }else{
                    if(currentInput.equals("")){
                        validOptions = Arrays.copyOf(Global.getAliasList(), Math.min(maxOutput, Global.getAliasList().length));
                    }else{
                        m = Global.getSpellPattern().matcher(currentInput);
                        if(m.find()){
                            currentInput = m.group(2);
                            if(m.group(1) != null){
                                preInput += m.group(1) + ":";
                            }
                            if(m.group(3) != null){
                                postInput += ":" + m.group(3);
                            }
                        }
                        if(currentInput.equals("")){
                            validOptions = Arrays.copyOf(Global.getAliasList(), Math.min(maxOutput, Global.getAliasList().length));
                        }else{
                            validOptions = getSpellAutocomplete(Global.getAliasList(), currentInput, maxOutput);
                        }
                    }

                    for(String validOption : validOptions){
                        values.add(preInput + validOption + postInput);
                    }
                }
            }
        }

        return values;
    }

    private void replyAutoComplete(@NotNull CommandAutoCompleteInteractionEvent event){
        List<String> values = getAutoCompleteValues(event);
        List<Command.Choice> options = new ArrayList<>();

        for(String value : values){
            if(value.length() >= 100){
                value = value.substring(0, 100);
            }
            options.add(new net.dv8tion.jda.api.interactions.commands.Command.Choice(value, value));
        }
        event.replyChoices(options).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event){
        long startTime = System.nanoTime();

        try{
            logger.debug("User \033[0;34m" + event.getUser().getName() + "\u001b[0;0m executing AutoComplete event " + event.getName() + ":" + event.getFocusedOption().getName() + " \"" + event.getFocusedOption().getValue() + "\"");
            replyAutoComplete(event);
            logger.debug("Executed AutoComplete event in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getFocusedOption().getValue() + "\"");
        }catch(Exception e){
            logger.warn("Failed AutoComplete event in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getFocusedOption().getValue() + "\"");
            e.printStackTrace();
        }
    }
}