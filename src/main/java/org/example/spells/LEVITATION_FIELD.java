package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LEVITATION_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LEVITATION_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "levitation_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Circle of buoyancy";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "cob"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A field of levitative magic";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LEVITATION_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0.6, 0.6, 0.3, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 10;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LEVITATION_FIELD",
	name 		= "$action_levitation_field",
	description = "$actiondesc_levitation_field",
	sprite 		= "data/ui_gfx/gun_actions/levitation_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/levitation_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/levitation_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- LEVITATION_FIELD
	spawn_probability                 = "0.3,0.6,0.6,0.3", -- LEVITATION_FIELD
	price = 120,
	mana = 10,
	max_uses = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/levitation_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/