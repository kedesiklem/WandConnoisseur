package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ACID_TRAIL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "acid_trail.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Acid trail";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Gives a projectile a trail of acid";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0.2, 0.3, 0.3, 0.4, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 15;
        this.trailMaterial = "acid";
        this.trailMaterialAmount = 5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ACID_TRAIL",
	name 		= "$action_acid_trail",
	description = "$actiondesc_acid_trail",
	sprite 		= "data/ui_gfx/gun_actions/acid_trail.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/acid_trail_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4,5", -- ACID_TRAIL
	spawn_probability                 = "0.3,0.2,0.3,0.3,0.4", -- ACID_TRAIL
	price = 160,
	mana = 15,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/acid_trail.xml",
	action 		= function()
		c.trail_material = c.trail_material .. "acid,"
		c.trail_material_amount = c.trail_material_amount + 5
		draw_actions( 1, true )
	end,
}*/