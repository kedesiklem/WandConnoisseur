package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_EXPLODING_DUCKS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class EXPLODING_DUCKS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "duck_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Flock of Ducks";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "duck"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a chaotic flock of spicy ducks";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_EXPLODING_DUCKS();
        this.relatedProjectileCount = 3;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.8, 0.5, 0.6, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 100;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 60;
        this.rechargeTime = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), this.relatedProjectileCount);
    }
}

/*{
	id          = "EXPLODING_DUCKS",
	name 		= "$action_exploding_ducks",
	description = "$actiondesc_exploding_ducks",
	spawn_requires_flag = "card_unlocked_exploding_deer",
	sprite 		= "data/ui_gfx/gun_actions/duck_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/exploding_deer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/duck.xml", 3},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5", -- EXPLODING_DEER
	spawn_probability                 = "0.8,0.5,0.6", -- EXPLODING_DEER
	price = 200,
	mana = 100,
	max_uses    = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/duck.xml")
		add_projectile("data/entities/projectiles/deck/duck.xml")
		add_projectile("data/entities/projectiles/deck/duck.xml")
		c.fire_rate_wait = c.fire_rate_wait + 60
		current_reload_time = current_reload_time + 20
	end,
}*/