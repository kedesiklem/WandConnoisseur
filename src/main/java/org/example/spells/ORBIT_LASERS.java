package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ORBIT_LASERS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ORBIT_LASERS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "orbit_lasers.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam Orbit";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "plasma orbit"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes four plasma beams rotate around a projectile";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ORBIT_LASERS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.7, 0, 0.4, 0.3, 0, 0, 0, 0, 0.2);
        this.price = 200;
        this.manaCost = 100;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ORBIT_LASERS",
	name 		= "$action_orbit_lasers",
	description = "$actiondesc_orbit_lasers",
	sprite 		= "data/ui_gfx/gun_actions/orbit_lasers.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_lasers.xml" },
	spawn_requires_flag = "card_unlocked_dragon",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5,10", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.2,0.7,0.4,0.3,0.2", -- GRAVITY_FIELD_ENEMY
	price = 200,
	mana = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_lasers.xml,"
		draw_actions( 1, true )
	end,
}*/