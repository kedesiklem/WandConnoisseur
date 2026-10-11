package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SLOW_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SLOW_BULLET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "slow_bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Energy Orb";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "orb"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A slow but powerful orb of energy";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SLOW_BULLET();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 30;
        this.castDelay = 6;
        this.spread = 3.6;
        this.recoil = 20.0;
        this.screenshake = 2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SLOW_BULLET",
	name 		= "$action_slow_bullet",
	description = "$actiondesc_slow_bullet",
	sprite 		= "data/ui_gfx/gun_actions/slow_bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slow_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bullet_slow.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- SLOW_BULLET
	spawn_probability                 = "1,1,1,1", -- SLOW_BULLET
	price = 160,
	mana = 30,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/bullet_slow.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/bullet_slow.xml")
		c.fire_rate_wait = c.fire_rate_wait + 6
		c.screenshake = c.screenshake + 2
		c.spread_degrees = c.spread_degrees + 3.6
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/