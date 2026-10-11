package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_TENTACLE;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class TENTACLE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "tentacle.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon Tentacle";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "tentacule"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Calls a terrifying appendage from another dimension";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_TENTACLE();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 1, 0.5, 1, 0.8, 0, 0, 0, 0);
        this.price = 200;
        this.manaCost = 20;
        this.castDelay = 40;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
	id          = "TENTACLE",
	name 		= "$action_tentacle",
	description = "$actiondesc_tentacle",
	spawn_requires_flag = "card_unlocked_tentacle",
	sprite 		= "data/ui_gfx/gun_actions/tentacle.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/tentacle_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/tentacle.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "3,4,5,6", -- TENTACLE
	spawn_probability                 = "1,0.5,1,0.8", -- TENTACLE
	price = 200,
	mana = 20,
	--max_uses = 40,
	custom_xml_file = "data/entities/misc/custom_cards/tentacle.xml",
	action 		= function()
		add_projectile("data/entities/projectiles/deck/tentacle.xml")
		c.fire_rate_wait = c.fire_rate_wait + 40
	end,
}*/