package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_GLOWING_BOLT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GLOWING_BOLT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "glowing_bolt.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Pinpoint of light";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "pinpoint"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "An extremely concentrated point of light that explodes after a moment";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GLOWING_BOLT();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.8, 0.9, 1, 0, 0, 0, 0, 0.1);
        this.price = 220;
        this.manaCost = 65;
        this.castDelay = 40;
        this.spread = 6.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "GLOWING_BOLT",
	name 		= "$action_glowing_bolt",
	description = "$actiondesc_glowing_bolt",
	sprite 		= "data/ui_gfx/gun_actions/glowing_bolt.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/glowing_bolt.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,10", -- FREEZING_GAZE
	spawn_probability                 = "0.8,0.9,1,0.1", -- FREEZING_GAZE
	price = 220,
	mana = 65,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/glowing_bolt.xml")
		c.fire_rate_wait = c.fire_rate_wait + 40
		c.spread_degrees = c.spread_degrees + 6.0
	end,
}*/