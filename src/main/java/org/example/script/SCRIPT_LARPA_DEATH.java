package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LARPA_DEATH extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "larpa_death.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Larpa Explosion";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/larpa_death.lua"
		execute_every_n_frame="-1"
		execute_on_removed="1"
		>
    </LuaComponent>
</Entity>*/

// data/scripts/projectiles/larpa_death.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = EntityGetRootEntity( GetUpdatedEntityID() )
local pos_x, pos_y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum() + GetUpdatedComponentID(), pos_x + pos_y + entity_id )

local variablestorages = EntityGetComponent( entity_id, "VariableStorageComponent" )
local projectile_file = ""

if ( variablestorages ~= nil ) then
	for j,storage_id in ipairs(variablestorages) do
		local var_name = ComponentGetValue( storage_id, "name" )
		if ( var_name == "projectile_file" ) then
			projectile_file = ComponentGetValue2( storage_id, "value_string" )
		end
	end
end

if ( #projectile_file > 0 ) then
	local angle = math.rad(Random(0,359))
	local length = 1500
	local how_many = 8
	local angle_inc = ( math.pi * 2 ) / how_many

	for i=1,how_many do
		local vel_x = math.cos( angle ) * length
		local vel_y = 0 - math.sin( angle ) * length

		local eid = shoot_projectile_from_projectile( entity_id, projectile_file, pos_x, pos_y, vel_x, vel_y )
		EntityAddTag( eid, "projectile_cloned" )
		angle = angle + angle_inc
	end
end*/