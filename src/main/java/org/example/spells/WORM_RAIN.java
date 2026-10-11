package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_WORM_BIG;
import org.example.projectiles.PROJECTILE_WORM_RAIN;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class WORM_RAIN extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "worm_rain.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Matosade";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Alea iacta est";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_WORM_BIG();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0.1, 0, 0, 0, 1);
        this.price = 300;
        this.manaCost = 225;
        this.hasCharges = true;
        this.maxCharges = 2;
        this.neverUnlimited = true;
        this.castDelay = 100;
        this.rechargeTime = 60;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(new PROJECTILE_WORM_RAIN());
    }
}

/*{
	id          = "WORM_RAIN",
	name 		= "$action_worm_rain",
	description = "$actiondesc_worm_rain",
	sprite 		= "data/ui_gfx/gun_actions/worm_rain.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/animals/worm_big.xml"},
	spawn_requires_flag = "card_unlocked_rain",
	never_unlimited		= true,
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "6,10", -- BOMB
	spawn_probability                 = "0.1,1", -- BOMB
	price = 300,
	mana = 225,
	max_uses    = 2,
	custom_xml_file = "data/entities/misc/custom_cards/worm_rain.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/worm_rain.xml")
		c.fire_rate_wait = c.fire_rate_wait + 100
		current_reload_time = current_reload_time + 60
	end,
}*/