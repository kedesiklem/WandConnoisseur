package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LIGHT_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIGHT_BULLET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "light_bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spark Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spark"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A weak but enchanting sparkling projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LIGHT_BULLET();
        this.spawnProbabilities = new SpawnProbabilities(2, 1, 0.5, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 5;
        this.castDelay = 3;
        this.spread = -1;
        this.critRate = 5;
        this.screenshake = 0.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LIGHT_BULLET",
	name 		= "$action_light_bullet",
	description = "$actiondesc_light_bullet",
	sprite 		= "data/ui_gfx/gun_actions/light_bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/light_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/light_bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2", -- LIGHT_BULLET
	spawn_probability                 = "2,1,0.5", -- LIGHT_BULLET
	price = 100,
	mana = 5,
	--max_uses = -1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/light_bullet.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 0.5
		c.spread_degrees = c.spread_degrees - 1.0
		c.damage_critical_chance = c.damage_critical_chance + 5
	end,
}*/