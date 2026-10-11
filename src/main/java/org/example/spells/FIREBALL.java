package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FIREBALL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIREBALL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "fireball.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fireball";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A powerful exploding spell";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FIREBALL();
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 0, 0.7, 1, 0, 0.5, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 70;
        this.castDelay = 50;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.spread = 4;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FIREBALL",
	name 		= "$action_fireball",
	description = "$actiondesc_fireball",
	sprite 		= "data/ui_gfx/gun_actions/Fireball.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/fireball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/fireball.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,3,4,6", -- FIREBALL
	spawn_probability                 = "1,0.7,1,0.5", -- FIREBALL
	price = 220,
	mana = 70,
	max_uses = 15,
	custom_xml_file = "data/entities/misc/custom_cards/fireball.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/fireball.xml")
		c.spread_degrees = c.spread_degrees + 4.0
		c.fire_rate_wait = c.fire_rate_wait + 50
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/