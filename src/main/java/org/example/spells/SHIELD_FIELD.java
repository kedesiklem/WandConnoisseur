package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SHIELD_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SHIELD_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "shield_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Circle of shielding";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A field of protective magic";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SHIELD_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.3, 0.4, 0.5, 0.3, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 10;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SHIELD_FIELD",
	name 		= "$action_shield_field",
	description = "$actiondesc_shield_field",
	sprite 		= "data/ui_gfx/gun_actions/shield_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/shield_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/shield_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "2,3,4,5,6", -- SHIELD_FIELD
	spawn_probability                 = "0.3,0.3,0.4,0.5,0.3", -- SHIELD_FIELD
	price = 160,
	mana = 20,
	max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/shield_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/