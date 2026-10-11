package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ORBIT_DISCS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ORBIT_DISCS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "orbit_discs.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sawblade Orbit";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "saw orbit"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes four sawblades rotate around a projectile";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ORBIT_DISCS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0.65, 0, 0.4, 0.3, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 70;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ORBIT_DISCS",
	name 		= "$action_orbit_discs",
	description = "$actiondesc_orbit_discs",
	sprite 		= "data/ui_gfx/gun_actions/orbit_discs.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_discs.xml" },
	spawn_requires_flag = "card_unlocked_dragon",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.3,0.65,0.4,0.3", -- GRAVITY_FIELD_ENEMY
	price = 200,
	mana = 70,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_discs.xml,"
		draw_actions( 1, true )
	end,
}*/