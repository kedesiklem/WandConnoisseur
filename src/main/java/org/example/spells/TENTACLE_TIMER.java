package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_TENTACLE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TENTACLE_TIMER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "tentacle_timer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Tentacle with timer";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tentacule timer", "tentacle timer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Calls a terrifying appendage from another dimension! Comes with a timer";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TENTACLE();
        this.triggerType = Projectile.TriggerType.timer;
        this.timerLength = 20;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.6, 0.8, 0.6, 0.7, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 20;
        this.autoStat = false;
        this.castDelay = 40;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.timerLength, this.triggerType));
        castState.addCastDelay(this.castDelay);
    }
}

/*{
	id          = "TENTACLE_TIMER",
	name 		= "$action_tentacle_timer",
	description = "$actiondesc_tentacle_timer",
	spawn_requires_flag = "card_unlocked_tentacle",
	sprite 		= "data/ui_gfx/gun_actions/tentacle_timer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/tentacle_timer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/tentacle.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,6", -- TENTACLE_TIMER
	spawn_probability                 = "0.6,0.8,0.6,0.7", -- TENTACLE_TIMER
	price = 250,
	mana = 20,
	--max_uses = 40,
	custom_xml_file = "data/entities/misc/custom_cards/tentacle_timer.xml",
	action 		= function()
		add_projectile_trigger_timer("data/entities/projectiles/deck/tentacle.xml",20,1)
		c.fire_rate_wait = c.fire_rate_wait + 40
	end,
}*/