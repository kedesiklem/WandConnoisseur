package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GRAVITY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "gravity.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Gravity";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "grav"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases the effect gravity has on a projectile";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.4, 0.4, 0.3, 0.3, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 1;
        this.gravity = 600.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "GRAVITY",
	name 		= "$action_gravity",
	description = "$actiondesc_gravity",
	sprite 		= "data/ui_gfx/gun_actions/gravity.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/w_shape_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- GRAVITY
	spawn_probability                 = "0.5,0.4,0.4,0.3,0.3", -- GRAVITY
	price = 50,
	mana = 1,
	--max_uses = 100,
	action 		= function()
		c.gravity = c.gravity + 600.0
		draw_actions( 1, true )
	end,
}*/