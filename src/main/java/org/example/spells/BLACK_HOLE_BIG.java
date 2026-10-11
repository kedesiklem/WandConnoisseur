package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BLACK_HOLE_BIG;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BLACK_HOLE_BIG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "black_hole_big.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giga black hole";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "giga bh"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A growing orb of negative energy that destroys everything in its reach";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BLACK_HOLE_BIG();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.8, 0, 0.8, 0, 0.8, 0.8, 0, 0, 0, 0.5);
        this.price = 320;
        this.manaCost = 240;
        this.hasCharges = true;
        this.maxCharges = 6;
        this.castDelay = 80;
        this.screenshake = 10.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BLACK_HOLE_BIG",
	name 		= "$action_black_hole_big",
	description = "$actiondesc_black_hole_big",
	sprite 		= "data/ui_gfx/gun_actions/black_hole_big.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/black_hole_big_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/black_hole_big.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "1,3,5,6,10", -- BLACK_HOLE_BIG
	spawn_probability                 = "0.8,0.8,0.8,0.8,0.5", -- BLACK_HOLE_BIG
	price = 320,
	mana = 240,
	max_uses    = 6,
	custom_xml_file = "data/entities/misc/custom_cards/black_hole_big.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/black_hole_big.xml")
		c.fire_rate_wait = c.fire_rate_wait + 80
		c.screenshake = c.screenshake + 10
	end,
}*/