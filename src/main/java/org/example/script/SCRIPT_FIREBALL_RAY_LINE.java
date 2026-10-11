package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_FIREBALL_RAY_LINE extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "fireball_ray_line.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Two-way fireball thrower";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/fireball_ray_line.lua"
		execute_every_n_frame="10">
   </LuaComponent>
</Entity>*/

// data/scripts/projectiles/fireball_ray_line.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )
local vel_x, vel_y = 0,0

edit_component( entity_id, "VelocityComponent", function(comp,vars)
	vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity", vel_x, vel_y)
end)

if ( vel_x ~= 0 ) or ( vel_y ~= 0 ) then
	local angle = 0 - math.atan2( vel_y, vel_x )
	local length = 2000

	local angle_up = angle + 3.1415 * 0.5
	local angle_down = angle - 3.1415 * 0.5

	vel_x = math.cos( angle_up ) * length
	vel_y = 0 - math.sin( angle_up ) * length

	shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/fireball_ray_small.xml", pos_x, pos_y, vel_x, vel_y )

	vel_x = math.cos( angle_down ) * length
	vel_y = 0 - math.sin( angle_down ) * length

	shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/fireball_ray_small.xml", pos_x, pos_y, vel_x, vel_y )
end*/