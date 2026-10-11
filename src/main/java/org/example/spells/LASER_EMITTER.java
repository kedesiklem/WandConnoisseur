package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LASER_EMITTER;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_DISINTEGRATED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LASER_EMITTER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "laser_emitter.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "plasma"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "An instantaneous, dangerous beam of light";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LASER_EMITTER();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_DISINTEGRATED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.8, 1, 0.5, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 60;
        this.castDelay = 6;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LASER_EMITTER",
	name 		= "$action_laser_emitter",
	description = "$actiondesc_laser_emitter",
	sprite 		= "data/ui_gfx/gun_actions/laser_emitter.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/laser_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/orb_laseremitter.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- LASER
	spawn_probability                 = "0.2,0.8,1,0.5", -- LASER
	price = 180,
	mana = 60,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/orb_laseremitter.xml")
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
		c.fire_rate_wait = c.fire_rate_wait + 6
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_disintegrated.xml,"
	end,
}*/