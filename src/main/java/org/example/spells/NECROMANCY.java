package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_NECROMANCY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class NECROMANCY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "necromancy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Necromancy";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes corpses of creatures killed by a projectile rise to your aid";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_NECROMANCY()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.4, 0.6, 0.6, 0.3, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 20;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "NECROMANCY",
	name 		= "$action_necromancy",
	description = "$actiondesc_necromancy",
	spawn_requires_flag = "card_unlocked_necromancy",
	sprite 		= "data/ui_gfx/gun_actions/necromancy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5", -- NECROMANCY
	spawn_probability                 = "0.4,0.6,0.6,0.3", -- NECROMANCY
	price = 80,
	mana = 20,
	--max_uses = 50,
	action 		= function()
		c.game_effect_entities = c.game_effect_entities .. "data/entities/misc/effect_necromancy.xml,"
		c.fire_rate_wait = c.fire_rate_wait + 10
		draw_actions( 1, true )
	end,
}*/