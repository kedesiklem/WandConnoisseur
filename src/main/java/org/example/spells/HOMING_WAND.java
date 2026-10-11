package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HOMING_WAND;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HOMING_WAND extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "homing_wand.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Wand Homing";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile accelerate towards wands";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HOMING_WAND()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.00001, 0.08, 0, 0.08, 0.25, 0.25, 0, 0, 0, 0.2);
        this.price = 500;
        this.manaCost = 200;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HOMING_WAND",
	name 		= "$action_homing_wand",
	description = "$actiondesc_homing_wand",
	sprite 		= "data/ui_gfx/gun_actions/homing_wand.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	spawn_requires_flag = "card_unlocked_homing_wand",
	related_extra_entities = { "data/entities/misc/homing_wand.xml", "data/entities/particles/tinyspark_white.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5,6,10", -- HOMING_WAND
	spawn_probability                 = "0.00001,0.08,0.08,0.25,0.25,0.2", -- SUMMON_WANDGHOST
	price = 500,
	mana = 200,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/homing_wand.xml,data/entities/particles/tinyspark_white.xml,"
		draw_actions( 1, true )
	end,
}*/