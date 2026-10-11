package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LASER_EMITTER_WIDER extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "laser_emitter_wider.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Plasma Beam Enhancer";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/laser_emitter_wider.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/laser_emitter_wider.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

entity_id = EntityGetRootEntity( entity_id )

if ( entity_id ~= NULL_ENTITY ) then
	local comps = EntityGetComponent( entity_id, "LaserEmitterComponent" )

	if ( comps ~= nil ) then
		for i,comp in ipairs( comps ) do
			local width = ComponentObjectGetValue2( comp, "laser", "beam_radius" ) or 0
			local dmg = ComponentObjectGetValue2( comp, "laser", "damage_to_entities" ) or 0
			local length = ComponentObjectGetValue2( comp, "laser", "max_length" ) or 0

			width = width + 2.0
			dmg = dmg + 0.025
			length = length + 36

			ComponentObjectSetValue2( comp, "laser", "beam_radius", width )
			ComponentObjectSetValue2( comp, "laser", "damage_to_entities", dmg )
			ComponentObjectSetValue2( comp, "laser", "max_length", length )
		end
	end
end*/