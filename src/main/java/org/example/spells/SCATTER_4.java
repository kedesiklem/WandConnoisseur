package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCATTER_4 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "scatter_4.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Quadruple Scatter Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "quad scatter", "quad spread", "quadruple scatter", "quadruple spread"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts 4 spells with low accuracy";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.6, 0.7, 0.8, 0.8, 0.6, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 2;
        this.autoStat = false;
        this.spread = 40.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(4, true, castState);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "SCATTER_4",
	name 		= "$action_scatter_4",
	description = "$actiondesc_scatter_4",
	sprite 		= "data/ui_gfx/gun_actions/scatter_4.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/scatter_4_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "1,2,3,4,5,6", -- SCATTER_4
	spawn_probability                 = "0.5,0.6,0.7,0.8,0.8,0.6", -- SCATTER_4
	price = 140,
	mana = 2,
	--max_uses = 100,
	action 		= function()
		draw_actions( 4, true )
		c.spread_degrees = c.spread_degrees + 40.0
	end,
}*/