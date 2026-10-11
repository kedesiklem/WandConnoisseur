package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ENERGY_SHIELD_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ENERGY_SHIELD_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "energy_shield_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Projectile energy shield";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "projectile shield", "proj shield", "shield proj"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a shield that deflects other projectiles";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ENERGY_SHIELD_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.5, 0.4, 0.3, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 5;
        this.speed = 0.4;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ENERGY_SHIELD_SHOT",
	name 		= "$action_energy_shield_shot",
	description = "$actiondesc_energy_shield_shot",
	sprite 		= "data/ui_gfx/gun_actions/energy_shield_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/energy_shield_shot_unidentified.png",
	related_extra_entities = { "data/entities/misc/energy_shield_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- ENERGY_SHIELD_SHOT
	spawn_probability                 = "0.3,0.3,0.5,0.4,0.3", -- ENERGY_SHIELD_SHOT
	price = 180,
	mana = 5,
	action 		= function()
		c.speed_multiplier = c.speed_multiplier * 0.4
		c.extra_entities = c.extra_entities .. "data/entities/misc/energy_shield_shot.xml,"

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/