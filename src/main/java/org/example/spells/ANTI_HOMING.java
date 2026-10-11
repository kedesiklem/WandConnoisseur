package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ANTI_HOMING;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ANTI_HOMING extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "anti_homing.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Anti Homing";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile accelerate away from your foes";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ANTI_HOMING()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.05, 0.3, 0.3, 0.1, 0.1, 0.01, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 1;
        this.castDelay = -20;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ANTI_HOMING",
	name 		= "$action_anti_homing",
	description = "$actiondesc_anti_homing",
	sprite 		= "data/ui_gfx/gun_actions/anti_homing.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/anti_homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/anti_homing.xml", "data/entities/particles/tinyspark_white.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5,6", -- ANTI_HOMING
	spawn_probability                 = "0.05,0.3,0.3,0.1,0.1,0.01", -- ANTI_HOMING
	price = 110,
	mana = 1,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/anti_homing.xml,data/entities/particles/tinyspark_white.xml,"
		c.fire_rate_wait    = c.fire_rate_wait - 20
		draw_actions( 1, true )
	end,
}*/