package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_AIR_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class AIR_BULLET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "air_bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Burst Of Air";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "air", "fart"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A brittle burst of air capable of greatly pushing objects";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_AIR_BULLET();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 5;
        this.castDelay = 3;
        this.spread = -2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "AIR_BULLET",
	name 		= "$action_air_bullet",
	description = "$actiondesc_air_bullet",
	sprite 		= "data/ui_gfx/gun_actions/air_bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/air_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/light_bullet_air.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2", -- AIR_BULLET
	spawn_probability                 = "1,1", -- AIR_BULLET
	price = 80,
	mana = 5,
	--max_uses = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/light_bullet_air.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		--c.screenshake = c.screenshake + 0.1
		c.spread_degrees = c.spread_degrees - 2.0
		--c.knockback_force = c.knockback_force + 2
	end,
}*/