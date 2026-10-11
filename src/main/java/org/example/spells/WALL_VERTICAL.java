package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_WALL_VERTICAL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class WALL_VERTICAL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "wall_vertical.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Vertical barrier";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A thin, vertical barrier that harms passing creatures, including you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_WALL_VERTICAL();
        this.spawnProbabilities = new SpawnProbabilities(0.4, 0.4, 0.6, 0, 0.5, 0.2, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 70;
        this.castDelay = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "WALL_VERTICAL",
	name 		= "$action_wall_vertical",
	description = "$actiondesc_wall_vertical",
	sprite 		= "data/ui_gfx/gun_actions/wall_vertical.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/wall_vertical.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,4,5", -- WALL_VERTICAL
	spawn_probability                 = "0.4,0.4,0.6,0.5,0.2", -- WALL_VERTICAL
	price = 160,
	mana = 70,
	--max_uses = 80,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/wall_vertical.xml")
		c.fire_rate_wait = c.fire_rate_wait + 5
	end,
}*/