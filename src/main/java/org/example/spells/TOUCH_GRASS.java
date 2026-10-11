package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TOUCH_GRASS;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TOUCH_GRASS extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.material;
    static final String staticImageFile = "touch_grass.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Touch of Grass";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Transmutes everything in a short radius into Earth, including walls, creatures... and you. Unless…";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TOUCH_GRASS();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0.1, 0.1, 0.1, 0, 0, 0.2);
        this.price = 360;
        this.manaCost = 190;
        this.hasCharges = true;
        this.maxCharges = 4;
        this.neverUnlimited = true;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TOUCH_GRASS",
	name 		= "$action_touch_grass",
	description = "$actiondesc_touch_grass",
	sprite 		= "data/ui_gfx/gun_actions/touch_grass.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/touch_grass.xml"},
	spawn_requires_flag = "card_unlocked_touch_grass",
	type 		= ACTION_TYPE_MATERIAL,
	spawn_level                       = "1,2,3,4,5,6,7,10", -- TOUCH_GRASS
	spawn_probability                 = "0,0,0,0,0.1,0.1,0.1,0.2", -- TOUCH_GRASS
	spawn_requires_flag = "card_unlocked_touch_grass",
	price = 360,
	mana = 190,
	max_uses    = 4,
	never_unlimited = true,
	custom_xml_file = "data/entities/misc/custom_cards/touch_grass.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/touch_grass.xml")
	end,
}*/