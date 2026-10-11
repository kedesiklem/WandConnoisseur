package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CHAIN_BOLT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CHAIN_BOLT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "chain_bolt.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chain Bolt";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Fires a mysterious bolt that jumps from enemy to enemy";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CHAIN_BOLT();
        this.spawnProbabilities = new SpawnProbabilities(0.75, 0, 0, 0, 1, 0.8, 0.6, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 80;
        this.castDelay = 45;
        this.spread = 14;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CHAIN_BOLT",
	name 		= "$action_chain_bolt",
	description = "$actiondesc_chain_bolt",
	sprite 		= "data/ui_gfx/gun_actions/chain_bolt.png",
	related_projectiles	= {"data/entities/projectiles/deck/chain_bolt.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,4,5,6", -- CHAIN_BOLT
	spawn_probability                 = "0.75,1,0.8,0.6", -- CHAIN_BOLT
	price = 240,
	mana = 80,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/chain_bolt.xml")
		c.spread_degrees = c.spread_degrees + 14.0
		c.fire_rate_wait = c.fire_rate_wait + 45
	end,
}*/