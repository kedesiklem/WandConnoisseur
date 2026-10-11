package org.example;

import org.example.config.BotConfig;
import org.example.config.EmoteConfig;
import org.example.GUI.MainWindow;
import org.example.listeners.AutoCompleteListener;
import org.example.listeners.CommandListener;
import org.example.listeners.MenuListener;
import org.example.main.CardPool;
import org.example.main.CastState;
import org.example.main.Global;
import org.example.main.WandList;
import org.example.projectiles.Projectile;
import org.example.script.Script;
import org.example.spells.Spell;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WandConnoisseur{
    private static final Logger logger = LoggerFactory.getLogger(WandConnoisseur.class);
    public static JDA jda;

    private static void generateEmotes(boolean newOnly){
        Spell[] spells = Global.getSpellList().getSpells(false);
        Projectile[] projectiles = Global.getProjectileList().getProjectiles(false);
        Script[] scripts = Global.getScriptList().getScripts(false);
        Set<String> emojis = null;

        if(newOnly){
            emojis = jda.retrieveApplicationEmojis().complete().stream().map(ApplicationEmoji::getName).collect(Collectors.toSet());
        }

        for(Spell spell : spells){
            if(!newOnly || !emojis.contains(Global.truncate(spell.getClass().getSimpleName().toLowerCase(), 32))){
                spell.createEmote();
            }
        }
        for(Projectile projectile : projectiles){
            if(!newOnly || !emojis.contains(Global.truncate(projectile.getClass().getSimpleName().toLowerCase(), 32))){
                projectile.createEmote();
            }
        }
        for(Script script : scripts){
            if(!newOnly || !emojis.contains(Global.truncate(script.getClass().getSimpleName().toLowerCase(), 32))){
                script.createEmote();
            }
        }

        // default emotes
        if(!newOnly || !emojis.contains("spell")){
            new Spell(){
                @Override protected void initialization(){}
                @Override protected void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){}
            }.createEmote();
        }
        if(!newOnly || !emojis.contains("projectile")){
            new Projectile(){}.createEmote();
        }
        if(!newOnly || !emojis.contains("script")){
            new Script(){
                @Override protected void initialization(){}
            }.createEmote();
        }
    }

    // TODO
    // better autocomplete for conditions
    // mob quizz (audio ?)
    // code every spell
    // circular array for cardPool
    // put on the raspberry
    public static void main(String[] args){
        boolean generateEmotes = false;
        boolean generateNewEmotes = false;
        boolean generateWandstat = false;
        boolean generateWandstatSVG = false;
        boolean localMod = false;

        for(String arg : args){
            switch(arg){
                case "-emote" -> generateEmotes = true;
                case "-emote_new" -> generateNewEmotes = true;
                case "-local" -> localMod = true;
                case "-modded" -> Global.modded = true;
                case "-wandstat" -> generateWandstat = true;
                case "-wandstat_svg" -> generateWandstatSVG = true;
                default -> {
                    System.err.println("Unknown option: " + arg);
                    System.exit(1);
                }
            }
        }

        Global.autoDeleteFiles();

        if(generateEmotes){
            generateEmotes(false);
        }
        if(generateWandstat){
            new WandList().generateAllSprites(false);
        }
        if(generateWandstatSVG){
            new WandList().generateAllSprites(true);
        }

        if(localMod){
            MainWindow.getInstance().run();
        }else{
            String botToken = BotConfig.getBotToken();
            if(botToken == null || botToken.isEmpty()){
                logger.error("Bot token not found in config.properties. Please provide a valid token.");
                return;
            }

            try{
                // Build JDA instance
                jda = JDABuilder.createDefault(botToken)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .addEventListeners(new AutoCompleteListener())
                    .addEventListeners(new CommandListener())
                    .addEventListeners(new MenuListener())
                    .build();

                jda.awaitReady();

                CommandListener.registerSlashCommands();
                if(generateNewEmotes){
                    generateEmotes(true);
                }
                EmoteConfig.initConfig(true);
                logger.info("Bot is online and ready!");
            }catch(Exception e){
                logger.error("Error starting the bot: ", e);
            }
        }
    }
}