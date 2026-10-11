package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TELEPORT_PROJECTILE_CLOSER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TELEPORT_PROJECTILE_CLOSER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "teleport_projectile_closer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Homebringer Teleport Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "homebringer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Brings the target hit closer to you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TELEPORT_PROJECTILE_CLOSER();
        this.spawnProbabilities = new SpawnProbabilities(0.4, 0.6, 0.6, 0, 0.7, 0.4, 0.4, 0, 0, 0, 0);
        this.price = 130;
        this.manaCost = 20;
        this.spread = -2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TELEPORT_PROJECTILE_CLOSER",
	name 		= "$action_teleport_closer",
	description = "$actiondesc_teleport_closer",
	sprite 		= "data/ui_gfx/gun_actions/teleport_projectile_closer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/teleport_projectile_closer.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- TELEPORT_PROJECTILE
	spawn_probability                 = "0.4,0.6,0.6,0.7,0.4,0.4", -- TELEPORT_PROJECTILE
	price = 130,
	mana = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/teleport_projectile_closer.xml")
		c.spread_degrees = c.spread_degrees - 2.0
	end,
}*/