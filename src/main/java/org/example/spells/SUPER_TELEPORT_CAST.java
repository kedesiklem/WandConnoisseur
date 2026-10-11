package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_SUPER_TELEPORT_CAST;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SUPER_TELEPORT_CAST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "super_teleport_cast.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Warp cast";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a spell immediately jump a long distance, stopped by walls";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SUPER_TELEPORT_CAST();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0.6, 0.8, 0.8, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 20;
        this.autoStat = false;
        this.castDelay = 10;
        this.spread = -6;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
        castState.addCastDelay(this.castDelay);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "SUPER_TELEPORT_CAST",
	name 		= "$action_super_teleport_cast",
	description = "$actiondesc_super_teleport_cast",
	sprite 		= "data/ui_gfx/gun_actions/super_teleport_cast.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/super_teleport_cast.xml"},
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "2,4,5,6", -- SUPER_TELEPORT_CAST
	spawn_probability                 = "0.2,0.6,0.8,0.8", -- SUPER_TELEPORT_CAST
	price = 160,
	mana = 20,
	action 		= function()
		add_projectile_trigger_death("data/entities/projectiles/deck/super_teleport_cast.xml", 1)
		c.fire_rate_wait = c.fire_rate_wait + 10
		c.spread_degrees = c.spread_degrees - 6
	end,
}*/