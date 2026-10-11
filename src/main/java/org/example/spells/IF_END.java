package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class IF_END extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "if_end.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);
    @Override
    protected void initialization(){
        this.name = "Requirement - Endpoint";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "req endpoint", "req end", "end point", "endpoint"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Any Requirement spells before this will skip all spells between them and this spell";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 10;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "IF_END",
	name 		= "$action_if_end",
	description = "$actiondesc_if_end",
	sprite 		= "data/ui_gfx/gun_actions/if_end.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_maths",
	type 		= ACTION_TYPE_OTHER,
	spawn_level                       = "10", -- MANA_REDUCE
	spawn_probability                 = "1", -- MANA_REDUCE
	price = 10,
	mana = 0,
	action 		= function( recursion_level, iteration )
		draw_actions( 1, true )
	end,
}*/