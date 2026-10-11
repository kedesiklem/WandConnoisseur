package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_BOUNCE_LASER extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "bounce_laser.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Laser bounce";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/bounce_laser.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

	<ProjectileComponent
		_tags="enabled_in_world"
		lifetime="-1"
		penetrate_entities="1"
		penetrate_world="1"
		damage="0.0"
		do_moveto_update="0"
	></ProjectileComponent>

	<VelocityComponent
		_tags="enabled_in_world"
	></VelocityComponent>

</Entity>*/

// data/scripts/projectiles/bounce_laser.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local parent_id = EntityGetParent( entity_id )

local target_id = 0

if ( parent_id ~= NULL_ENTITY ) then
	target_id = parent_id
else
	target_id = entity_id
end

if ( target_id ~= NULL_ENTITY ) then
	local projectile_components = EntityGetComponent( target_id, "ProjectileComponent" )

	if( projectile_components == nil ) then return end

	if ( #projectile_components > 0 ) then
		edit_component( target_id, "ProjectileComponent", function(comp,vars)
			vars.bounce_fx_file = "data/entities/projectiles/deck/bounce_laser_launcher.xml"
		end)
	end
end*/