package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CIRCLE_SHAPE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "circle_shape.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Formation - Hexagon";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "hexa", "hexa cast", "hexa spell"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts 6 spells in a hexagonal pattern";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0.2, 0.3, 0.3, 0.3, 0.3, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 6;
        this.autoStat = false;
        this.pattern = 180;
        this.spread = -15.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(6, true, castState);
        castState.setPattern(this.pattern);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "CIRCLE_SHAPE",
	name 		= "$action_circle_shape",
	description = "$actiondesc_circle_shape",
	sprite 		= "data/ui_gfx/gun_actions/circle_shape.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/circle_shape_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "1,2,3,4,5,6", -- CIRCLE_SHAPE
	spawn_probability                 = "0.1,0.2,0.3,0.3,0.3,0.3", -- CIRCLE_SHAPE
	price = 50,
	mana = 6,
	--max_uses = 100,
	action 		= function()
		draw_actions(6, true)
		c.pattern_degrees = 180
		c.spread_degrees = c.spread_degrees - 15.0
	end,
}*/