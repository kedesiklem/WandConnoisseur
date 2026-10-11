package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CRITICAL_HIT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "critical_hit.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Critical Plus";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "crit"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile +15% chance of a critical hit";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.6, 0.6, 0.7, 0.6, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 5;
        this.critRate = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CRITICAL_HIT",
	name 		= "$action_critical_hit",
	description = "$actiondesc_critical_hit",
	sprite 		= "data/ui_gfx/gun_actions/critical_hit.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/damage_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- CRITICAL_HIT
	spawn_probability                 = "0.5,0.6,0.6,0.7,0.6", -- CRITICAL_HIT
	price = 140,
	mana = 5,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/critical_hit.xml",
	action 		= function()
		c.damage_critical_chance = c.damage_critical_chance + 15
		draw_actions( 1, true )
	end,
}*/