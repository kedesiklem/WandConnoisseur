package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TENTACLE_PORTAL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TENTACLE_PORTAL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "tentacle_portal.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Eldritch Portal";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "yamete portal"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A weak but enchanting sparkling projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TENTACLE_PORTAL();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0.4, 0.4, 0.5, 0, 0, 0, 0, 0, 0.2);
        this.price = 220;
        this.manaCost = 140;
        this.hasCharges = true;
        this.maxCharges = 5;
        this.castDelay = 30;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TENTACLE_PORTAL",
	name 		= "$action_tentacle_portal",
	description = "$actiondesc_tentacle_portal",
	sprite 		= "data/ui_gfx/gun_actions/tentacle_portal.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/slimeball_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/tentacle_portal.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,10", -- TENTACLE_PORTAL
	spawn_probability                 = "0.4,0.4,0.4,0.5,0.2", -- TENTACLE_PORTAL
	price = 220,
	mana = 140,
	max_uses = 5,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/tentacle_portal.xml")
		c.fire_rate_wait = c.fire_rate_wait + 30
	end,
}*/