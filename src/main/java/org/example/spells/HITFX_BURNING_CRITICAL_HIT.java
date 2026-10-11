package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_BURNING_CRITICAL_HIT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_BURNING_CRITICAL_HIT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "burning_critical.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Critical on burning";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "crit on burning", "crit on fire"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile always do a critical hit on burning enemies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_BURNING_CRITICAL_HIT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0, 0.4, 0.2, 0.2, 0, 0, 0, 0, 0);
        this.price = 70;
        this.manaCost = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HITFX_BURNING_CRITICAL_HIT",
	name 		= "$action_hitfx_burning_critical_hit",
	description = "$actiondesc_hitfx_burning_critical_hit",
	sprite 		= "data/ui_gfx/gun_actions/burning_critical.png",
	sprite_unidentified = "data/entities/misc/hitfx_burning_critical_hit.xml",
	related_extra_entities = { "data/entities/particles/freeze_charge.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- HITFX_BURNING_CRITICAL_HIT
	spawn_probability                 = "0.2,0.4,0.2,0.2", -- HITFX_BURNING_CRITICAL_HIT
	price = 70,
	mana = 10,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_burning_critical_hit.xml,"
		draw_actions( 1, true )
	end,
}*/