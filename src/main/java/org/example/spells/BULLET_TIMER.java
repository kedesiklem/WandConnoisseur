package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BULLET_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bullet_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Arrow With Timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "magic arrow timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical arrow that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BULLET();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 10;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.5, 0.5, 0.5, 0.6, 0, 0, 0, 0);
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
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
    }
}

/*{
	id          = "BULLET_TIMER",
	name 		= "$action_bullet_timer",
	description = "$actiondesc_bullet_timer",
	sprite 		= "data/ui_gfx/gun_actions/bullet_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bullet_timer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "2,3,4,5,6", -- BULLET_TIMER
	spawn_probability                   = "0.5,0.5,0.5,0.5,0.6", -- BULLET_TIMER
	price = 190,
	mana = 35,
	--max_uses = 80,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 4
		c.screenshake = c.screenshake + 2
		c.spread_degrees = c.spread_degrees + 2.0
		c.damage_critical_chance = c.damage_critical_chance + 5
		add_projectile_trigger_timer("data/entities/projectiles/deck/bullet.xml", 10, 1)
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 23.0
	end,
}*/