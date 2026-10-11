package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FREEZING_GAZE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FREEZING_GAZE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "freezing_gaze.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Freezing gaze";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A heart-freezingly sinister aura";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FREEZING_GAZE();
        this.relatedProjectileCount = 12;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.9, 1, 1, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 45;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 20;
        this.pattern = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone(), this.relatedProjectileCount);
    }
}

/*{
	id          = "FREEZING_GAZE",
	name 		= "$action_freezing_gaze",
	description = "$actiondesc_freezing_gaze",
	sprite 		= "data/ui_gfx/gun_actions/freezing_gaze.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/freezing_gaze_beam.xml",12},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4", -- FREEZING_GAZE
	spawn_probability                 = "0.9,1,1", -- FREEZING_GAZE
	price = 180,
	mana = 45,
	max_uses	= 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		add_projectile("data/entities/projectiles/deck/freezing_gaze_beam.xml")
		c.pattern_degrees = 30
		c.fire_rate_wait = c.fire_rate_wait + 20
	end,
}*/