package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class W_SHAPE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "w_shape.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Formation - Trifurcated";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "w shape", "trifurcated"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts 2 spells in a trifurcated pattern";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.3, 0.5, 0.3, 0.3, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 3;
        this.autoStat = false;
        this.pattern = 20;
        this.spread = -5.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(3, true, castState);
        castState.setPattern(this.pattern);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "W_SHAPE",
	name 		= "$action_w_shape",
	description = "$actiondesc_w_shape",
	sprite 		= "data/ui_gfx/gun_actions/w_shape.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/w_shape_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "2,3,4,5,6", -- W_SHAPE
	spawn_probability                 = "0.4,0.3,0.5,0.3,0.3", -- W_SHAPE
	price = 50,
	mana = 3,
	--max_uses = 100,
	action 		= function()
		draw_actions(3, true)
		c.pattern_degrees = 20
		c.spread_degrees = c.spread_degrees - 5.0
	end,
}*/