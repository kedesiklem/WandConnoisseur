package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BUCKSHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BUCKSHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "buckshot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Triplicate Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "triplicate", "shotgun"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A formation of three small, fast bolts";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BUCKSHOT();
        this.relatedProjectileCount = 3;
        this.spawnProbabilities = new SpawnProbabilities(1, 1, 0.9, 0.9, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 25;
        this.castDelay = 8;
        this.spread = 14;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), this.relatedProjectileCount);
    }
}

/*{
	id          = "BUCKSHOT",
	name 		= "$action_buckshot",
	description = "$actiondesc_buckshot",
	sprite 		= "data/ui_gfx/gun_actions/buckshot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/buckshot_player.xml",3},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3,4", -- BUCKSHOT
	spawn_probability                 = "1,1,0.9,0.9,0.6", -- BUCKSHOT
	price = 160,
	mana = 25,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/buckshot_player.xml")
		add_projectile("data/entities/projectiles/deck/buckshot_player.xml")
		add_projectile("data/entities/projectiles/deck/buckshot_player.xml")
		c.fire_rate_wait = c.fire_rate_wait + 8
		c.spread_degrees = c.spread_degrees + 14.0
	end,
}*/