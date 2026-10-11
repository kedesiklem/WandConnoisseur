package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TELEPORT_PROJECTILE_SHORT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TELEPORT_PROJECTILE_SHORT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "teleport_projectile_short.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Small Teleport Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "short tele", "short tp"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A shortlived magical bolt that moves you wherever it ends up flying";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TELEPORT_PROJECTILE_SHORT();
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0.6, 0.6, 0, 0.4, 0.4, 0.4, 0, 0, 0, 0);
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
	id          = "TELEPORT_PROJECTILE_SHORT",
	name 		= "$action_teleport_projectile_short",
	description = "$actiondesc_teleport_projectile_short",
	sprite 		= "data/ui_gfx/gun_actions/teleport_projectile_short.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/teleport_projectile_short.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- TELEPORT_PROJECTILE
	spawn_probability                 = "0.6,0.6,0.6,0.4,0.4,0.4", -- TELEPORT_PROJECTILE
	price = 130,
	mana = 20,
	--max_uses = 80,
	custom_xml_file = "data/entities/misc/custom_cards/teleport_projectile_short.xml",
		action 		= function()
		add_projectile("data/entities/projectiles/deck/teleport_projectile_short.xml")
		c.spread_degrees = c.spread_degrees - 2.0
	end,
}*/