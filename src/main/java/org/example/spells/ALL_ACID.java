package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ALL_ACID;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ALL_ACID extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "all_acid.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spells to acid";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transforms every projectile currently in the air into a pool of acid";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ALL_ACID();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.1, 0, 0.05, 0, 0, 0, 1);
        this.price = 600;
        this.manaCost = 200;
        this.castDelay = 100;
        this.rechargeTime = 100;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ALL_ACID",
	name 		= "$action_all_acid",
	description = "$actiondesc_all_acid",
	sprite 		= "data/ui_gfx/gun_actions/all_acid.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	spawn_requires_flag = "card_unlocked_alchemy",
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "4,6,10", -- DESTRUCTION
	spawn_probability                 = "0.1,0.05,1", -- DESTRUCTION
	price = 600,
	mana = 200,
	--max_uses    = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/all_acid.xml")
		c.fire_rate_wait = c.fire_rate_wait + 100
		current_reload_time = current_reload_time + 100
	end,
}*/