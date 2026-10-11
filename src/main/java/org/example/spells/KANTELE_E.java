package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_KANTELE_E;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class KANTELE_E extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "kantele_e.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Kantele - Note E";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "kantele e"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Music for your ears!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_KANTELE_E();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 10;
        this.manaCost = 1;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "KANTELE_E",
	name 		= "$action_kantele_e",
	description = "$actiondesc_kantele_e",
	spawn_requires_flag = "card_unlocked_kantele",
	sprite 		= "data/ui_gfx/gun_actions/kantele_e.png",
	related_projectiles	= {"data/entities/projectiles/deck/kantele/kantele_e.xml"},
	type 		= ACTION_TYPE_OTHER,
	spawn_level                       = "10", -- OCARINA_E
	spawn_probability                 = "0", -- OCARINA_E
	price = 10,
	mana = 1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/kantele/kantele_e.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/