package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_COLOUR_GREEN;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class COLOUR_GREEN extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "colour_green.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Green Glimmer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "green"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a green sparkly trail";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_COLOUR_GREEN()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.1, 0.1, 0, 0, 0, 0, 0, 0);
        this.price = 40;
        this.manaCost = 0;
        this.castDelay = -8;
        this.screenshake = -2.5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_red.xml," red ???
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "COLOUR_GREEN",
	name 		= "$action_colour_green",
	description = "$actiondesc_colour_green",
	sprite 		= "data/ui_gfx/gun_actions/colour_green.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/particles/tinyspark_red.xml", "data/entities/misc/colour_green.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- HOMING
	spawn_probability                 = "0.4,0.1,0.1", -- HOMING
	spawn_requires_flag = "card_unlocked_paint",
	price = 40,
	mana = 0,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,data/entities/misc/colour_green.xml,"
		c.fire_rate_wait = c.fire_rate_wait - 8
		c.screenshake = c.screenshake - 2.5
		if ( c.screenshake < 0 ) then
			c.screenshake = 0
		end
		draw_actions( 1, true )
	end,
}*/