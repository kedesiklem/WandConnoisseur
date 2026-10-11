package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LIGHTNING;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIGHTNING extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "lightning.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Lightning Bolt";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The primordial force of nature";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LIGHTNING();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0.9, 0, 0, 0.7, 1, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 70;
        this.castDelay = 50;
        this.setRecoil = true;
        this.recoil = 180.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LIGHTNING",
	name 		= "$action_lightning",
	description = "$actiondesc_lightning",
	sprite 		= "data/ui_gfx/gun_actions/lightning.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/lightning_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/lightning.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,5,6", -- LIGHTNING
	spawn_probability                 = "1,0.9,0.7,1", -- LIGHTNING
	price = 250,
	mana = 70,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/electric_charge.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/lightning.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		shot_effects.recoil_knockback = 180.0
	end,
}*/