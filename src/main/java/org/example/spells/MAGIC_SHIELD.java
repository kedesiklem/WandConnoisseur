package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MAGIC_SHIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MAGIC_SHIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "magic_shield.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Guard";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Four guarding lights rotate around you for a time";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MAGIC_SHIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0, 0.6, 0.7, 1, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 40;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MAGIC_SHIELD",
	name 		= "$action_magic_shield",
	description = "$actiondesc_magic_shield",
	sprite 		= "data/ui_gfx/gun_actions/magic_shield.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spiral_shot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/magic_shield_start.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,4,5,6", -- SPIRAL_SHOT
	spawn_probability                 = "0.5,0.6,0.7,1", -- SPIRAL_SHOT
	price = 100,
	mana = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/magic_shield_start.xml")
		c.fire_rate_wait = c.fire_rate_wait + 20
	end,
}*/