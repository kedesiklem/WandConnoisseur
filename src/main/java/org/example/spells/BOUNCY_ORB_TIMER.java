package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_BOUNCY_ORB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOUNCY_ORB_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bouncy_orb_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Energy sphere with timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "energy sphere timer", "sphere timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fast, arcing projectile that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BOUNCY_ORB();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 200;
        this.spawnProbabilities = new SpawnProbabilities(0.5, 0, 0.5, 0, 0.5, 0, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 50;
        this.autoStat = false;
        this.castDelay = 10;
        this.setRecoil = true;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
        castState.addCastDelay(this.castDelay);
        cardPool.setRecoil(this.recoil);
    }
}

/*{
	id          = "BOUNCY_ORB_TIMER",
	name 		= "$action_bouncy_orb_timer",
	description = "$actiondesc_bouncy_orb_timer",
	sprite 		= "data/ui_gfx/gun_actions/bouncy_orb_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/disc_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bouncy_orb.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2,4", -- BOUNCY_ORB_TIMER
	spawn_probability                 = "0.5,0.5,0.5", -- BOUNCY_ORB_TIMER
	price = 150,
	mana = 50,
	--max_uses = 40,
	action 		= function()
		add_projectile_trigger_timer("data/entities/projectiles/deck/bouncy_orb.xml",200,1)
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 10
		shot_effects.recoil_knockback = 20.0
	end,
}*/