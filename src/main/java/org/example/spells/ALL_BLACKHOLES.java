package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ALL_BLACKHOLES;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ALL_BLACKHOLES extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "all_blackholes.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spells to black holes";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spells to bh"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transforms every projectile currently in the air into a black hole";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ALL_BLACKHOLES();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.1, 0, 0, 0.05, 0, 0, 0, 1);
        this.price = 500;
        this.manaCost = 200;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.neverUnlimited = true;
        this.castDelay = 100;
        this.rechargeTime = 100;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ALL_BLACKHOLES",
	name 		= "$action_all_blackholes",
	description = "$actiondesc_all_blackholes",
	sprite 		= "data/ui_gfx/gun_actions/all_blackholes.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	spawn_requires_flag = "card_unlocked_alchemy",
	never_unlimited		= true,
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "3,6,10", -- DESTRUCTION
	spawn_probability                 = "0.1,0.05,1", -- DESTRUCTION
	price = 500,
	mana = 200,
	max_uses    = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/all_blackholes.xml")
		c.fire_rate_wait = c.fire_rate_wait + 100
		current_reload_time = current_reload_time + 100
	end,
}*/