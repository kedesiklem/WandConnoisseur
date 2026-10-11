package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HOMING_ACCELERATING;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HOMING_ACCELERATING extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "homing_accelerating.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Accelerative Homing";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "accel homing"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile homes towards enemies at an increasing pace";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HOMING_ACCELERATING()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0.3, 0.3, 0.5, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 60;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white_small.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HOMING_ACCELERATING",
	name 		= "$action_homing_accelerating",
	description = "$actiondesc_homing_accelerating",
	sprite 		= "data/ui_gfx/gun_actions/homing_accelerating.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/homing_accelerating.xml", "data/entities/particles/tinyspark_white_small.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4", -- HOMING
	spawn_probability                 = "0.1,0.3,0.3,0.5", -- HOMING
	price = 180,
	mana = 60,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/homing_accelerating.xml,data/entities/particles/tinyspark_white_small.xml,"
		draw_actions( 1, true )
	end,
}*/