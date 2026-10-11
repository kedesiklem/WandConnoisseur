package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class I_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "i_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Iplicate Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "iplicate"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts a copy of a projectile behind you, but the copy still cost mana";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0.1, 0.2, 0.5, 0, 0, 0, 0, 0, 0, 0);
        this.price = 130;
        this.manaCost = 40;
        this.hasCharges = true;
        this.maxCharges = 30;
        this.pattern = 180;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell copiedSpell;
        Projectile projectile;
        int projectileCount;
        int count = 2;

        copiedSpell = cardPool.getDeckSpell(0);

        if(copiedSpell != null && (copiedSpell.type == SpellType.projectile || copiedSpell.type == SpellType.static_projectile) && copiedSpell.relatedProjectile != null && (!copiedSpell.hasCharges || copiedSpell.chargesLeft > 0)){
            projectile = copiedSpell.getRelatedProjectile();
            projectileCount = copiedSpell.getRelatedProjectileCount();

            for(int i=0; i < count; i++){
                if(cardPool.getMaxMana() >= cardPool.getManaUsage() + copiedSpell.manaCost){
                    for(int j=0; j < projectileCount; j++){
                        castState.addProjectile(projectile.clone());
                    }
                    cardPool.addManaUsage(copiedSpell.manaCost);
                }else{
                    break;
                }
            }
        }

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "I_SHOT",
	name 		= "$action_i_shot",
	description = "$actiondesc_i_shot",
	sprite 		= "data/ui_gfx/gun_actions/i_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/i_shape_unidentified.png",
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "1,2,3", -- I_SHAPE
	spawn_probability                 = "0.1,0.2,0.5", -- I_SHAPE
	price = 130,
	mana = 40,
	max_uses = 30,
	action 		= function()
		local data

		if ( #deck > 0 ) then
			data = deck[1]
		end

		if ( data ~= nil ) and ( ( data.type == ACTION_TYPE_PROJECTILE ) or ( data.type == ACTION_TYPE_STATIC_PROJECTILE ) ) and ( data.related_projectiles ~= nil ) and ( ( data.uses_remaining == nil ) or ( data.uses_remaining ~= 0 ) ) then
			local count = 2
			for i=1,count-1 do
				if ( mana >= data.mana ) then
					local proj = data.related_projectiles[1]
					local proj_count = data.related_projectiles[2] or 1

					for a=1,proj_count do
						add_projectile(proj)
					end

					mana = mana - data.mana
				else
					OnNotEnoughManaForAction()
					break
				end
			end
		end

		c.pattern_degrees = 180

		draw_actions( 1, true )
	end,
}*/