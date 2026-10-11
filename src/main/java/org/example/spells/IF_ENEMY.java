package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class IF_ENEMY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "if_enemy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Requirement - Enemies";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "requirement enemies", "requirement enemy", "req enemy", "req enemies", "req ennemies", "req ennemy"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The next spell is skipped if there are less than 15 enemies nearby";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 100;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell currentSpell;
        List<Spell> currentDeck = cardPool.getDeckList();
        int endId = -1;
        int elseId = -1;
        int envelope_min = 0;
        int envelope_max = 0;
        boolean skip = Global.getEnemyCount() < 15;

        for(int i=0; i < currentDeck.size(); i++){
            currentSpell = currentDeck.get(i);
            if(currentSpell.getClass() == IF_ELSE.class){
                endId = i;
                elseId = i;
            }else if(currentSpell.getClass() == IF_END.class){
                endId = i;
                break;
            }else if(currentSpell.getClass().getSimpleName().startsWith("IF_")){
                endId = -1;
                break;
            }
        }

        if(skip){
            if(elseId >= 0){
                envelope_max = elseId;
            }else if(endId >= 0){
                envelope_max = endId;
            }
            cardPool.discardId(envelope_min, envelope_max);
        }else if(elseId >= 0){
            envelope_min = elseId;
            envelope_max = endId >= 0 ? endId : cardPool.getDeckSize() - 1;
            cardPool.discardId(envelope_min, envelope_max);
        }

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "IF_ENEMY",
	name 		= "$action_if_enemy",
	description = "$actiondesc_if_enemy",
	sprite 		= "data/ui_gfx/gun_actions/if_enemy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_maths",
	type 		= ACTION_TYPE_OTHER,
	spawn_level                       = "10", -- MANA_REDUCE
	spawn_probability                 = "1", -- MANA_REDUCE
	price = 100,
	mana = 0,
	action 		= function( recursion_level, iteration )
		local endpoint = -1
		local elsepoint = -1
		local x,y = EntityGetTransform( GetUpdatedEntityID() )
		local enemies = EntityGetInRadiusWithTag( x, y, 240, "homing_target" )

		local doskip = false
		if ( #enemies < 15 ) then
			doskip = true
		end

		if ( #deck > 0 ) then
			for i,v in ipairs( deck ) do
				if ( v ~= nil ) then
					if ( string.sub( v.id, 1, 3 ) == "IF_" ) and ( v.id ~= "IF_END" ) and ( v.id ~= "IF_ELSE" ) then
						endpoint = -1
						break
					end

					if ( v.id == "IF_ELSE" ) then
						endpoint = i
						elsepoint = i
					end

					if ( v.id == "IF_END" ) then
						endpoint = i
						break
					end
				end
			end

			local envelope_min = 1
			local envelope_max = 1

			if doskip then
				if ( elsepoint > 0 ) then
					envelope_max = elsepoint
				elseif ( endpoint > 0 ) then
					envelope_max = endpoint
				end

				for i=envelope_min,envelope_max do
					local v = deck[envelope_min]

					if ( v ~= nil ) then
						table.insert( discarded, v )
						table.remove( deck, envelope_min )
					end
				end
			else
				if ( elsepoint > 0 ) then
					envelope_min = elsepoint

					if ( endpoint > 0 ) then
						envelope_max = endpoint
					else
						envelope_max = #deck
					end

					for i=envelope_min,envelope_max do
						local v = deck[envelope_min]

						if ( v ~= nil ) then
							table.insert( discarded, v )
							table.remove( deck, envelope_min )
						end
					end
				end
			end
		end

		draw_actions( 1, true )
	end,
}*/