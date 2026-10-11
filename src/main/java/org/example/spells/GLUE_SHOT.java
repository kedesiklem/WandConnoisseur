package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_GLUE_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GLUE_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "glue_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Glue Ball";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "glue", "coom"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile that explodes into a sticky mess";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GLUE_SHOT();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.7, 0.4, 0.2, 0.5, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 25;
        this.castDelay = 30;
        this.spread = 5.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "GLUE_SHOT",
	name 		= "$action_glue_shot",
	description = "$actiondesc_glue_shot",
	sprite 		= "data/ui_gfx/gun_actions/glue_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/glue_shot.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5", -- GLUE_SHOT
	spawn_probability                 = "0.7,0.4,0.2,0.5", -- GLUE_SHOT
	price = 140,
	mana = 25,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/glue_shot.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
		c.spread_degrees = c.spread_degrees + 5.0
	end,
}*/