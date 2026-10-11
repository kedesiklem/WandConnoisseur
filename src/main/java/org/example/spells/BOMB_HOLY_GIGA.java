package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BOMB_HOLY_GIGA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOMB_HOLY_GIGA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bomb_holy_giga.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giga Holy Bomb";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Bigger and therefore holier";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BOMB_HOLY_GIGA();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
        this.price = 600;
        this.manaCost = 600;
        this.hasCharges = true;
        this.maxCharges = 2;
        this.neverUnlimited = true;
        this.castDelay = 120;
        this.rechargeTime = 160;
        this.recoil = 100.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BOMB_HOLY_GIGA",
	name 		= "$action_bomb_holy_giga",
	description = "$actiondesc_bomb_holy_giga",
	spawn_requires_flag = "card_unlocked_bomb_holy_giga",
	sprite 		= "data/ui_gfx/gun_actions/bomb_holy_giga.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/bomb_holy_giga.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "10", -- BOMB_HOLY
	spawn_probability                 = "1", -- BOMB_HOLY
	price = 600,
	mana = 600,
	max_uses    = 2,
	never_unlimited = true,
	custom_xml_file = "data/entities/misc/custom_cards/bomb_holy_giga.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/bomb_holy_giga.xml")
		current_reload_time = current_reload_time + 160
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 100.0
		c.fire_rate_wait = c.fire_rate_wait + 120
	end,
}*/