package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LASER;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_DISINTEGRATED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LASER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "laser.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Concentrated Light";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "conc light"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A pinpointed beam of light";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LASER();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_DISINTEGRATED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 30;
        this.castDelay = -22;
        this.recoil = 20.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LASER",
	name 		= "$action_laser",
	description = "$actiondesc_laser",
	sprite 		= "data/ui_gfx/gun_actions/laser.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/laser_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/laser.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,4", -- LASER
	spawn_probability                 = "1,1,1", -- LASER
	price = 180,
	mana = 30,
	--max_uses = 80,
	custom_xml_file = "data/entities/misc/custom_cards/laser.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/laser.xml")
		c.fire_rate_wait = c.fire_rate_wait - 22
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_disintegrated.xml,"
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
	end,
}*/