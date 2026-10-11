package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ROCKET_TIER_3;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ROCKET_TIER_3 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "rocket_tier_3.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giant Magic Missile";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The most powerful version of Magic missile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ROCKET_TIER_3();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.25, 0.5, 1, 1, 1, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 6;
        this.castDelay = 120;
        this.setRecoil = true;
        this.recoil = 180.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
        // c.ragdoll_fx = 2
    }
}

/*{
	id          = "ROCKET_TIER_3",
	name 		= "$action_rocket_tier_3",
	description = "$actiondesc_rocket_tier_3",
	sprite 		= "data/ui_gfx/gun_actions/rocket_tier_3.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/rocket_tier_3.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5,6", -- ROCKET_TIER_3
	spawn_probability                 = "0.25,0.5,1,1,1", -- ROCKET_TIER_3
	price = 250,
	mana = 120,
	max_uses    = 6,
	custom_xml_file = "data/entities/misc/custom_cards/rocket_tier_3.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/rocket_tier_3.xml")
		c.fire_rate_wait = c.fire_rate_wait + 120
		--current_reload_time = current_reload_time + 40
		c.ragdoll_fx = 2
		shot_effects.recoil_knockback = 180.0
	end,
}*/