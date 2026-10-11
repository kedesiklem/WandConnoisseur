package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_PURPLE_EXPLOSION_FIELD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PURPLE_EXPLOSION_FIELD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "purple_explosion_field.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Glittering field";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Small explosions appear randomly over a large area";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_PURPLE_EXPLOSION_FIELD();
        this.spawnProbabilities = new SpawnProbabilities(0.7, 1, 0.7, 0, 0.5, 0.5, 0.3, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 10;
        this.multiplySpeed = false;
        this.speed = -2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "PURPLE_EXPLOSION_FIELD",
	name 		= "$action_purple_explosion_field",
	description = "$actiondesc_purple_explosion_field",
	sprite 		= "data/ui_gfx/gun_actions/purple_explosion_field.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/purple_explosion_field.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- PURPLE_EXPLOSION_FIELD
	spawn_probability                 = "0.7,1,0.7,0.5,0.5,0.3", -- PURPLE_EXPLOSION_FIELD
	price = 160,
	mana = 90,
	max_uses = 20,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/purple_explosion_field.xml")
		c.fire_rate_wait = c.fire_rate_wait + 10
		c.speed_multiplier = c.speed_multiplier - 2

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/