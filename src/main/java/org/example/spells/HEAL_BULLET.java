package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_HEAL_BULLET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HEAL_BULLET extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "heal_bullet.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Healing Bolt";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "heal"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A magical bolt that heals other beings";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_HEAL_BULLET();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 1, 1, 0.6, 0, 0, 0, 0, 0, 0);
        this.price = 60;
        this.manaCost = 15;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.neverUnlimited = true;
        this.castDelay = 4;
        this.spread = 2.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "HEAL_BULLET",
	name 		= "$action_heal_bullet",
	description = "$actiondesc_heal_bullet",
	sprite 		= "data/ui_gfx/gun_actions/heal_bullet.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heal_bullet_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/heal_bullet.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4", -- HEAL_BULLET
	spawn_probability                 = "1,1,0.6", -- HEAL_BULLET
	price = 60,
	mana = 15,
	max_uses = 20,
	never_unlimited = true,
	custom_xml_file = "data/entities/misc/custom_cards/heal_bullet.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/heal_bullet.xml")
		c.fire_rate_wait = c.fire_rate_wait + 4
		c.spread_degrees = c.spread_degrees + 2.0
	end,
}*/