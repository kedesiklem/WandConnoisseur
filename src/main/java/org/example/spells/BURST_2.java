package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BURST_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "burst_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Double Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "double", "double cast"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts 2 spells";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0, 0, 0, 0);
        this.price = 140;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(2, true, castState);
    }
}

/*{
	id          = "BURST_2",
	name 		= "$action_burst_2",
	description = "$actiondesc_burst_2",
	sprite 		= "data/ui_gfx/gun_actions/burst_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/burst_2_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "0,1,2,3,4,5,6", -- BURST_2
	spawn_probability                 = "0.8,0.8,0.8,0.8,0.8,0.8,0.8", -- BURST_2
	price = 140,
    mana = 0,
	--max_uses = 100,
	action 		= function()
		draw_actions( 2, true )
	end,
}*/