package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BLOOD_SPARKS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BLOOD_MAGIC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "blood_magic.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Blood magic";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Reduces a spell's mana cost and recharge time greatly, at the costs of four health points";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BLOOD_SPARKS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.3, 0.7, 0, 0, 0, 0.5);
        this.price = 150;
        this.manaCost = -100;
        this.castDelay = -20;
        this.rechargeTime = -20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
        HealthComponent healthComponent = Global.getPlayer().getHealthComponent();
        healthComponent.trueDamage(Math.min(4.0, healthComponent.getHp() - 1.0));
    }
}

/*{
	id          = "BLOOD_MAGIC",
	name 		= "$action_blood_magic",
	description = "$actiondesc_blood_magic",
	sprite 		= "data/ui_gfx/gun_actions/blood_magic.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	related_extra_entities = { "data/entities/particles/blood_sparks.xml" },
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.3,0.7,0.5", -- MANA_REDUCE
	price = 150,
	mana = -100,
	custom_xml_file = "data/entities/misc/custom_cards/blood_magic.xml",
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/particles/blood_sparks.xml,"
		c.fire_rate_wait = c.fire_rate_wait - 20
		current_reload_time = current_reload_time - 20
		draw_actions( 1, true )

		local entity_id = GetUpdatedEntityID()

		local dcomps = EntityGetComponent( entity_id, "DamageModelComponent" )

		if ( dcomps ~= nil ) and ( #dcomps > 0 ) then
			for a,b in ipairs( dcomps ) do
				local hp = ComponentGetValue2( b, "hp" )
				hp = math.max( hp - 0.16, 0.04 )
				ComponentSetValue2( b, "hp", hp )
			end
		end
	end,
}*/