package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_EXPLOSION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_EXPLOSION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_explosion.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Explosive bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile explode as it bounces";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_EXPLOSION()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.6, 0.8, 0.8, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 20;
        this.castDelay = 25;
        this.recoil = 20.0;
        this.bounce = 1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE_EXPLOSION",
	name 		= "$action_bounce_explosion",
	description = "$actiondesc_bounce_explosion",
	sprite 		= "data/ui_gfx/gun_actions/bounce_explosion.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_explosion.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5", -- BOUNCE_EXPLOSION
	spawn_probability                 = "0.2,0.6,0.8,0.8", -- BOUNCE_EXPLOSION
	price = 180,
	mana = 20,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_explosion.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 25
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
		draw_actions( 1, true )
	end,
}*/