package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ICEBALL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ICEBALL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "iceball.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Iceball";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical ball of frozen fire";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ICEBALL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.8, 0.9, 0.9, 0, 0.6, 0, 0, 0, 0);
        this.price = 260;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 80;
        this.spread = 8.0;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ICEBALL",
	name 		= "$action_iceball",
	description = "$actiondesc_iceball",
	sprite 		= "data/ui_gfx/gun_actions/iceball.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/fireball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/iceball.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,6", -- FIREBALL
	spawn_probability                 = "0.8,0.9,0.9,0.6", -- FIREBALL
	price = 260,
	mana = 90,
	max_uses = 15,
	custom_xml_file = "data/entities/misc/custom_cards/iceball.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/iceball.xml")
		c.spread_degrees = c.spread_degrees + 8.0
		c.fire_rate_wait = c.fire_rate_wait + 80
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/