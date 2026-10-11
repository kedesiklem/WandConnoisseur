package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIFETIME_DOWN extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "lifetime_down.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Reduce Lifetime";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "lifetime down"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Reduces the lifetime of a spell";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.5, 0.5, 0.75, 0.5, 0, 0, 0, 0.1);
        this.price = 90;
        this.manaCost = 10;
        this.castDelay = -15;
        this.lifetime = -42;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LIFETIME_DOWN",
	name 		= "$action_lifetime_down",
	description = "$actiondesc_lifetime_down",
	sprite 		= "data/ui_gfx/gun_actions/lifetime_down.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5,6,10", -- LIFETIME_DOWN
	spawn_probability                 = "0.5,0.5,0.75,0.5,0.1", -- LIFETIME_DOWN
	price = 90,
	mana = 10,
	--max_uses = 150,
	custom_xml_file = "data/entities/misc/custom_cards/lifetime_down.xml",
	action 		= function()
		c.lifetime_add 		= c.lifetime_add - 42
		c.fire_rate_wait = c.fire_rate_wait - 15
		draw_actions( 1, true )
	end,
}*/