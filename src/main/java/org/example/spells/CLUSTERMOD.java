package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_CLUSTERMOD;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CLUSTERMOD extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "clusterbomb.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Clusterbolt";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile release a cluster of explosive bolts upon hitting a wall";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_CLUSTERMOD()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 1, 0.6, 0, 0, 0, 0, 0, 0, 0);
        this.price = 160;
        this.manaCost = 30;
        this.castDelay = 20;
        this.recoil = 10.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        // c.explosion_radius = c.explosion_radius + 4.0
        // c.damage_explosion_add = c.damage_explosion_add + 0.2
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "CLUSTERMOD",
	name 		= "$action_clustermod",
	description = "$actiondesc_clustermod",
	sprite 		= "data/ui_gfx/gun_actions/clusterbomb.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3", -- EXPLOSIVE_PROJECTILE
	spawn_probability                 = "0.5,1,0.6", -- EXPLOSIVE_PROJECTILE
	price = 160,
	mana = 30,
	--max_uses = 50,
	custom_xml_file = "data/entities/misc/custom_cards/clusterbomb.xml",
	action 		= function()
		c.explosion_radius = c.explosion_radius + 4.0
		c.damage_explosion_add = c.damage_explosion_add + 0.2
		c.fire_rate_wait   = c.fire_rate_wait + 20
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0

		c.extra_entities = c.extra_entities .. "data/entities/misc/clusterbomb.xml,"

		draw_actions( 1, true )
	end,
}*/