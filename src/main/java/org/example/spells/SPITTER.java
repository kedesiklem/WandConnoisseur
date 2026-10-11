package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SPITTER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPITTER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "spitter.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spitter Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A short-lived magical bolt";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SPITTER();
        this.spawnProbabilities = new SpawnProbabilities(1, 1, 1, 0.5, 0, 0, 0, 0, 0, 0, 0);
        this.price = 110;
        this.manaCost = 5;
        this.castDelay = -1;
        this.spread = 6;
        this.screenshake = 0.1;
        // c.dampening = 0.1
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SPITTER",
	name 		= "$action_spitter",
	description = "$actiondesc_spitter",
	sprite 		= "data/ui_gfx/gun_actions/spitter.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spitter_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/spitter.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3", -- SPITTER
	spawn_probability                 = "1,1,1,0.5", -- SPITTER
	price = 110,
	mana = 5,
	--max_uses = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/spitter.xml")
		-- damage = 0.1
		c.fire_rate_wait = c.fire_rate_wait - 1
		c.screenshake = c.screenshake + 0.1
		c.dampening = 0.1
		c.spread_degrees = c.spread_degrees + 6.0
	end,
}*/