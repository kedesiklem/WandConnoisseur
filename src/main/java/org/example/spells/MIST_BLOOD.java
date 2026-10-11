package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MIST_BLOOD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MIST_BLOOD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "mist_blood.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Blood Mist";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A cloud of blood mist";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MIST_BLOOD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 40;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MIST_BLOOD",
	name 		= "$action_mist_blood",
	description = "$actiondesc_mist_blood",
	sprite 		= "data/ui_gfx/gun_actions/mist_blood.png",
    sprite_unidentified = "data/ui_gfx/gun_actions/slimeball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/mist_blood.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- MIST_BLOOD
	spawn_probability                 = "0.4,0.4,0.4,0.4", -- MIST_BLOOD
	price = 120,
	mana = 40,
	max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/mist_blood.xml")
		c.fire_rate_wait = c.fire_rate_wait + 10
	end,
}*/