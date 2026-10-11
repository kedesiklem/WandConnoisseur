package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MANA_REDUCE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "mana.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Add Mana";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "mana"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Immediately adds 30 mana to the wand";
        this.type = Spell.SpellType.modifier;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.7, 0.9, 1, 1, 1, 1, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = -30;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "MANA_REDUCE",
	name 		= "$action_mana_reduce",
	description = "$actiondesc_mana_reduce",
	sprite 		= "data/ui_gfx/gun_actions/mana.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5,6", -- MANA_REDUCE
	spawn_probability                 = "0.7,0.9,1,1,1,1", -- MANA_REDUCE
	price = 250,
	mana = -30,
	--max_uses = 150,
	custom_xml_file = "data/entities/misc/custom_cards/mana_reduce.xml",
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/