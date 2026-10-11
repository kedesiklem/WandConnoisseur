package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CIRCLE_OIL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CIRCLE_OIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "circle_oil.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Circle of oil";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "An expanding circle of slick oil";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CIRCLE_OIL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 15;
        this.castDelay = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CIRCLE_OIL",
	name 		= "$action_circle_oil",
	description = "$actiondesc_circle_oil",
	sprite 		= "data/ui_gfx/gun_actions/circle_oil.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slimeball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/circle_oil.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4", -- CIRCLE_OIL
	spawn_probability                 = "0.4,0.4,0.4,0.4", -- CIRCLE_OIL
	price = 160,
	mana = 20,
	max_uses = 15,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/circle_oil.xml")
		c.fire_rate_wait = c.fire_rate_wait + 20
	end,
}*/