package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_FLOATING_ARC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FLOATING_ARC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "floating_arc.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Floating arc";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile float above the ground";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_FLOATING_ARC()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0, 0.4, 0, 0.5, 0, 0, 0, 0, 0);
        this.price = 30;
        this.manaCost = 0;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "FLOATING_ARC",
	name 		= "$action_floating_arc",
	description = "$actiondesc_floating_arc",
	sprite 		= "data/ui_gfx/gun_actions/floating_arc.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/floating_arc.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,5", -- FLOATING_ARC
	spawn_probability                 = "0.4,0.4,0.5", -- FLOATING_ARC
	price = 30,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/floating_arc.xml,"
		c.fire_rate_wait    = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/