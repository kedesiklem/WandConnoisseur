package org.example.menu;

import org.example.localization.LocalizedText;
import org.example.main.CardHistory;
import org.example.main.Global;
import org.example.spells.Spell;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageEditAction;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

public class MenuDeckAnimation extends Menu{
    private static final LocalizedText ERROR_ID_OOB = Global.getLanguageManager().get("ERROR_ID_OOB");
    private static final LocalizedText ERROR_INVALID_NUMBER = Global.getLanguageManager().get("ERROR_INVALID_NUMBER");
    private static final LocalizedText MESSAGE_GOTO = Global.getLanguageManager().get("MESSAGE_GOTO");
    private static final LocalizedText MESSAGE_ID = Global.getLanguageManager().get("MESSAGE_ID");
    private static final Pattern idPattern = Pattern.compile("^([0-9]+):([0-9]+)$");
    private final CardHistory cardHistory;

    public MenuDeckAnimation(String id, String title, String description, CardHistory cardHistory){
        super(id, title, description);
        this.cardHistory = cardHistory;
    }

    // abstract
    public ActionRow getActionRow(Locale language){
        return ActionRow.of(Button.primary(this.id + ";0", "<<"), Button.primary(this.id + ";1", "<"), Button.secondary(this.id + ";2", MESSAGE_GOTO.get(language)), Button.primary(this.id + ";3", ">"), Button.primary(this.id + ";4", ">>"));
    }

    public void replyHookEvent(SlashCommandInteractionEvent event){
        WebhookMessageEditAction<Message> callback = event.getHook().editOriginal(MessageEditData.fromEmbeds(this.toMessageEmbed()));
        ActionRow actionRow = this.getActionRow(event.getGuildLocale().toLocale());

        if(actionRow != null){
            callback.setComponents(actionRow);
        }

        callback.queue();
    }

    // override
    @Override
    public boolean editEvent(ButtonInteractionEvent event){
        String buttonId = event.getButton().getId();
        if(buttonId == null){
            return false;
        }
        int semicolonIndex = buttonId.lastIndexOf(";");
        buttonId = semicolonIndex != -1 ? buttonId.substring(semicolonIndex + 1) : buttonId;

        switch(buttonId){
            case "0" -> this.cardHistory.previousCardPoolStep();
            case "1" -> this.cardHistory.previousStep();
            case "2" -> {
                TextInput input = TextInput.create("value", MESSAGE_ID.get(event), TextInputStyle.SHORT).setRequired(true).build();
                Modal modal = Modal.create(this.id + ";modal", MESSAGE_GOTO.get(event)).addActionRow(input).build();
                event.replyModal(modal).queue();
                return true;
            }
            case "3" -> this.cardHistory.nextStep();
            case "4" -> this.cardHistory.nextCardPoolStep();
            default -> {return false;}
        }

        event.editMessageEmbeds(this.toMessageEmbed()).queue();
        return true;
    }

    @Override
    public boolean editEvent(ModalInteractionEvent event){
        ModalMapping valueOption = event.getValue("value");
        String input;
        int value;
        int value2;
        Matcher m;

        if(valueOption == null){
            return false;
        }

        input = valueOption.getAsString();
        m = idPattern.matcher(input);
        if(m.find()){
            try{
                value = Integer.parseInt(m.group(1));
                value2 = Integer.parseInt(m.group(2));
            }catch(NumberFormatException e){
                event.reply(ERROR_INVALID_NUMBER.get(event, input)).setEphemeral(true).queue();
                return true;
            }
            value--;
            value2--;

            if(!this.cardHistory.goToStep(value, value2)){
                event.reply(ERROR_ID_OOB.get(event, input)).setEphemeral(true).queue();
                return true;
            }
        }else{
            try{
                value = Integer.parseInt(input);
            }catch(NumberFormatException e){
                event.reply(ERROR_INVALID_NUMBER.get(event, input)).setEphemeral(true).queue();
                return true;
            }
            value--;

            if(!this.cardHistory.goToStep(value)){
                event.reply(ERROR_ID_OOB.get(event, input)).setEphemeral(true).queue();
                return true;
            }
        }

        event.editMessageEmbeds(this.toMessageEmbed()).queue();
        return true;
    }

    @Override
    public MessageEmbed toMessageEmbed(){
        Spell[][] currentStep = this.cardHistory.getCurrentStep();
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder sb = new StringBuilder();

        sb.append("**Call stack**: ");
        for(int i=0; i < currentStep[3].length; i++){
            sb.append(currentStep[3][i].getEmote());
        }

        embed.setTitle((this.cardHistory.getStep() + 1) + "/" + this.cardHistory.getSize() + " [" + (this.cardHistory.getCardOnlyStep() + 1) + "/" + this.cardHistory.getCardPoolSize() + "]:[" + (this.cardHistory.getCurrentCallStackStepAbsolute() + 1) + "/" + Math.max(this.cardHistory.getCurrentCardPoolSize(), 1) + "]");
        embed.setDescription(sb.toString());

        for(int i=0; i < 3; i++){
            sb.setLength(0);
            for(int j=0; j < currentStep[i].length; j++){
                sb.append(currentStep[i][j].getEmote());
                if(j + 1 < currentStep[i].length && sb.length() + currentStep[i][j+1].getEmote().length() > 1024 || j + 2 < currentStep[i].length && sb.length() + currentStep[i][j+1].getEmote().length() + 3 > 1024){
                    sb.append("+").append(Math.min(currentStep[i].length - j - 1, 99));
                    break;
                }
            }
            switch(i){
                case 0 -> embed.addField("Discard", sb.toString(), true);
                case 1 -> embed.addField("Hand", sb.toString(), true);
                case 2 -> embed.addField("Deck", sb.toString(), true);
            }
        }

        return embed.build();
    }
}