package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FISH;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FISH extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "fish.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon fish";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "FISH!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FISH();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.01, 0.01, 0.01, 0, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 80;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FISH",
	name 		= "$action_fish",
	description = "$actiondesc_fish",
	spawn_requires_flag = "card_unlocked_fish",
	sprite 		= "data/ui_gfx/gun_actions/fish.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/fish_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/fish.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5", -- FISH
	spawn_probability                 = "0.01,0.01,0.01", -- FISH
	price = 250,
	mana = 90,
	max_uses    = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/fish.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
	end,
}*/