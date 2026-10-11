package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_TOXIC_TO_ACID;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOXIC_TO_ACID extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "toxic_to_acid.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Toxic sludge to acid";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "toxic to acid"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes any toxic sludge within a projectile's range turn into acid";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_TOXIC_TO_ACID()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 50;
        this.castDelay = 10;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_green.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "TOXIC_TO_ACID",
	name 		= "$action_toxic_to_acid",
	description = "$actiondesc_toxic_to_acid",
	sprite 		= "data/ui_gfx/gun_actions/toxic_to_acid.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	related_extra_entities = { "data/entities/misc/toxic_to_acid.xml", "data/entities/particles/tinyspark_green.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- TOXIC_TO_ACID
	spawn_probability                 = "0.3,0.3,0.3", -- TOXIC_TO_ACID
	price = 120,
	mana = 50,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/toxic_to_acid.xml,data/entities/particles/tinyspark_green.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/