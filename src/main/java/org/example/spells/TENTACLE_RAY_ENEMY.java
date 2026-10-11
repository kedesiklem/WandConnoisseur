package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_TENTACLE_RAY_ENEMY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TENTACLE_RAY_ENEMY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "tentacle_ray_enemy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Personal tentacler";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes creatures hit by a projectile grow tentacles in a chaotic manner";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_TENTACLE_RAY_ENEMY()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.4, 0.5, 0.4, 0, 0, 0, 0, 0);
        this.price = 150;
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
	id          = "TENTACLE_RAY_ENEMY",
	name 		= "$action_tentacle_ray_enemy",
	description = "$actiondesc_tentacle_ray_enemy",
	sprite 		= "data/ui_gfx/gun_actions/tentacle_ray_enemy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_tentacle_ray_enemy.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- TENTACLE_RAY_ENEMY
	spawn_probability                 = "0,0.1,0.4,0.5,0.4", -- TENTACLE_RAY_ENEMY
	price = 150,
	mana = 90,
	max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_tentacle_ray_enemy.xml,"
		draw_actions( 1, true )
	end,
}*/