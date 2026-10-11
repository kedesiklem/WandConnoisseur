package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BIG_MAGIC_SHIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BIG_MAGIC_SHIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "big_magic_shield.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Big Magic Guard";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Eight guarding lights rotate around you for a time";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BIG_MAGIC_SHIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0.4, 0.5, 0.6, 0, 0, 0, 0.2);
        this.price = 120;
        this.manaCost = 60;
        this.castDelay = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BIG_MAGIC_SHIELD",
	name 		= "$action_big_magic_shield",
	description = "$actiondesc_big_magic_shield",
	sprite 		= "data/ui_gfx/gun_actions/big_magic_shield.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spiral_shot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/big_magic_shield_start.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,4,5,6,10", -- SPIRAL_SHOT
	spawn_probability                 = "0.2,0.4,0.5,0.6,0.2", -- SPIRAL_SHOT
	price = 120,
	mana = 60,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/big_magic_shield_start.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
	end,
}*/