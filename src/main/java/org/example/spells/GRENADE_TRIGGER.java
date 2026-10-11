package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_GRENADE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GRENADE_TRIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "grenade_trigger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Firebolt With Trigger";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "firebolt trigger"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A bouncy, explosive bolt that that casts another spell upon collision";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GRENADE();
        this.triggerType = Projectile.TriggerType.trigger;
        this.spawnProbabilities = new SpawnProbabilities(0.5, 0.5, 0.2, 0.5, 0.5, 0.1, 0, 0, 0, 0, 0);
        this.price = 210;
        this.manaCost = 50;
        this.hasCharges = true;
        this.maxCharges = 25;
        this.autoStat = false;
        this.setRecoil = true;
        this.castDelay = 30;
        this.recoil = 80.0;
        this.screenshake = 4.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addCastDelay(this.castDelay);
        castState.addScreenshake(this.screenshake);
        // c.child_speed_multiplier = c.child_speed_multiplier * 0.75
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));

        cardPool.setRecoil(this.recoil);
    }
}

/*{
	id          = "GRENADE_TRIGGER",
	name 		= "$action_grenade_trigger",
	description = "$actiondesc_grenade_trigger",
	sprite 		= "data/ui_gfx/gun_actions/grenade_trigger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/grenade_trigger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/grenade.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "0,1,2,3,4,5", -- GRENADE_TRIGGER
	spawn_probability                   = "0.5,0.5,0.2,0.5,0.5,1", -- GRENADE_TRIGGER
	price = 210,
	max_uses    = 25,
	custom_xml_file = "data/entities/misc/custom_cards/grenade_trigger.xml",
	mana = 50,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 30
		c.screenshake = c.screenshake + 4.0
		--current_reload_time = current_reload_time + 60
		c.child_speed_multiplier = c.child_speed_multiplier * 0.75
		add_projectile_trigger_hit_world("data/entities/projectiles/deck/grenade.xml", 1)
		shot_effects.recoil_knockback = 80.0
	end,
}*/