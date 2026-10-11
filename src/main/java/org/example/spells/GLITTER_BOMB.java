package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_GLITTER_BOMB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GLITTER_BOMB extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "glitter_bomb.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Glitter Bomb";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a bomb that explodes into volatile fragments";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_GLITTER_BOMB();
        this.spawnProbabilities = new SpawnProbabilities(0.8, 0.9, 0.8, 0.7, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 70;
        this.hasCharges = true;
        this.maxCharges = 16;
        this.castDelay = 50;
        this.spread = 12.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "GLITTER_BOMB",
	name 		= "$action_glitter_bomb",
	description = "$actiondesc_glitter_bomb",
	sprite 		= "data/ui_gfx/gun_actions/glitter_bomb.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/dynamite_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/glitter_bomb.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3,4", -- GLITTER_BOMB
	spawn_probability                 = "0.8,0.9,0.8,0.7,0.6", -- GLITTER_BOMB
	price = 200,
	mana = 70,
	max_uses	= 16,
	custom_xml_file = "data/entities/misc/custom_cards/glitter_bomb.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/glitter_bomb.xml")
		c.fire_rate_wait = c.fire_rate_wait + 50
		c.spread_degrees = c.spread_degrees + 12.0
	end,
}*/