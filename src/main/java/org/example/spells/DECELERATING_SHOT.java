package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_DECELERATING_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DECELERATING_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "decelerating_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Decelerating shot";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "deccel"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile decelerate as it flies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_DECELERATING_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.1, 0.7, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 10;
        this.castDelay = -8;
        this.recoil = -10.0;
        this.speed = 1.68;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "DECELERATING_SHOT",
	name 		= "$action_decelerating_shot",
	description = "$actiondesc_decelerating_shot",
	sprite 		= "data/ui_gfx/gun_actions/decelerating_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heavy_shot_unidentified.png",
	related_extra_entities = { "data/entities/misc/decelerating_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- ACCELERATING_SHOT
	spawn_probability                 = "0.4,0.1,0.7", -- ACCELERATING_SHOT
	price = 80,
	mana = 10,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/decelerating_shot.xml",
	action 		= function()
		c.fire_rate_wait    = c.fire_rate_wait - 8
		c.speed_multiplier = c.speed_multiplier * 1.68
		shot_effects.recoil_knockback = shot_effects.recoil_knockback - 10.0
		c.extra_entities = c.extra_entities .. "data/entities/misc/decelerating_shot.xml,"

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/