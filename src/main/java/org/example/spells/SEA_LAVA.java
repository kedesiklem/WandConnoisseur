package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SEA_LAVA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SEA_LAVA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "sea_lava.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sea of lava";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large body of lava below the caster";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SEA_LAVA();
        this.spawnProbabilities = new SpawnProbabilities(0.2, 0, 0, 0, 0.2, 0.5, 0.6, 0, 0, 0, 0);
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
	id          = "SEA_LAVA",
	name 		= "$action_sea_lava",
	description = "$actiondesc_sea_lava",
	spawn_requires_flag = "card_unlocked_sea_lava",
	sprite 		= "data/ui_gfx/gun_actions/sea_lava.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sea_lava_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/sea_lava.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "0,4,5,6", -- SEA_LAVA
	spawn_probability                 = "0.2,0.2,0.5,0.6", -- SEA_LAVA
	price = 350,
	mana = 140,
	max_uses = 3,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/sea_lava.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/