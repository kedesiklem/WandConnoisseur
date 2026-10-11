package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_LARPA_UPWARDS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LARPA_UPWARDS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "larpa_upwards.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Upwards larpa";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "upward larpa", "up larpa"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile cast copies of itself with an upwards trajectory";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LARPA_UPWARDS()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.1, 0.2, 0.4, 0, 0, 0, 0, 0.2);
        this.price = 290;
        this.manaCost = 120;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LARPA_UPWARDS",
	name 		= "$action_larpa_upwards",
	description = "$actiondesc_larpa_upwards",
	sprite 		= "data/ui_gfx/gun_actions/larpa_upwards.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/larpa_upwards.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,10", -- FIREBALL_RAY
	spawn_probability                 = "0.1,0.1,0.2,0.4,0.2", -- FIREBALL_RAY
	price = 290,
	mana = 120,
	--max_uses = 20,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 15
		c.extra_entities = c.extra_entities .. "data/entities/misc/larpa_upwards.xml,"
		draw_actions( 1, true )
	end,
}*/