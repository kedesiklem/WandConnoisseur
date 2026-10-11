package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MINE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MINE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "mine.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Unstable crystal";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A crystal that explodes when someone comes nearby";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MINE();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0, 0.75, 1, 0, 0.5, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 30;
        this.recoil = 60.0;
        this.speed = 0.75;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.child_speed_multiplier = c.child_speed_multiplier * 0.75
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id 			= "MINE",
	name 		= "$action_mine",
	description = "$actiondesc_mine",
	sprite 		= "data/ui_gfx/gun_actions/mine.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/mine_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/mine.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level	           = "1,3,4,6", -- MINE
	spawn_probability	   = "1,0.75,1,0.5", -- MINE
	price = 200,
	mana = 20,
	max_uses	= 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/mine.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
		c.child_speed_multiplier = c.child_speed_multiplier * 0.75
		c.speed_multiplier = c.speed_multiplier * 0.75
		shot_effects.recoil_knockback = 60.0

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/