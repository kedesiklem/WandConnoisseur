package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.projectiles.PROJECTILE_BLANKSTONE_STONE_FUSER;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class BLANKSTONE_STONE_FUSER extends Spell{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final SpellType staticType = SpellType.static_projectile;
    static final String staticImageFile = "stone_fuser.png";
    static final BufferedImage staticImage = loadImage(staticType, staticImageFile);

    @Override
    protected void initialization(){
        this.modded = true;
        this.name = "Stone Fuser";
        //this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        this.imageFile = staticImageFile;
        this.image = staticImage;
        this.emote = staticEmote;
        this.description = "Brutally merge multiple stones in a large radius.";
        this.type = staticType;
        this.relatedProjectile = new PROJECTILE_BLANKSTONE_STONE_FUSER();
        this.spawnProbabilities = new SpawnProbabilities(0, 0, 0.1, 0.1, 0, 0, 0.3, 0.5, 0, 0, 0.05);
        this.price = 250;
        this.manaCost = 200;
        this.hasCharges = true;
        this.maxCharges = 5;
        this.castDelay = 60;
        this.rechargeTime = 30;
        this.screenshake = 10.0;
    }

    @Override
    public void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        castState.addProjectile(this.relatedProjectile.clone());
    }
}

/*{
        {
        id          = "BLANKSTONE_STONE_FUSER",
        name 		= "$spell_blankstone_stone_fuser_name",
        description = "$spell_blankstone_stone_fuser_desc",
        sprite 		= "mods/blankStone/files/ui_gfx/gun_actions/stone_fuser.png",
        sprite_unidentified = "data/ui_gfx/gun_actions/explosive_projectile_unidentified.png",
        related_extra_entities = { "mods/blankStone/files/entities/misc/stone_fuser.xml" },
        type 		= ACTION_TYPE_STATIC_PROJECTILE,
        spawn_level                       = "2,3,6,7,10", -- LAVA_TO_BLOOD
        spawn_probability                 = "0.1,0.1,0.3,0.5,0.05", -- LAVA_TO_BLOOD
        price = 250,
        mana = 200,
        max_uses = 5,
        action 		= function()
            add_projectile("mods/blankStone/files/entities/misc/stone_fuser.xml")
            c.fire_rate_wait = c.fire_rate_wait + 60
            current_reload_time = current_reload_time + 30
            c.screenshake = c.screenshake + 10
        end,
    },
}*/