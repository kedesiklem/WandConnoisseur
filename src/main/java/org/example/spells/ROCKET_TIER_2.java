package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ROCKET_TIER_2;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ROCKET_TIER_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "rocket_tier_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Large Magic Missile";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A more powerful version of Magic missile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ROCKET_TIER_2();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 1, 1, 0.8, 0.5, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 8;
        this.castDelay = 90;
        this.setRecoil = true;
        this.recoil = 160.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
        // c.ragdoll_fx = 2
    }
}

/*{
	id          = "ROCKET_TIER_2",
	name 		= "$action_rocket_tier_2",
	description = "$actiondesc_rocket_tier_2",
	sprite 		= "data/ui_gfx/gun_actions/rocket_tier_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/rocket_tier_2.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5,6", -- ROCKET_TIER_2
	spawn_probability                 = "0.5,1,1,0.8,0.5", -- ROCKET_TIER_2
	price = 240,
	mana = 90,
	max_uses    = 8,
	custom_xml_file = "data/entities/misc/custom_cards/rocket_tier_2.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/rocket_tier_2.xml")
		c.fire_rate_wait = c.fire_rate_wait + 90
		--current_reload_time = current_reload_time + 40
		c.ragdoll_fx = 2
		shot_effects.recoil_knockback = 160.0
	end,
}*/