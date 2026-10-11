package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_LIQUID_TO_EXPLOSION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIQUID_TO_EXPLOSION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "liquid_to_explosion.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Liquid Detonation";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Converts nearby nonmagical liquids into explosive gunpowder";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LIQUID_TO_EXPLOSION()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 40;
        this.castDelay = 20;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LIQUID_TO_EXPLOSION",
	name 		= "$action_liquid_to_explosion",
	description = "$actiondesc_liquid_to_explosion",
	sprite 		= "data/ui_gfx/gun_actions/liquid_to_explosion.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/liquid_to_explosion.xml", "data/entities/particles/tinyspark_red.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- BLOOD_TO_ACID
	spawn_probability                 = "0.3,0.3,0.3", -- BLOOD_TO_ACID
	price = 120,
	mana = 40,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/liquid_to_explosion.xml,data/entities/particles/tinyspark_red.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 20
		draw_actions( 1, true )
	end,
}*/