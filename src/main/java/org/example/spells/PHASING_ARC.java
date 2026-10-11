package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_PHASING_ARC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PHASING_ARC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "phasing_arc.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Phasing Arc";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "phasing"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile fly much slower, but teleport short distances over its flight";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_PHASING_ARC()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.3, 0.6, 0.1, 0, 0, 0, 0, 0);
        this.price = 170;
        this.manaCost = 2;
        this.autoStat = false;
        this.castDelay = -12;
        this.lifetime = 80;
        this.speed = 0.33;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addScript(this.relatedScripts);
        cardPool.draw(1, true, castState);
        castState.addCastDelay(this.castDelay);
        castState.addLifetime(this.lifetime);
        castState.multiplySpeed(this.speed, 0.0, 20.0);
        // c.child_speed_multiplier	= c.child_speed_multiplier * 0.33
    }
}

/*{
	id          = "PHASING_ARC",
	name 		= "$action_phasing_arc",
	description = "$actiondesc_phasing_arc",
	sprite 		= "data/ui_gfx/gun_actions/phasing_arc.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/phasing_arc.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5", -- HORIZONTAL_ARC
	spawn_probability                 = "0.2,0.3,0.6,0.1", -- HORIZONTAL_ARC
	price = 170,
	mana = 2,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/phasing_arc.xml,"
		draw_actions( 1, true )
		c.fire_rate_wait    = c.fire_rate_wait - 12
		c.lifetime_add 		= c.lifetime_add + 80
		c.speed_multiplier	= c.speed_multiplier * 0.33
		c.child_speed_multiplier	= c.child_speed_multiplier * 0.33

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end
	end,
}*/