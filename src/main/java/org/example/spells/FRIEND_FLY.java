package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FRIEND_FLY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FRIEND_FLY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "friend_fly.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Friendly fly";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a friendly fly that attacks your enemies!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FRIEND_FLY();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.2, 0.6, 0.5, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 120;
        this.castDelay = 80;
        this.rechargeTime = 40;
        this.spread = 24.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FRIEND_FLY",
	name 		= "$action_friend_fly",
	description = "$actiondesc_friend_fly",
	sprite 		= "data/ui_gfx/gun_actions/friend_fly.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spiral_shot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/friend_fly.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "4,5,6", -- SPIRAL_SHOT
	spawn_probability                 = "0.2,0.6,0.5", -- SPIRAL_SHOT
	price = 160,
	mana = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/friend_fly.xml")
		c.spread_degrees = c.spread_degrees + 24.0
		c.fire_rate_wait = c.fire_rate_wait + 80
		current_reload_time = current_reload_time + 40
	end,
}*/