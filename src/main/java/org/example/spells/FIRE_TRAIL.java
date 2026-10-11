package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_APPLY_FIRE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIRE_TRAIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "fire_trail.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fire trail";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a trail of fiery particles";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_APPLY_FIRE()};
        this.spawnProbabilities = new SpawnProbabilities(0.4, 0.5, 0.3, 0.3, 0.2, 0, 0, 0, 0, 0, 0);
        this.price = 130;
        this.manaCost = 10;
        this.trailMaterial = "fire";
        this.trailMaterialAmount = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "FIRE_TRAIL",
	name 		= "$action_fire_trail",
	description = "$actiondesc_fire_trail",
	sprite 		= "data/ui_gfx/gun_actions/fire_trail.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/fire_trail_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "0,1,2,3,4", -- FIRE_TRAIL
	spawn_probability                 = "0.4,0.5,0.3,0.3,0.2", -- FIRE_TRAIL
	price = 130,
	mana = 10,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/fire_trail.xml",
	action 		= function()
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_apply_on_fire.xml,"
		c.trail_material = c.trail_material .. "fire,"
		c.trail_material_amount = c.trail_material_amount + 10
		draw_actions( 1, true )
	end,
}*/