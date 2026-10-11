package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CLOUD_OIL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CLOUD_OIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "cloud_oil.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Oil cloud";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Creates a rain of oil";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CLOUD_OIL();
        this.spawnProbabilities = new SpawnProbabilities(0.2, 0.3, 0.4, 0.4, 0.3, 0.2, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CLOUD_OIL",
	name 		= "$action_cloud_oil",
	description = "$actiondesc_cloud_oil",
	sprite 		= "data/ui_gfx/gun_actions/cloud_oil.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/cloud_water_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/cloud_oil.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,3,4,5", -- CLOUD_WATER
	spawn_probability                 = "0.2,0.3,0.4,0.4,0.3,0.2", -- CLOUD_WATER
	price = 100,
	mana = 20,
	max_uses = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/cloud_oil.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/