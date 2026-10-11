package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FREEZE_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FREEZE_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "freeze_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Circle of stillness";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "circle of freeze"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A field of freezing magic";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FREEZE_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0.3, 0, 0.6, 0, 0.7, 0.3, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 50;
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
	id          = "FREEZE_FIELD",
	name 		= "$action_freeze_field",
	description = "$actiondesc_freeze_field",
	sprite 		= "data/ui_gfx/gun_actions/freeze_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/freeze_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,2,4,5", -- FREEZE_FIELD
	spawn_probability                 = "0.3,0.6,0.7,0.3", -- FREEZE_FIELD
	price = 200,
	mana = 50,
	max_uses = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/freeze_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/