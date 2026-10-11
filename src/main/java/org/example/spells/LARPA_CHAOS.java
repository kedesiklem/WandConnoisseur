package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_LARPA_CHAOS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LARPA_CHAOS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "larpa_chaos.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaos larpa";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile cast copies of itself in random directions";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LARPA_CHAOS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.2, 0.3, 0.4, 0, 0, 0, 0, 0.2);
        this.price = 260;
        this.manaCost = 100;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LARPA_CHAOS",
	name 		= "$action_larpa_chaos",
	description = "$actiondesc_larpa_chaos",
	sprite 		= "data/ui_gfx/gun_actions/larpa_chaos.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/larpa_chaos.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,10", -- FIREBALL_RAY
	spawn_probability                 = "0.1,0.2,0.3,0.4,0.2", -- FIREBALL_RAY
	price = 260,
	mana = 100,
	--max_uses = 20,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 15
		c.extra_entities = c.extra_entities .. "data/entities/misc/larpa_chaos.xml,"
		draw_actions( 1, true )
	end,
}*/