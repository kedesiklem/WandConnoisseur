package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_FLOATING_ARC extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "floating_arc.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Floating arc";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/floating_arc.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/floating_arc.lua
/*dofile_once( "data/scripts/lib/utilities.lua" )

local ray_distance = 30
local target_y = 12
local max_vel_y = 240

local entity_id = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )
local velocity_comp = EntityGetFirstComponent( entity_id, "VelocityComponent")
if velocity_comp == nil then return end

local ray_success,r_x,r_y = RaytraceSurfacesAndLiquiform( pos_x, pos_y, pos_x, pos_y + ray_distance )
if not ray_success then return end

-- adjust velocity so that projectile steers towards target height
local vel_x,vel_y = ComponentGetValueVector2( velocity_comp, "mVelocity")
local pvel_y = vel_y
vel_y = -(pos_y - r_y + target_y) * 60 -- velocity for reaching target height
vel_y = clamp(vel_y, -max_vel_y, max_vel_y) -- speed limit
vel_y = lerp(vel_y, pvel_y, 0.5) -- blend with existing velocity for a smoother flight
vel_x = vel_x * 0.98 -- extra friction

ComponentSetValueVector2( velocity_comp, "mVelocity", vel_x, vel_y)*/