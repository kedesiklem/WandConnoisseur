package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TORCH extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.passif;
    static final String staticImageFile = "torch.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Torch";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Lights your wand right up!";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(1, 0.6, 0.5, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "TORCH",
	name 		= "$action_torch",
	description = "$actiondesc_torch",
	sprite 		= "data/ui_gfx/gun_actions/torch.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/torch_unidentified.png",
	type 		= ACTION_TYPE_PASSIVE,
	spawn_level                       = "0,1,2", -- TORCH
	spawn_probability                 = "1,0.6,0.5", -- TORCH
	price = 100,
	mana = 0,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/torch.xml",
	action 		= function()
		draw_actions( 1, true )
	end,
}*/