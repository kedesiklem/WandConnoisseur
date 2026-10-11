package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ARC_FIRE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ARC_FIRE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "arc_fire.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fire Arc";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Creates arcs of fire between projectiles (requires 2 projectile spells)";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ARC_FIRE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.2, 0.2, 0.5, 0.2, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ARC_FIRE",
	name 		= "$action_arc_fire",
	description = "$actiondesc_arc_fire",
	sprite 		= "data/ui_gfx/gun_actions/arc_fire.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/arc_fire_unidentified.png",
	related_extra_entities = { "data/entities/misc/arc_fire.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- ARC_FIRE
	spawn_probability                 = "0.4,0.2,0.2,0.5,0.2", -- ARC_FIRE
	price = 160,
	--max_uses 	= 15,
	mana = 15,
	custom_xml_file = "data/entities/misc/custom_cards/arc_fire.xml",
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/arc_fire.xml,"
		draw_actions( 1, true )
	end,
}*/