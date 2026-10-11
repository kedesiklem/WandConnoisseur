package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_POISON_BLAST;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class POISON_BLAST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "poison_blast.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Explosion of poison";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "An alchemical explosion";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_POISON_BLAST();
        this.spawnProbabilities = new SpawnProbabilities(0, 0.5, 0.8, 0, 0.4, 0, 0.3, 0, 0, 0, 0);
        this.price = 140;
        this.manaCost = 30;
        this.castDelay = 3;
        this.screenshake = 0.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "POISON_BLAST",
	name 		= "$action_poison_blast",
	description = "$actiondesc_poison_blast",
	sprite 		= "data/ui_gfx/gun_actions/poison_blast.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/poison_blast_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/poison_blast.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "1,2,4,6", -- POISON_BLAST
	spawn_probability                 = "0.5,0.8,0.4,0.3", -- POISON_BLAST
	price = 140,
	mana = 30,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/poison_blast.xml",
	is_dangerous_blast = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/poison_blast.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 0.5
	end,
}*/