package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_MEGALASER;
import org.example.projectiles.PROJECTILE_MEGALASER_BEAM;
import org.example.script.Script;
import org.example.script.SCRIPT_EFFECT_DISINTEGRATED;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class MEGALASER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "megalaser.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Intense concentrated light";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "icl"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A spectral wand is summoned that casts a huge beam of light";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_MEGALASER();
        this.relatedScripts = new Script[]{new SCRIPT_EFFECT_DISINTEGRATED()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.6, 0.6, 0.8, 0.6, 0, 0, 0, 0.3);
        this.price = 300;
        this.manaCost = 110;
        this.castDelay = 90;
        this.recoil = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(new PROJECTILE_MEGALASER_BEAM(), 5);
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "MEGALASER",
	name 		= "$action_megalaser",
	description = "$actiondesc_megalaser",
	sprite 		= "data/ui_gfx/gun_actions/megalaser.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/megalaser_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/megalaser.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,6,10", -- MEGALASER
	spawn_probability                 = "0.6,0.6,0.8,0.6,0.3", -- MEGALASER
	price = 300,
	mana = 110,
	action 		= function()
		-- beams are added in advance so that they inherit modifiers
		add_projectile("data/entities/projectiles/deck/megalaser_beam.xml")
		add_projectile("data/entities/projectiles/deck/megalaser_beam.xml")
		add_projectile("data/entities/projectiles/deck/megalaser_beam.xml")
		add_projectile("data/entities/projectiles/deck/megalaser_beam.xml")
		add_projectile("data/entities/projectiles/deck/megalaser_beam.xml")

		add_projectile("data/entities/projectiles/deck/megalaser.xml")
		c.fire_rate_wait = c.fire_rate_wait + 90
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 20.0
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_disintegrated.xml,"
	end,
}*/