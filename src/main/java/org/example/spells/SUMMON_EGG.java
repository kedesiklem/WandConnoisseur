package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_SUMMON_EGG_FIRE;
import org.example.projectiles.PROJECTILE_SUMMON_EGG_MONSTER;
import org.example.projectiles.PROJECTILE_SUMMON_EGG_RED;
import org.example.projectiles.PROJECTILE_SUMMON_EGG_SLIME;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SUMMON_EGG extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.projectile;
    static final String staticImageFile = "summon_egg.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Summon egg";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "egg"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Summons an egg that houses a friendly creature";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_SUMMON_EGG_MONSTER();
        this.spawnProbabilities = new SpawnProbabilities(0.7, 0.8, 0.8, 0.7, 0.6, 0.6, 0.5, 0, 0, 0, 0);
        this.price = 220;
        this.manaCost = 100;
        this.hasCharges = true;
        this.maxCharges = 2;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        RandomGenerator random = Global.getRandomGenerator();
        long frame = Global.getCurrentFrame();

        random.setSeed(frame, frame);

        switch(random.random(0, 3)){
            case 0 -> castState.addProjectile(new PROJECTILE_SUMMON_EGG_MONSTER());
            case 1 -> castState.addProjectile(new PROJECTILE_SUMMON_EGG_SLIME());
            case 2 -> castState.addProjectile(new PROJECTILE_SUMMON_EGG_RED());
            case 3 -> castState.addProjectile(new PROJECTILE_SUMMON_EGG_FIRE());
        }
    }
}

/*{
	id          = "SUMMON_EGG",
	name 		= "$action_summon_egg",
	description = "$actiondesc_summon_egg",
	sprite 		= "data/ui_gfx/gun_actions/summon_egg.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/bomb_unidentified.png",
	related_projectiles	= {"data/entities/items/pickup/egg_monster.xml"},
	type 		= ACTION_TYPE_PROJECTILE,
	spawn_level                       = "0,1,2,3,4,5,6", -- SUMMON_EGG
	spawn_probability                 = "0.7,0.8,0.8,0.7,0.6,0.6,0.5", -- SUMMON_EGG
	price = 220,
	mana = 100,
	max_uses    = 2,
	action 		= function()
		SetRandomSeed( GameGetFrameNum(), GameGetFrameNum() )
		local types = {"monster","slime","red","fire"}
		local rnd = Random(1, #types)
		local egg_name = "egg_" .. tostring(types[rnd]) .. ".xml"
		add_projectile("data/entities/items/pickup/" .. egg_name)
	end,
}*/