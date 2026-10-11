package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_WATER_TO_POISON;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class WATER_TO_POISON extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "water_to_poison.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Water to poison";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes any water within a projectile's range turns into poison";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_WATER_TO_POISON()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 30;
        this.castDelay = 10;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_purple.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "WATER_TO_POISON",
	name 		= "$action_water_to_poison",
	description = "$actiondesc_water_to_poison",
	sprite 		= "data/ui_gfx/gun_actions/water_to_poison.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/water_to_poison.xml", "data/entities/particles/tinyspark_purple.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- WATER_TO_POISON
	spawn_probability                 = "0.3,0.3,0.3", -- WATER_TO_POISON
	price = 80,
	mana = 30,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/water_to_poison.xml,data/entities/particles/tinyspark_purple.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/