package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_WORM_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class WORM_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "worm.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Worm Launcher";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a giant worm to cause havoc for a moment!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_WORM_SHOT();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.6, 0.8, 0.6, 0.4, 0, 0, 0, 0.6);
        this.price = 200;
        this.manaCost = 150;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.neverUnlimited = true;
        this.castDelay = 80;
        this.rechargeTime = 40;
        this.spread = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "WORM_SHOT",
	name 		= "$action_worm_shot",
	description = "$actiondesc_worm_shot",
	spawn_requires_flag = "card_unlocked_exploding_deer",
	sprite 		= "data/ui_gfx/gun_actions/worm.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/exploding_deer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/worm_shot.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,6,10", -- EXPLODING_DEER
	spawn_probability                 = "0.6,0.8,0.6,0.4,0.6", -- EXPLODING_DEER
	price = 200,
	mana = 150,
	max_uses    = 10,
	never_unlimited = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/worm_shot.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
		current_reload_time = current_reload_time + 40
		c.spread_degrees = c.spread_degrees + 20
	end,
}*/