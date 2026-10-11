package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_HORIZONTAL_ARC;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class HORIZONTAL_ARC extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "horizontal_arc.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Horizontal path";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "horizontal"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Forces a projectile on a horizontal path, but increases its damage";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_HORIZONTAL_ARC()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.4, 0, 0.4, 0, 0.4, 0, 0, 0, 0, 0);
        this.price = 20;
        this.manaCost = 0;
        this.autoStat = false;
        this.castDelay = -6;
        this.damageComponent.setDamage(7.5, DamageType.PROJECTILE);
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addScript(this.relatedScripts);
        cardPool.draw(1, true, castState);
        castState.addDamageComponent(this.damageComponent);
        castState.addCastDelay(this.castDelay);
    }
}

/*{
	id          = "HORIZONTAL_ARC",
	name 		= "$action_horizontal_arc",
	description = "$actiondesc_horizontal_arc",
	sprite 		= "data/ui_gfx/gun_actions/horizontal_arc.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/horizontal_arc.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,3,5", -- HORIZONTAL_ARC
	spawn_probability                 = "0.4,0.4,0.4", -- HORIZONTAL_ARC
	price = 20,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/horizontal_arc.xml,"
		draw_actions( 1, true )
		c.damage_projectile_add = c.damage_projectile_add + 0.3
		c.fire_rate_wait    = c.fire_rate_wait - 6
	end,
}*/