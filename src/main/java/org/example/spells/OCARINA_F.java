package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_OCARINA_F;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class OCARINA_F extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.other;
    static final String staticImageFile = "ocarina_f.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Ocarina - Note F";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "ocarina f", "f"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Music for your ears!";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_OCARINA_F();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 10;
        this.manaCost = 1;
        this.castDelay = 15;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "OCARINA_F",
	name 		= "$action_ocarina_f",
	description = "$actiondesc_ocarina_f",
	spawn_requires_flag = "card_unlocked_ocarina",
	sprite 		= "data/ui_gfx/gun_actions/ocarina_f.png",
	related_projectiles	= {"data/entities/projectiles/deck/ocarina/ocarina_f.xml"},
	type 		= ACTION_TYPE_OTHER,
	spawn_level                       = "10", -- OCARINA_F
	spawn_probability                 = "0", -- OCARINA_F
	price = 10,
	mana = 1,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/ocarina/ocarina_f.xml")
		c.fire_rate_wait = c.fire_rate_wait + 15
	end,
}*/