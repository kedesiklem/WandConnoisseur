package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SWARM_FIREBUG;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SWARM_FIREBUG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "swarm_firebug.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Firebug swarm";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons four fire bugs to aid you in battle";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SWARM_FIREBUG();
        this.relatedProjectileCount = 4;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0, 0.4, 0.5, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 70;
        this.castDelay = 60;
        this.rechargeTime = 20;
        this.spread = 12.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), 3); // casts 3 by default but 4 when copied with add trigger...
    }
}

/*{
	id          = "SWARM_FIREBUG",
	name 		= "$action_swarm_firebug",
	description = "$actiondesc_swarm_firebug",
	sprite 		= "data/ui_gfx/gun_actions/swarm_firebug.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spiral_shot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/swarm_firebug.xml",4},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "2,5,6", -- SPIRAL_SHOT
	spawn_probability                 = "0.2,0.4,0.5", -- SPIRAL_SHOT
	price = 100,
	mana = 70,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/swarm_firebug.xml")
		add_projectile("data/entities/projectiles/deck/swarm_firebug.xml")
		add_projectile("data/entities/projectiles/deck/swarm_firebug.xml")
		c.spread_degrees = c.spread_degrees + 12.0
		c.fire_rate_wait = c.fire_rate_wait + 60
		current_reload_time = current_reload_time + 20
	end,
}*/