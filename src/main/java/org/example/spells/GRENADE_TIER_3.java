package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_GRENADE_TIER_3;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GRENADE_TIER_3 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "grenade_tier_3.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giant Firebolt";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The most powerful version of Firebolt";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GRENADE_TIER_3();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.25, 0.5, 0.75, 1, 1, 0, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 80;
        this.setRecoil = true;
        this.recoil = 140.0;
        this.screenshake = 15.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.child_speed_multiplier = c.child_speed_multiplier * 0.9
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "GRENADE_TIER_3",
	name 		= "$action_grenade_tier_3",
	description = "$actiondesc_grenade_tier_3",
	sprite 		= "data/ui_gfx/gun_actions/grenade_tier_3.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/grenade_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/grenade_tier_3.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- GRENADE_TIER_3
	spawn_probability                 = "0.25,0.5,0.75,1,1", -- GRENADE_TIER_3
	price = 220,
	mana = 90,
	max_uses    = 20,
	custom_xml_file = "data/entities/misc/custom_cards/grenade_tier_3.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/grenade_tier_3.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
		c.screenshake = c.screenshake + 15.0
		c.child_speed_multiplier = c.child_speed_multiplier * 0.9
		--current_reload_time = current_reload_time + 40
		shot_effects.recoil_knockback = 140.0
	end,
}*/