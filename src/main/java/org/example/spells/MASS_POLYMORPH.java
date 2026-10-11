package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MASS_POLYMORPH;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MASS_POLYMORPH extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "polymorph.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Muodonmuutos";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Baa";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MASS_POLYMORPH();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 600;
        this.manaCost = 220;
        this.hasCharges = true;
        this.maxCharges = 3;
        this.castDelay = 140;
        this.rechargeTime = 240;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MASS_POLYMORPH",
	name 		= "$action_mass_polymorph",
	description = "$actiondesc_mass_polymorph",
	sprite 		= "data/ui_gfx/gun_actions/polymorph.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/mass_polymorph.xml"},
	spawn_requires_flag = "card_unlocked_polymorph",
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "10", -- DESTRUCTION
	spawn_probability                 = "1", -- DESTRUCTION
	price = 600,
	mana = 220,
	max_uses    = 3,
	ai_never_uses = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/mass_polymorph.xml")
		c.fire_rate_wait = c.fire_rate_wait + 140
		current_reload_time = current_reload_time + 240
	end,
}*/