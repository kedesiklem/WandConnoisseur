package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BOUNCY_ORB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCY_ORB extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bouncy_orb.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Energy sphere";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "sphere"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fast, arcing projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BOUNCY_ORB();
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 20;
        this.castDelay = 10;
        this.setRecoil = true;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BOUNCY_ORB",
	name 		= "$action_bouncy_orb",
	description = "$actiondesc_bouncy_orb",
	sprite 		= "data/ui_gfx/gun_actions/bouncy_orb.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/disc_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bouncy_orb.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2,4", -- BOUNCY_ORB
	spawn_probability                 = "1,1,1", -- BOUNCY_ORB
	price = 120,
	mana = 20,
	--max_uses = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/bouncy_orb.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 10
		shot_effects.recoil_knockback = 20.0
	end,
}*/