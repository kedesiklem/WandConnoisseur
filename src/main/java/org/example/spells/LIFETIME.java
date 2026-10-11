package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LIFETIME extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "lifetime.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Increase Lifetime";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "lifetime", "lifetime up"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Increases the lifetime of a spell";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.5, 0.5, 0.5, 0.75, 0, 0, 0, 0.1);
        this.price = 250;
        this.manaCost = 40;
        this.castDelay = 13;
        this.lifetime = 75;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "LIFETIME",
	name 		= "$action_lifetime",
	description = "$actiondesc_lifetime",
	sprite 		= "data/ui_gfx/gun_actions/lifetime.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,4,5,6,10", -- LIFETIME
	spawn_probability                 = "0.5,0.5,0.5,0.75,0.1", -- LIFETIME
	price = 250,
	mana = 40,
	--max_uses = 150,
	custom_xml_file = "data/entities/misc/custom_cards/lifetime.xml",
	action 		= function()
		c.lifetime_add 		= c.lifetime_add + 75
		c.fire_rate_wait = c.fire_rate_wait + 13
		draw_actions( 1, true )
	end,
}*/