package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_LARPA_CHAOS_2;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LARPA_CHAOS_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "larpa_chaos_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Copy trail";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile leave a trail of copies of itself";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LARPA_CHAOS_2()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.1, 0, 0.4, 0, 0, 0, 0, 0.1);
        this.price = 300;
        this.manaCost = 150;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LARPA_CHAOS_2",
	name 		= "$action_larpa_chaos_2",
	description = "$actiondesc_larpa_chaos_2",
	sprite 		= "data/ui_gfx/gun_actions/larpa_chaos_2.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	spawn_requires_flag = "card_unlocked_alchemy",
	related_extra_entities = { "data/entities/misc/larpa_chaos_2.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,5,10", -- FIREBALL_RAY
	spawn_probability                 = "0.1,0.4,0.1", -- FIREBALL_RAY
	price = 300,
	mana = 150,
	--max_uses = 20,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 20
		c.extra_entities = c.extra_entities .. "data/entities/misc/larpa_chaos_2.xml,"
		draw_actions( 1, true )
	end,
}*/