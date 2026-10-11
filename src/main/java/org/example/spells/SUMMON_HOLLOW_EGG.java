package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_SUMMON_HOLLOW_EGG;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SUMMON_HOLLOW_EGG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "summon_hollow_egg.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon hollow egg";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "hollow egg", "egg trigger", "egg expiration"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons an otherwise empty egg that casts a spell upon cracking open";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SUMMON_HOLLOW_EGG();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0.8, 0.7, 0, 0, 0.8, 0.3, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 30;
        this.autoStat = false;
        this.castDelay = -12;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
    }
}

/*{
	id          = "SUMMON_HOLLOW_EGG",
	name 		= "$action_summon_hollow_egg",
	description = "$actiondesc_summon_hollow_egg",
	sprite 		= "data/ui_gfx/gun_actions/summon_hollow_egg.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/items/pickup/egg_hollow.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,5,6", -- SUMMON_HOLLOW_EGG
	spawn_probability                 = "0.6,0.8,0.7,0.8,0.3", -- SUMMON_HOLLOW_EGG
	price = 140,
	mana = 30,
	action 		= function()
		add_projectile_trigger_death("data/entities/items/pickup/egg_hollow.xml", 1)
		c.fire_rate_wait = c.fire_rate_wait - 12
	end,
}*/