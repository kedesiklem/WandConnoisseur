package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "bounce.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile bounce on impact";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 1, 1, 0.4, 0, 0.2, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 0;
        this.bounce = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "BOUNCE",
	name 		= "$action_bounce",
	description = "$actiondesc_bounce",
	sprite 		= "data/ui_gfx/gun_actions/bounce.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bounce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,6", -- BOUNCE
	spawn_probability                 = "1,1,0.4,0.2", -- BOUNCE
	price = 50,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.bounces = c.bounces + 10
		draw_actions( 1, true )
	end,
}*/