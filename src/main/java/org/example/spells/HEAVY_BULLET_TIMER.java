package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_HEAVY_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HEAVY_BULLET_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "heavy_bullet_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magic Bolt With Timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "magic bolt timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A powerful magical bolt that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_HEAVY_BULLET();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 10;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.5, 0.5, 0.5, 0.7, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 40;
        this.castDelay = 7;
        this.critRate = 5;
        this.spread = 5.0;
        this.recoil = 50.0;
        this.screenshake = 2.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
    }
}

/*{
	id          = "HEAVY_BULLET_TIMER",
	name 		= "$action_heavy_bullet_timer",
	description = "$actiondesc_heavy_bullet_timer",
	sprite 		= "data/ui_gfx/gun_actions/heavy_bullet_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heavy_bullet_timer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet_heavy.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "2,3,4,5,6", -- HEAVY_BULLET_TIMER
	spawn_probability                   = "0.5,0.5,0.5,0.5,0.7", -- HEAVY_BULLET_TIMER
	price = 240,
	mana = 40,
	--max_uses = 50,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 7
		c.screenshake = c.screenshake + 2.5
		c.spread_degrees = c.spread_degrees + 5.0
		c.damage_critical_chance = c.damage_critical_chance + 5
		-- c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_disintegrated.xml,"
		add_projectile_trigger_timer("data/entities/projectiles/deck/bullet_heavy.xml", 10, 1)
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 50.0
	end,
}*/