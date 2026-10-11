package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TOUCH_ALCOHOL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOUCH_ALCOHOL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "touch_alcohol.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Touch of Spirits";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes everything in a short radius into alcohol, including walls, creatures... and you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TOUCH_ALCOHOL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0.1, 0, 0, 0.4);
        this.price = 360;
        this.manaCost = 240;
        this.hasCharges = true;
        this.maxCharges = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TOUCH_ALCOHOL",
	name 		= "$action_touch_alcohol",
	description = "$actiondesc_touch_alcohol",
	sprite 		= "data/ui_gfx/gun_actions/touch_alcohol.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/touch_alcohol.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5,6,7,10", -- TOUCH_ALCOHOL
	spawn_probability                 = "0,0,0,0,0.1,0.1,0.1,0.4", -- TOUCH_ALCOHOL
	price = 360,
	mana = 240,
	max_uses    = 5,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/touch_alcohol.xml")
	end,
}*/