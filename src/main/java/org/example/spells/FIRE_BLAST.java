package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FIRE_BLAST;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIRE_BLAST extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "fire_blast.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Explosion of brimstone";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fiery explosion";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FIRE_BLAST();
        this.spawnProbabilities = new SpawnProbabilities(0.5, 0.7, 0, 0.6, 0, 0.4, 0, 0, 0, 0, 0);
        this.price = 120;
        this.manaCost = 10;
        this.castDelay = 3;
        this.screenshake = 0.5;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "FIRE_BLAST",
	name 		= "$action_fire_blast",
	description = "$actiondesc_fire_blast",
	sprite 		= "data/ui_gfx/gun_actions/fire_blast.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/fire_blast_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/fireblast.xml"},
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	spawn_level                       = "0,1,3,5", -- FIRE_BLAST
	spawn_probability                 = "0.5,0.7,0.6,0.4", -- FIRE_BLAST
	price = 120,
	mana = 10,
	--max_uses = 30,
	custom_xml_file = "data/entities/misc/custom_cards/fire_blast.xml",
	is_dangerous_blast = true,
	action 		= function()
		add_projectile("data/entities/projectiles/deck/fireblast.xml")
		c.fire_rate_wait = c.fire_rate_wait + 3
		c.screenshake = c.screenshake + 0.5
	end,
}*/