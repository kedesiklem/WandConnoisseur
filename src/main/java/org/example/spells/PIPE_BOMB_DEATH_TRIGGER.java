package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_PIPE_BOMB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PIPE_BOMB_DEATH_TRIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "pipe_bomb_death_trigger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Dormant Crystal With Trigger";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "dormant crystal trigger", "dormant crystal expiration"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A crystal that explodes when caught in an explosion";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_PIPE_BOMB();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.6, 0.8, 1, 0.8, 0, 0, 0, 0, 0);
        this.price = 230;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.autoStat = false;
        this.castDelay = 30;
        this.recoil = 60.0;
        this.speed = 0.75;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addCastDelay(this.castDelay);
        // c.child_speed_multiplier = c.child_speed_multiplier * 0.75
        castState.multiplySpeed(this.speed);

        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));

        cardPool.addRecoil(this.recoil);
        if(castState.getSpeedMultiplier() > 20.0){
            castState.setSpeedMultiplier(20.0);
        }else if(castState.getSpeedMultiplier() < 0.0){
            castState.setSpeedMultiplier(0.0);
        }
    }
}

/*{
	id          = "PIPE_BOMB_DEATH_TRIGGER",
	name 		= "$action_pipe_bomb_death_trigger",
	description = "$actiondesc_pipe_bomb_death_trigger",
	sprite 		= "data/ui_gfx/gun_actions/pipe_bomb_death_trigger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/pipe_bomb_death_trigger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/pipe_bomb.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5", -- PIPE_BOMB_DEATH_TRIGGER
	spawn_probability                 = "0.6,0.8,1,0.8", -- PIPE_BOMB_DEATH_TRIGGER
	price = 230,
	mana = 20,
	max_uses    = 20,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 30
		c.child_speed_multiplier = c.child_speed_multiplier * 0.75
		c.speed_multiplier = c.speed_multiplier * 0.75
		add_projectile_trigger_death("data/entities/projectiles/deck/pipe_bomb.xml", 1)
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 60.0

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/