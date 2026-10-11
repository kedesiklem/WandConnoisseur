package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class UNSTABLE_GUNPOWDER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "unstable_gunpowder.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Firecrackers";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile release firecrackers when it disappears";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.3, 0.4, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 15;
        this.material = "gunpowder_unstable";
        this.materialAmount = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "UNSTABLE_GUNPOWDER",
	name 		= "$action_unstable_gunpowder",
	description = "$actiondesc_unstable_gunpowder",
	sprite 		= "data/ui_gfx/gun_actions/unstable_gunpowder.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/unstable_gunpowder_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                      = "2,3,4", -- UNSTABLE_GUNPOWDER
	spawn_probability                = "0.3,0.4,0.4", -- UNSTABLE_GUNPOWDER
	price = 140,
	mana = 15,
	--max_uses    = 20,
	custom_xml_file = "data/entities/misc/custom_cards/unstable_gunpowder.xml",
	action 		= function()
		c.material = "gunpowder_unstable"
		c.material_amount = c.material_amount + 10
		--shot_effects.recoil_knockback = shot_effects.recoil_knockback + 30.0
		draw_actions( 1, true )
	end,
}*/