package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TELEPORT_PROJECTILE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TELEPORT_PROJECTILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "teleport_projectile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Teleport bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tele", "tp"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical bolt that moves you wherever it ends up flying";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TELEPORT_PROJECTILE();
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0.6, 0.6, 0, 0.4, 0.4, 0.4, 0, 0, 0, 0);
        this.price = 130;
        this.manaCost = 40;
        this.castDelay = 3;
        this.spread = -2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TELEPORT_PROJECTILE",
	name 		= "$action_teleport_projectile",
	description = "$actiondesc_teleport_projectile",
	sprite 		= "data/ui_gfx/gun_actions/teleport_projectile.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/teleport_projectile.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- TELEPORT_PROJECTILE
	spawn_probability                 = "0.6,0.6,0.6,0.4,0.4,0.4", -- TELEPORT_PROJECTILE
	price = 130,
	mana = 40,
	--max_uses = 80,
	custom_xml_file = "data/entities/misc/custom_cards/teleport_projectile.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/teleport_projectile.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.spread_degrees = c.spread_degrees - 2.0
	end,
}*/