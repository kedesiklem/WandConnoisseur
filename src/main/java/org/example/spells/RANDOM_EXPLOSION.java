package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_RANDOM_EXPLOSION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class RANDOM_EXPLOSION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "random_explosion.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaos magic";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile launch a random spell (out of a limited selection) when it hits something";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_RANDOM_EXPLOSION()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.3, 0, 0.6, 1, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 30;
        this.castDelay = 40;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_purple_bright.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "RANDOM_EXPLOSION",
	name 		= "$action_random_explosion",
	description = "$actiondesc_random_explosion",
	sprite 		= "data/ui_gfx/gun_actions/random_explosion.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/random_explosion.xml", "data/entities/particles/tinyspark_purple_bright.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,5,6", -- TRANSMUTATION
	spawn_probability                 = "0.3,0.6,1", -- TRANSMUTATION
	price = 240,
	mana = 120,
	max_uses = 30,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/random_explosion.xml,data/entities/particles/tinyspark_purple_bright.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 40
		draw_actions( 1, true )
	end,
}*/