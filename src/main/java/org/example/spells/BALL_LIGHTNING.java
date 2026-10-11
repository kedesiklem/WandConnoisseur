package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BALL_LIGHTNING;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BALL_LIGHTNING extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "ball_lightning.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Ball Lightning";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "elec balls", "blue balls"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons three short range electrical orbs";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BALL_LIGHTNING();
        this.relatedProjectileCount = 3;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.4, 0, 1, 1, 0, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 70;
        this.castDelay = 50;
        this.setRecoil = true;
        this.recoil = 120.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), this.relatedProjectileCount);
    }
}

/*{
	id          = "BALL_LIGHTNING",
	name 		= "$action_ball_lightning",
	description = "$actiondesc_ball_lightning",
	sprite 		= "data/ui_gfx/gun_actions/ball_lightning.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/lightning_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/ball_lightning.xml",3},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,4,5", -- LIGHTNING
	spawn_probability                 = "0.2,0.4,1,1", -- LIGHTNING
	price = 250,
	mana = 70,
	custom_xml_file = "data/entities/misc/custom_cards/electric_charge.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/ball_lightning.xml")
		add_projectile("data/entities/projectiles/deck/ball_lightning.xml")
		add_projectile("data/entities/projectiles/deck/ball_lightning.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		shot_effects.recoil_knockback = 120.0
	end,
}*/