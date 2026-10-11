package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_RUBBER_BALL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class RUBBER_BALL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "rubber_ball.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Bouncing burst";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "bb"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A very bouncy projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_RUBBER_BALL();
        this.spawnProbabilities = new SpawnProbabilities(1, 1, 0, 0, 0, 0, 0.2, 0, 0, 0, 0);
        this.price = 60;
        this.manaCost = 5;
        this.castDelay = -2;
        this.spread = -1.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "RUBBER_BALL",
	name 		= "$action_rubber_ball",
	description = "$actiondesc_rubber_ball",
	sprite 		= "data/ui_gfx/gun_actions/rubber_ball.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rubber_ball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/rubber_ball.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,6", -- RUBBER_BALL
	spawn_probability                 = "1,1,0.2", -- RUBBER_BALL
	price = 60,
	mana = 5,
	--max_uses = 150,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/rubber_ball.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait - 2
		c.spread_degrees = c.spread_degrees - 1.0
	end,
}*/