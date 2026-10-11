package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_TENTACLE_RAY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TENTACLE_RAY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "tentacle_ray.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Tentacler";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile cast tentacles in random directions";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_TENTACLE_RAY()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 110;
        this.hasCharges = true;
        this.maxCharges = 16;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*	id          = "TENTACLE_RAY",
	name 		= "$action_tentacle_ray",
	description = "$actiondesc_tentacle_ray",
	sprite 		= "data/ui_gfx/gun_actions/tentacle_ray.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/tentacle_ray.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- TENTACLE_RAY
	spawn_probability                 = "0.1,0,0.4,0.4,0.4", -- TENTACLE_RAY
	price = 150,
	mana = 110,
	max_uses = 16,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/tentacle_ray.xml,"
		draw_actions( 1, true )
	end,
}*/