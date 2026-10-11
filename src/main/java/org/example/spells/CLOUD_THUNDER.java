package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CLOUD_THUNDER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CLOUD_THUNDER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "cloud_thunder.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Thundercloud";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Creates a stormy cloud";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CLOUD_THUNDER();
        this.spawnProbabilities = new SpawnProbabilities(0.3, 0.3, 0.2, 0.3, 0.4, 0.5, 0, 0, 0, 0, 0);
        this.price = 190;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 5;
        this.castDelay = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CLOUD_THUNDER",
	name 		= "$action_cloud_thunder",
	description = "$actiondesc_cloud_thunder",
	sprite 		= "data/ui_gfx/gun_actions/cloud_thunder.png",
	spawn_requires_flag = "card_unlocked_cloud_thunder",
	sprite_unidentified = "data/ui_gfx/gun_actions/cloud_water_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/cloud_thunder.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,3,4,5", -- CLOUD_THUNDER
	spawn_probability                 = "0.3,0.3,0.2,0.3,0.4,0.5", -- CLOUD_THUNDER
	price = 190,
	mana = 90,
	max_uses = 5,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/cloud_thunder.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
	end,
}*/