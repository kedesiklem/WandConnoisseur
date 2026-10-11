package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_MONEY_MAGIC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MONEY_MAGIC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "golden_punch.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Gold to Power";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "gtp", "g2p"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Spends 5% of your current gold and adds damage to a projectile proportional to the amount spent";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_MONEY_MAGIC()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.2, 0, 0.8, 0.3, 0, 0, 0, 0.5);
        this.price = 200;
        this.manaCost = 30;
        this.autoStat = false;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        int gold = Global.getPlayer().getGold();
        int damage = Math.min((int)Math.floor(gold*0.05), 24000);

        if(damage > 1){
            damage = Math.max(damage, 10);
            if(!Global.getPlayer().payGold(damage)){
                return;
            }
            castState.addScript(this.relatedScripts);

            DamageComponent damageComponent = new DamageComponent();
            if(damage < 120){
                damageComponent.setDamage(damage, DamageType.PROJECTILE);
            }else if(damage < 300){
                damageComponent.setDamage(damage*5.0/7.0, DamageType.PROJECTILE);
            }else if(damage < 500){
                damageComponent.setDamage(damage*5.0/9.0, DamageType.PROJECTILE);
            }else{
                damageComponent.setDamage(damage*5.0/11.0, DamageType.PROJECTILE);
            }
            castState.addDamageComponent(damageComponent);
        }

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "MONEY_MAGIC",
	name 		= "$action_money_magic",
	description = "$actiondesc_money_magic",
	sprite 		= "data/ui_gfx/gun_actions/golden_punch.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	related_extra_entities = { "data/entities/particles/gold_sparks.xml" },
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "3,5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.2,0.8,0.3,0.5", -- MANA_REDUCE
	price = 200,
	mana = 30,
	custom_xml_file = "data/entities/misc/custom_cards/money_magic.xml",
	action 		= function()
		local entity_id = GetUpdatedEntityID()

		local dcomp = EntityGetFirstComponent( entity_id, "WalletComponent" )

		if ( dcomp ~= nil ) then
			local money = ComponentGetValue2( dcomp, "money" )
			local moneyspent = ComponentGetValue2( dcomp, "money_spent" )
			local damage = math.min( math.floor( money * 0.05 ), 24000 )

			if ( damage > 1 ) and ( money >= 10 ) then
				damage = math.max( damage, 10 )

				c.extra_entities = c.extra_entities .. "data/entities/particles/gold_sparks.xml,"

				money = money - damage
				moneyspent = moneyspent + damage
				ComponentSetValue2( dcomp, "money", money )
				ComponentSetValue2( dcomp, "money_spent", moneyspent )

				-- print( "Spent " .. tostring( damage ) )

				if ( damage < 120 ) then
					c.damage_projectile_add = c.damage_projectile_add + ( damage / 25 )
				elseif ( damage < 300 ) then
					c.damage_projectile_add = c.damage_projectile_add + ( damage / 35 )
				elseif ( damage < 500 ) then
					c.damage_projectile_add = c.damage_projectile_add + ( damage / 45 )
				else
					c.damage_projectile_add = c.damage_projectile_add + ( damage / 55 )
				end
			end
		end

		draw_actions( 1, true )
	end,
}*/