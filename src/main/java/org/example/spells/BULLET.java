package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BULLET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Arrow";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A handy magical arrow";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BULLET();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 1, 0.8, 0.5, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 20;
        this.castDelay = 4;
        this.critRate = 5;
        this.spread = 2.0;
        this.recoil = 23.0;
        this.screenshake = 2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BULLET",
	name 		= "$action_bullet",
	description = "$actiondesc_bullet",
	sprite 		= "data/ui_gfx/gun_actions/bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- BULLET
	spawn_probability                 = "1,1,1,0.8,0.5", -- BULLET
	price = 150,
	mana = 20,
	--max_uses = -1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/bullet.xml")
		c.fire_rate_wait = c.fire_rate_wait + 4
		c.screenshake = c.screenshake + 2
		c.spread_degrees = c.spread_degrees + 2.0
		c.damage_critical_chance = c.damage_critical_chance + 5
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 23.0
	end,
}*/