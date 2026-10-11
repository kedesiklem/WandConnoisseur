package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_ELECTRIC_CHARGE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ELECTRIC_CHARGE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "electric_charge.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Electric charge";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "elec"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile an electric charge, that it will release on impact";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ELECTRIC_CHARGE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 0, 0.8, 0.7, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 8;
        this.damageComponent.setDamage(2.5, DamageType.ELECTRICITY);
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.lightning_count = c.lightning_count + 1
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ELECTRIC_CHARGE",
	name 		= "$action_electric_charge",
	description = "$actiondesc_electric_charge",
	sprite 		= "data/ui_gfx/gun_actions/electric_charge.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/particles/electricity.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5", -- ELECTRIC_CHARGE
	spawn_probability                 = "1,1,0.8,0.7", -- ELECTRIC_CHARGE
	price = 150,
	mana = 8,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/electric_charge.xml",
	action 		= function()
		c.lightning_count = c.lightning_count + 1
		c.damage_electricity_add = c.damage_electricity_add + 0.1
		c.extra_entities = c.extra_entities .. "data/entities/particles/electricity.xml,"
		draw_actions( 1, true )
	end,
}*/