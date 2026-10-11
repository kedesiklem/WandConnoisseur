package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_SPIRALING_SHOT extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "spiraling_shot.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Spiral Arc";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<LuaComponent
		script_source_file="data/scripts/projectiles/orbit_shot_init.lua"
		execute_every_n_frame="2"
		remove_after_executed="1"
		>
	</LuaComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/orbit_shot.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>
</Entity>*/

// data/scripts/projectiles/orbit_shot_init.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local comps = EntityGetComponent( entity_id, "VariableStorageComponent", "orbit_shot" )

if ( EntityHasTag( entity_id, "orbit_shot" ) == false ) then
	EntityAddComponent( entity_id, "VariableStorageComponent",
	{
		name = "origin_x",
		value_float = x,
	} )

	EntityAddComponent( entity_id, "VariableStorageComponent",
	{
		name = "origin_y",
		value_float = y,
	} )

	SetRandomSeed( x - 353, y * GameGetFrameNum() )
	local orbit_speed = Random( 30, 70 ) * ( Random( 0, 1 ) * 2 - 1 )

	EntityAddComponent( entity_id, "VariableStorageComponent",
	{
		name = "orbit_speed",
		value_int = orbit_speed,
	} )

	local vel_x,vel_y = 10,10
	edit_component( entity_id, "VelocityComponent", function(comp,vars)
		vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")
	end)
	local spd = math.sqrt( vel_y ^ 2 + vel_x ^ 2 )

	--print( tostring( spd ) )

	EntityAddComponent( entity_id, "VariableStorageComponent",
	{
		name = "rot_speed",
		value_float = spd,
	} )

	EntityAddTag( entity_id, "orbit_shot" )
end*/

// data/scripts/projectiles/orbit_shot.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )
local ox,oy,spd,rspd

local comps = EntityGetComponent( entity_id, "VariableStorageComponent" )

if ( comps ~= nil ) then
	for i,v in ipairs( comps ) do
		local n = ComponentGetValue2( v, "name" )

		if ( n == "origin_x" ) then
			ox = ComponentGetValue2( v, "value_float" )
		elseif ( n == "origin_y" ) then
			oy = ComponentGetValue2( v, "value_float" )
		elseif ( n == "orbit_speed" ) then
			spd = ComponentGetValue2( v, "value_int" )
		elseif ( n == "rot_speed" ) then
			rspd = ComponentGetValue2( v, "value_float" )
		end
	end
end

if ( ox ~= nil ) and ( oy ~= nil ) and ( spd ~= nil ) and ( rspd ~= nil ) then
	edit_component( entity_id, "VelocityComponent", function(comp,vars)
		local vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")

		local dir = get_direction( x, y, ox, oy )
		spd = math.rad( spd )

		vel_x = math.cos( dir + spd ) * rspd
		vel_y = 0 - math.sin( dir + spd ) * rspd

		ComponentSetValueVector2( comp, "mVelocity", vel_x, vel_y)
	end)
end*/