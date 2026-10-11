package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FLAMETHROWER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FLAMETHROWER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "flamethrower.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Flamethrower";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A stream of fire!!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FLAMETHROWER();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.8, 0.9, 0.9, 0, 0.6, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 60;
        this.spread = 4.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FLAMETHROWER",
	name 		= "$action_flamethrower",
	description = "$actiondesc_flamethrower",
	sprite 		= "data/ui_gfx/gun_actions/flamethrower.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/flamethrower_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/flamethrower.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,6", -- FLAMETHROWER
	spawn_probability                 = "0.8,0.9,0.9,0.6", -- FLAMETHROWER
	price = 220,
	mana = 20,
	max_uses = 60,
	custom_xml_file = "data/entities/misc/custom_cards/flamethrower.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/flamethrower.xml")
		c.spread_degrees = c.spread_degrees + 4.0
	end,
}*/