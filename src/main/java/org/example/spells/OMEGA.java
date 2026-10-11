package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class OMEGA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "omega.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Omega";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts copies of every spell in your wand";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 600;
        this.manaCost = 320;
        this.castDelay = 50;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell currentSpell;
        List<Spell> currentPool;

        currentPool = cardPool.getDiscardList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(currentSpell.getClass() != RESET.class){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }

        currentPool = cardPool.getHandList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(!currentSpell.recursive){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }

        currentPool = cardPool.getDeckList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(currentSpell.getClass() != RESET.class){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }
    }
}

/*{
	id          = "OMEGA",
	name 		= "$action_omega",
	description = "$actiondesc_omega",
	sprite 		= "data/ui_gfx/gun_actions/omega.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_duplicate",
	type 		= ACTION_TYPE_OTHER,
	recursive	= true,
	spawn_level                       = "5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.1,0.1,1", -- MANA_REDUCE
	price = 600,
	mana = 320,
	action 		= function( recursion_level, iteration )
		c.fire_rate_wait = c.fire_rate_wait + 50

		if ( discarded ~= nil ) then
			for i,data in ipairs( discarded ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( rec > -1 ) and ( data.id ~= "RESET" ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end

		if ( hand ~= nil ) then
			for i,data in ipairs( hand ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( ( data.recursive == nil ) or ( data.recursive == false ) ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end

		if ( deck ~= nil ) then
			for i,data in ipairs( deck ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( rec > -1 ) and ( data.id ~= "RESET" ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end
	end,
}*/