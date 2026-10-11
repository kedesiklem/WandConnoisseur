package org.example.commands;

import org.example.localization.LocalizedException;
import org.example.localization.LocalizedText;
import org.example.main.Global;
import org.example.main.SpellFilter;
import org.example.spells.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class SpellListCommand extends CommandLocal{
    private static final LocalizedText COMMAND_SPELL_LIST = Global.getLanguageManager().get("COMMAND_SPELL_LIST");
    private static final LocalizedText COMMAND_SPELL_LIST_DESCRIPTION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_DESCRIPTION");
    private static final LocalizedText COMMAND_SPELL_LIST_PROPERTY = Global.getLanguageManager().get("COMMAND_SPELL_LIST_PROPERTY");
    private static final LocalizedText COMMAND_SPELL_LIST_PROPERTY_DESCRIPTION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_PROPERTY_DESCRIPTION");
    private static final LocalizedText COMMAND_SPELL_LIST_CONDITION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_CONDITION");
    private static final LocalizedText COMMAND_SPELL_LIST_CONDITION_DESCRIPTION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_CONDITION_DESCRIPTION");
    private static final LocalizedText COMMAND_SPELL_LIST_SORT = Global.getLanguageManager().get("COMMAND_SPELL_LIST_SORT");
    private static final LocalizedText COMMAND_SPELL_LIST_SORT_DESCRIPTION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_SORT_DESCRIPTION");
    private static final LocalizedText COMMAND_SPELL_LIST_TYPE = Global.getLanguageManager().get("COMMAND_SPELL_LIST_TYPE");
    private static final LocalizedText COMMAND_SPELL_LIST_TYPE_DESCRIPTION = Global.getLanguageManager().get("COMMAND_SPELL_LIST_TYPE_DESCRIPTION");
    private static final LocalizedText ERROR_CONDITION = Global.getLanguageManager().get("ERROR_CONDITION");
    private static final LocalizedText ERROR_FILTER_EMPTY = Global.getLanguageManager().get("ERROR_FILTER_EMPTY");
    private static final LocalizedText ERROR_GENERATING_FILE = Global.getLanguageManager().get("ERROR_GENERATING_FILE");
    private static final LocalizedText ERROR_INVALID_TYPE = Global.getLanguageManager().get("ERROR_INVALID_TYPE");
    private static final LocalizedText MESSAGE_ALL_SPELLS = Global.getLanguageManager().get("MESSAGE_ALL_SPELLS");
    private static final LocalizedText MESSAGE_SORTED = Global.getLanguageManager().get("MESSAGE_SORTED");
    private static final LocalizedText MESSAGE_SPELLS_FILTERED = Global.getLanguageManager().get("MESSAGE_SPELLS_FILTERED");

    public SpellListCommand(){
        this.name = COMMAND_SPELL_LIST;
        this.description = COMMAND_SPELL_LIST_DESCRIPTION;
        this.commandOptions = new CommandOption[]{
            new CommandOption(OptionType.STRING, COMMAND_SPELL_LIST_PROPERTY, COMMAND_SPELL_LIST_PROPERTY_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_SPELL_LIST_CONDITION, COMMAND_SPELL_LIST_CONDITION_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_SPELL_LIST_SORT, COMMAND_SPELL_LIST_SORT_DESCRIPTION, false, true),
            new CommandOption(OptionType.STRING, COMMAND_SPELL_LIST_TYPE, COMMAND_SPELL_LIST_TYPE_DESCRIPTION, false, false, new String[]{"message", "csv"})
        };
    }

    public void executeSlash(SlashCommandInteractionEvent event){
        OptionMapping propertyOption = event.getOption("property");
        OptionMapping filterOption = event.getOption("condition");
        OptionMapping sortOption = event.getOption("sort");
        OptionMapping typeOption = event.getOption("type");
        String propertyFilter = "";
        String filter = "";
        String sort = "";
        int type = 0;
        String[][] properties = new String[0][0];
        Spell[] spellList = Global.getSpellList().getSpells(false);
        SpellFilter spellFilter = Global.getSpellFilter();
        StringBuilder result = new StringBuilder();

        if(typeOption != null){
            switch(typeOption.getAsString()){
                case "message" -> {}
                case "csv" -> type = 1;
                default -> {
                    event.reply(ERROR_INVALID_TYPE.get(event, typeOption.getAsString())).setEphemeral(true).queue();
                    return;
                }
            }
        }
        if(propertyOption != null){
            propertyFilter = propertyOption.getAsString().trim();
        }
        if(filterOption != null){
            filter = filterOption.getAsString().trim();
        }
        if(sortOption != null){
            sort = sortOption.getAsString().trim();
        }

        if(filterOption != null){
            try{
                spellList = spellFilter.filter(spellList, filter);
            }catch(LocalizedException e){
                event.reply(ERROR_CONDITION.get(event) + e.toString(event.getGuildLocale().toLocale())).setEphemeral(true).queue();
                return;
            }
            if(spellList.length == 0){
                event.reply(ERROR_FILTER_EMPTY.get(event, filter)).setEphemeral(true).queue();
                return;
            }
            result.append(MESSAGE_SPELLS_FILTERED.get(event, filter));
        }else{
            result.append(MESSAGE_ALL_SPELLS.get(event));
        }

        if(sortOption != null){
            try{
                spellList = spellFilter.sort(spellList, sort);
            }catch(LocalizedException e){
                event.reply(e.toString(event.getGuildLocale().toLocale())).setEphemeral(true).queue();
                return;
            }
            result.append(MESSAGE_SORTED.get(event, sort));
        }
        if(propertyOption != null){
            try{
                properties = spellFilter.getStringProperties(spellList, propertyFilter);
            }catch(LocalizedException e){
                event.reply(e.toString(event.getGuildLocale().toLocale())).setEphemeral(true).queue();
                return;
            }
        }
        result.append(type == 0 ? " :\n\n" : "");

        event.deferReply(false).queue();
        event.getHook().editOriginal(result.toString()).queue();

        switch(type){
            case 0 -> {
                for(int i=0; i < spellList.length; i++){
                    if(i != 0){
                        result.append("\n");
                    }
                    result.append(spellList[i].getEmote()).append(" ").append(spellList[i].getName());
                    if(propertyOption != null){
                        for(String[] property : properties){
                            result.append("\n\u200E        \u2022 **").append(property[0]).append("**: ").append(property[i + 1]);
                        }
                    }
                }
                Global.sendMessage(result.toString(), event, true, false);
            }
            case 1 -> {
                try{
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);

                    writer.write(filter.isEmpty() ? "" : "condition:;" + filter);
                    writer.write(sort.isEmpty() ? "" : "tri:;" + sort);
                    writer.write(filter.isEmpty() && sort.isEmpty() ? "" : "\n\n");

                    writer.write("spell;");
                    if(propertyOption != null){
                        for(String[] property : properties){
                            writer.write(property[0] + ";");
                        }
                    }
                    writer.write("\n");

                    for(int i=0; i < spellList.length; i++){
                        writer.write(spellList[i].getClass().getSimpleName() + ";");
                        if(propertyOption != null){
                            for(String[] property : properties){
                                writer.write(property[i + 1] + ";");
                            }
                        }
                        writer.write("\n");
                    }

                    writer.close();
                    event.getHook().editOriginal(result.toString()).setFiles(Global.byteToUpload(out.toByteArray(), "spell_list.csv")).queue();
                }catch(Exception e){
                    System.out.println("error writing the file \"" + "spell_list_.csv" + "\" " + e.getMessage());
                    event.getHook().editOriginal(result + "\n\n" + ERROR_GENERATING_FILE.get(event)).queue();
                }
            }
        }
    }
}