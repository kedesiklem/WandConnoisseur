package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SEA_OIL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SEA_OIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "sea_oil.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sea of oil";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large body of oil below the caster";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SEA_OIL();
        this.spawnProbabilities = new SpawnProbabilities(0.3, 0, 0, 0, 0.5, 0.6, 0.3, 0, 0, 0, 0);
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
	id          = "SEA_OIL",
	name 		= "$action_sea_oil",
	description = "$actiondesc_sea_oil",
	sprite 		= "data/ui_gfx/gun_actions/sea_oil.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sea_oil_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/sea_oil.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "0,4,5,6", -- SEA_OIL
	spawn_probability                 = "0.3,0.5,0.6,0.3", -- SEA_OIL
	price = 350,
	mana = 140,
	max_uses = 3,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/sea_oil.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/