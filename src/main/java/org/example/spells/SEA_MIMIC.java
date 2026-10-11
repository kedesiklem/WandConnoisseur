package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SEA_MIMIC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SEA_MIMIC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "sea_mimic.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sea of Mimicium";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large body of mimicium under the caster";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SEA_MIMIC();
        this.spawnProbabilities = new SpawnProbabilities(0.05, 0, 0, 0, 0.05, 0.1, 0.1, 0, 0, 0, 0.2);
        this.price = 350;
        this.manaCost = 140;
        this.hasCharges = true;
        this.maxCharges = 2;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SEA_MIMIC",
	name 		= "$action_sea_mimic",
	description = "$actiondesc_sea_mimic",
	sprite 		= "data/ui_gfx/gun_actions/sea_mimic.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sea_acid_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/sea_mimic.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "0,4,5,6,10", -- SEA_MIMIC
	spawn_probability                 = "0.05,0.05,0.1,0.1,0.2", -- SEA_MIMIC
	spawn_requires_flag = "card_unlocked_sea_mimic",
	price = 350,
	mana = 140,
	max_uses = 2,
	never_unlimited = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/sea_mimic.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/