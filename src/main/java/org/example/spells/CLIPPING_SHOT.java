package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_CLIPPING_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CLIPPING_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "clipping_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Drilling shot";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile the power to go through the ground";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CLIPPING_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.3, 0.6, 0.4, 0.6, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 160;
        this.castDelay = 50;
        this.rechargeTime = 40;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CLIPPING_SHOT",
	name 		= "$action_clipping_shot",
	description = "$actiondesc_clipping_shot",
	sprite 		= "data/ui_gfx/gun_actions/clipping_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/clipping_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- CLIPPING_SHOT
	spawn_probability                 = "0.2,0.3,0.6,0.4,0.6", -- CLIPPING_SHOT
	price = 200,
	mana = 160,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/clipping_shot.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 50
		current_reload_time = current_reload_time + 40
		draw_actions( 1, true )
	end,
}*/