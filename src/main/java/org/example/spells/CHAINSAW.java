package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_CHAINSAW;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class CHAINSAW extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "chainsaw.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chainsaw";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A good tool for cutting meat. Also has some magical properties...";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_CHAINSAW();
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
        this.price = 80;
        this.manaCost = 1;
        this.setCastDelay = true;
        this.castDelay = 0;
        this.rechargeTime = -10;
        this.spread = 6.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "CHAINSAW",
	name 		= "$action_chainsaw",
	description = "$actiondesc_chainsaw",
	sprite 		= "data/ui_gfx/gun_actions/chainsaw.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chainsaw_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/chainsaw.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2", -- CHAINSAW
	spawn_probability                 = "1,1", -- CHAINSAW
	price = 80,
	mana = 1,
	--max_uses = 1000,
	sound_loop_tag = "sound_chainsaw",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/chainsaw.xml")
		c.fire_rate_wait = 0
		c.spread_degrees = c.spread_degrees + 6.0
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the digger reload time back to 0
	end,
}*/