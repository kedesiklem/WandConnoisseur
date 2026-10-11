package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_PEBBLE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PEBBLE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "pebble.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon rock spirit";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons an autonomous rock ally";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_PEBBLE();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.9, 1, 0, 0.9, 0, 0.6, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 80;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "PEBBLE",
	name 		= "$action_pebble",
	description = "$actiondesc_pebble",
	sprite 		= "data/ui_gfx/gun_actions/pebble.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/pebble_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/pebble_player.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,4,6", -- PEBBLE
	spawn_probability                 = "0.9,1,0.9,0.6", -- PEBBLE
	price = 200,
	mana = 120,
	max_uses    = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/pebble_player.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
	end,
}*/