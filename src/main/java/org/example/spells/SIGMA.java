package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class SIGMA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "sigma.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sigma";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "sigma balls"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Copies every static projectile -type spell in the wand when cast";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0.1, 0.2, 0, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 500;
        this.manaCost = 120;
        this.castDelay = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell currentSpell;
        List<Spell> currentPool;
        int currentCastDelay = castState.getCastDelay();
        int currentRechargeTime = cardPool.getRechargeTime();
        int currentMana = cardPool.getManaUsage();

        currentPool = cardPool.getDiscardList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(currentSpell.type == SpellType.static_projectile){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }

        currentPool = cardPool.getHandList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(currentSpell.type == SpellType.static_projectile){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }

        currentPool = cardPool.getDeckList();
        for(int i=0; i < currentPool.size(); i++){
            currentSpell = currentPool.get(i);
            if(currentSpell.type == SpellType.static_projectile){
                copyDrawDisabled(cardPool, castState, currentSpell, recursionLevel);
            }
        }

        castState.setCastDelay(currentCastDelay);
        cardPool.setRechargeTime(currentRechargeTime);
        cardPool.setManaUsage(currentMana);

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "SIGMA",
	name 		= "$action_sigma",
	description = "$actiondesc_sigma",
	sprite 		= "data/ui_gfx/gun_actions/sigma.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_duplicate",
	type 		= ACTION_TYPE_OTHER,
	recursive	= true,
	spawn_level                       = "4,5,10", -- MANA_REDUCE
	spawn_probability                 = "0.1,0.2,1", -- MANA_REDUCE
	price = 500,
	mana = 120,
	action 		= function( recursion_level, iteration )
		c.fire_rate_wait = c.fire_rate_wait + 30

		local firerate = c.fire_rate_wait
		local reload = current_reload_time
		local mana_ = mana

		if ( discarded ~= nil ) then
			for i,data in ipairs( discarded ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( data.type == 1 ) and ( rec > -1 ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end

		if ( hand ~= nil ) then
			for i,data in ipairs( hand ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( data.type == 1 ) and ( rec > -1 ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end

		if ( deck ~= nil ) then
			for i,data in ipairs( deck ) do
				local rec = check_recursion( data, recursion_level )
				if ( data ~= nil ) and ( data.type == 1 ) and ( rec > -1 ) then
					dont_draw_actions = true
					data.action( rec )
					dont_draw_actions = false
				end
			end
		end

		c.fire_rate_wait = firerate
		current_reload_time = reload
		mana = mana_

		draw_actions( 1, true )
	end,
}*/