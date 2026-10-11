package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_LUMINOUS_DRILL;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class LUMINOUS_DRILL extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "luminous_drill.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Luminous Drill";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "lumi"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A pinpointed, short-ranged beam of concentrated light";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_LUMINOUS_DRILL();
        this.spawnProbabilities = new SpawnProbabilities(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0.1);
        this.price = 150;
        this.manaCost = 10;
        this.castDelay = -35;
        this.rechargeTime = -10;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "LUMINOUS_DRILL",
	name 		= "$action_luminous_drill",
	description = "$actiondesc_luminous_drill",
	sprite 		= "data/ui_gfx/gun_actions/luminous_drill.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/chainsaw_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/luminous_drill.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,2,10", -- LUMINOUS_DRILL
	spawn_probability                 = "1,1,0.1", -- LUMINOUS_DRILL
	price = 150,
	mana = 10,
	--max_uses = 1000,
	sound_loop_tag = "sound_digger",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/luminous_drill.xml")
		c.fire_rate_wait = c.fire_rate_wait - 35
		current_reload_time = current_reload_time - ACTION_DRAW_RELOAD_TIME_INCREASE - 10 -- this is a hack to get the digger reload time back to 0
	end,
}*/