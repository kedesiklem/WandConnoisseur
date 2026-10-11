package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_FIREWORK_BLUE;
import org.example.projectiles.PROJECTILE_FIREWORK_GREEN;
import org.example.projectiles.PROJECTILE_FIREWORK_ORANGE;
import org.example.projectiles.PROJECTILE_FIREWORK_PINK;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class FIREWORK extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "fireworks.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fireworks!";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "fireworks"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "A fiery, explosive projectile";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_FIREWORK_PINK();
        this.spawnProbabilities = new SpawnProbabilities(0, 1, 0.8, 1, 1, 0.5, 0.3, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 70;
        this.hasCharges = true;
        this.maxCharges = 25;
        this.castDelay = 60;
        this.setRecoil = true;
        this.recoil = 120.0;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        long frame = Global.getCurrentFrame();
        RandomGenerator random = Global.getRandomGenerator();
        random.setSeed(frame, frame);

        switch(random.random(0, 3)){
            case 0 -> castState.addProjectile(new PROJECTILE_FIREWORK_PINK());
            case 1 -> castState.addProjectile(new PROJECTILE_FIREWORK_GREEN());
            case 2 -> castState.addProjectile(new PROJECTILE_FIREWORK_BLUE());
            case 3 -> castState.addProjectile(new PROJECTILE_FIREWORK_ORANGE());
        }
        //c.ragdoll_fx = 2
    }
}

/*{
	id          = "FIREWORK",
	name 		= "$action_firework",
	description = "$actiondesc_firework",
	spawn_requires_flag = "card_unlocked_firework",
	sprite 		= "data/ui_gfx/gun_actions/fireworks.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/rocket_unidentified.png",
	related_projectiles	= {"data/entities/projectiles/deck/fireworks/firework_pink.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "1,2,3,4,5,6", -- FIREWORK
	spawn_probability                 = "1,0.8,1,1,0.5,0.3", -- FIREWORK
	price = 220,
	mana = 70,
	max_uses    = 25,
	action 		= function()
		SetRandomSeed( GameGetFrameNum(), GameGetFrameNum() )
		local types = {"pink","green","blue","orange"}
		local rnd = Random(1, #types)
		local firework_name = "firework_" .. tostring(types[rnd]) .. ".xml"
		add_projectile("data/entities/projectiles/deck/fireworks/" .. firework_name)
		c.fire_rate_wait = c.fire_rate_wait + 60
		--current_reload_time = current_reload_time + 40
		c.ragdoll_fx = 2
		shot_effects.recoil_knockback = 120.0
	end,
}*/
