package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class RECOIL_DAMPER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "recoil_damper.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Recoil Damper";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Reduces the recoil when casting spells";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.6, 0, 0, 0.7, 0, 0, 0, 0);
        this.price = 100;
        this.manaCost = 5;
        this.recoil = -200.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "RECOIL_DAMPER",
	name 		= "$action_recoil_damper",
	description = "$actiondesc_recoil_damper",
	sprite 		= "data/ui_gfx/gun_actions/recoil_damper.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/recoil_damper_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "3,6", -- RECOIL_DAMPER
	spawn_probability                 = "0.6,0.7", -- RECOIL_DAMPER
	price = 100,
	mana = 5,
	--max_uses = 150,
	action 		= function()
		shot_effects.recoil_knockback = shot_effects.recoil_knockback - 200
		draw_actions( 1, true )
	end,
}*/