package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_FIREBALL_RAY_ENEMY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIREBALL_RAY_ENEMY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "fireball_ray_enemy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Personal fireball thrower";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "personal fireball"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile turn the creatures it hits into living fireball throwers";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_FIREBALL_RAY_ENEMY()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.6, 0, 0.4, 0.3, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "FIREBALL_RAY_ENEMY",
	name 		= "$action_fireball_ray_enemy",
	description = "$actiondesc_fireball_ray_enemy",
	sprite 		= "data/ui_gfx/gun_actions/fireball_ray_enemy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_fireball_ray_enemy.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5", -- FIREBALL_RAY_ENEMY
	spawn_probability                 = "0.5,0.6,0.4,0.3", -- FIREBALL_RAY_ENEMY
	price = 100,
	mana = 90,
	max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_fireball_ray_enemy.xml,"
		draw_actions( 1, true )
	end,
}*/