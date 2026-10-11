package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_SLOW_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SLOW_BULLET_TRIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "slow_bullet_trigger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Energy Orb With A Trigger";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "energy orb trigger", "orb trigger"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A slow but powerful orb of energy that casts another spell upon collision";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SLOW_BULLET();
        this.triggerType = Projectile.TriggerType.trigger;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.5, 0.5, 0.5, 1, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 50;
        this.autoStat = false;
        this.castDelay = 25;
        this.spread = 10.0;
        this.recoil = 20.0;
        this.screenshake = 2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addCastDelay(this.castDelay);
        castState.addSpread(this.spread);
        castState.addScreenshake(this.screenshake);

        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));

        cardPool.addRecoil(this.recoil);
    }
}

/*{
	id          = "SLOW_BULLET_TRIGGER",
	name 		= "$action_slow_bullet_trigger",
	description = "$actiondesc_slow_bullet_trigger",
	sprite 		= "data/ui_gfx/gun_actions/slow_bullet_trigger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slow_bullet_trigger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet_slow.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5", -- SLOW_BULLET_TRIGGER
	spawn_probability                 = "0.5,0.5,0.5,0.5,1", -- SLOW_BULLET_TRIGGER
	price = 200,
	mana = 50,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/bullet_slow.xml",
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 25
		c.screenshake = c.screenshake + 2
		c.spread_degrees = c.spread_degrees + 10
		add_projectile_trigger_hit_world("data/entities/projectiles/deck/bullet_slow.xml", 1)
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/