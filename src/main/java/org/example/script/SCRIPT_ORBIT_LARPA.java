package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_ORBIT_LARPA extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "orbit_larpa.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Orbit Larpa";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
    <InheritTransformComponent>
    </InheritTransformComponent>

	<VariableStorageComponent
		_tags="orbit_projectile_type"
		name="orbit_projectile_type"
		value_string="orbit_larpa"
		>
	</VariableStorageComponent>

	<VariableStorageComponent
		_tags="orbit_projectile_speed"
		name="orbit_projectile_speed"
		value_float="0"
		>
	</VariableStorageComponent>

    <LuaComponent
		script_source_file="data/scripts/projectiles/orbit_projectile.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/orbit_projectile_rotation.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>
</Entity>*/

// data/scripts/projectiles/orbit_projectile.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local root_id    = EntityGetRootEntity( entity_id )
local x, y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum() + GetUpdatedComponentID(), x + y + entity_id )

local projtype = EntityGetFirstComponent( entity_id, "VariableStorageComponent", "orbit_projectile_type" )
local projspeed = EntityGetFirstComponent( entity_id, "VariableStorageComponent", "orbit_projectile_speed" )

if ( projtype ~= nil ) and ( projspeed ~= nil ) then
	local projfile = ComponentGetValue2( projtype, "value_string" )
	local proj = "data/entities/misc/" .. projfile .. ".xml"

	if ( projfile == "orbit_larpa" ) then
		proj = ""

		local comps = EntityGetComponent( root_id, "VariableStorageComponent" )
		if ( comps ~= nil ) then
			for i,comp in ipairs( comps ) do
				local name = ComponentGetValue2( comp, "name" )
				if ( name == "projectile_file" ) then
					proj = ComponentGetValue2( comp, "value_string" )
					break
				end
			end
		end
	end

	if ( proj ~= nil ) and ( #proj > 0 ) then
		local speed = Random( -1, 1 ) * 0.1
		while ( speed == 0 ) do
			speed = Random( -1, 1 ) * 0.1
		end

		ComponentSetValue2( projspeed, "value_float", speed )

		for i=1,4 do
			local eid = shoot_projectile_from_projectile( entity_id, proj, x, y, 0, 0 )
			EntityAddChild( entity_id, eid )

			if ( projfile == "orbit_larpa" ) then
				EntityAddTag( eid, "orbit_projectile" )
				EntityAddTag( eid, "projectile_cloned" )

				local comp = EntityGetFirstComponent( eid, "ProjectileComponent" )
				if ( comp ~= nil ) then
					ComponentSetValue2( comp, "lifetime", 7200 )
					ComponentSetValue2( comp, "die_on_low_velocity", false )
				end

				comp = EntityGetFirstComponent( eid, "LifetimeComponent" )
				if ( comp ~= nil ) then
					ComponentSetValue2( comp, "lifetime", 7200 )
				end
			end
		end
	end
end*/

// data/scripts/projectiles/orbit_projectile_rotation.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local projspeed = EntityGetFirstComponent( entity_id, "VariableStorageComponent", "orbit_projectile_speed" )
local orbits = EntityGetAllChildren( entity_id )

if ( projspeed ~= nil ) and ( orbits ~= nil ) then
	local speed = ComponentGetValue2( projspeed, "value_float" )
	local dist = 24

	local id = 0

	for i,v in ipairs( orbits ) do
		if EntityHasTag( v, "orbit_projectile" ) then
			local angle = math.pi * 0.5 * id + GameGetFrameNum() * speed
			local rot = 0 - ( angle - math.pi * 0.5 )

			if EntityHasTag( v, "orbit_laser" ) then
				dist = 8
			end

			local px = x + math.cos( angle ) * dist
			local py = y - math.sin( angle ) * dist

			EntitySetTransform( v, px, py, rot )
			EntityApplyTransform( v, px, py, rot )

			id = id + 1
		end
	end
end*/