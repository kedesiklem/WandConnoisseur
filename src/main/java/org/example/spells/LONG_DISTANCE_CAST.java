package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_LONG_DISTANCE_CAST;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LONG_DISTANCE_CAST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "long_distance_cast.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Long-distance cast";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "ldc"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts a spell some distance away from the caster";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LONG_DISTANCE_CAST();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0.7, 0.8, 0, 0.6, 0.3, 0.4, 0, 0, 0, 0);
        this.price = 90;
        this.manaCost = 0;
        this.autoStat = false;
        this.castDelay = -5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
        castState.addCastDelay(this.castDelay);
    }
}

/*{
	id          = "LONG_DISTANCE_CAST",
	name 		= "$action_long_distance_cast",
	description = "$actiondesc_long_distance_cast",
	sprite 		= "data/ui_gfx/gun_actions/long_distance_cast.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/long_distance_cast.xml"},
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "0,1,2,4,5,6", -- LONG_DISTANCE_CAST
	spawn_probability                 = "0.6,0.7,0.8,0.6,0.3,0.4", -- LONG_DISTANCE_CAST
	price = 90,
	mana = 0,
	action 		= function()
		add_projectile_trigger_death("data/entities/projectiles/deck/long_distance_cast.xml", 1)
		c.fire_rate_wait = c.fire_rate_wait - 5
	end,
}*/