package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CURSED_ORB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CURSED_ORB extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "cursed_orb.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Cursed Sphere";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile that brings bad luck to anyone it hits";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CURSED_ORB();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0.2, 0.1, 0, 0, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 40;
        this.castDelay = 20;
        this.recoil = 40.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CURSED_ORB",
	name 		= "$action_cursed_orb",
	description = "$actiondesc_cursed_orb",
	sprite 		= "data/ui_gfx/gun_actions/cursed_orb.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/disc_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/orb_cursed.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3", -- CURSED_ORB
	spawn_probability                 = "0.3,0.2,0.1", -- CURSED_ORB
	price = 200,
	mana = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/orb_cursed.xml")
		c.fire_rate_wait = c.fire_rate_wait + 20
		shot_effects.recoil_knockback = 40.0
	end,
}*/