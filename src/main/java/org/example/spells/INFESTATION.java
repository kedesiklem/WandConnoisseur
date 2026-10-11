package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_INFESTATION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class INFESTATION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "infestation.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Infestation";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A bunch of magical sparks that fly in every direction";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_INFESTATION();
        this.relatedProjectileCount = 10;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.3, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 40;
        this.castDelay = -2;
        this.spread = 25;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), 6); // casts 6 by default but 10 when copied with add trigger...
    }
}

/*{
	id          = "INFESTATION",
	name 		= "$action_infestation",
	description = "$actiondesc_infestation",
	sprite 		= "data/ui_gfx/gun_actions/infestation.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rubber_ball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/infestation.xml",10},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4", -- RUBBER_BALL
	spawn_probability                 = "0.1,0.3,0.4", -- RUBBER_BALL
	price = 160,
	mana = 40,
	--max_uses = 150,
	action 		= function()
		for i=1,6 do
			add_projectile("data/entities/projectiles/deck/infestation.xml")
		end
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait - 2
		c.spread_degrees = c.spread_degrees + 25
	end,
}*/