package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MATERIAL_FEUR;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_APPLY_FEURED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MATERIAL_FEUR extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "material_feur.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.modded = true;
        this.name = "Feur";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmute drops of feur from nothing";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MATERIAL_FEUR();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_APPLY_FEURED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 0;
        this.castDelay = -15;
        this.rechargeTime = -10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}