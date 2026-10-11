package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_CRITICAL_FEUR;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_CRITICAL_FEUR extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "critical_feur.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.modded = true;
        this.name = "Critical on feur enemies";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "crit on quoi", "crit on feur"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile always do a critical hit on feur enemies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_CRITICAL_FEUR()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0, 0.2, 0.4, 0.2, 0, 0, 0, 0, 0);
        this.price = 70;
        this.manaCost = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}