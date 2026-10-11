package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ORBIT_FIREBALLS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ORBIT_FIREBALLS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "orbit_fireballs.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fireball Orbit";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes four fireballs rotate around a projectile";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ORBIT_FIREBALLS()};
        this.spawnProbabilities = new SpawnProbabilities(0.2, 0.3, 0.7, 0, 0.4, 0.2, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 40;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ORBIT_FIREBALLS",
	name 		= "$action_orbit_fireballs",
	description = "$actiondesc_orbit_fireballs",
	sprite 		= "data/ui_gfx/gun_actions/orbit_fireballs.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_fireballs.xml" },
	spawn_requires_flag = "card_unlocked_dragon",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "0,1,2,4,5", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.2,0.3,0.7,0.4,0.2", -- GRAVITY_FIELD_ENEMY
	price = 140,
	mana = 40,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_fireballs.xml,"
		draw_actions( 1, true )
	end,
}*/