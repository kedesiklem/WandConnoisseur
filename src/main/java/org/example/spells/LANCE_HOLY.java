package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LANCE_HOLY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LANCE_HOLY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "lance_holy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Holy Lance";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fast-flying, penetrating lance that glows with power";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LANCE_HOLY();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.5, 0, 0.8, 1, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 120;
        this.castDelay = 30;
        this.spread = -10.0;
        this.setRecoil = true;
        this.recoil = 60.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LANCE_HOLY",
	name 		= "$action_holy",
	description = "$actiondesc_holy",
	sprite 		= "data/ui_gfx/gun_actions/lance_holy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/lance_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/lance_holy.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,5,6", -- LANCE
	spawn_probability                 = "0.5,0.8,1", -- LANCE
	price = 250,
	mana = 120,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/lance_holy.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/lance_holy.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 30
		c.spread_degrees = c.spread_degrees - 10
		shot_effects.recoil_knockback = 60.0
	end,
}*/