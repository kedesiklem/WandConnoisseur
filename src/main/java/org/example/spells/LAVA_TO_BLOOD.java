package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_LAVA_TO_BLOOD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LAVA_TO_BLOOD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "lava_to_blood.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Lava to blood";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes any lava within a projectile's range turn into blood";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LAVA_TO_BLOOD()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 30;
        this.castDelay = 10;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_orange.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LAVA_TO_BLOOD",
	name 		= "$action_lava_to_blood",
	description = "$actiondesc_lava_to_blood",
	sprite 		= "data/ui_gfx/gun_actions/lava_to_blood.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/lava_to_blood.xml", "data/entities/particles/tinyspark_orange.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- LAVA_TO_BLOOD
	spawn_probability                 = "0.3,0.3,0.3", -- LAVA_TO_BLOOD
	price = 80,
	mana = 30,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/lava_to_blood.xml,data/entities/particles/tinyspark_orange.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/