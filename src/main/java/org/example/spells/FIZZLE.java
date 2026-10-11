package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_FIZZLE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIZZLE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "fizzle.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fizzle";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a spell a small probability of short-circuiting";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_FIZZLE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.4, 0.3, 0.1, 0, 0, 0, 0, 0);
        this.price = 0;
        this.manaCost = 0;
        this.castDelay = -10;
        this.speed = 1.2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "FIZZLE",
	name 		= "$action_fizzle",
	description = "$actiondesc_fizzle",
	sprite 		= "data/ui_gfx/gun_actions/fizzle.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/fizzle.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5", -- CHAOTIC_ARC
	spawn_probability                 = "0.4,0.3,0.1", -- CHAOTIC_ARC
	price = 0,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/fizzle.xml,"
		c.speed_multiplier = c.speed_multiplier * 1.2
		c.fire_rate_wait = c.fire_rate_wait - 10

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/