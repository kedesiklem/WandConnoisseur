package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_CHAIN_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CHAIN_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "chain_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chain Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Causes a projectile to cast a copy of itself upon expiring, up to 5 times";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CHAIN_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0, 0.6, 0.8, 0, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 70;
        this.damageComponent.setDamage(-5.0, DamageType.PROJECTILE);
        this.lifetime = -30;
        this.spread = 10.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        /*if (c.explosion_radius < 0) then
            c.explosion_radius = 0
        end*/
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CHAIN_SHOT",
	name 		= "$action_chain_shot",
	description = "$actiondesc_chain_shot",
	sprite 		= "data/ui_gfx/gun_actions/chain_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/chain_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,4,5", -- AREA_DAMAGE
	spawn_probability                 = "0.4,0.6,0.8", -- AREA_DAMAGE
	price = 240,
	mana = 70,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/chain_shot.xml,"
		c.lifetime_add = c.lifetime_add - 30
		c.damage_projectile_add = c.damage_projectile_add - 0.2
		c.explosion_radius = c.explosion_radius - 5.0
		if (c.explosion_radius < 0) then
			c.explosion_radius = 0
		end
		c.spread_degrees = c.spread_degrees + 10.0
		draw_actions( 1, true )
	end,
}*/