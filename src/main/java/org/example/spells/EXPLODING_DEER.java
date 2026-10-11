package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_EXPLODING_DEER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class EXPLODING_DEER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "exploding_deer.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon deercoy";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "deer"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a seemingly-innocuous deer";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_EXPLODING_DEER();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.6, 0.6, 0.6, 0, 0, 0, 0, 0);
        this.price = 170;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 80;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "EXPLODING_DEER",
	name 		= "$action_exploding_deer",
	description = "$actiondesc_exploding_deer",
	spawn_requires_flag = "card_unlocked_exploding_deer",
	sprite 		= "data/ui_gfx/gun_actions/exploding_deer.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/exploding_deer_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/exploding_deer.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5", -- EXPLODING_DEER
	spawn_probability                 = "0.6,0.6,0.6", -- EXPLODING_DEER
	price = 170,
	mana = 120,
	max_uses    = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/exploding_deer.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
	end,
}*/