package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_ROCKET_OCTAGON extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "rocket_octagon.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Octagonal bolt bundle";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/rocket_octagon.lua"
		execute_every_n_frame="3"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/rocket_octagon.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

local vel_x,vel_y = 0,0

edit_component( entity_id, "VelocityComponent", function(comp,vars)
	vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity" )
end)

local speed = math.sqrt( vel_y ^ 2 + vel_x ^ 2 )

if ( speed < 50 ) then
	local how_many = 8
	local angle = 0
	local angle_inc = (math.pi * 2) / how_many

	for i=1,how_many do
		local shot_vel_x = math.cos(angle) * 150
		local shot_vel_y = 0 - math.sin(angle) * 150

		angle = angle + angle_inc

		shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/rocket_downwards.xml", pos_x + shot_vel_x * 0.05, pos_y + shot_vel_y * 0.05, shot_vel_x, shot_vel_y, false )
	end

	EntityKill( entity_id )
end*/