package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_SPELLS_TO_POWER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPELLS_TO_POWER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "spells_to_power.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spells to Power";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "stp"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Converts any nearby projectiles cast by you into extra damage";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_SPELLS_TO_POWER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.5, 0.5, 0.5, 0, 0, 0, 0.1);
        this.price = 140;
        this.manaCost = 110;
        this.castDelay = 40;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "SPELLS_TO_POWER",
	name 		= "$action_spells_to_power",
	description = "$actiondesc_spells_to_power",
	sprite 		= "data/ui_gfx/gun_actions/spells_to_power.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/spells_to_power.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6,10", -- AREA_DAMAGE
	spawn_probability                 = "0.3,0.3,0.5,0.5,0.5,0.1", -- AREA_DAMAGE
	price = 140,
	mana = 110,
	-- max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/spells_to_power.xml,"
		c.fire_rate_wait    = c.fire_rate_wait + 40
		draw_actions( 1, true )
	end,
}*/