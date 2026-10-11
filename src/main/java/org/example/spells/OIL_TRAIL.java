package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_APPLY_OILED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class OIL_TRAIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "oil_trail.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Oil trail";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a trail of oil";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_APPLY_OILED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 10;
        this.trailMaterial = "oil";
        this.trailMaterialAmount = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "OIL_TRAIL",
	name 		= "$action_oil_trail",
	description = "$actiondesc_oil_trail",
	sprite 		= "data/ui_gfx/gun_actions/oil_trail.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/oil_trail_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- OIL_TRAIL
	spawn_probability                 = "0.3,0.3,0.3", -- OIL_TRAIL
	price = 160,
	mana = 10,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/oil_trail.xml",
	action 		= function()
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_apply_oiled.xml,"
		c.trail_material = c.trail_material .. "oil,"
		c.trail_material_amount = c.trail_material_amount + 20
		draw_actions( 1, true )
	end,
}*/