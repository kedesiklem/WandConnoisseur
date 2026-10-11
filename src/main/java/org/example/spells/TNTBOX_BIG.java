package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TNTBOX_BIG;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TNTBOX_BIG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "tntbox_big.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Large Explosive Box";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "box"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a large box of explosive matter";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TNTBOX_BIG();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.8, 0, 1, 0.7, 0, 0, 0, 0, 0);
        this.price = 170;
        this.manaCost = 40;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TNTBOX_BIG",
	name 		= "$action_tntbox_big",
	description = "$actiondesc_tntbox_big",
	sprite 		= "data/ui_gfx/gun_actions/tntbox_big.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/tntbox_big.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,4,5", -- SUMMON_ROCK
	spawn_probability                 = "0.8,1,0.7", -- SUMMON_ROCK
	price = 170,
	mana = 40,
	max_uses    = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/tntbox_big.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
	end,
}*/