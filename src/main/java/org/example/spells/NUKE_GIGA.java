package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_NUKE_GIGA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class NUKE_GIGA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "nuke_giga.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giga Nuke";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "What do you expect?";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_NUKE_GIGA();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 800;
        this.hasCharges = true;
        this.maxCharges = 1;
        this.neverUnlimited = true;
        this.manaCost = 500;
        this.setCastDelay = true;
        this.castDelay = 50;
        this.rechargeTime = 800;
        this.screenshake = 30.5;
        this.recoil = 300.0;
        this.speed = 0.5;
        this.goreParticles = 30;
        this.material = "fire";
        this.materialAmount = 80;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
		// c.ragdoll_fx = 2
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "NUKE_GIGA",
	name 		= "$action_nuke_giga",
	description = "$actiondesc_nuke_giga",
	sprite 		= "data/ui_gfx/gun_actions/nuke_giga.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/nuke_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/nuke_giga.xml"},
	spawn_requires_flag = "card_unlocked_nukegiga",
	spawn_manual_unlock = true,
	never_unlimited		= true,
	recursive	= true,
	ai_never_uses = true,
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "10", -- NUKE
	spawn_probability                 = "1", -- NUKE
	price = 800,
	mana = 500,
	max_uses    = 1,
	custom_xml_file = "data/entities/misc/custom_cards/nuke_giga.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/nuke_giga.xml")
		c.fire_rate_wait = 50
		c.speed_multiplier = c.speed_multiplier * 0.5
		c.material = "fire"
		c.material_amount = c.material_amount + 80
		c.ragdoll_fx = 2
		c.gore_particles = c.gore_particles + 30
		c.screenshake = c.screenshake + 30.5
		current_reload_time = current_reload_time + 800
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 300.0

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/