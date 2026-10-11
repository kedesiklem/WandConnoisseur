package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SEA_WATER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SEA_WATER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "sea_water.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sea of water";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large body of water below the caster";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SEA_WATER();
        this.spawnProbabilities = new SpawnProbabilities(0.5, 0, 0, 0, 0.4, 0.3, 0.2, 0, 0, 0, 0);
        this.price = 350;
        this.manaCost = 140;
        this.hasCharges = true;
        this.maxCharges = 3;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SEA_WATER",
	name 		= "$action_sea_water",
	description = "$actiondesc_sea_water",
	sprite 		= "data/ui_gfx/gun_actions/sea_water.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sea_water_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/sea_water.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "0,4,5,6", -- SEA_WATER
	spawn_probability                 = "0.5,0.4,0.3,0.2", -- SEA_WATER
	price = 350,
	mana = 140,
	max_uses = 3,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/sea_water.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/