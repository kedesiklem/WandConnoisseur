package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_ALL_DEATHCROSSES extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "all_deathcrosses.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_ALL_DEATHCROSSES(){
        this.name = "Spells to death crosses";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity tags="projectile_player" >
	<LuaComponent
		script_source_file="data/scripts/projectiles/all_deathcrosses.lua"
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

// data/scripts/projectiles/all_deathcrosses.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local projectiles = EntityGetWithTag( "projectile" )

if ( #projectiles > 0 ) then
	for i,projectile_id in ipairs( projectiles ) do
		local tags = EntityGetTags( projectile_id )

		if ( tags == nil ) or ( string.find( tags, "death_cross" ) == nil ) then
			local px, py = EntityGetTransform( projectile_id )

			local projectilecomponents = EntityGetComponent( projectile_id, "ProjectileComponent" )
			local velocitycomponents = EntityGetComponent( projectile_id, "VelocityComponent" )

			if ( projectilecomponents ~= nil ) then
				for j,comp_id in ipairs( projectilecomponents ) do
					ComponentSetValue( comp_id, "on_death_explode", "0" )
					ComponentSetValue( comp_id, "on_lifetime_out_explode", "0" )
				end
			end

			SetRandomSeed( px, py - 543 )
			local opts = { "death_cross", "death_cross_big" }
			local rnd = Random( 1, #opts )
			local opt = opts[rnd]

			shoot_projectile_from_projectile( projectile_id, "data/entities/projectiles/deck/" .. opt .. ".xml", px, py, 0, 0 )
			EntityKill( projectile_id )
		end
	end
end*/