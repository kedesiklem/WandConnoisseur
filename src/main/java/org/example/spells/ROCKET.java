package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ROCKET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ROCKET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "rocket.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Missile";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fiery, explosive projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ROCKET();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 1, 0.5, 0.3, 0, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 70;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 60;
        this.setRecoil = true;
        this.recoil = 120.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
        // c.ragdoll_fx = 2
    }
}

/*{
	id          = "ROCKET",
	name 		= "$action_rocket",
	description = "$actiondesc_rocket",
	sprite 		= "data/ui_gfx/gun_actions/rocket.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/rocket.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- ROCKET
	spawn_probability                 = "1,1,1,0.5,0.3", -- ROCKET
	price = 220,
	mana = 70,
	max_uses    = 10,
	custom_xml_file = "data/entities/misc/custom_cards/rocket.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/rocket.xml")
		c.fire_rate_wait = c.fire_rate_wait + 60
		--current_reload_time = current_reload_time + 40
		c.ragdoll_fx = 2
		shot_effects.recoil_knockback = 120.0
	end,
}*/