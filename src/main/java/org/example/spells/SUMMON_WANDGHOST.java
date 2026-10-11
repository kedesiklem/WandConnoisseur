package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SUMMON_WANDGHOST;
import org.example.projectiles.PROJECTILE_WAND_EFFET;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SUMMON_WANDGHOST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "summon_wandghost.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Taikasauva";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "taikasauva", "wand ghost", "wandghost"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons a possessed wand to aid you";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SUMMON_WANDGHOST();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.08, 0, 0.1, 0.3, 0.3, 0, 0, 0, 0.1);
        this.price = 420;
        this.manaCost = 300;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
        castState.addProjectile(new PROJECTILE_WAND_EFFET());
    }
}

/*{
	id          = "SUMMON_WANDGHOST",
	name 		= "$action_summon_wandghost",
	description = "$actiondesc_summon_wandghost",
	sprite 		= "data/ui_gfx/gun_actions/summon_wandghost.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/wand_ghost_player.xml"},
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "2,4,5,6,10", -- SUMMON_WANDGHOST
	spawn_probability                 = "0.08,0.1,0.3,0.3,0.1", -- SUMMON_WANDGHOST
	price = 420,
	mana = 300,
	max_uses    = 1,
	never_unlimited = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/wand_ghost_player.xml")
		add_projectile("data/entities/particles/image_emitters/wand_effect.xml")
	end,
}*/