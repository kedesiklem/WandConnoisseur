package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HOMING_ACCELERATING extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "homing_accelerating.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Accelerative Homing";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<HomingComponent
		homing_targeting_coeff="20.0"
		homing_velocity_multiplier="0.4"
		detect_distance="200"
	>
	</HomingComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/homing_accelerating.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>
</Entity>*/

// data/scripts/projectiles/homing_accelerating.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )
entity_id = EntityGetRootEntity( entity_id )

if ( entity_id ~= NULL_ENTITY ) then
	local projectile_components = EntityGetComponent( entity_id, "HomingComponent" )

	if( projectile_components == nil ) then return end

	if ( #projectile_components > 0 ) then
		edit_component( entity_id, "HomingComponent", function(comp,vars)
			local targeting = ComponentGetValue2( comp, "homing_targeting_coeff" )
			local velocity = ComponentGetValue2( comp, "homing_velocity_multiplier" )

			targeting = math.min( 800, targeting + 2 )
			velocity = math.min( 2.0, velocity + 0.01 )

			ComponentSetValue2( comp, "homing_targeting_coeff", targeting )
			ComponentSetValue2( comp, "homing_velocity_multiplier", velocity )
		end)
	end
end*/