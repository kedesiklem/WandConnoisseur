package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class I_SHAPE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.multicast;
    static final String staticImageFile = "i_shape.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Formation - Behind Your Back";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "behind your back", "behind back"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts two spells: one ahead of and one behind the caster";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.5, 0.3, 0, 0, 0, 0, 0, 0, 0);
        this.price = 30;
        this.autoStat = false;
        this.pattern = 180;
        this.spread = -5.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(2, true, castState);
        castState.setPattern(this.pattern);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "I_SHAPE",
	name 		= "$action_i_shape",
	description = "$actiondesc_i_shape",
	sprite 		= "data/ui_gfx/gun_actions/i_shape.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/i_shape_unidentified.png",
	type 		= ACTION_TYPE_DRAW_MANY,
	spawn_level                       = "1,2,3", -- I_SHAPE
	spawn_probability                 = "0.4,0.5,0.3", -- I_SHAPE
	price = 30,
	mana = 0,
	--max_uses = 100,
	action 		= function()
		draw_actions(2, true)
		c.pattern_degrees = 180
		c.spread_degrees = c.spread_degrees - 5.0
	end,
}*/