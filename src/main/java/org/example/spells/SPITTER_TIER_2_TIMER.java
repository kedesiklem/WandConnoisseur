package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_SPITTER_TIER_2;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPITTER_TIER_2_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "spitter_green_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Large spitter bolt with timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "large spitter timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A more powerful version of Spitter Bolt that casts another spell after a timer runs out";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SPITTER_TIER_2();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 40;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 0.5, 0.5, 1, 0, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 30;
        this.autoStat = false;
        this.castDelay = -2;
        this.spread = 7.5;
        this.screenshake = 1.1;
        // c.dampening = 0.2
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
        castState.addCastDelay(this.castDelay);
        castState.addScreenshake(this.screenshake);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "SPITTER_TIER_2_TIMER",
	name 		= "$action_spitter_tier_2_timer",
	description = "$actiondesc_spitter_tier_2_timer",
	sprite 		= "data/ui_gfx/gun_actions/spitter_green_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spitter_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/spitter_tier_2.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5", -- SPITTER_TIER_2_TIMER
	spawn_probability                 = "0.5,0.5,0.5,1", -- SPITTER_TIER_2_TIMER
	price = 220,
	mana = 30,
	--max_uses = 120,
	action 		= function()
		add_projectile_trigger_timer("data/entities/projectiles/deck/spitter_tier_2.xml", 40, 1)
		-- damage = 0.1
		c.fire_rate_wait = c.fire_rate_wait - 2
		c.screenshake = c.screenshake + 1.1
		c.dampening = 0.2
		c.spread_degrees = c.spread_degrees + 7.5
	end,
}*/