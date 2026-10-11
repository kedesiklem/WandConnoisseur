package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_MATTER_EATER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MATTER_EATER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "matter_eater.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Matter eater";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "me"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile eat the environment as it flies";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_MATTER_EATER()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0.9, 0, 0.1, 0.2, 0, 0, 0, 0, 0.2);
        this.price = 280;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.neverUnlimited = true;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "MATTER_EATER",
	name 		= "$action_matter_eater",
	description = "$actiondesc_matter_eater",
	sprite 		= "data/ui_gfx/gun_actions/matter_eater.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/matter_eater.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5,10", -- MATTER_EATER
	spawn_probability                 = "0.1,0.9,0.1,0.2,0.2", -- MATTER_EATER
	price = 280,
	mana = 120,
	max_uses = 10,
	never_unlimited = true,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/matter_eater.xml,"
		draw_actions( 1, true )
	end,
}*/