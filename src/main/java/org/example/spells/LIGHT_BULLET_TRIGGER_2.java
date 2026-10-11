package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_LIGHT_BULLET_BLUE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIGHT_BULLET_TRIGGER_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "light_bullet_trigger_2.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spark Bolt With Double Trigger";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spark double trigger", "sdt"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A spark bolt that casts two new spells upon collision";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LIGHT_BULLET_BLUE();
        this.triggerType = Projectile.TriggerType.trigger;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 1, 0.5, 0, 1, 1, 0, 0, 0, 0.2);
        this.price = 250;
        this.manaCost = 15;
        this.castDelay = 4;
        this.critRate = 5;
        this.screenshake = 1.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(2, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
    }
}

/*{
	id          = "LIGHT_BULLET_TRIGGER_2",
	name 		= "$action_light_bullet_trigger_2",
	description = "$actiondesc_light_bullet_trigger_2",
	sprite 		= "data/ui_gfx/gun_actions/light_bullet_trigger_2.png",
	related_projectiles	= {"data/entities/projectiles/deck/light_bullet_blue.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                         = "2,3,5,6,10", -- LIGHT_BULLET_TRIGGER_2
	spawn_probability                   = "1,0.5,1,1,0.2", -- LIGHT_BULLET_TRIGGER_2
	price = 250,
	mana = 15,
	--max_uses = 100,
	action 		= function()
		c.fire_rate_wait = c.fire_rate_wait + 4
		c.screenshake = c.screenshake + 1
		c.damage_critical_chance = c.damage_critical_chance + 5
		add_projectile_trigger_hit_world("data/entities/projectiles/deck/light_bullet_blue.xml", 2)
	end,
}*/