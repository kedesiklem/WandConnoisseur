package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_CURSE_WITHER_MELEE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CURSE_WITHER_MELEE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "curse_wither_melee.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Weakening Curse - Melee";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "curse melee"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Target hit by a projectile takes 25% extra melee damage for a time";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CURSE_WITHER_MELEE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.2, 0.4, 0.7, 0.7, 0, 0, 0, 0.1);
        this.price = 100;
        this.manaCost = 50;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CURSE_WITHER_MELEE",
	name 		= "$action_curse_wither_melee",
	description = "$actiondesc_curse_wither_melee",
	sprite 		= "data/ui_gfx/gun_actions/curse_wither_melee.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_curse_wither_melee.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5,6,10", -- FIREBALL_RAY_ENEMY
	spawn_probability                 = "0.2,0.4,0.7,0.7,0.1", -- FIREBALL_RAY_ENEMY
	price = 100,
	mana = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_curse_wither_melee.xml,"
		draw_actions( 1, true )
	end,
}*/