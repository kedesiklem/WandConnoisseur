package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BULLET_TRIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bullet_trigger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Arrow With Trigger";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "magic arrow trigger"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical arrow that casts another spell upon collision";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BULLET();
        this.triggerType = Projectile.TriggerType.trigger;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.5, 0.5, 0.6, 0.5, 0, 0, 0, 0, 0);
        this.price = 190;
        this.manaCost = 35;
        this.castDelay = 4;
        this.critRate = 5;
        this.spread = 2.0;
        this.recoil = 23.0;
        this.screenshake = 2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
    }
}

/*{
	id          = "BULLET_TRIGGER",
	name 		= "$action_bullet_trigger",
	description = "$actiondesc_bullet_trigger",
	sprite 		= "data/ui_gfx/gun_actions/bullet_trigger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bullet_trigger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "1,2,3,4,5", -- BULLET_TRIGGER
	spawn_probability                   = "0.5,0.5,0.5,0.6,0.5", -- BULLET_TRIGGER
	price = 190,
	mana = 35,
	--max_uses = 80,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 4
		c.screenshake = c.screenshake + 2
		c.spread_degrees = c.spread_degrees + 2.0
		c.damage_critical_chance = c.damage_critical_chance + 5
		add_projectile_trigger_hit_world("data/entities/projectiles/deck/bullet.xml", 1)
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 23.0
	end,
}*/