package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BOMB;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOMB extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bomb.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Bomb";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a bomb that destroys ground very efficiently";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BOMB();
        this.spawnProbabilities = new SpawnProbabilities(1, 1, 1, 1, 0.5, 0.5, 0.1, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 25;
        this.hasCharges = true;
        this.maxCharges = 3;
        this.castDelay = 100;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BOMB",
	name 		= "$action_bomb",
	description = "$actiondesc_bomb",
	sprite 		= "data/ui_gfx/gun_actions/bomb.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/bomb.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3,4,5,6", -- BOMB
	spawn_probability                 = "1,1,1,1,0.5,0.5,0.1", -- BOMB
	price = 200,
	mana = 25,
	max_uses    = 3,
	custom_xml_file = "data/entities/misc/custom_cards/bomb.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/bomb.xml")
		c.fire_rate_wait = c.fire_rate_wait + 100
	end,
}*/