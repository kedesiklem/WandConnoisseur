package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ROCKET_OCTAGON;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ROCKET_OCTAGON extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "rocket_octagon.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Octagonal bolt bundle";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile launch 8 magical bolts if it moves slowly enough";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ROCKET_OCTAGON()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.6, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 100;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ROCKET_OCTAGON",
	name 		= "$action_rocket_octagon",
	description = "$actiondesc_rocket_octagon",
	sprite 		= "data/ui_gfx/gun_actions/rocket_octagon.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/rocket_octagon.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- ROCKET_DOWNWARDS
	spawn_probability                 = "0.5,0.6,0.3", -- ROCKET_DOWNWARDS
	price = 200,
	mana = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/rocket_octagon.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 20
		draw_actions( 1, true )
	end,
}*/