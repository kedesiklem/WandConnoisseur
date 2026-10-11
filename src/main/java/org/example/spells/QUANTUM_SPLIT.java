package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_QUANTUM_SPLIT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class QUANTUM_SPLIT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "quantum_split.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Quantum Split";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile split into three projectiles whose existences are entangled";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_QUANTUM_SPLIT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.6, 0.5, 0.5, 1, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 10;
        this.castDelay = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "QUANTUM_SPLIT",
	name 		= "$action_quantum_split",
	description = "$actiondesc_quantum_split",
	sprite 		= "data/ui_gfx/gun_actions/quantum_split.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	related_extra_entities = { "data/entities/misc/quantum_split.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- MANA_REDUCE
	spawn_probability                 = "0.5,0.6,0.5,0.5,1", -- MANA_REDUCE
	price = 200,
	mana = 10,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/quantum_split.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 5
		draw_actions( 1, true )
	end,
}*/