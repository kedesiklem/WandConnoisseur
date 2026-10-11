package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_VACUUM_POWDER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class VACUUM_POWDER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "vacuum_powder.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Powder Vacuum Field";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Sucks powder-like materials nearby and releases them upon expiring";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_VACUUM_POWDER();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.7, 0, 0.3, 0.4, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 40;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "VACUUM_POWDER",
	name 		= "$action_vacuum_powder",
	description = "$actiondesc_vacuum_powder",
	sprite 		= "data/ui_gfx/gun_actions/vacuum_powder.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chaos_polymorph_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/vacuum_powder.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "2,3,5,6", -- PROJECTILE_GRAVITY_FIELD
	spawn_probability                 = "0.3,0.7,0.3,0.4", -- PROJECTILE_GRAVITY_FIELD
	price = 150,
	mana = 40,
	max_uses = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/vacuum_powder.xml")
		c.fire_rate_wait = c.fire_rate_wait + 10
	end,
}*/