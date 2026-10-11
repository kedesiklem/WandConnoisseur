package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_CHAOTIC_ARC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CHAOTIC_ARC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "chaotic_arc.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaotic path";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Causes a projectile to chaotically fly wherever it wishes";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CHAOTIC_ARC()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0, 0.55, 0, 0.4, 0, 0, 0, 0, 0);
        this.price = 10;
        this.manaCost = 0;
        this.speed = 2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CHAOTIC_ARC",
	name 		= "$action_chaotic_arc",
	description = "$actiondesc_chaotic_arc",
	sprite 		= "data/ui_gfx/gun_actions/chaotic_arc.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/chaotic_arc.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,5", -- CHAOTIC_ARC
	spawn_probability                 = "0.4,0.55,0.4", -- CHAOTIC_ARC
	price = 10,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/chaotic_arc.xml,"
		c.speed_multiplier = c.speed_multiplier * 2

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/