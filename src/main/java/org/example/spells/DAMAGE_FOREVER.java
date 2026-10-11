package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.main.Global.DamageTypeDoublePair;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class DAMAGE_FOREVER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.modifier;
    static final String staticImageFile = "damage_forever.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Mana To Damage";
        this.alias = new String[]{this.getClass().getSimpleName(), this.name, "mana to power", "mtd", "m2d"};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "If the wand has more than 50 mana, all mana over that is converted into additional damage";
        this.type = staticType;
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.2, 0.3, 0.6, 0.5, 0.2, 0, 0, 0, 0.2);
        this.price = 240;
        this.manaCost = 0;
        this.hasCharges = true;
        this.maxCharges = 20;
        this.neverUnlimited = true;
        this.castDelay = 15;
        this.rechargeTime = 10;
        this.recoil = 10.0;
        this.goreParticles = 15;
    }

    // TODO
    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        int availableMana = cardPool.getMaxMana() - cardPool.getManaUsage();

        if(availableMana > 50){
            castState.addDamageComponent(new DamageComponent(new DamageTypeDoublePair(0.625*(availableMana - 50), DamageType.PROJECTILE)));
            cardPool.setManaUsage(cardPool.getMaxMana() - 50);
        }
        // c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,"

        cardPool.draw(1, true, castState);
    }
}

/*{
	id          = "DAMAGE_FOREVER",
	name 		= "$action_damage_forever",
	description = "$actiondesc_damage_forever",
	sprite 		= "data/ui_gfx/gun_actions/damage_forever.png",
	sprite_unidentified = "data/ui_gfx/gun_actions/damage_unidentified.png",
	related_extra_entities = { "data/entities/particles/tinyspark_red.xml" },
	type 		= ACTION_TYPE_MODIFIER,
	spawn_level                       = "2,3,4,5,6,10", -- DAMAGE
	spawn_probability                 = "0.2,0.3,0.6,0.5,0.2,0.2", -- DAMAGE
	price = 240,
	mana = 0,
	max_uses = 20,
	never_unlimited = true,
	custom_xml_file = "data/entities/misc/custom_cards/damage_forever.xml",
	action 		= function()
		if ( mana > 50 ) then
			local manaforspell = mana - 50
			c.damage_projectile_add = c.damage_projectile_add + 0.025 * manaforspell
			mana = 50
		end

		c.gore_particles    = c.gore_particles + 15
		c.fire_rate_wait    = c.fire_rate_wait + 15
		current_reload_time = current_reload_time + 10
		c.extra_entities    = c.extra_entities .. "data/entities/particles/tinyspark_red.xml,"
		shot_effects.recoil_knockback = shot_effects.recoil_knockback + 10.0
		draw_actions( 1, true )
	end,
}*/