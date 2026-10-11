package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_TELEPORT_CAST;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TELEPORT_CAST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "teleport_cast.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Teleporting cast";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tp cast"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts a spell from the closest enemy";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TELEPORT_CAST();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.6, 0.6, 0, 0.6, 0.8, 1, 0, 0, 0, 0);
        this.price = 190;
        this.manaCost = 100;
        this.autoStat = false;
        this.castDelay = 20;
        this.spread = 24.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
        castState.addCastDelay(this.castDelay);
        castState.addSpread(this.spread);
    }
}

/*{
	id          = "TELEPORT_CAST",
	name 		= "$action_teleport_cast",
	description = "$actiondesc_teleport_cast",
	sprite 		= "data/ui_gfx/gun_actions/teleport_cast.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/teleport_cast.xml"},
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "1,2,4,5,6", -- TELEPORT_CAST
	spawn_probability                 = "0.6,0.6,0.6,0.8,1", -- TELEPORT_CAST
	price = 190,
	mana = 100,
	action 		= function()
		add_projectile_trigger_death("data/entities/projectiles/deck/teleport_cast.xml", 1)
		c.fire_rate_wait = c.fire_rate_wait + 20
		c.spread_degrees = c.spread_degrees + 24
	end,
}*/