package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_METEOR;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class METEOR extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "meteor.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Meteor";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A destructive projectile from the skies!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_METEOR();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.6, 0.6, 0.7, 0, 0, 0, 0.5);
        this.price = 280;
        this.manaCost = 150;
        this.hasCharges = true;
        this.maxCharges = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "METEOR",
	name 		= "$action_meteor",
	description = "$actiondesc_meteor",
	sprite 		= "data/ui_gfx/gun_actions/meteor.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/meteor_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/meteor.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "4,5,6,10", -- METEOR
	spawn_probability                 = "0.6,0.6,0.7,0.5", -- METEOR
	price = 280,
	mana = 150,
	max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/meteor.xml")
	end,
}*/