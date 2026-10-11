package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_DARKFLAME;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DARKFLAME extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "darkflame.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Path of dark flame";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "podf"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A trail of dark, deadly flames";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DARKFLAME();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 1, 0, 0.9, 0.8, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 90;
        this.hasCharges = true;
        this.maxCharges = 60;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "DARKFLAME",
	name 		= "$action_darkflame",
	description = "$actiondesc_darkflame",
	sprite 		= "data/ui_gfx/gun_actions/darkflame.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/darkflame_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/darkflame.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,5,6", -- DARKFLAME
	spawn_probability                 = "1,0.9,0.8", -- DARKFLAME
	price = 180,
	mana = 90,
	custom_xml_file = "data/entities/misc/custom_cards/darkflame.xml",
	max_uses    = 60,
	action 		= function()
		add_projectile("data/entities/projectiles/darkflame.xml")
		c.fire_rate_wait = c.fire_rate_wait + 20
	end,
}*/