package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.script.Script;
import org.example.script.SCRIPT_HITFX_TOXIC_CHARM;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HITFX_TOXIC_CHARM extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "charm_on_toxic.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Charm on toxic sludge";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "charm on toxic"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile charm creatures covered in toxic sludge";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HITFX_TOXIC_CHARM()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0, 0.2, 0.3, 0.2, 0, 0, 0, 0, 0);
        this.price = 150;
        this.manaCost = 70;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "HITFX_TOXIC_CHARM",
	name 		= "$action_hitfx_toxic_charm",
	description = "$actiondesc_hitfx_toxic_charm",
	sprite 		= "data/ui_gfx/gun_actions/charm_on_toxic.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/freeze_unidentified.png",
	related_extra_entities = { "data/entities/misc/hitfx_toxic_charm.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,4,5", -- HITFX_TOXIC_CHARM
	spawn_probability                 = "0.2,0.2,0.3,0.2", -- HITFX_TOXIC_CHARM
	price = 150,
	mana = 70,
	--max_uses = 50,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/hitfx_toxic_charm.xml,"
		draw_actions( 1, true )
	end,
}*/