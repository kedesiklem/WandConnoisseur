package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_SPARK;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_SPARK extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_spark.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Bubbly bounce";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "bubble bounce"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile shoot bubble sparks as it bounces";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_SPARK()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.6, 0.6, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 20;
        this.castDelay = 8;
        this.recoil = 5.0;
        this.bounce = 1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE_SPARK",
	name 		= "$action_bounce_spark",
	description = "$actiondesc_bounce_spark",
	sprite 		= "data/ui_gfx/gun_actions/bounce_spark.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_spark.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4", -- BOUNCE_SPARK
	spawn_probability                 = "0.2,0.6,0.6,0.6", -- BOUNCE_SPARK
	price = 120,
	mana = 20,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_spark.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 8
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 5.0
		draw_actions( 1, true )
	end,
}*/