package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HOMING_SHORT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HOMING_SHORT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "homing_short.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Short-range Homing";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "short range homing", "short homing"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile flies towards targets when near them";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HOMING_SHORT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.8, 1, 0.4, 0.3, 0.1, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 40;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white_weak.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HOMING_SHORT",
	name 		= "$action_homing_short",
	description = "$actiondesc_homing_short",
	sprite 		= "data/ui_gfx/gun_actions/homing_short.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/homing_short.xml", "data/entities/particles/tinyspark_white_weak.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5,6", -- HOMING
		spawn_probability                 = "0.4,0.8,1,0.4,0.3,0.1", -- HOMING
	price = 160,
	mana = 40,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/homing_short.xml,data/entities/particles/tinyspark_white_weak.xml,"
		draw_actions( 1, true )
	end,
}*/