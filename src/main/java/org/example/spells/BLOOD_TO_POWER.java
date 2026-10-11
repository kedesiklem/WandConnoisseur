package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import static org.example.main.Global.DamageTypeDoublePair.of;
import org.example.script.Script;
import org.example.script.SCRIPT_BLOOD_SPARKS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BLOOD_TO_POWER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "blood_punch.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Blood to Power";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "btp", "b2p"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile gains additional damage at the cost of 20% of your health";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BLOOD_SPARKS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0, 0.8, 0.2, 0, 0, 0, 0.5);
        this.price = 150;
        this.manaCost = 20;
        this.autoStat = false;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        HealthComponent healthComponent = Global.getPlayer().getHealthComponent();
        double hp = healthComponent.getHp();
        double damage = Math.min(hp*0.44, 24000.0);
        double selfDamage = hp*0.2;

        if(selfDamage > 5.0){
            castState.addScript(this.relatedScripts);
            healthComponent.damage(new DamageComponent(of(selfDamage, DamageType.CURSE)));
            castState.getDamageComponent().add(damage, DamageType.PROJECTILE);
        }

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BLOOD_TO_POWER",
	name 		= "$action_blood_to_power",
	description = "$actiondesc_blood_to_power",
	sprite 		= "data/ui_gfx/gun_actions/blood_punch.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	related_extra_entities = { "data/entities/particles/blood_sparks.xml" },
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "2,5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.2,0.8,0.2,0.5", -- MANA_REDUCE
	price = 150,
	mana = 20,
	custom_xml_file = "data/entities/misc/custom_cards/blood_to_power.xml",
	action 		= function()
		local entity_id = GetUpdatedEntityID()

		local dcomp = EntityGetFirstComponent( entity_id, "DamageModelComponent" )

		if ( dcomp ~= nil ) then
			local hp = ComponentGetValue2( dcomp, "hp" )
			local damage = math.min( hp * 0.44, 960 )
			local self_damage = hp * 0.2

			if ( hp >= 0.4 ) and ( self_damage > 0.2 ) then
				c.extra_entities = c.extra_entities .. "data/entities/particles/blood_sparks.xml,"

				EntityInflictDamage( entity_id, self_damage, "DAMAGE_CURSE", "$action_blood_to_power", "NONE", 0, 0, entity_id )

				-- print( "Spent " .. tostring( damage ) )

				c.damage_projectile_add = c.damage_projectile_add + damage
			end
		end

		draw_actions( 1, true )
	end,
}*/