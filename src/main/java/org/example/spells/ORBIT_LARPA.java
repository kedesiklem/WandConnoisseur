package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ORBIT_LARPA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ORBIT_LARPA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "orbit_larpa.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Orbit Larpa";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "larpa orbit"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes four copies of a projectile rotate around it";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ORBIT_LARPA()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.2, 0.2, 0, 0.8, 0, 0, 0, 0.2);
        this.price = 240;
        this.manaCost = 90;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ORBIT_LARPA",
	name 		= "$action_orbit_larpa",
	description = "$actiondesc_orbit_larpa",
	sprite 		= "data/ui_gfx/gun_actions/orbit_larpa.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_larpa.xml" },
	spawn_requires_flag = "card_unlocked_dragon",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,6,10", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.2,0.2,0.8,0.2", -- GRAVITY_FIELD_ENEMY
	price = 240,
	mana = 90,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_larpa.xml,"
		draw_actions( 1, true )
	end,
}*/