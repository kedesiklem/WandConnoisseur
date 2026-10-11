package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DAMAGE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "damage.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Damage Plus";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "damage up"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases the damage done by a projectile";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.6, 0.6, 0.8, 0.6, 0.6, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 5;
        this.castDelay = 5;
        this.damageComponent.setDamage(10.0, DamageType.PROJECTILE);
        this.recoil = 10.0;
        this.goreParticles = 5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
		// c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_yellow.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "DAMAGE",
	name 		= "$action_damage",
	description = "$actiondesc_damage",
	sprite 		= "data/ui_gfx/gun_actions/damage.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/damage_unidentified.png",
	related_extra_entities = { "data/entities/particles/tinyspark_yellow.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- DAMAGE
	spawn_probability                 = "0.6,0.6,0.8,0.6,0.6", -- DAMAGE
	price = 140,
	mana = 5,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/damage.xml",
	action 		= function()
		c.damage_projectile_add = c.damage_projectile_add + 0.4
		c.gore_particles    = c.gore_particles + 5
		c.fire_rate_wait    = c.fire_rate_wait + 5
		c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_yellow.xml,"
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0
		draw_actions( 1, true )
	end,
}*/