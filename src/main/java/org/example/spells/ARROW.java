package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ARROW;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ARROW extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "arrow.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Arrow";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons an arrow";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ARROW();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 0, 0.6, 0.3, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 15;
        this.castDelay = 10;
        this.spread = -20.0;
        this.setRecoil = true;
        this.recoil = 30.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ARROW",
	name 		= "$action_arrow",
	description = "$actiondesc_arrow",
	sprite 		= "data/ui_gfx/gun_actions/arrow.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/arrow_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/arrow.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,4,5", -- ARROW
	spawn_probability                 = "1,1,0.6,0.3", -- ARROW
	price = 140,
	mana = 15,
	--max_uses = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/arrow.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 10
		c.spread_degrees = c.spread_degrees - 20
		shot_effects.recoil_knockback = 30.0
	end,
}*/