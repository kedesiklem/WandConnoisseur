package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_LARPA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_LARPA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_larpa.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Larpa Bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile will launch a copy of itself when it bounces";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_LARPA()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.4, 0.6, 0.4, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 80;
        this.castDelay = 32;
        this.recoil = 10.0;
        this.bounce = 1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE_LARPA",
	name 		= "$action_bounce_larpa",
	description = "$actiondesc_bounce_larpa",
	sprite 		= "data/ui_gfx/gun_actions/bounce_larpa.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_larpa.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "4,5,6", -- BOUNCE_SPARK
	spawn_probability                 = "0.4,0.6,0.4", -- BOUNCE_SPARK
	price = 250,
	mana = 80,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_larpa.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 32
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0
		draw_actions( 1, true )
	end,
}*/