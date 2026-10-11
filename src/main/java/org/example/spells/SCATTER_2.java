package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCATTER_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "scatter_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Double Scatter Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "double scatter", "double spread"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts 2 spells with low accuracy";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0.8, 0.8, 0.7, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.autoStat = false;
        this.spread = 10.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(2, true, castState);
        castState.addSpread(this.spread);
    }
}

/*{

	id          = "SCATTER_2",
	name 		= "$action_scatter_2",
	description = "$actiondesc_scatter_2",
	sprite 		= "data/ui_gfx/gun_actions/scatter_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/scatter_2_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "0,1,2", -- SCATTER_2
	spawn_probability                 = "0.8,0.8,0.7", -- SCATTER_2
	price = 100,
	mana = 0,
	--max_uses = 100,
	action 		= function()
		draw_actions( 2, true )
		c.spread_degrees = c.spread_degrees + 10.0
	end,
}*/