package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TOUCH_PISS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOUCH_PISS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "touch_piss.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Touch of Gold?";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "touch of piss"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes everything in a short radius into urine, including walls, creatures... and you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TOUCH_PISS();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.035, 0.035, 0.035, 0, 0, 0.1);
        this.price = 360;
        this.manaCost = 190;
        this.hasCharges = true;
        this.maxCharges = 4;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TOUCH_PISS",
	name 		= "$action_touch_piss",
	description = "$actiondesc_touch_piss",
	sprite 		= "data/ui_gfx/gun_actions/touch_piss.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/touch_piss.xml"},
	spawn_requires_flag = "card_unlocked_piss",
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5,6,7,10", -- TOUCH_PISS
	spawn_probability                 = "0,0,0,0,0.035,0.035,0.035,0.1", -- TOUCH_PISS
	price = 360,
	mana = 190,
	max_uses    = 4,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/touch_piss.xml")
	end,
}*/