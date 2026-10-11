package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CLOUD_WATER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CLOUD_WATER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "cloud_water.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Rain cloud";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Creates a watery weather phenomenon";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CLOUD_WATER();
        this.spawnProbabilities = new SpawnProbabilities(0.2, 0.3, 0.4, 0.4, 0.3, 0.2, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 30;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CLOUD_WATER",
	name 		= "$action_cloud_water",
	description = "$actiondesc_cloud_water",
	sprite 		= "data/ui_gfx/gun_actions/cloud_water.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/cloud_water_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/cloud_water.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,3,4,5", -- CLOUD_WATER
	spawn_probability                 = "0.2,0.3,0.4,0.4,0.3,0.2", -- CLOUD_WATER
	price = 140,
	mana = 30,
	max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/cloud_water.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/