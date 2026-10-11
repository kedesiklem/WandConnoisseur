package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class DUPLICATE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "duplicate.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spell Duplication";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "sd", "duplication"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Duplicates every spell cast before it";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.2, 0, 0, 0, 1);
        this.recursive = true;
        this.price = 250;
        this.manaCost = 250;
        this.autoStat = false;
        this.castDelay = 20;
        this.rechargeTime = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell currentSpell;
        List<Spell> currentHand = cardPool.getHandList();
        int savedHandSize = cardPool.getHandSize();

        for(int i=0; i < currentHand.size(); i++){
            currentSpell = currentHand.get(i);
            if(i < savedHandSize && currentSpell != null && currentSpell.getClass() != this.getClass()){
                copy(cardPool, castState, currentSpell, recursionLevel);
            }
        }
        castState.addCastDelay(this.castDelay);
        cardPool.addRechargeTime(this.rechargeTime);

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "DUPLICATE",
	name 		= "$action_duplicate",
	description = "$actiondesc_duplicate",
	sprite 		= "data/ui_gfx/gun_actions/duplicate.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_mestari",
	type 		= ACTION_TYPE_OTHER,
	recursive	= true,
	spawn_level                       = "5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.1,0.2,1", -- MANA_REDUCE
	price = 250,
	mana = 250,
	action 		= function( recursion_level, iteration )
		local hand_count = #hand

		for i,v in ipairs( hand ) do
			local rec = check_recursion( v, recursion_level )
			if ( v.id ~= "DUPLICATE" ) and ( i <= hand_count ) and ( rec > -1 ) then
				v.action( rec )
			end
		end

		c.fire_rate_wait = c.fire_rate_wait + 20
		current_reload_time = current_reload_time + 20

		draw_actions( 1, true )
	end,
}*/