package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FUNKY_SPELL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FUNKY_SPELL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "machinegun_bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "???";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "???";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FUNKY_SPELL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0.1, 0, 0, 0, 0.1);
        this.price = 50;
        this.manaCost = 5;
        this.castDelay = -3;
        this.spread = 2;
        this.critRate = 1;
        this.screenshake = 0.2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FUNKY_SPELL",
	name 		= "???",
	description = "???",
	sprite 		= "data/ui_gfx/gun_actions/machinegun_bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/light_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/machinegun_bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_requires_flag = "card_unlocked_funky",
	spawn_level                       = "6,10", -- LIGHT_BULLET
	spawn_probability                 = "0.1,0.1", -- LIGHT_BULLET
	price = 50,
	mana = 5,
	--max_uses = -1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/machinegun_bullet.xml")
		c.fire_rate_wait = c.fire_rate_wait - 3
		c.screenshake = c.screenshake + 0.2
		c.spread_degrees = c.spread_degrees + 2.0
		c.damage_critical_chance = c.damage_critical_chance + 1
	end,
}*/