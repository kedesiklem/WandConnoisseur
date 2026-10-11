package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BURST_8 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "burst_8.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Octuple Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "octo", "octo cast", "octo spell", "octuple", "octuple cast"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts 8 spells";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0, 0, 0, 0.5);
        this.price = 300;
        this.manaCost = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(8, true, castState);
    }
}

/*{
	id          = "BURST_8",
	name 		= "$action_burst_8",
	description = "$actiondesc_burst_8",
	sprite 		= "data/ui_gfx/gun_actions/burst_8.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/burst_4_unidentified.png",
	spawn_requires_flag = "card_unlocked_musicbox",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "5,6,10", -- BURST_4
	spawn_probability                 = "0.1,0.1,0.5", -- BURST_4
	price = 300,
	mana = 30,
	--max_uses = 100,
	action 		= function()
		draw_actions( 8, true )
	end,
}*/