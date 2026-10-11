package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class ENERGY_SHIELD_SECTOR extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.passif;
    static final String staticImageFile = "energy_shield_sector.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Energy shield sector";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "shield sector"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Deflects incoming projectiles";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0.1, 0.5, 0.6, 0.8, 0.5, 0.4, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "ENERGY_SHIELD_SECTOR",
	name 		= "$action_energy_shield_sector",
	description = "$actiondesc_energy_shield_sector",
	sprite 		= "data/ui_gfx/gun_actions/energy_shield_sector.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/energy_shield_sector_unidentified.png",
	type 		= ACTION_TYPE_PASSIVE,
	spawn_level                       = "0,1,2,3,4,5", -- ENERGY_SHIELD_SECTOR
	spawn_probability                 = "0.1,0.5,0.6,0.8,0.5,0.4", -- ENERGY_SHIELD_SECTOR
	price = 160,
	custom_xml_file = "data/entities/misc/custom_cards/energy_shield_sector.xml",
	action 		= function()
		-- does nothing to the projectiles
		draw_actions( 1, true )
	end,
}*/