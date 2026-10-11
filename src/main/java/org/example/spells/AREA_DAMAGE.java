package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_AREA_DAMAGE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class AREA_DAMAGE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "area_damage.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Damage field";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile an energy field that constantly deals 3.5 damage to nearby creatures";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_AREA_DAMAGE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.5, 0.5, 0.5, 0.6, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "AREA_DAMAGE",
	name 		= "$action_area_damage",
	description = "$actiondesc_area_damage",
	sprite 		= "data/ui_gfx/gun_actions/area_damage.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/area_damage.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- AREA_DAMAGE
	spawn_probability                 = "0.4,0.5,0.5,0.5,0.6", -- AREA_DAMAGE
	price = 140,
	mana = 30,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/area_damage.xml,"
		draw_actions( 1, true )
	end,
}*/