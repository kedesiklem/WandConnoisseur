package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_PROJECTILE_GRAVITY_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_GRAVITY_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "projectile_gravity_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Projectile gravity field";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Projectiles caught within the field are attracted towards its center";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_PROJECTILE_GRAVITY_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.6, 0, 0, 0.3, 0.3, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 120;
        this.hasCharges = true;
        this.maxCharges = 6;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "PROJECTILE_GRAVITY_FIELD",
	name 		= "$action_projectile_gravity_field",
	description = "$actiondesc_projectile_gravity_field",
	sprite 		= "data/ui_gfx/gun_actions/projectile_gravity_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chaos_polymorph_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/projectile_gravity_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "2,5,6", -- PROJECTILE_GRAVITY_FIELD
	spawn_probability                 = "0.6,0.3,0.3", -- PROJECTILE_GRAVITY_FIELD
	price = 250,
	mana = 120,
	max_uses = 6,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/projectile_gravity_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/