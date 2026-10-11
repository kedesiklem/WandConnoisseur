package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DRAW_RANDOM_X3 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "draw_random_x3.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Copy random spell thrice";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "cr3"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts a random spell among the spells in your wand three times!";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.1, 0.3, 0.1, 0.1, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 250;
        this.manaCost = 50;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        int dataSize = cardPool.getDeckSize() + cardPool.getDiscardSize();

        if(dataSize == 0){
            return;
        }

        Spell currentSpell;
        long frame = Global.getCurrentFrame();
        RandomGenerator random = Global.getRandomGenerator();
        random.setSeed(frame + cardPool.getDeckSize(), frame - 325 + cardPool.getDiscardSize());
        int id = random.random(0, dataSize - 1);
        int checks = 0;

        while(checks < dataSize){
            currentSpell = id < cardPool.getDeckSize() ? cardPool.getDeckSpell(id) : cardPool.getDiscardSpell(id - cardPool.getDeckSize());

            if(recursionLevel + (currentSpell.recursive ? 1 : 0) < recursionLimit && (!currentSpell.getHasCharges() || currentSpell.getChargesLeft() > 0)){
                for(int i=0; i < 3; i++){
                    copy(cardPool, castState, currentSpell, recursionLevel);
                }
                currentSpell.removeCharge();
                return;
            }

            id = (id + 1) % dataSize;
            checks++;
        }
    }
}

/*{
	id          = "DRAW_RANDOM_X3",
	name 		= "$action_draw_random_x3",
	description = "$actiondesc_draw_random_x3",
	sprite 		= "data/ui_gfx/gun_actions/draw_random_x3.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_pyramid",
	type 		= ACTION_TYPE_OTHER,
	recursive	= true,
	spawn_level                       = "3,4,5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.1,0.3,0.1,0.1,1", -- MANA_REDUCE
	price = 250,
	mana = 50,
	action 		= function( recursion_level, iteration )
		SetRandomSeed( GameGetFrameNum() + #deck, GameGetFrameNum() - 325 + #discarded )
		local datasize = #deck + #discarded
		local rnd = Random( 1, datasize )

		local data = {}

		if ( rnd <= #deck ) then
			data = deck[rnd]
		else
			data = discarded[rnd - #deck]
		end

		local checks = 0
		local rec = check_recursion( data, recursion_level )

		while ( data ~= nil ) and ( ( rec == -1 ) or ( ( data.uses_remaining ~= nil ) and ( data.uses_remaining == 0 ) ) ) and ( checks < datasize ) do
			rnd = ( rnd % datasize ) + 1
			checks = checks + 1

			if ( rnd <= #deck ) then
				data = deck[rnd]
			else
				data = discarded[rnd - #deck]
			end

			rec = check_recursion( data, recursion_level )
		end

		if ( data ~= nil ) and ( rec > -1 ) and ( ( data.uses_remaining == nil ) or ( data.uses_remaining ~= 0 ) ) then
			for i=1,3 do
				data.action( rec )
			end

			if ( data.uses_remaining ~= nil ) and ( data.uses_remaining > 0 ) then
				data.uses_remaining = data.uses_remaining - 1

				local reduce_uses = ActionUsesRemainingChanged( data.inventoryitem_id, data.uses_remaining )
				if not reduce_uses then
					data.uses_remaining = data.uses_remaining + 1 -- cancel the reduction
				end
			end
		end
	end,
}*/