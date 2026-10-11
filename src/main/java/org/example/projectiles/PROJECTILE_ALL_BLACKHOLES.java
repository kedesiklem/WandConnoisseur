package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_ALL_BLACKHOLES extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "all_blackholes.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_ALL_BLACKHOLES(){
        this.name = "Spells to black holes";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}
/*<Entity tags="projectile_player" >
	<LuaComponent
		script_source_file="data/scripts/projectiles/all_blackholes.lua"
		execute_every_n_frame="-1"
		execute_on_added="1"
		>
	</LuaComponent>

	<LifetimeComponent
		lifetime="2"
		>
	</LifetimeComponent>

	<AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/all_spell"
      set_latest_event_position="1" >
	</AudioComponent>
</Entity>*/

// data/scripts/projectiles/all_blackholes.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local projectiles = EntityGetWithTag( "projectile" )

if ( #projectiles > 0 ) then
	for i,projectile_id in ipairs( projectiles ) do
		local tags = EntityGetTags( projectile_id )

		if ( tags == nil ) or ( string.find( tags, "black_hole" ) == nil ) then
			local px, py = EntityGetTransform( projectile_id )
			local vel_x, vel_y = 0,0

			local projectilecomponents = EntityGetComponent( projectile_id, "ProjectileComponent" )
			local velocitycomponents = EntityGetComponent( projectile_id, "VelocityComponent" )

			if ( projectilecomponents ~= nil ) then
				for j,comp_id in ipairs( projectilecomponents ) do
					ComponentSetValue( comp_id, "on_death_explode", "0" )
					ComponentSetValue( comp_id, "on_lifetime_out_explode", "0" )
				end
			end

			if ( velocitycomponents ~= nil ) then
				edit_component( projectile_id, "VelocityComponent", function(comp,vars)
					vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity", vel_x, vel_y)
				end)
			end

			shoot_projectile_from_projectile( projectile_id, "data/entities/projectiles/deck/black_hole.xml", px, py, vel_x, vel_y )
			EntityKill( projectile_id )
		end
	end
end*/