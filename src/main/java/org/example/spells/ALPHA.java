package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ALPHA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "alpha.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Alpha";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts a copy of the first spell in your wand";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.2, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 200;
        this.manaCost = 40;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell copiedSpell;

        if(cardPool.getDiscardSize() > 0){
            copiedSpell = cardPool.getDiscardSpell(0);
        }else if(cardPool.getHandSize() > 0){
            copiedSpell = cardPool.getHandSpell(0);
        }else{
            copiedSpell = null;
        }

        copy(cardPool, castState, copiedSpell, recursionLevel);
    }
}

/*{
	id          = "ALPHA",
	name 		= "$action_alpha",
	description = "$actiondesc_alpha",
	sprite 		= "data/ui_gfx/gun_actions/alpha.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_duplicate",
	type 		= ACTION_TYPE_OTHER,
	recursive	= true,
	spawn_level                       = "5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.1,0.2,1", -- MANA_REDUCE
	price = 200,
	mana = 40,
	action 		= function( recursion_level, iteration )
		c.fire_rate_wait = c.fire_rate_wait + 15

		local data = {}

		if ( #discarded > 0 ) then
			data = discarded[1]
		elseif ( #hand > 0 ) then
			data = hand[1]
		elseif ( #deck > 0 ) then
			data = deck[1]
		else
			data = nil
		end

		local rec = check_recursion( data, recursion_level )

		if ( data ~= nil ) and ( rec > -1 ) then
			data.action( rec )
		end

		--draw_actions( 1, true )
	end,
}*/