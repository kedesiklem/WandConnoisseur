package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SPITTER_TIER_2;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPITTER_TIER_2 extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "spitter_green.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Large spitter bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "large spitter"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A more powerful version of Spitter Bolt";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SPITTER_TIER_2();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 1, 1, 1, 0.5, 0, 0, 0, 0, 0);
        this.price = 190;
        this.manaCost = 25;
        this.castDelay = -2;
        this.spread = 7.5;
        this.screenshake = 1.1;
        // c.dampening = 0.2
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "SPITTER_TIER_2",
	name 		= "$action_spitter_tier_2",
	description = "$actiondesc_spitter_tier_2",
	sprite 		= "data/ui_gfx/gun_actions/spitter_green.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spitter_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/spitter_tier_2.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5", -- SPITTER_TIER_2
	spawn_probability                 = "1,1,1,0.5", -- SPITTER_TIER_2
	price = 190,
	mana = 25,
	--max_uses = 120,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/spitter_tier_2.xml")
		-- damage = 0.1
		c.fire_rate_wait = c.fire_rate_wait - 2
		c.screenshake = c.screenshake + 1.1
		c.dampening = 0.2
		c.spread_degrees = c.spread_degrees + 7.5
	end,
}*/