package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SWARM_WASP;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SWARM_WASP extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "swarm_wasp.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Wasp swarm";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summon six wasps to aid you in battle";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SWARM_WASP();
        this.relatedProjectileCount = 6;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.2, 0.5, 0.6, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 80;
        this.castDelay = 60;
        this.rechargeTime = 20;
        this.spread = 24.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), 5); // casts 5 by default but 6 when copied with add trigger...
    }
}

/*{
	id          = "SWARM_WASP",
	name 		= "$action_swarm_wasp",
	description = "$actiondesc_swarm_wasp",
	sprite 		= "data/ui_gfx/gun_actions/swarm_wasp.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spiral_shot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/swarm_wasp.xml",6},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "4,5,6", -- SPIRAL_SHOT
	spawn_probability                 = "0.2,0.5,0.6", -- SPIRAL_SHOT
	price = 120,
	mana = 80,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/swarm_wasp.xml")
		add_projectile("data/entities/projectiles/deck/swarm_wasp.xml")
		add_projectile("data/entities/projectiles/deck/swarm_wasp.xml")
		add_projectile("data/entities/projectiles/deck/swarm_wasp.xml")
		add_projectile("data/entities/projectiles/deck/swarm_wasp.xml")
		c.spread_degrees = c.spread_degrees + 24.0
		c.fire_rate_wait = c.fire_rate_wait + 60
		current_reload_time = current_reload_time + 20
	end,
}*/