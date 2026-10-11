package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_EXPLOSION_ALCOHOL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_EXPLOSION_ALCOHOL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "explode_on_alcohol.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Explosion on drunk enemies";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "explosion on alcohol"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile explode upon collision with creatures covered in alcohol";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_EXPLOSION_ALCOHOL()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.3, 0, 0.2, 0.2, 0.2, 0, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HITFX_EXPLOSION_ALCOHOL",
	name 		= "$action_hitfx_explosion_alcohol",
	description = "$actiondesc_hitfx_explosion_alcohol",
	sprite 		= "data/ui_gfx/gun_actions/explode_on_alcohol.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_explode_alcohol.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- HITFX_EXPLOSION_ALCOHOL
	spawn_probability                 = "0.3,0.2,0.2,0.2", -- HITFX_EXPLOSION_ALCOHOL
	price = 140,
	mana = 20,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_explode_alcohol.xml,"
		draw_actions( 1, true )
	end,
}*/