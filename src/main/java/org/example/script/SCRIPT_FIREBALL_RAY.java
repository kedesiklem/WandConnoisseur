package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_FIREBALL_RAY extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "fireball_ray.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Fireball thrower";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/fireball_ray.lua"
		execute_every_n_frame="10">
   </LuaComponent>
</Entity>*/

// data/scripts/projectiles/fireball_ray.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum() + GetUpdatedComponentID(), pos_x + pos_y + entity_id )

local angle = math.rad(Random(0,359))
local length = 3000

local vel_x = math.cos( angle ) * length
local vel_y = 0 - math.sin( angle ) * length

shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/fireball_ray.xml", pos_x, pos_y, vel_x, vel_y )*/