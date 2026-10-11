package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_HOLE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_HOLE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_hole.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Vacuum bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile remove earth as it bounces";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_HOLE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0, 0.4, 0, 0.4, 0, 0, 0, 0.1);
        this.price = 220;
        this.manaCost = 60;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.neverUnlimited = true;
        this.castDelay = 40;
        this.recoil = 10.0;
        this.bounce = 1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE_HOLE",
	name 		= "$action_bounce_hole",
	description = "$actiondesc_bounce_hole",
	sprite 		= "data/ui_gfx/gun_actions/bounce_hole.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_hole.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,4,6,10", -- BOUNCE_EXPLOSION
	spawn_probability                 = "0.1,0.4,0.4,0.1", -- BOUNCE_EXPLOSION
	price = 220,
	mana = 60,
	max_uses = 20,
	never_unlimited = true,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_hole.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 40
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0
		draw_actions( 1, true )
	end,
}*/