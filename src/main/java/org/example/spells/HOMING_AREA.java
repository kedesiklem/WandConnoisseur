package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HOMING_AREA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HOMING_AREA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "homing_area.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Projectile Area Teleport";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tp homing", "pat"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "If a valid target appears somewhere in the proximity of a projectile, the projectile will teleport right on top of the target";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HOMING_AREA()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.4, 0.6, 0.7, 0.4, 0, 0, 0, 0);
        this.price = 175;
        this.manaCost = 60;
        this.castDelay = 8;
        this.spread = 6.0;
        this.speed = 0.75;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_white.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HOMING_AREA",
	name 		= "$action_homing_area",
	description = "$actiondesc_homing_area",
	sprite 		= "data/ui_gfx/gun_actions/homing_area.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/homing_unidentified.png",
	related_extra_entities = { "data/entities/misc/homing_area.xml", "data/entities/particles/tinyspark_white.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6", -- HOMING_ROTATE
	spawn_probability                 = "0.2,0.4,0.6,0.7,0.4", -- HOMING_ROTATE
	price = 175,
	mana = 60,
	--max_uses = 100,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/homing_area.xml,data/entities/particles/tinyspark_white.xml,"
		c.fire_rate_wait    = c.fire_rate_wait + 8
		c.spread_degrees = c.spread_degrees + 6
		c.speed_multiplier	= c.speed_multiplier * 0.75

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/