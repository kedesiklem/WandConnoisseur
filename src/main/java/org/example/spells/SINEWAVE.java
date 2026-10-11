package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_SINEWAVE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SINEWAVE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "sinewave.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Slithering path";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "slithering", "slither"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile move rapidly in a slithering manner, like a snake";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_SINEWAVE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0, 0.55, 0, 0.4, 0, 0, 0, 0);
        this.price = 10;
        this.manaCost = 0;
        this.speed = 2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "SINEWAVE",
	name 		= "$action_sinewave",
	description = "$actiondesc_sinewave",
	sprite 		= "data/ui_gfx/gun_actions/sinewave.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/sinewave.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,4,6", -- SINEWAVE
	spawn_probability                 = "0.4,0.55,0.4", -- SINEWAVE
	price = 10,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/sinewave.xml,"
		c.speed_multiplier = c.speed_multiplier * 2

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/