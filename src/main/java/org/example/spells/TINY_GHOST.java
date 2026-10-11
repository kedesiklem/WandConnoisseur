package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TINY_GHOST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.passif;
    static final String staticImageFile = "tiny_ghost.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Tiny Ghost";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "ghost"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a tiny ethereal being to your help";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0.5, 1, 0.8, 0.7, 0.5, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "TINY_GHOST",
	name 		= "$action_tiny_ghost",
	description = "$actiondesc_tiny_ghost",
	sprite 		= "data/ui_gfx/gun_actions/tiny_ghost.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/torch_unidentified.png",
	type 		= ACTION_TYPE_PASSIVE,
	spawn_level                       = "1,2,3,4,5,6", -- TINY_GHOST
	spawn_probability                 = "0.1,0.5,1,0.8,0.7,0.5", -- TINY_GHOST
	price = 160,
	mana = 0,
	custom_xml_file = "data/entities/misc/custom_cards/tiny_ghost.xml",
	action 		= function()
		draw_actions( 1, true )
	end,
}*/