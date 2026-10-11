package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class T_SHAPE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "t_shape.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Formation - Above And Below";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "t shape", "above below", "above and below"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts 3 spells - ahead, above and below the caster";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.5, 0.4, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 30;
        this.manaCost = 3;
        this.autoStat = false;
        this.pattern = 90;
        this.spread = -8.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(3, true, castState);
        castState.setPattern(this.pattern);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "T_SHAPE",
	name 		= "$action_t_shape",
	description = "$actiondesc_t_shape",
	sprite 		= "data/ui_gfx/gun_actions/t_shape.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/t_shape_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "1,2,3,4", -- T_SHAPE
	spawn_probability                 = "0.4,0.5,0.4,0.3", -- T_SHAPE
	price = 30,
	mana = 3,
	--max_uses = 100,
	action 		= function()
		draw_actions(3, true)
		c.pattern_degrees = 90
		c.spread_degrees = c.spread_degrees - 8.0
	end,
}*/