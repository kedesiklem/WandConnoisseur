package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SWAPPER_PROJECTILE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SWAPPER_PROJECTILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "swapper_projectile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Swapper";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "It was theorized that the source of qualia would be transferred …But it turns out it was the whole body all along.";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SWAPPER_PROJECTILE();
        this.spawnProbabilities = new SpawnProbabilities(0.05, 0.05, 0.1, 0, 0.4, 0.4, 0.1, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 5;
        this.castDelay = 3;
        this.screenshake = 0.5;
        this.spread = -2.0;
        this.critRate = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SWAPPER_PROJECTILE",
	name 		= "$action_swapper_projectile",
	description = "$actiondesc_swapper_projectile",
	sprite 		= "data/ui_gfx/gun_actions/swapper_projectile.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/light_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/swapper.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- SWAPPER_PROJECTILE
	spawn_probability                 = "0.05,0.05,0.1,0.4,0.4,0.1", -- SWAPPER_PROJECTILE
	price = 100,
	mana = 5,
	--max_uses = -1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/swapper.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 0.5
		c.spread_degrees = c.spread_degrees - 2.0
		c.damage_critical_chance = c.damage_critical_chance + 5
	end,
}*/