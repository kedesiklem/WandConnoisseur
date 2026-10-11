package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_POLLEN;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class POLLEN extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "pollen.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Pollen";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "polen"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A small, floating projectile that homes towards nearby creatures";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_POLLEN();
        this.spawnProbabilities = new SpawnProbabilities(0.6, 1, 0, 1, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 10;
        this.castDelay = 2;
        this.spread = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "POLLEN",
	name 		= "$action_pollen",
	description = "$actiondesc_pollen",
	sprite 		= "data/ui_gfx/gun_actions/pollen.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/arrow_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/pollen.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,3,4", -- ARROW
	spawn_probability                 = "0.6,1,1,0.6", -- ARROW
	price = 110,
	mana = 10,
	--max_uses = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/pollen.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 2
		c.spread_degrees = c.spread_degrees + 20
	end,
}*/