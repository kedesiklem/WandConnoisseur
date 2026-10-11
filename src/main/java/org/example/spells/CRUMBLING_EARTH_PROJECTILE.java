package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_CRUMBLING_EARTH_PROJECTILE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CRUMBLING_EARTH_PROJECTILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "crumbling_earth_projectile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Earthquake shot";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile crumble the earth it hits";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CRUMBLING_EARTH_PROJECTILE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.3, 0.4, 0.4, 0.3, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 45;
        this.hasCharges = true;
        this.maxCharges = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CRUMBLING_EARTH_PROJECTILE",
	name 		= "$action_crumbling_earth_projectile",
	description = "$actiondesc_crumbling_earth_projectile",
	sprite 		= "data/ui_gfx/gun_actions/crumbling_earth_projectile.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/arc_fire_unidentified.png",
	related_extra_entities = { "data/entities/misc/crumbling_earth_projectile.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- ARC_POISON
	spawn_probability                 = "0.2,0.3,0.4,0.4,0.3", -- ARC_POISON
	price = 200,
	max_uses 	= 15,
	mana = 45,
	-- custom_xml_file = "data/entities/misc/custom_cards/arc_poison.xml",
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/crumbling_earth_projectile.xml,"
		draw_actions( 1, true )
	end,
}*/