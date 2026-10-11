package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_FLY_UPWARDS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FLY_UPWARDS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "fly_upwards.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fly upwards";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "fly upward", "fly up"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Causes a projectile to aim straight upwards a short time after casting";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_FLY_UPWARDS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0, 0.45, 0, 0.3, 0, 0, 0, 0);
        this.price = 20;
        this.manaCost = 0;
        this.autoStat = false;
        this.castDelay = -8;
        this.speed = 1.2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addScript(this.relatedScripts);
        cardPool.draw(1, true, castState);
        castState.addCastDelay(this.castDelay);
        castState.multiplySpeed(this.speed);
    }
}

/*{
	id          = "FLY_UPWARDS",
	name 		= "$action_fly_upwards",
	description = "$actiondesc_fly_upwards",
	sprite 		= "data/ui_gfx/gun_actions/fly_upwards.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/fly_upwards.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,4,6", -- FLY_UPWARDS
	spawn_probability                 = "0.3,0.45,0.3", -- FLY_UPWARDS
	price = 20,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/fly_upwards.xml,"
		draw_actions( 1, true )
		c.fire_rate_wait    = c.fire_rate_wait - 8
		c.speed_multiplier	= c.speed_multiplier * 1.2
	end,
}*/