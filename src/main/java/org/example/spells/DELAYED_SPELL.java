package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;
import org.example.projectiles.PROJECTILE_DELAYED_SPELL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DELAYED_SPELL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "delayed_spell.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Delayed spellcast";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "ds"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A static, magical phenomenon that casts 3 extra spells after a short while";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_DELAYED_SPELL();
        this.triggerType = Projectile.TriggerType.expiration;
        this.spawnProbabilities = new SpawnProbabilities(0.8, 0.8, 1, 0, 0.7, 0.5, 0.4, 0, 0, 0, 0);
        this.price = 240;
        this.manaCost = 20;
        this.autoStat = false;
        this.castDelay = 10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        cardPool.draw(3, true, castState.addProjectileTrigger(this.relatedProjectile.clone(), this.triggerType));
        castState.addCastDelay(this.castDelay);
    }
}

/*{
	id          = "DELAYED_SPELL",
	name 		= "$action_delayed_spell",
	description = "$actiondesc_delayed_spell",
	sprite 		= "data/ui_gfx/gun_actions/delayed_spell.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/teleport_projectile_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/delayed_spell.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,2,4,5,6", -- DELAYED_SPELL
	spawn_probability                 = "0.8,0.8,1,0.7,0.5,0.4", -- DELAYED_SPELL
	price = 240,
	mana = 20,
	action 		= function()
		add_projectile_trigger_death("data/entities/projectiles/deck/delayed_spell.xml", 3)
		c.fire_rate_wait = c.fire_rate_wait + 10
	end,
}*/