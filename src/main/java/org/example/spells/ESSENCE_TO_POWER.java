package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ESSENCE_TO_POWER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ESSENCE_TO_POWER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "essence_to_power.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Essence to Power";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "etp"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases a projectile's damage based on the number of creatures nearby";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ESSENCE_TO_POWER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.5, 0.5, 0, 0, 0.5, 0, 0, 0, 0.1);
        this.price = 120;
        this.manaCost = 110;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ESSENCE_TO_POWER",
	name 		= "$action_enemies_to_power",
	description = "$actiondesc_enemies_to_power",
	sprite 		= "data/ui_gfx/gun_actions/essence_to_power.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/essence_to_power.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,6,10", -- AREA_DAMAGE
	spawn_probability                 = "0.2,0.5,0.5,0.5,0.1", -- AREA_DAMAGE
	price = 120,
	mana = 110,
	-- max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/essence_to_power.xml,"
		c.fire_rate_wait    = c.fire_rate_wait + 20
		draw_actions( 1, true )
	end,
}*/