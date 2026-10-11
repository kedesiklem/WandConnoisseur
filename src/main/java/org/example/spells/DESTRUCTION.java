package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_DESTRUCTION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DESTRUCTION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "destruction.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Destruction";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Instantly decimates foes around you, at the cost of your maximum HP";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DESTRUCTION();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 600;
        this.manaCost = 240;
        this.hasCharges = true;
        this.maxCharges = 5;
        this.castDelay = 150;
        this.rechargeTime = 240;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "DESTRUCTION",
	name 		= "$action_destruction",
	description = "$actiondesc_destruction",
	sprite 		= "data/ui_gfx/gun_actions/destruction.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/destruction.xml"},
	spawn_requires_flag = "card_unlocked_destruction",
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "10", -- DESTRUCTION
	spawn_probability                 = "1", -- DESTRUCTION
	price = 600,
	mana = 240,
	max_uses    = 5,
	ai_never_uses = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/destruction.xml")
		c.fire_rate_wait = c.fire_rate_wait + 150
		current_reload_time = current_reload_time + 240
	end,
}*/