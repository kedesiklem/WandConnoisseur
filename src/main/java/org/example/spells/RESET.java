package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class RESET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "reset.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Wand Refresh";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "wr"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Reloads the wand immediately";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 120;
        this.manaCost = 20;
        this.rechargeTime = -25;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.copyHandToDiscard();
        cardPool.copyDeckToDiscard();
        cardPool.handFullClear();
        cardPool.deckFullClear();

        if(!cardPool.getForceStopDraws()){
            cardPool.setForceStopDraws(true);
            cardPool.refreshWrap();
        }
    }
}

/*{
	id          = "RESET",
	name 		= "$action_reset",
	description = "$actiondesc_reset",
	sprite 		= "data/ui_gfx/gun_actions/reset.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	spawn_requires_flag = "card_unlocked_mestari",
	type 		= ACTION_TYPE_UTILITY,
	recursive	= true,
	spawn_level                       = "10", -- BOMB
	spawn_probability                 = "1", -- BOMB
	price = 120,
	mana = 20,
	action 		= function()
		current_reload_time = current_reload_time - 25

		for i,v in ipairs( hand ) do
			-- print( "removed " .. v.id .. " from hand" )
			table.insert( discarded, v )
		end

		for i,v in ipairs( deck ) do
			-- print( "removed " .. v.id .. " from deck" )
			table.insert( discarded, v )
		end

		hand = {}
		deck = {}

		if ( force_stop_draws == false ) then
			force_stop_draws = true
			move_discarded_to_deck()
			order_deck()
		end
	end,
}*/