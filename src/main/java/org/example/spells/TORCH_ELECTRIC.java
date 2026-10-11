package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TORCH_ELECTRIC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.passif;
    static final String staticImageFile = "torch_electric.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Electric Torch";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "elec torch", "torch elec"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives your wand a bright but very dangerous light!";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0.8, 0.6, 0.4, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "TORCH_ELECTRIC",
	name 		= "$action_torch_electric",
	description = "$actiondesc_torch_electric",
	sprite 		= "data/ui_gfx/gun_actions/torch_electric.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/torch_unidentified.png",
	type 		= ACTION_TYPE_PASSIVE,
	spawn_level                       = "0,1,2", -- TORCH_ELECTRIC
	spawn_probability                 = "0.8,0.6,0.4", -- TORCH_ELECTRIC
	price = 150,
	mana = 0,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/torch_electric.xml",
	action 		= function()
		draw_actions( 1, true )
	end,
}*/