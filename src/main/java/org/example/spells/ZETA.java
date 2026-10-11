package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ZETA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "zeta.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Zeta";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Copies a random spell in another wand you're holding";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0, 0.4, 0, 0, 0, 0, 0.5);
        this.recursive = true;
        this.price = 200;
        this.manaCost = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Wand wand = Global.getLastWand();
        Spell[] spells = wand == null ? new Spell[0] : wand.getSpells(false);

        if(spells.length == 0){
            cardPool.draw(1, true, castState);
            return;
        }

        RandomGenerator random = Global.getRandomGenerator();
        random.setSeed(Global.getPlayer().getX() + Global.getCurrentFrame(), Global.getPlayer().getY() + 251);

        copyDrawDisabled(cardPool, castState, spells[random.random(0, spells.length - 1)], recursionLevel);
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ZETA",
	name 		= "$action_zeta",
	description = "$actiondesc_zeta",
	sprite 		= "data/ui_gfx/gun_actions/zeta.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_duplicate",
	type 		= ACTION_TYPE_OTHER,
	spawn_manual_unlock = true,
	recursive	= true,
	spawn_level                       = "2,5,10", -- MANA_REDUCE
	spawn_probability                 = "0.2,0.4,0.5", -- MANA_REDUCE
	price = 200,
	mana = 10,
	action 		= function( recursion_level, iteration )
		local entity_id = GetUpdatedEntityID()
		local x, y = EntityGetTransform( entity_id )
		local options = {}

		local children = EntityGetAllChildren( entity_id )
		local inventory = EntityGetFirstComponent( entity_id, "Inventory2Component" )

		if ( children ~= nil ) and ( inventory ~= nil ) then
			local active_wand = ComponentGetValue2( inventory, "mActiveItem" )

			for i,child_id in ipairs( children ) do
				if ( EntityGetName( child_id ) == "inventory_quick" ) then
					local wands = EntityGetAllChildren( child_id )

					if ( wands ~= nil ) then
						for k,wand_id in ipairs( wands ) do
							if ( wand_id ~= active_wand ) and EntityHasTag( wand_id, "wand" ) then
								local spells = EntityGetAllChildren( wand_id )

								if ( spells ~= nil ) then
									for j,spell_id in ipairs( spells ) do
										local comp = EntityGetFirstComponentIncludingDisabled( spell_id, "ItemActionComponent" )

										if ( comp ~= nil ) then
											local action_id = ComponentGetValue2( comp, "action_id" )

											table.insert( options, action_id )
										end
									end
								end
							end
						end
					end
				end
			end
		end

		if ( #options > 0 ) then
			SetRandomSeed( x + GameGetFrameNum(), y + 251 )

			local rnd = Random( 1, #options )
			local action_id = options[rnd]

			for i,data in ipairs( actions ) do
				if ( data.id == action_id ) then
					local rec = check_recursion( data, recursion_level )
					if ( rec > -1 ) then
						dont_draw_actions = true
						data.action( rec )
						dont_draw_actions = false
					end
					break
				end
			end
		end

		draw_actions( 1, true )
	end,
}*/