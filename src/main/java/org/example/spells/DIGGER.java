package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_DIGGER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DIGGER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "digger.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Digging Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "drill"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A bolt that is ideal for mining operations";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DIGGER();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0.5, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 70;
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
	id          = "DIGGER",
	name 		= "$action_digger",
	description = "$actiondesc_digger",
	sprite 		= "data/ui_gfx/gun_actions/digger.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/digger_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/digger.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2", -- DIGGER
	spawn_probability                 = "1,0.5", -- DIGGER
	price = 70,
	mana = 0,
	sound_loop_tag = "sound_digger",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/digger.xml")
		c.fire_rate_wait = c.fire_rate_wait + 1
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the digger reload time back to 0
	end,
}*/