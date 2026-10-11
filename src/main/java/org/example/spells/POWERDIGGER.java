package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_POWERDIGGER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class POWERDIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "powerdigger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Digging Blast";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "drill bad"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "More powerful digging";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_POWERDIGGER();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 1, 0.8, 0, 0, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 0;
        this.castDelay = 1;
        this.rechargeTime = -10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "POWERDIGGER",
	name 		= "$action_powerdigger",
	description = "$actiondesc_powerdigger",
	sprite 		= "data/ui_gfx/gun_actions/powerdigger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/powerdigger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/powerdigger.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4", -- POWERDIGGER
	spawn_probability                 = "0.5,1,0.8", -- POWERDIGGER
	price = 110,
	mana = 0,
	sound_loop_tag = "sound_digger",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/powerdigger.xml")
		c.fire_rate_wait = c.fire_rate_wait + 1
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the digger reload time back to 0
	end,
}*/