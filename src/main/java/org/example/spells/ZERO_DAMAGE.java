package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_ZERO_DAMAGE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ZERO_DAMAGE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "zero_damage.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Null Shot";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases a projectile's duration dramatically but removes all damage and explosion from it";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_ZERO_DAMAGE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.3, 0.3, 0.6, 0, 0, 0, 0, 0.3);
        this.price = 50;
        this.manaCost = 5;
        this.castDelay = -5;
        this.lifetime = 280;
        this.recoil = -10.0;
        this.setGoreParticles = true;
        this.goreParticles = 0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.setDamage(this.damageComponent);
        // c.damage_explosion_add = 0
        // c.damage_null_all = 1
        // c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_white_small.xml"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ZERO_DAMAGE",
	name 		= "$action_zero_damage",
	description = "$actiondesc_zero_damage",
	sprite 		= "data/ui_gfx/gun_actions/zero_damage.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/damage_unidentified.png",
	related_extra_entities = { "data/entities/particles/tinyspark_white_small.xml", "data/entities/misc/zero_damage.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5,10", -- DAMAGE
	spawn_probability                 = "0.3,0.3,0.6,0.3", -- DAMAGE
	price = 50,
	mana = 5,
	--max_uses = 50,
	action 		= function()
		c.damage_electricity_add = 0
		c.damage_explosion_add = 0
		c.damage_explosion = 0
		c.damage_critical_chance = 0
		c.damage_ice_add = 0
		c.damage_projectile_add = 0
		c.damage_null_all = 1
		c.gore_particles    = 0
		c.fire_rate_wait    = c.fire_rate_wait - 5
		c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_white_small.xml,data/entities/misc/zero_damage.xml,"
		shot_effects.recoil_knockback = shot_effects.recoil_knockback - 10.0
		c.lifetime_add 		= c.lifetime_add + 280
		draw_actions( 1, true )
	end,
}*/