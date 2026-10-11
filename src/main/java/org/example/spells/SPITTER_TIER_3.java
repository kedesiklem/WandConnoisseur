package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SPITTER_TIER_3;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPITTER_TIER_3 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "spitter_purple.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giant spitter bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "giant spitter"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "The most powerful version of Spitter Bolt";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SPITTER_TIER_3();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.8, 0.8, 1, 1, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 40;
        this.castDelay = -4;
        this.screenshake = 3.1;
        this.spread = 9.0;
        // c.dampening = 0.3
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SPITTER_TIER_3",
	name 		= "$action_spitter_tier_3",
	description = "$actiondesc_spitter_tier_3",
	sprite 		= "data/ui_gfx/gun_actions/spitter_purple.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spitter_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/spitter_tier_3.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,6", -- SPITTER_TIER_3
	spawn_probability                 = "0.8,0.8,1,1", -- SPITTER_TIER_3
	price = 240,
	mana = 40,
	--max_uses = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/spitter_tier_3.xml")
		-- damage = 0.1
		c.fire_rate_wait = c.fire_rate_wait - 4
		c.screenshake = c.screenshake + 3.1
		c.dampening = 0.3
		c.spread_degrees = c.spread_degrees + 9.0
	end,
}*/