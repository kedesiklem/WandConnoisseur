package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.script.Script;
import org.example.script.SCRIPT_TRUE_ORBIT;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TRUE_ORBIT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "true_orbit.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "True Orbit";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Makes a projectile rotate around the caster like an orbiting planet";
        this.type = staticType;
        this.relatedScripts = new Script[]{new SCRIPT_TRUE_ORBIT()};
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.3, 0.4, 0, 0, 0, 0, 0, 0);
        this.price = 40;
        this.manaCost = 2;
        this.autoStat = false;
        this.castDelay = -20;
        this.damageComponent.setDamage(2.5, DamageType.PROJECTILE);
        this.lifetime = 80;
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
	id          = "TRUE_ORBIT",
	name 		= "$action_true_orbit",
	description = "$actiondesc_true_orbit",
	sprite 		= "data/ui_gfx/gun_actions/true_orbit.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/sinewave_unidentified.png",
	related_extra_entities = { "data/entities/misc/true_orbit.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4", -- HORIZONTAL_ARC
	spawn_probability                 = "0.2,0.3,0.4", -- HORIZONTAL_ARC
	price = 40,
	mana = 2,
	--max_uses = 150,
	action 		= function()
		c.extra_entities = c.extra_entities .. "data/entities/misc/true_orbit.xml,"
		draw_actions( 1, true )
		c.damage_projectile_add = c.damage_projectile_add + 0.1
		c.fire_rate_wait    = c.fire_rate_wait - 20
		c.lifetime_add 		= c.lifetime_add + 80
	end,
}*/