package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HEAVY_SPREAD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "heavy_spread.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Heavy spread";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spread"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a much lower cast delay, but no respect to your aim";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0.7, 0.8, 0, 0.8, 0.8, 0.6, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 2;
        this.castDelay = -7;
        this.rechargeTime = -15;
        this.spread = 720.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HEAVY_SPREAD",
	name 		= "$action_heavy_spread",
	description = "$actiondesc_heavy_spread",
	sprite 		= "data/ui_gfx/gun_actions/heavy_spread.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "0,1,2,4,5,6", -- HEAVY_SPREAD
	spawn_probability                 = "0.6,0.7,0.8,0.8,0.8,0.6", -- HEAVY_SPREAD
	price = 100,
	mana = 2,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait - 7
		current_reload_time = current_reload_time - 15
		c.spread_degrees = c.spread_degrees + 720
		draw_actions( 1, true )
	end,
}*/