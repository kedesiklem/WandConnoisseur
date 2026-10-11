package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BUBBLESHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BUBBLESHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bubbleshot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Bubble Spark";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "bubble"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A bouncy, inaccurate spell";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BUBBLESHOT();
        this.spawnProbabilities = new SpawnProbabilities(1, 0.6, 1, 0.5, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 5;
        this.castDelay = -5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
        // c.dampening = 0.1
    }
}

/*{
	id          = "BUBBLESHOT",
	name 		= "$action_bubbleshot",
	description = "$actiondesc_bubbleshot",
	sprite 		= "data/ui_gfx/gun_actions/bubbleshot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bubbleshot_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/bubbleshot.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3", -- BUBBLESHOT
	spawn_probability                 = "1,0.6,1,0.5", -- BUBBLESHOT
	price = 100,
	mana = 5,
	--max_uses = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/bubbleshot.xml")
		-- damage = 0.1
		c.fire_rate_wait = c.fire_rate_wait - 5
		c.dampening = 0.1
	end,
}*/