package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MATERIAL_WATER;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_APPLY_WET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MATERIAL_WATER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "material_water.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Water";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "wataa"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmute drops of water from nothing";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MATERIAL_WATER();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_APPLY_WET()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 0;
        this.castDelay = -15;
        this.rechargeTime = -10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MATERIAL_WATER",
	name 		= "$action_material_water",
	description = "$actiondesc_material_water",
	sprite 		= "data/ui_gfx/gun_actions/material_water.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/material_water_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/material_water.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5", -- MATERIAL_WATER
	spawn_probability                 = "0.4,0.4,0.4,0.4,0.4", -- MATERIAL_WATER
	price = 110,
	mana = 0,
	sound_loop_tag = "sound_spray",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/material_water.xml")
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_apply_wet.xml,"
		c.fire_rate_wait = c.fire_rate_wait - 15
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the cement reload time back to 0
	end,
}*/