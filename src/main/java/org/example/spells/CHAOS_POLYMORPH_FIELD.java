package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CHAOS_POLYMORPH_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CHAOS_POLYMORPH_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "chaos_polymorph_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Circle of unstable metamorphosis";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "circle of chaotic poly"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A field of transformative magic";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CHAOS_POLYMORPH_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0.3, 0.5, 0.6, 0.3, 0.3, 0, 0, 0, 0);
        this.price = 200;
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
	id          = "CHAOS_POLYMORPH_FIELD",
	name 		= "$action_chaos_polymorph_field",
	description = "$actiondesc_chaos_polymorph_field",
	sprite 		= "data/ui_gfx/gun_actions/chaos_polymorph_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chaos_polymorph_field_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/chaos_polymorph_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "1,2,3,4,5,6", -- CHAOS_POLYMORPH_FIELD
	spawn_probability                 = "0.3,0.3,0.5,0.6,0.3,0.3", -- CHAOS_POLYMORPH_FIELD
	price = 200,
	mana = 20,
	max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/chaos_polymorph_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/