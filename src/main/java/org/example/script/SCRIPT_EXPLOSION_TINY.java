package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EXPLOSION_TINY extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "explosion_tiny.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Concentrated Explosion";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/explosion_tiny.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/explosion_tiny.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

entity_id = EntityGetRootEntity( entity_id )

if ( entity_id ~= NULL_ENTITY ) then
	edit_component( entity_id, "ProjectileComponent", function(comp,vars)
		ComponentObjectSetValue( comp, "config_explosion", "explosion_radius", "5" )
	end)

	local comps = EntityGetComponent( entity_id, "ExplosionComponent" )
	if ( comps ~= nil ) then
		for i,v in ipairs( comps ) do
			ComponentObjectSetValue( v, "config_explosion", "explosion_radius", "5" )
		end
	end

	comps = EntityGetComponent( entity_id, "ExplodeOnDamageComponent" )
	if ( comps ~= nil ) then
		for i,v in ipairs( comps ) do
			ComponentObjectSetValue( v, "config_explosion", "explosion_radius", "5" )
		end
	end
end*/