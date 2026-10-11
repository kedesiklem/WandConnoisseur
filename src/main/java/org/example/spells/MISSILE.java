package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MISSILE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MISSILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "missile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon missile";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A missile!!!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MISSILE();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.5, 1, 0, 1, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 60;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.rechargeTime = 30;
        this.spread = 3.0;
        this.recoil = 60.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MISSILE",
	name 		= "$action_missile",
	description = "$actiondesc_missile",
	sprite 		= "data/ui_gfx/gun_actions/missile.png",
	related_projectiles	= {"data/entities/projectiles/deck/rocket_player.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,5", -- MISSILE
	spawn_probability                        = "0.5,0.5,1,1", -- MISSILE
	price = 200,
	mana = 60,
	max_uses    = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/rocket_player.xml")
		current_reload_time = current_reload_time + 30
		c.spread_degrees = c.spread_degrees + 3.0
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 60.0
	end,
}*/