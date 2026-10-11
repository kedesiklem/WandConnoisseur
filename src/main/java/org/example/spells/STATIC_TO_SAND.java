package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_STATIC_TO_SAND;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class STATIC_TO_SAND extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "static_to_sand.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Ground to sand";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "gts"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes any hard, solid materials within a projectile's range turn into sand";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_STATIC_TO_SAND()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0.2);
        this.price = 140;
        this.manaCost = 70;
        this.hasCharges = true;
        this.maxCharges = 8;
        this.castDelay = 60;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_yellow.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "STATIC_TO_SAND",
	name 		= "$action_static_to_sand",
	description = "$actiondesc_static_to_sand",
	sprite 		= "data/ui_gfx/gun_actions/static_to_sand.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/static_to_sand.xml", "data/entities/particles/tinyspark_yellow.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,10", -- STATIC_TO_SAND
	spawn_probability                 = "0.3,0.3,0.3,0.2", -- STATIC_TO_SAND
	price = 140,
	mana = 70,
	max_uses = 8,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/static_to_sand.xml,data/entities/particles/tinyspark_yellow.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 60
		draw_actions( 1, true )
	end,
}*/