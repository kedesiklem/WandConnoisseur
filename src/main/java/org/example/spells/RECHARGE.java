package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class RECHARGE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "recharge.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Reduce recharge time";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Reduces the time between spellcasts";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.8, 0.9, 1, 0.8, 0.9, 1, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 12;
        this.castDelay = -10;
        this.rechargeTime = -20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "RECHARGE",
	name 		= "$action_recharge",
	description = "$actiondesc_recharge",
	sprite 		= "data/ui_gfx/gun_actions/recharge.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5,6", -- RECHARGE
	spawn_probability                 = "0.8,0.9,1,0.8,0.9,1", -- RECHARGE
	price = 200,
	mana = 12,
	--max_uses = 150,
	action 		= function()
		c.fire_rate_wait    = c.fire_rate_wait - 10
		current_reload_time = current_reload_time - 20
		draw_actions( 1, true )
	end,
}*/