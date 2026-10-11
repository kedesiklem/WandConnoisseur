package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BOUNCE_LASER_EMITTER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE_LASER_EMITTER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce_laser_emitter.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam Bounce";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "plasma bounce"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile launches a plasma beam upon bouncing";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BOUNCE_LASER_EMITTER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.4, 0.8, 0.4, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 40;
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
	id          = "BOUNCE_LASER_EMITTER",
	name 		= "$action_bounce_laser_emitter",
	description = "$actiondesc_bounce_laser_emitter",
	sprite 		= "data/ui_gfx/gun_actions/bounce_laser_emitter.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/bounce_laser_emitter.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5", -- BOUNCE_SPARK
	spawn_probability                 = "0.4,0.8,0.4", -- BOUNCE_SPARK
	price = 180,
	mana = 40,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/bounce_laser_emitter.xml,"
		c.bounces = c.bounces + 1
		c.fire_rate_wait = c.fire_rate_wait + 12
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 5.0
		draw_actions( 1, true )
	end,
}*/