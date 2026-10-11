package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_BURN_TRAIL;
import org.example.script.SCRIPT_EFFECT_APPLY_FIRE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BURN_TRAIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "burn_trail.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Burning trail";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a tail of fire";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_BURN_TRAIL(), new SCRIPT_EFFECT_APPLY_FIRE()};
        this.spawnProbabilities = new SpawnProbabilities(0.4, 0.3, 0.3, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BURN_TRAIL",
	name 		= "$action_burn_trail",
	description = "$actiondesc_burn_trail",
	sprite 		= "data/ui_gfx/gun_actions/burn_trail.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/burn_trail_unidentified.png",
	related_extra_entities = { "data/entities/misc/burn.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "0,1,2", -- BURN_TRAIL
	spawn_probability                 = "0.4,0.3,0.3", -- BURN_TRAIL
	price = 100,
	mana = 5,
	--max_uses = 120,
	custom_xml_file = "data/entities/misc/custom_cards/burn_trail.xml",
	action 		= function()
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_apply_on_fire.xml,"
		c.extra_entities = c.extra_entities .. "data/entities/misc/burn.xml,"
		draw_actions( 1, true )
	end,
}*/