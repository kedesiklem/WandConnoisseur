package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_COLOUR_RED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class COLOUR_RED extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "colour_red.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Red Glimmer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "red"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a red sparkly trail";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_COLOUR_RED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.2, 0.4, 0.2, 0.2, 0.2, 0, 0, 0, 0);
        this.price = 40;
        this.manaCost = 0;
        this.castDelay = -8;
        this.screenshake = -2.5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "COLOUR_RED",
	name 		= "$action_colour_red",
	description = "$actiondesc_colour_red",
	sprite 		= "data/ui_gfx/gun_actions/colour_red.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/particles/tinyspark_red.xml", "data/entities/misc/colour_red.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5,6", -- HOMING
	spawn_probability                 = "0.2,0.2,0.4,0.2,0.2,0.2", -- HOMING
	spawn_requires_flag = "card_unlocked_paint",
	price = 40,
	mana = 0,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,data/entities/misc/colour_red.xml,"
		c.fire_rate_wait = c.fire_rate_wait - 8
		c.screenshake = c.screenshake - 2.5
		if ( c.screenshake < 0 ) then
			c.screenshake = 0
		end
		draw_actions( 1, true )
	end,
}*/