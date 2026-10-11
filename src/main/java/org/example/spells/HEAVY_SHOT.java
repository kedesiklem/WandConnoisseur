package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_HEAVY_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HEAVY_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "heavy_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Heavy Shot";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "heavy"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Greatly increases the damage done by a projectile, at the cost of its speed";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HEAVY_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.4, 0.5, 0, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 7;
        this.castDelay = 10;
        this.damageComponent.setDamage(43.75, DamageType.PROJECTILE);
        this.recoil = 50.0;
        this.speed = 0.3;
        this.goreParticles = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HEAVY_SHOT",
	name 		= "$action_heavy_shot",
	description = "$actiondesc_heavy_shot",
	sprite 		= "data/ui_gfx/gun_actions/heavy_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heavy_shot_unidentified.png",
	related_extra_entities = { "data/entities/particles/heavy_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- HEAVY_SHOT
	spawn_probability                 = "0.4,0.4,0.5", -- HEAVY_SHOT
	price = 150,
	mana = 7,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/heavy_shot.xml",
	action 		= function()
		c.damage_projectile_add = c.damage_projectile_add + 1.75
		c.fire_rate_wait    = c.fire_rate_wait + 10
		c.gore_particles    = c.gore_particles + 10
		c.speed_multiplier = c.speed_multiplier * 0.3
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 50.0
		c.extra_entities = c.extra_entities .. "data/entities/particles/heavy_shot.xml,"

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/