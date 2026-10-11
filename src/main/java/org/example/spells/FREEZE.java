package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_FREEZE;
import org.example.script.SCRIPT_EFFECT_FROZEN;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FREEZE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "freeze.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Freeze charge";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a frozen charge, that it will release on impact";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_FREEZE(), new SCRIPT_EFFECT_FROZEN()};
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0, 1, 0.9, 0.8, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 10;
        this.damageComponent.setDamage(5.0, DamageType.ICE);
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "FREEZE",
	name 		= "$action_freeze",
	description = "$actiondesc_freeze",
	sprite 		= "data/ui_gfx/gun_actions/freeze.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_unidentified.png",
	related_extra_entities = { "data/entities/particles/freeze_charge.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- FREEZE
	spawn_probability                 = "1,1,0.9,0.8", -- FREEZE
	price = 140,
	mana = 10,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/freeze.xml",
	action 		= function()
		c.damage_ice_add = c.damage_ice_add + 0.2
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_frozen.xml,"
		c.extra_entities = c.extra_entities .. "data/entities/particles/freeze_charge.xml,"
		draw_actions( 1, true )
	end,
}*/