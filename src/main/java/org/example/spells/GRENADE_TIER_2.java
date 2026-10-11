package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_GRENADE_TIER_2;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GRENADE_TIER_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "grenade_tier_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Large Firebolt";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A more powerful version of Firebolt";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GRENADE_TIER_2();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 1, 1, 1, 0.5, 0, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 50;
        this.setRecoil = true;
        this.recoil = 120.0;
        this.screenshake = 8.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.child_speed_multiplier = c.child_speed_multiplier * 0.75
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "GRENADE_TIER_2",
	name 		= "$action_grenade_tier_2",
	description = "$actiondesc_grenade_tier_2",
	sprite 		= "data/ui_gfx/gun_actions/grenade_tier_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/grenade_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/grenade_tier_2.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- GRENADE_TIER_2
	spawn_probability                 = "0.5,1,1,1,0.5", -- GRENADE_TIER_2
	price = 220,
	mana = 90,
	max_uses    = 20,
	custom_xml_file = "data/entities/misc/custom_cards/grenade_tier_2.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/grenade_tier_2.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		c.screenshake = c.screenshake + 8.0
		c.child_speed_multiplier = c.child_speed_multiplier * 0.75
		--current_reload_time = current_reload_time + 40
		shot_effects.recoil_knockback = 120.0
	end,
}*/