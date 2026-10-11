package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_DISC_BULLET_BIG;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DISC_BULLET_BIG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "disc_bullet_big.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giga disc projectile";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "giga saw"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large, serrated disc with a curious flight pattern";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DISC_BULLET_BIG();
        this.spawnProbabilities = new SpawnProbabilities(0.6, 0, 0.7, 0, 0.8, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 38;
        this.castDelay = 20;
        this.spread = 3.4;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "DISC_BULLET_BIG",
	name 		= "$action_disc_bullet_big",
	description = "$actiondesc_disc_bullet_big",
	sprite 		= "data/ui_gfx/gun_actions/disc_bullet_big.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/disc_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/disc_bullet_big.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2,4", -- DISC_BULLET_BIG
	spawn_probability                 = "0.6,0.7,0.8", -- DISC_BULLET_BIG
	price = 180,
	mana = 38,
	--max_uses = 40,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/disc_bullet_big.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 20
		c.spread_degrees = c.spread_degrees + 3.4
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/