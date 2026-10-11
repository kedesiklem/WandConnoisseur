package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TOUCH_BLOOD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOUCH_BLOOD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "touch_blood.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Touch of Blood";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes everything in a short radius into blood, including walls, creatures... and you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TOUCH_BLOOD();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0.1, 0, 0, 0.5);
        this.price = 390;
        this.manaCost = 270;
        this.hasCharges = true;
        this.maxCharges = 3;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TOUCH_BLOOD",
	name 		= "$action_touch_blood",
	description = "$actiondesc_touch_blood",
	sprite 		= "data/ui_gfx/gun_actions/touch_blood.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/touch_blood.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5,6,7,10", -- TOUCH_BLOOD
	spawn_probability                 = "0,0,0,0,0.1,0.1,0.1,0.5", -- TOUCH_BLOOD
	price = 390,
	mana = 270,
	max_uses    = 3,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/touch_blood.xml")
	end,
}*/