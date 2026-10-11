package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_DYNAMITE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DYNAMITE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "dynamite.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Dynamite";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tnt"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a small explosive";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DYNAMITE();
        this.spawnProbabilities = new SpawnProbabilities(1, 0.9, 0.8, 0.7, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 50;
        this.hasCharges = true;
        this.maxCharges = 16;
        this.castDelay = 50;
        this.spread = 6.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "DYNAMITE",
	name 		= "$action_dynamite",
	description = "$actiondesc_dynamite",
	sprite 		= "data/ui_gfx/gun_actions/dynamite.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/tnt.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3,4", -- DYNAMITE
	spawn_probability                 = "1,0.9,0.8,0.7,0.6", -- DYNAMITE
	price = 160,
	mana = 50,
	max_uses    = 16,
	custom_xml_file = "data/entities/misc/custom_cards/tnt.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/tnt.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		c.spread_degrees = c.spread_degrees + 6.0
	end,
}*/