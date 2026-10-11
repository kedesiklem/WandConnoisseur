package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_LIGHT_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIGHT_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "light_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Light shot";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "lightshot"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile move considerably faster, but deal less damage";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_LIGHT_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.5, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 60;
        this.manaCost = 5;
        this.damageComponent.setDamage(-25.0, DamageType.PROJECTILE);
        this.castDelay = -3;
        this.spread = -6.0;
        this.recoil = -10.0;
        this.speed = 7.5;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        /*c.explosion_radius = c.explosion_radius - 10.0
		if (c.explosion_radius < 0) then
			c.explosion_radius = 0
		end*/
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LIGHT_SHOT",
	name 		= "$action_light_shot",
	description = "$actiondesc_light_shot",
	sprite 		= "data/ui_gfx/gun_actions/light_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/heavy_shot_unidentified.png",
	related_extra_entities = { "data/entities/particles/light_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- LIGHT_SHOT
	spawn_probability                 = "0.3,0.5,0.4", -- LIGHT_SHOT
	price = 60,
	mana = 5,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/light_shot.xml",
	action 		= function()
		c.damage_projectile_add = c.damage_projectile_add - 1.0
		c.explosion_radius = c.explosion_radius - 10.0
		if (c.explosion_radius < 0) then
			c.explosion_radius = 0
		end
		c.fire_rate_wait    = c.fire_rate_wait - 3
		c.speed_multiplier = c.speed_multiplier * 7.5
		c.spread_degrees = c.spread_degrees - 6
		shot_effects.recoil_knockback = shot_effects.recoil_knockback - 10.0
		c.extra_entities = c.extra_entities .. "data/entities/particles/light_shot.xml,"

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/