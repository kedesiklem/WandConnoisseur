package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TOUCH_SMOKE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOUCH_SMOKE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "touch_smoke.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Touch of Smoke";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes everything in a short radius into smoke, including walls, creatures... and you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TOUCH_SMOKE();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0.1, 0, 0, 0.4);
        this.price = 350;
        this.manaCost = 230;
        this.hasCharges = true;
        this.maxCharges = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TOUCH_SMOKE",
	name 		= "$action_touch_smoke",
	description = "$actiondesc_touch_smoke",
	sprite 		= "data/ui_gfx/gun_actions/touch_smoke.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/touch_smoke.xml"},
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5,6,7,10", -- TOUCH_SMOKE
	spawn_probability                 = "0,0,0,0,0.1,0.1,0.1,0.4", -- TOUCH_SMOKE
	price = 350,
	mana = 230,
	max_uses    = 5,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/touch_smoke.xml")
	end,
}*/