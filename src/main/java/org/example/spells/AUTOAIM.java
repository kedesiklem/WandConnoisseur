package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_AUTOAIM;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class AUTOAIM extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "autoaim.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Auto-Aim";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "auto aim"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile turns towards the nearest visible enemy";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_AUTOAIM()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 25;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "AUTOAIM",
	name 		= "$action_autoaim",
	description = "$actiondesc_autoaim",
	sprite 		= "data/ui_gfx/gun_actions/autoaim.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/autoaim_unidentified.png",
	related_extra_entities = { "data/entities/misc/autoaim.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- AUTOAIM
	spawn_probability                 = "0.4,0.4,0.4,0.4,0.4", -- AUTOAIM
	price = 150,
	mana = 25,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/autoaim.xml,"
		draw_actions( 1, true )
	end,
}*/