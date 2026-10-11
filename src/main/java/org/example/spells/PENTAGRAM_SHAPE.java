package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PENTAGRAM_SHAPE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "pentagram_shape.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Formation - Pentagon";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "penta", "penta cast", "penta spell"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts 5 spells in a pentagonal pattern";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.3, 0.2, 0.1, 0, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 5;
        this.autoStat = false;
        this.pattern = 180;
        this.spread = -12.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(5, true, castState);
        castState.setPattern(this.pattern);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "PENTAGRAM_SHAPE",
	name 		= "$action_pentagram_shape",
	description = "$actiondesc_pentagram_shape",
	sprite 		= "data/ui_gfx/gun_actions/pentagram_shape.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/pentagram_shape_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "1,2,3,4,5", -- PENTAGRAM_SHAPE
	spawn_probability                 = "0.4,0.4,0.3,0.2,0.1", -- PENTAGRAM_SHAPE
	price = 50,
	mana = 5,
	--max_uses = 100,
	action 		= function()
		draw_actions(5, true)
		c.pattern_degrees = 180
		c.spread_degrees = c.spread_degrees - 12.0
		--c.rad_pattern_degrees_offset = 150 // TODO: implement this
		--c.pattern_pos_offset = 30
	end,
}*/