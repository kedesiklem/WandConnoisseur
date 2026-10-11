package org.example.commands;

import static org.example.localization.LanguageManager.Language;
import org.example.localization.LocalizedText;

import java.util.Locale;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public record CommandOption(OptionType optionType, LocalizedText name, LocalizedText description, boolean required, boolean autoComplete, String[] staticChoices){
    public CommandOption(OptionType optionType, LocalizedText name, LocalizedText description, boolean required, boolean autoComplete){
        this(optionType, name, description, required, autoComplete, null);
    }

    public String toString(){
        return "[" + optionType.toString() + ", \"" + this.name + "\", \"" + this.description + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Language language){
        return "[" + optionType.toString() + ", \"" + this.name.get(language) + "\", \"" + this.description.get(language) + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Locale language){
        return "[" + optionType.toString() + ", \"" + this.name.get(language) + "\", \"" + this.description.get(language) + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Language language, String[] args){
        return "[" + optionType.toString() + ", \"" + this.name.get(language, args) + "\", \"" + this.description.get(language, args) + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Locale language, String[] args){
        return "[" + optionType.toString() + ", \"" + this.name.get(language, args) + "\", \"" + this.description.get(language, args) + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Language language, String arg){
        return "[" + optionType.toString() + ", \"" + this.name.get(language, arg) + "\", \"" + this.description.get(language, arg) + "\", " + this.required + ", " + this.autoComplete + "]";
    }

    public String toString(Locale language, String arg){
        return "[" + optionType.toString() + ", \"" + this.name.get(language, arg) + "\", \"" + this.description.get(language, arg) + "\", " + this.required + ", " + this.autoComplete + "]";
    }
}