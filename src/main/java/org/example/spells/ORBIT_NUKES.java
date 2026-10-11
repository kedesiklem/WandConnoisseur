package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ORBIT_NUKES;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ORBIT_NUKES extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "orbit_nukes.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Nuke Orbit";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes four… nukes(?!) rotate around a projectile";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ORBIT_NUKES()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0, 0.2, 0.1, 0.2, 0, 0, 0, 1);
        this.price = 400;
        this.manaCost = 250;
        this.hasCharges = true;
        this.maxCharges = 3;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ORBIT_NUKES",
	name 		= "$action_orbit_nukes",
	description = "$actiondesc_orbit_nukes",
	sprite 		= "data/ui_gfx/gun_actions/orbit_nukes.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_nukes.xml" },
	spawn_requires_flag = "card_unlocked_dragon",
	type 		= ACTION_TYPE_MODIFIER,
	ai_never_uses = true,
	spawn_level                       = "2,4,5,6,10", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.1,0.2,0.1,0.2,1", -- GRAVITY_FIELD_ENEMY
	price = 400,
	mana = 250,
	max_uses = 3,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_nukes.xml,"
		draw_actions( 1, true )
	end,
}*/