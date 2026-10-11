package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LANCE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LANCE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "lance.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Glowing Lance";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical lance that cuts through soft materials";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LANCE();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.9, 1, 0, 0, 0.8, 1, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 30;
        this.castDelay = 20;
        this.spread = -20.0;
        this.setRecoil = true;
        this.recoil = 60.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LANCE",
	name 		= "$action_lance",
	description = "$actiondesc_lance",
	sprite 		= "data/ui_gfx/gun_actions/lance.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/lance_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/lance.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,5,6", -- LANCE
	spawn_probability                 = "0.9,1,0.8,1", -- LANCE
	price = 180,
	mana = 30,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/lance.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/lance.xml")
		-- damage = 0.3
		c.fire_rate_wait = c.fire_rate_wait + 20
		c.spread_degrees = c.spread_degrees - 20
		shot_effects.recoil_knockback = 60.0
	end,
}*/