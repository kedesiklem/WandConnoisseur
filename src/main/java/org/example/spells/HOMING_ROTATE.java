package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HOMING_ROTATE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HOMING_ROTATE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "homing_rotate.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Rotate Towards Foes";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "rotate"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile turn towards your foes";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HOMING_ROTATE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.4, 0.6, 0.4, 0.4, 0, 0, 0, 0);
        this.price = 175;
        this.manaCost = 40;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HOMING_ROTATE",
	name 		= "$action_homing_rotate",
	description = "$actiondesc_homing_rotate",
	sprite 		= "data/ui_gfx/gun_actions/homing_rotate.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/homing_rotate.xml", "data/entities/particles/tinyspark_white.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- HOMING_ROTATE
	spawn_probability                 = "0.2,0.4,0.6,0.4,0.4", -- HOMING_ROTATE
	price = 175,
	mana = 40,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/homing_rotate.xml,data/entities/particles/tinyspark_white.xml,"
		draw_actions( 1, true )
	end,
}*/