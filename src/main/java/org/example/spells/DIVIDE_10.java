package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DIVIDE_10 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "divide_10.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);
    private int lastIteration = 0;

    @Override
    protected void initialization(){
        this.name = "Divide By 10";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "d10"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts the next spell 10 times, but with reduced damage";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 400;
        this.manaCost = 200;
        this.autoStat = false;
        this.castDelay = 80;
        this.rechargeTime = 20;
        this.hasCharges = true;
        this.maxCharges = 5;
        this.damageComponent.setDamage(-37.5, DamageType.PROJECTILE);
        this.pattern = 5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell currentSpell = null;
        int iter_max = iterationLevel;
        int imax = 0;
        int count = 10;
        int iterationLimit = 2;
        boolean copyFailed = false;
        int currentCastDelay = 0;
        int currentRechargeTime = 0;

        castState.addCastDelay(this.castDelay);
        cardPool.addRechargeTime(this.rechargeTime);

        currentSpell = cardPool.getDeckSpell(iterationLevel - 1);
        if(iterationLevel > iterationLimit){
            count = 1;
        }

        if(currentSpell != null && (!currentSpell.hasCharges || currentSpell.chargesLeft > 0)){
            if(currentSpell.recursive && recursionLevel + 1 > recursionLimit){
                copyFailed = true;
            }
            currentCastDelay = castState.getCastDelay();
            currentRechargeTime = cardPool.getRechargeTime();

            for(int i=0; i < count; i++){
                if(i == 0 && !copyFailed){
                    cardPool.disableDraw();
                }
                copy(cardPool, castState, currentSpell, recursionLevel, iterationLevel + 1, false);
                imax = currentSpell.getLastIteration();
                if(!copyFailed){
                    cardPool.enableDraw();
                }
                if(imax > 0){
                    iter_max = imax;
                }
            }
            currentSpell.removeCharge();

            if(iterationLevel == 1 && !copyFailed){
                castState.setCastDelay(currentCastDelay);
                cardPool.setRechargeTime(currentRechargeTime);
                cardPool.discardNext(iter_max);
            }
        }

        castState.addDamageComponent(this.damageComponent);
        /*c.explosion_radius = c.explosion_radius - 40.0
        if (c.explosion_radius < 0) then
            c.explosion_radius = 0
        end*/
        castState.setPattern(this.pattern);
        this.lastIteration = iter_max;
    }

    @Override
    public int getLastIteration(){
        return this.lastIteration;
    }
}

/*{
	id          = "DIVIDE_10",
	name 		= "$action_divide_10",
	description = "$actiondesc_divide_10",
	sprite 		= "data/ui_gfx/gun_actions/divide_10.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_divide",
	type 		= ACTION_TYPE_OTHER,
	spawn_level                       = "10", -- MANA_REDUCE
	spawn_probability                 = "1", -- MANA_REDUCE
	price = 400,
	mana = 200,
	max_uses = 5,
	action 		= function( recursion_level, iteration )
		c.fire_rate_wait = c.fire_rate_wait + 80
		current_reload_time = current_reload_time + 20

		local data = {}

		local iter = iteration or 1
		local iter_max = iteration or 1

		if ( #deck > 0 ) then
			data = deck[iter] or nil
		else
			data = nil
		end

		local count = 10
		if ( iter >= 3 ) then
			count = 1
		end

		local rec = check_recursion( data, recursion_level )

		if ( data ~= nil ) and ( rec > -1 ) and ( ( data.uses_remaining == nil ) or ( data.uses_remaining ~= 0 ) ) then
			local firerate = c.fire_rate_wait
			local reload = current_reload_time

			for i=1,count do
				if ( i == 1 ) then
					dont_draw_actions = true
				end
				local imax = data.action( rec, iter + 1 )
				dont_draw_actions = false
				if (imax ~= nil) then
					iter_max = imax
				end
			end

			if ( data.uses_remaining ~= nil ) and ( data.uses_remaining > 0 ) then
				data.uses_remaining = data.uses_remaining - 1

				local reduce_uses = ActionUsesRemainingChanged( data.inventoryitem_id, data.uses_remaining )
				if not reduce_uses then
					data.uses_remaining = data.uses_remaining + 1 -- cancel the reduction
				end
			end

			if (iter == 1) then
				c.fire_rate_wait = firerate
				current_reload_time = reload

				for i=1,iter_max do
					if (#deck > 0) then
						local d = deck[1]
						table.insert( discarded, d )
						table.remove( deck, 1 )
					end
				end
			end
		end

		c.damage_projectile_add = c.damage_projectile_add - 1.5
		c.explosion_radius = c.explosion_radius - 40.0
		if (c.explosion_radius < 0) then
			c.explosion_radius = 0
		end

		c.pattern_degrees = 5

		return iter_max
	end,
}*/