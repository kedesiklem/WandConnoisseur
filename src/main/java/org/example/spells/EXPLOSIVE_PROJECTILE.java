package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class EXPLOSIVE_PROJECTILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "explosive_projectile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Explosive projectile";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "ep"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile more destructive to the environment";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 1, 1, 0.8, 0, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 30;
        this.castDelay = 40;
        this.recoil = 30.0;
        this.speed = 0.75;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        /*c.explosion_radius = c.explosion_radius + 15.0
		c.damage_explosion_add = c.damage_explosion_add + 0.2*/
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "EXPLOSIVE_PROJECTILE",
	name 		= "$action_explosive_projectile",
	description = "$actiondesc_explosive_projectile",
	sprite 		= "data/ui_gfx/gun_actions/explosive_projectile.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- EXPLOSIVE_PROJECTILE
	spawn_probability                 = "1,1,0.8", -- EXPLOSIVE_PROJECTILE
	price = 120,
	mana = 30,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/explosive_projectile.xml",
	action 		= function()
		c.explosion_radius = c.explosion_radius + 15.0
		c.damage_explosion_add = c.damage_explosion_add + 0.2
		c.fire_rate_wait   = c.fire_rate_wait + 40
		c.speed_multiplier = c.speed_multiplier * 0.75
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 30.0

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/