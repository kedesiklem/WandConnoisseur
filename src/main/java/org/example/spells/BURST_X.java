package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BURST_X extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "burst_x.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Myriad Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "myriad"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Simultaneously casts as many spells as you have left uncast in your wand";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0, 0, 0, 0.5);
        this.price = 500;
        this.manaCost = 50;
        this.hasCharges = true;
        this.maxCharges = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(cardPool.getDeckSize(), true, castState);
    }
}

/*{
	id          = "BURST_X",
	name 		= "$action_burst_x",
	description = "$actiondesc_burst_x",
	sprite 		= "data/ui_gfx/gun_actions/burst_x.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/burst_4_unidentified.png",
	spawn_requires_flag = "card_unlocked_musicbox",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "5,6,10", -- BURST_4
	spawn_probability                 = "0.1,0.1,0.5", -- BURST_4
	price = 500,
	mana = 50,
	max_uses = 30,
	action 		= function()
		if ( #deck > 0 ) then
			draw_actions( #deck, true )
		end
	end,
}*/