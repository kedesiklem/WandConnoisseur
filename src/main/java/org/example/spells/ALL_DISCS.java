package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ALL_DISCS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ALL_DISCS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "all_discs.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spells to giga sawblades";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transforms every projectile currently in the air into a giant sawblade";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ALL_DISCS();
        this.spawnProbabilities = new SpawnProbabilities(0.1, 0, 0, 0, 0, 0, 0.05, 0, 0, 0, 1);
        this.price = 400;
        this.manaCost = 100;
        this.castDelay = 50;
        this.rechargeTime = 50;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ALL_DISCS",
	name 		= "$action_all_discs",
	description = "$actiondesc_all_discs",
	sprite 		= "data/ui_gfx/gun_actions/all_discs.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	spawn_requires_flag = "card_unlocked_alchemy",
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "0,6,10", -- DESTRUCTION
	spawn_probability                 = "0.1,0.05,1", -- DESTRUCTION
	price = 400,
	mana = 100,
	--max_uses    = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/all_discs.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		current_reload_time = current_reload_time + 50
	end,
}*/