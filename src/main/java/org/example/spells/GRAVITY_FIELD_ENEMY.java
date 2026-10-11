package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_GRAVITY_FIELD_ENEMY;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class GRAVITY_FIELD_ENEMY extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "gravity_field_enemy.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Personal gravity field";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes creatures hit by a projectile gain a temporary gravity well that draws projectiles in";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_GRAVITY_FIELD_ENEMY()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.6, 0, 0.4, 0.4, 0, 0, 0, 0, 0);
        this.price = 250;
        this.manaCost = 110;
        this.hasCharges = true;
        this.maxCharges = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "GRAVITY_FIELD_ENEMY",
	name 		= "$action_gravity_field_enemy",
	description = "$actiondesc_gravity_field_enemy",
	sprite 		= "data/ui_gfx/gun_actions/gravity_field_enemy.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/electric_charge_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_gravity_field_enemy.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,4,5", -- GRAVITY_FIELD_ENEMY
	spawn_probability                 = "0.5,0.6,0.4,0.4", -- GRAVITY_FIELD_ENEMY
	price = 250,
	mana = 110,
	max_uses = 20,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_gravity_field_enemy.xml,"
		draw_actions( 1, true )
	end,
}*/