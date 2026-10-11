package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_LASER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_LASER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_laser.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Laser bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile release a bundle of concentrated light as it bounces";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_LASER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.4, 0.8, 0.4, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 30;
        this.castDelay = 12;
        this.recoil = 5.0;
        this.bounce = 1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE_LASER",
	name 		= "$action_bounce_laser",
	description = "$actiondesc_bounce_laser",
	sprite 		= "data/ui_gfx/gun_actions/bounce_laser.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_laser.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5", -- BOUNCE_SPARK
	spawn_probability                 = "0.4,0.8,0.4", -- BOUNCE_SPARK
	price = 180,
	mana = 30,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_laser.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 12
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 5.0
		draw_actions( 1, true )
	end,
}*/