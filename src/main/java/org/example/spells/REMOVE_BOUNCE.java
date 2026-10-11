package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_REMOVE_BOUNCE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class REMOVE_BOUNCE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "remove_bounce.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Remove Bounce";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A normally bouncy projectile stops doing so";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_REMOVE_BOUNCE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.2, 1, 1, 0, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 0;
        this.setBounce = true;
        this.bounce = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "REMOVE_BOUNCE",
	name 		= "$action_remove_bounce",
	description = "$actiondesc_remove_bounce",
	sprite 		= "data/ui_gfx/gun_actions/remove_bounce.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bounce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5", -- BOUNCE
	spawn_probability                 = "0.2,0.2,1,1", -- BOUNCE
	price = 50,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/remove_bounce.xml,"
		c.bounces = 0
		draw_actions( 1, true )
	end,
}*/