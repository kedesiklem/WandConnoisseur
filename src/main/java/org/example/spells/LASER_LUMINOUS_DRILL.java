package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_LUMINOUS_DRILL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LASER_LUMINOUS_DRILL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "luminous_drill_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Luminous Drill With Timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "lumi timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A pinpointed, short-ranged beam of concentrated light that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LUMINOUS_DRILL();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 4;
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 1, 0, 0, 0, 0.2, 0, 0, 0, 0.1);
        this.price = 220;
        this.manaCost = 30;
        this.autoStat = false;
        this.castDelay = -35;
        this.rechargeTime = -10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
        castState.addCastDelay(this.castDelay);
        cardPool.addRechargeTime(this.rechargeTime);
    }
}

/*{
	id          = "LASER_LUMINOUS_DRILL",
	name 		= "$action_luminous_drill_timer",
	description = "$actiondesc_luminous_drill_timer",
	sprite 		= "data/ui_gfx/gun_actions/luminous_drill_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chainsaw_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/luminous_drill.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2,6,10", -- LASER_LUMINOUS_DRILL
	spawn_probability                 = "1,1,0.2,0.1", -- LASER_LUMINOUS_DRILL
	price = 220,
	mana = 30,
	--max_uses = 1000,
	sound_loop_tag = "sound_digger",
	action 		= function()
		add_projectile_trigger_timer("data/entities/projectiles/deck/luminous_drill.xml",4,1)
		c.fire_rate_wait = c.fire_rate_wait - 35
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the digger reload time back to 0
	end,
}*/