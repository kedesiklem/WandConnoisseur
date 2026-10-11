package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BLACK_HOLE_GIGA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BLACK_HOLE_GIGA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "black_hole_giga.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Omega Black Hole";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "omega bh"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Even light dies eventually...";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BLACK_HOLE_GIGA();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 600;
        this.manaCost = 500;
        this.hasCharges = true;
        this.maxCharges = 6;
        this.neverUnlimited = true;
        this.castDelay = 120;
        this.rechargeTime = 100;
        this.screenshake = 40.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // local black_holes = EntityGetWithTag( "black_hole_giga" )
        // if ( #black_holes < 3 ) then
        castState.addProjectile(this.relatedProjectile.clone());
        // end
    }
}

/*{
	id          = "BLACK_HOLE_GIGA",
	name 		= "$action_black_hole_giga",
	description = "$actiondesc_black_hole_giga",
	sprite 		= "data/ui_gfx/gun_actions/black_hole_giga.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/black_hole_big_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/black_hole_giga.xml"},
	spawn_requires_flag = "card_unlocked_black_hole",
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "10", -- BLACK_HOLE_BIG
	spawn_probability                 = "1", -- BLACK_HOLE_BIG
	price = 600,
	mana = 500,
	max_uses    = 6,
	never_unlimited = true,
	custom_xml_file = "data/entities/misc/custom_cards/black_hole_giga.xml",
	action 		= function()
		local black_holes = EntityGetWithTag( "black_hole_giga" )

		if ( #black_holes < 3 ) then
			add_projectile("data/entities/projectiles/deck/black_hole_giga.xml")
			c.fire_rate_wait = c.fire_rate_wait + 120
			current_reload_time = current_reload_time + 100
			c.screenshake = c.screenshake + 40
		end
	end,
}*/