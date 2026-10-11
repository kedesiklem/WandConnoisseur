package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ACCELERATING_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ACCELERATING_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "accelerating_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Accelerating shot";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "accelerating", "accel"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Causes a projectile to accelerate as it flies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ACCELERATING_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.4, 1, 0, 0, 0, 0, 0, 0);
        this.price = 190;
        this.manaCost = 20;
        this.castDelay = 8;
        this.recoil = 10.0;
        this.speed = 0.32;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ACCELERATING_SHOT",
	name 		= "$action_accelerating_shot",
	description = "$actiondesc_accelerating_shot",
	sprite 		= "data/ui_gfx/gun_actions/accelerating_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heavy_shot_unidentified.png",
	related_extra_entities = { "data/entities/misc/accelerating_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- ACCELERATING_SHOT
	spawn_probability                 = "0.5,0.4,1", -- ACCELERATING_SHOT
	price = 190,
	mana = 20,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/accelerating_shot.xml",
	action 		= function()
		c.fire_rate_wait    = c.fire_rate_wait + 8
		c.speed_multiplier = c.speed_multiplier * 0.32
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0
		c.extra_entities = c.extra_entities .. "data/entities/misc/accelerating_shot.xml,"

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/