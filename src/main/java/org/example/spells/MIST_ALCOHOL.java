package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MIST_ALCOHOL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MIST_ALCOHOL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "mist_alcohol.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Mist Of Spirits";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "mist of spirit", "mist of alcohol", "mist alcohol"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A cloud of potent alcohol";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MIST_ALCOHOL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 40;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MIST_ALCOHOL",
	name 		= "$action_mist_alcohol",
	description = "$actiondesc_mist_alcohol",
	sprite 		= "data/ui_gfx/gun_actions/mist_alcohol.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slimeball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/mist_alcohol.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4", -- MIST_ALCOHOL
	spawn_probability                 = "0.4,0.4,0.4,0.4", -- MIST_ALCOHOL
	price = 80,
	mana = 40,
	--max_uses = 10,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/mist_alcohol.xml")
		c.fire_rate_wait = c.fire_rate_wait + 10
	end,
}*/