package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ROCKET_DOWNWARDS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ROCKET_DOWNWARDS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "rocket_downwards.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Downwards bolt bundle";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "downward bolt bundle", "down bolt bundle"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile separate into a bundle of 5 explosive bolts as soon as it moves downwards";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ROCKET_DOWNWARDS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.5, 0.7, 0.7, 0, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 90;
        this.castDelay = 25;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ROCKET_DOWNWARDS",
	name 		= "$action_rocket_downwards",
	description = "$actiondesc_rocket_downwards",
	sprite 		= "data/ui_gfx/gun_actions/rocket_downwards.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/rocket_downwards.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4", -- ROCKET_DOWNWARDS
	spawn_probability                 = "0.2,0.5,0.7,0.7", -- ROCKET_DOWNWARDS
	price = 200,
	mana = 90,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/rocket_downwards.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 25
		draw_actions( 1, true )
	end,
}*/