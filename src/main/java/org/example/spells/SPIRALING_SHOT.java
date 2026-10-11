package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_SPIRALING_SHOT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SPIRALING_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "spiraling_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spiral Arc";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "spiral"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A projectile flies in a spiralling pattern";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_SPIRALING_SHOT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0.2, 0.3, 0.4, 0.5, 0, 0, 0, 0, 0, 0);
        this.price = 30;
        this.manaCost = 0;
        this.autoStat = false;
        this.castDelay = -6;
        this.damageComponent.setDamage(2.5, DamageType.PROJECTILE);
        this.lifetime = 50;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addScript(this.relatedScripts);
        cardPool.draw(1, true, castState);
        castState.addDamageComponent(this.damageComponent);
        castState.addCastDelay(this.castDelay);
        castState.addLifetime(this.lifetime);
    }
}

/*{
	id          = "SPIRALING_SHOT",
    name 		= "$action_spiraling_shot",
	description = "$actiondesc_spiraling_shot",
	sprite 		= "data/ui_gfx/gun_actions/spiraling_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/orbit_shot.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "1,2,3,4", -- HORIZONTAL_ARC
	spawn_probability                 = "0.2,0.3,0.4,0.5", -- HORIZONTAL_ARC
	price = 30,
	mana = 0,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/orbit_shot.xml,"
		draw_actions( 1, true )
		c.damage_projectile_add = c.damage_projectile_add + 0.1
		c.fire_rate_wait    = c.fire_rate_wait - 6
		c.lifetime_add 		= c.lifetime_add + 50
	end,
}*/