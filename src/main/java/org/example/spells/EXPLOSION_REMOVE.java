package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_EXPLOSION_REMOVE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class EXPLOSION_REMOVE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "explosion_remove.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Remove Explosion";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile no longer explode";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_EXPLOSION_REMOVE()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0, 0.6, 0.7, 0.2, 0, 0, 0, 0);
        this.price = 50;
        this.manaCost = 0;
        this.castDelay = -15;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.explosion_radius = c.explosion_radius - 30.0
        // c.damage_explosion_add = c.damage_explosion_add - 0.8
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "EXPLOSION_REMOVE",
	name 		= "$action_explosion_remove",
	description = "$actiondesc_explosion_remove",
	sprite 		= "data/ui_gfx/gun_actions/explosion_remove.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	related_extra_entities = { "data/entities/misc/explosion_remove.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,4,5,6", -- LIFETIME_DOWN
	spawn_probability                 = "0.2,0.6,0.7,0.2", -- LIFETIME_DOWN
	price = 50,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/explosion_remove.xml,"
		c.fire_rate_wait = c.fire_rate_wait - 15
		c.explosion_radius = c.explosion_radius - 30.0
		c.damage_explosion_add = c.damage_explosion_add - 0.8
		draw_actions( 1, true )
	end,
}*/