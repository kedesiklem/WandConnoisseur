package org.example.listeners;

import org.example.localization.LocalizedText;
import org.example.main.Global;
import org.example.menu.Menu;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuListener extends ListenerAdapter{
    private static final LocalizedText ERROR_MENU_OUTDATED = Global.getLanguageManager().get("ERROR_MENU_OUTDATED");
    private static final LocalizedText ERROR_INTERACTION = Global.getLanguageManager().get("ERROR_INTERACTION");
    private static final Logger logger = LoggerFactory.getLogger(MenuListener.class);

    @Override
    public void onReady(@NotNull ReadyEvent event){
        logger.info("MenuListener is ready!");
    }

    public void replyStringSelectInteraction(StringSelectInteractionEvent event){
        Menu menu = Global.menuManager.getById(Menu.getRootId(event.getValues().get(0)));

        if(menu == null){
            event.reply(ERROR_MENU_OUTDATED.get(event)).setEphemeral(true).queue();
            return;
        }

        if(!menu.editEvent(event)){
            event.reply(ERROR_INTERACTION.get(event)).setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event){
        long startTime = System.nanoTime();

        logger.debug("User \033[0;31m" + event.getUser().getName() + "\u001b[0;0m executing StringSelect event \"" + event.getValues().get(0) + "\"");
        replyStringSelectInteraction(event);
        logger.debug("Executed StringSelect event in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getValues().get(0) + "\"");
    }

    public void replyButtonInteraction(ButtonInteractionEvent event){
        Menu menu = Global.menuManager.getById(Menu.getRootId(event.getComponentId()));

        if(menu == null){
            event.reply(ERROR_MENU_OUTDATED.get(event)).setEphemeral(true).queue();
            return;
        }

        if(!menu.editEvent(event)){
            event.reply(ERROR_INTERACTION.get(event)).setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        long startTime = System.nanoTime();

        logger.debug("User \033[0;31m" + event.getUser().getName() + "\u001b[0;0m executing ButtonInteraction event \"" + event.getComponentId() + "\"");
        replyButtonInteraction(event);
        logger.debug("Executed ButtonInteraction event in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getComponentId() + "\"");
    }

    private void replyModalInteraction(ModalInteractionEvent event){
        Menu menu = Global.menuManager.getById(Menu.getRootId(event.getModalId()));

        if(menu == null){
            event.reply(ERROR_MENU_OUTDATED.get(event)).setEphemeral(true).queue();
            return;
        }

        if(!menu.editEvent(event)){
            event.reply(ERROR_INTERACTION.get(event)).setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event){
        long startTime = System.nanoTime();

        logger.debug("User \033[0;31m" + event.getUser().getName() + "\u001b[0;0m executing ModalInteraction event \"" + event.getModalId() + "\"");
        replyModalInteraction(event);
        logger.debug("Executed ModalInteraction event in " + (System.nanoTime() - startTime)/1000000.0 + "ms \"" + event.getModalId() + "\"");
    }
}