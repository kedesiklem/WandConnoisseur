package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPEED extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "speed.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Speed Up";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases the speed at which a projectile flies through the air";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 3;
        this.speed = 2.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "SPEED",
	name 		= "$action_speed",
	description = "$actiondesc_speed",
	sprite 		= "data/ui_gfx/gun_actions/speed.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/speed_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3", -- SPEED
	spawn_probability                 = "1,0.5,0.5", -- SPEED
	price = 100,
	mana = 3,
	--max_uses = 100,
	custom_xml_file = "data/entities/misc/custom_cards/speed.xml",
	action 		= function()
		c.speed_multiplier = c.speed_multiplier * 2.5

		if ( c.speed_multiplier >= 20 ) then
			c.speed_multiplier = math.min( c.speed_multiplier, 20 )
		elseif ( c.speed_multiplier < 0 ) then
			c.speed_multiplier = 0
		end

		draw_actions( 1, true )
	end,
}*/