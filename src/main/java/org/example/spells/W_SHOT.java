package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.Projectile;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class W_SHOT extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.utility;
    static final String staticImageFile = "w_shot.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Wuplicate Spell";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "wuplicate"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Casts copies of a projectile in a trifurcated pattern, but the copies still cost mana";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.2, 0, 0.5, 0.4, 0, 0, 0, 0);
        this.price = 180;
        this.manaCost = 70;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.pattern = 20;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        Spell copiedSpell;
        Projectile projectile;
        int projectileCount;
        int count = 3;

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
	id          = "W_SHOT",
	name 		= "$action_w_shot",
	description = "$actiondesc_w_shot",
	sprite 		= "data/ui_gfx/gun_actions/w_shot.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/i_shape_unidentified.png",
	type 		= ACTION_TYPE_UTILITY,
	spawn_level                       = "2,3,5,6", -- I_SHAPE
	spawn_probability                 = "0.1,0.2,0.5,0.4", -- I_SHAPE
	price = 180,
	mana = 70,
	max_uses = 20,
	action 		= function()
		local data

		if ( #deck > 0 ) then
			data = deck[1]
		end

		if ( data ~= nil ) and ( ( data.type == ACTION_TYPE_PROJECTILE ) or ( data.type == ACTION_TYPE_STATIC_PROJECTILE ) ) and ( data.related_projectiles ~= nil ) and ( ( data.uses_remaining == nil ) or ( data.uses_remaining ~= 0 ) ) then
			local count = 3
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

		c.pattern_degrees = 20

		draw_actions( 1, true )
	end,
}*/