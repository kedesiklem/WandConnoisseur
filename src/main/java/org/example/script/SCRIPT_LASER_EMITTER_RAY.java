package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LASER_EMITTER_RAY extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "laser_emitter_ray.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam Thrower";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/laser_emitter_ray.lua"
		execute_every_n_frame="9">
   </LuaComponent>

   <LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/laser_emitter_ray.lua"
		execute_every_n_frame="5"
		remove_after_executed="1">
   </LuaComponent>
</Entity>*/

// data/scripts/projectiles/laser_emitter_ray.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum() + GetUpdatedComponentID(), pos_x + pos_y + entity_id )

local angle = math.rad(Random(0,359))
local length = 40

local vel_x = math.cos( angle ) * length
local vel_y = 0 - math.sin( angle ) * length

local eid = shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/orb_laseremitter_weak.xml", pos_x, pos_y, vel_x, vel_y )*/