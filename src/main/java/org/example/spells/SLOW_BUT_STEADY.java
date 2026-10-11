package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SLOW_BUT_STEADY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "slow_but_steady.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Slow But Steady";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The reload time of the wand is set to exactly 1.5 seconds";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.1, 0.2, 0.6, 0.6, 0, 0, 0, 0.4);
        this.price = 50;
        this.manaCost = 0;
        this.setRechargeTime = true;
        this.rechargeTime = 90;
        this.recoil = -80.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "SLOW_BUT_STEADY",
	name 		= "$action_slow_but_steady",
	description = "$actiondesc_slow_but_steady",
	sprite 		= "data/ui_gfx/gun_actions/slow_but_steady.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_maths",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5,6,10", -- LIFETIME
	spawn_probability                 = "0.1,0.2,0.6,0.6,0.4", -- LIFETIME
	price = 50,
	mana = 0,
	action 		= function()
		current_reload_time = 90
		shot_effects.recoil_knockback = shot_effects.recoil_knockback - 80.0
		draw_actions( 1, true )
	end,
}*/