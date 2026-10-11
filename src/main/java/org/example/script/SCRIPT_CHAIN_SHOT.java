package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_CHAIN_SHOT extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "chain_shot.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chain Spell";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/chain_shot_init.lua"
		execute_on_added="1"
		execute_every_n_frame="-1"
		>
	</LuaComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/chain_shot_launch.lua"
		execute_every_n_frame="-1"
		execute_on_removed="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/chain_shot_init.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x,y = EntityGetTransform( entity_id )

local comps = EntityGetComponent( entity_id, "VariableStorageComponent" )
local iteration = 0
local projectile_file = ""

if ( comps ~= nil ) then
	for i,v in ipairs( comps ) do
		local n = ComponentGetValue2( v, "name" )
		if ( n == "chain_shot" ) then
			iteration = ComponentGetValue2( v, "value_int" )
		elseif ( n == "projectile_file" ) then
			projectile_file = ComponentGetValue2( v, "value_string" )
		end
	end
end

if ( iteration == 0 ) then
	print( "Chain shot failed" )
elseif ( iteration > 0 ) and ( iteration < 5 ) and ( #projectile_file > 0 ) and ( string.find(projectile_file, "exploding_deer") == nil ) and ( string.find(projectile_file, "pebble") == nil ) then
	iteration = iteration + 1
	local vel_x,vel_y = 0,0

	edit_component( entity_id, "VelocityComponent", function(comp,vars)
		vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")
	end)

	SetRandomSeed( x - vel_x, y + vel_y )

	if ( math.abs( vel_x ) + math.abs( vel_y ) < 20 ) then
		vel_x = vel_x + Random( -400, 400 )
		vel_y = vel_y + Random( -400, 400 )
	end

	vel_x = vel_x + Random( -10, 10 )
	vel_y = vel_y + Random( -10, 10 )

	local eid = shoot_projectile_from_projectile( entity_id, projectile_file, x, y, vel_x, vel_y )
	EntityLoadToEntity( "data/entities/misc/chain_shot.xml", eid )
	EntityAddComponent( eid, "VariableStorageComponent",
	{
		name = "chain_shot",
		value_int = iteration,
	} )
end*/

// data/scripts/projectiles/chain_shot_launch.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local comps = EntityGetComponent( entity_id, "VariableStorageComponent" )
local iteration = 0

if ( comps ~= nil ) then
	for i,v in ipairs( comps ) do
		local n = ComponentGetValue2( v, "name" )
		if ( n == "chain_shot" ) then
			iteration = ComponentGetValue2( v, "value_int" )
			break
		end
	end
end

if ( iteration == 0 ) then
	iteration = 1
	EntityAddComponent( entity_id, "VariableStorageComponent",
	{
		name = "chain_shot",
		value_int = 1,
	} )
end*/