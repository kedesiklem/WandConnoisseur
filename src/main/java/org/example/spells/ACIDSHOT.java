package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_ACIDSHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ACIDSHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "acidshot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Acid Ball";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A terrifying acidic projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_ACIDSHOT();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 0.9, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 20;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "ACIDSHOT",
	name 		= "$action_acidshot",
	description = "$actiondesc_acidshot",
	sprite 		= "data/ui_gfx/gun_actions/acidshot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/acidshot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/acidshot.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- ACIDSHOT
	spawn_probability                 = "1,1,0.9,0.6", -- ACIDSHOT
	price = 180,
	mana = 20,
	max_uses = 20,
	custom_xml_file = "data/entities/misc/custom_cards/acidshot.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/acidshot.xml")
		c.fire_rate_wait = c.fire_rate_wait + 10
	end,
}*/