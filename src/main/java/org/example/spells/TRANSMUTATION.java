package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_TRANSMUTATION;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TRANSMUTATION extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "transmutation.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaotic transmutation";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes various liquids and powdery substances within a projectile's range into something else";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_TRANSMUTATION()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0.3, 0.3, 0, 0, 0, 0.2);
        this.price = 180;
        this.manaCost = 80;
        this.hasCharges = true;
        this.maxCharges = 8;
        this.castDelay = 20;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_purple_bright.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "TRANSMUTATION",
	name 		= "$action_transmutation",
	description = "$actiondesc_transmutation",
	sprite 		= "data/ui_gfx/gun_actions/transmutation.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/transmutation.xml", "data/entities/particles/tinyspark_purple_bright.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6,10", -- TRANSMUTATION
	spawn_probability                 = "0.3,0.3,0.3,0.3,0.3,0.2", -- TRANSMUTATION
	price = 180,
	mana = 80,
	max_uses = 8,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/transmutation.xml,data/entities/particles/tinyspark_purple_bright.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 20
		draw_actions( 1, true )
	end,
}*/