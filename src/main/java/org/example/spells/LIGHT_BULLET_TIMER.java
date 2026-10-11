package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_LIGHT_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIGHT_BULLET_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "light_bullet_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spark Bolt With Timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spark timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A spark bolt that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LIGHT_BULLET();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 10;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.5, 0.75, 0, 0, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 10;
        this.castDelay = 3;
        this.critRate = 5;
        this.screenshake = 0.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
    }
}

/*{
	id          = "LIGHT_BULLET_TIMER",
	name 		= "$action_light_bullet_timer",
	description = "$actiondesc_light_bullet_timer",
	sprite 		= "data/ui_gfx/gun_actions/light_bullet_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/light_bullet_timer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/light_bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "1,2,3", -- LIGHT_BULLET_TIMER
	spawn_probability                   = "0.5,0.5,0.75", -- LIGHT_BULLET_TIMER
	price = 140,
	mana = 10,
	--max_uses = 100,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 0.5
		c.damage_critical_chance = c.damage_critical_chance + 5
		add_projectile_trigger_timer("data/entities/projectiles/deck/light_bullet.xml", 10, 1)
	end,
}*/