package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_CRITICAL_WATER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_CRITICAL_WATER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "critical_water.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Critical on wet (water) enemies";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "crit on water", "crit on wataa", "crit on wet"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile always do a critical hit on wet (water) enemies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_CRITICAL_WATER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0, 0.2, 0.4, 0.2, 0, 0, 0, 0, 0);
        this.price = 70;
        this.manaCost = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HITFX_CRITICAL_WATER",
	name 		= "$action_hitfx_critical_water",
	description = "$actiondesc_hitfx_critical_water",
	sprite 		= "data/ui_gfx/gun_actions/critical_water.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_critical_water.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- HITFX_CRITICAL_WATER
	spawn_probability                 = "0.2,0.2,0.4,0.2", -- HITFX_CRITICAL_WATER
	price = 70,
	mana = 10,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_critical_water.xml,"
		draw_actions( 1, true )
	end,
}*/