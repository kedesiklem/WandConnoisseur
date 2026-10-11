package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SLIMEBALL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SLIMEBALL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "slimeball.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Slimeball";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A dripping ball of poisonous slime";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SLIMEBALL();
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 0, 1, 0.7, 0, 0, 0, 0, 0, 0);
        this.price = 130;
        this.manaCost = 20;
        this.castDelay = 10;
        this.spread = 4.0;
        this.speed = 1.1;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SLIMEBALL",
	name 		= "$action_slimeball",
	description = "$actiondesc_slimeball",
	sprite 		= "data/ui_gfx/gun_actions/slimeball.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slimeball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/slime.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,3,4", -- SLIMEBALL
	spawn_probability                 = "1,1,0.7", -- SLIMEBALL
	price = 130,
	mana = 20,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/slimeball.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/slime.xml")
		c.spread_degrees = c.spread_degrees + 4.0
		c.fire_rate_wait = c.fire_rate_wait + 10
		c.speed_multiplier = c.speed_multiplier * 1.1

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/