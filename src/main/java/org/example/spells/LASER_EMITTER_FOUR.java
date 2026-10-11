package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LASER_EMITTER;
import org.example.projectiles.PROJECTILE_LASER_EMITTER_FOUR;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_DISINTEGRATED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LASER_EMITTER_FOUR extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "laser_emitter_four.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam Cross";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "plasma cross"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Four deadly plasma beams in a cross-shape. Look out, they can hurt you as well!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LASER_EMITTER();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_DISINTEGRATED()};
        this.spawnProbabilities = new SpawnProbabilities(0.2, 0.3, 1, 0.5, 1, 0, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 80;
        this.castDelay = 15;
        this.recoil = 30.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // it's not the same as related projectile...
        castState.addProjectile(new PROJECTILE_LASER_EMITTER_FOUR());
    }
}

/*{
	id          = "LASER_EMITTER_FOUR",
	name 		= "$action_laser_emitter_four",
	description = "$actiondesc_laser_emitter_four",
	sprite 		= "data/ui_gfx/gun_actions/laser_emitter_four.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/laser_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/orb_laseremitter.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- LASER
	spawn_probability                 = "0.2,0.9,0.3,0.5,1", -- LASER
	price = 200,
	mana = 80,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/orb_laseremitter_four.xml")
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 30.0
		c.fire_rate_wait = c.fire_rate_wait + 15
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_disintegrated.xml,"
	end,
}*/