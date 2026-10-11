package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BURST_4 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "burst_4.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Quadruple Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "quad", "quad cast", "quad spell", "quadruple", "quadruple cast", "quadruple spell"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts 4 spells";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.5, 0.6, 0.6, 0.6, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(4, true, castState);
    }
}

/*{
	id          = "BURST_4",
	name 		= "$action_burst_4",
	description = "$actiondesc_burst_4",
	sprite 		= "data/ui_gfx/gun_actions/burst_4.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/burst_4_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "2,3,4,5,6", -- BURST_4
	spawn_probability                 = "0.4,0.5,0.6,0.6,0.6", -- BURST_4
	price = 180,
	mana = 5,
    --max_uses = 100,
	action 		= function()
		draw_actions( 4, true )
	end,
}*/