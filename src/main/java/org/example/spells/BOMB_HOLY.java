package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BOMB_HOLY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BOMB_HOLY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "bomb_holy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Holy Bomb";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a bomb that... well...";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BOMB_HOLY();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.2, 0.2, 0.2, 0.2, 0, 0, 0, 0.5);
        this.price = 400;
        this.manaCost = 300;
        this.hasCharges = true;
        this.maxCharges = 2;
        this.castDelay = 40;
        this.rechargeTime = 80;
        this.recoil = 100.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "BOMB_HOLY",
	name 		= "$action_bomb_holy",
	description = "$actiondesc_bomb_holy",
	spawn_requires_flag = "card_unlocked_bomb_holy",
	sprite 		= "data/ui_gfx/gun_actions/bomb_holy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/bomb_holy.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "2,3,4,5,6,10", -- BOMB_HOLY
	spawn_probability                 = "0.2,0.2,0.2,0.2,0.2,0.5", -- BOMB_HOLY
	price = 400,
	mana = 300,
	max_uses    = 2,
	custom_xml_file = "data/entities/misc/custom_cards/bomb_holy.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/bomb_holy.xml")
		current_reload_time = current_reload_time + 80
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 100.0
		c.fire_rate_wait = c.fire_rate_wait + 40
	end,
}*/