package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_METEOR_RAIN;
import org.example.projectiles.PROJECTILE_METEOR_RAIN_METEOR;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_METEOR_RAIN;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class METEOR_RAIN extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "meteor_rain.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Meteorisade";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Alea iacta est";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_METEOR_RAIN_METEOR();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_METEOR_RAIN()};
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
        castState.addProjectile(new PROJECTILE_METEOR_RAIN());
    }
}

/*{
	id          = "METEOR_RAIN",
	name 		= "$action_meteor_rain",
	description = "$actiondesc_meteor_rain",
	sprite 		= "data/ui_gfx/gun_actions/meteor_rain.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= { "data/entities/projectiles/deck/meteor_rain_meteor.xml" },
	related_extra_entities = { "data/entities/misc/effect_meteor_rain.xml" },
	spawn_requires_flag = "card_unlocked_rain",
	never_unlimited		= true,
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "6,10", -- BOMB
	spawn_probability                 = "0.1,1", -- BOMB
	price = 300,
	mana = 225,
	max_uses    = 2,
	custom_xml_file = "data/entities/misc/custom_cards/meteor_rain.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/meteor_rain.xml")
		c.extra_entities = c.extra_entities .. "data/entities/misc/effect_meteor_rain.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 100
		current_reload_time = current_reload_time + 60
	end,
}*/