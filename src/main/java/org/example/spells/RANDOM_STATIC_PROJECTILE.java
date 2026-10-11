package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class RANDOM_STATIC_PROJECTILE extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "random_static_projectile.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Random static projectile spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "random static projectile"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts one random static projectile spell";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0, 0.2, 0, 0.1, 0.2, 0, 0, 0, 0.5);
        this.recursive = true;
        this.price = 160;
        this.manaCost = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell[] spells = recursionLevel < recursionLimit ? Global.getSpellListStaticProjectile().getSpells(false) : Global.getSpellListStaticProjectileNonRecursive().getSpells(false);

        if(spells.length == 0){
            return;
        }

        long frame = Global.getCurrentFrame();
        RandomGenerator random = Global.getRandomGenerator();
        random.setSeed(frame + cardPool.getDeckSize(), frame + 253);
        copy(cardPool, castState, spells[random.random(0, spells.length - 1)], recursionLevel);
    }
}

/*{
	id          = "RANDOM_STATIC_PROJECTILE",
	name 		= "$action_random_static_projectile",
	description = "$actiondesc_random_static_projectile",
	sprite 		= "data/ui_gfx/gun_actions/random_static_projectile.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/spread_reduce_unidentified.png",
	spawn_requires_flag = "card_unlocked_pyramid",
	type 		= ACTION_TYPE_STATIC_PROJECTILE,
	recursive	= true,
	spawn_level                       = "3,5,6,10", -- MANA_REDUCE
	spawn_probability                 = "0.2,0.1,0.2,0.5", -- MANA_REDUCE
	price = 160,
	mana = 20,
	action 		= function( recursion_level, iteration )
		SetRandomSeed( GameGetFrameNum() + #deck, GameGetFrameNum() + 253 )
		local rnd = Random( 1, #actions )
		local data = actions[rnd]

		local safety = 0
		local rec = check_recursion( data, recursion_level )
		local flag = data.spawn_requires_flag
		local usable = true
		if ( flag ~= nil ) and ( HasFlagPersistent( flag ) == false ) then
			usable = false
		end

		while ( safety < 100 ) and ( ( data.type ~= 1 ) or ( rec == -1 ) or ( usable == false ) ) do
			rnd = Random( 1, #actions )
			data = actions[rnd]
			rec = check_recursion( data, recursion_level )
			flag = data.spawn_requires_flag
			usable = true
			if ( flag ~= nil ) and ( HasFlagPersistent( flag ) == false ) then
				usable = false
			end

			safety = safety + 1
		end

		data.action( rec )
	end,
}*/