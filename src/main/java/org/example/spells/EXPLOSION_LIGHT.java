package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_EXPLOSION_LIGHT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class EXPLOSION_LIGHT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "explosion_light.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Magical Explosion";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A large explosion that doesn't damage the ground";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_EXPLOSION_LIGHT();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.5, 1, 0, 0.7, 0.5, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 80;
        this.castDelay = 3;
        this.screenshake = 2.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "EXPLOSION_LIGHT",
	name 		= "$action_explosion_light",
	description = "$actiondesc_explosion_light",
	sprite 		= "data/ui_gfx/gun_actions/explosion_light.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosion_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/explosion_light.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "2,3,5,6", -- EXPLOSION
	spawn_probability                 = "0.5,1,0.7,0.5", -- EXPLOSION
	price = 160,
	mana = 80,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/explosion_light.xml",
	is_dangerous_blast = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/explosion_light.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 2.5
	end,
}*/