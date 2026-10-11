package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_EXPLOSION_SLIME_GIGA;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_EXPLOSION_SLIME_GIGA extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "explode_on_slime_giga.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Giant explosion on slimy enemies";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "giant explosion on slimy", "giant explosion on slime"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile explode powerfully upon collision with creatures covered in slime";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_EXPLOSION_SLIME_GIGA()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0, 0.1, 0.3, 0.1, 0, 0, 0, 0, 0);
        this.price = 300;
        this.manaCost = 200;
        this.hasCharges = true;
        this.maxCharges = 20;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.extra_entities = c.extra_entities .. "data/entities/particles/tinyspark_purple.xml,"
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HITFX_EXPLOSION_SLIME_GIGA",
	name 		= "$action_hitfx_explosion_slime_giga",
	description = "$actiondesc_hitfx_explosion_slime_giga",
	sprite 		= "data/ui_gfx/gun_actions/explode_on_slime_giga.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_explode_slime_giga.xml", "data/entities/particles/tinyspark_purple.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- HITFX_EXPLOSION_SLIME_GIGA
	spawn_probability                 = "0.1,0.1,0.3,0.1", -- HITFX_EXPLOSION_SLIME_GIGA
	price = 300,
	mana = 200,
	max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_explode_slime_giga.xml,data/entities/particles/tinyspark_purple.xml,"
		draw_actions( 1, true )
	end,
}*/