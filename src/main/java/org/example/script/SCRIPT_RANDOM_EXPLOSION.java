package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_RANDOM_EXPLOSION extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "random_explosion.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaos magic";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/random_explosion.lua"
		execute_on_added="1"
		remove_after_executed="1">
   </LuaComponent>
</Entity>*/

// data/scripts/projectiles/random_explosion.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()

local projectilecomponents = EntityGetComponent( entity_id, "ProjectileComponent" )

local opts = {"acidshot","alcohol_blast","black_hole_big","cloud_thunder","cloud_acid","cloud_blood","cloud_water","death_cross","death_cross_big","fireball","firebomb","grenade","grenade_tier_2","grenade_tier_3","lightning","meteor","nuke","regeneration_field","rocket","rocket_tier_2","rocket_tier_3","tentacle_portal","thunder_blast","wall_square","xray"}

SetRandomSeed( entity_id + 2533, entity_id - 36 )
local rnd = Random( 1, #opts )

local result = "data/entities/projectiles/deck/" .. opts[rnd] .. ".xml"

if ( projectilecomponents ~= nil ) then
	for i,comp_id in ipairs( projectilecomponents ) do
		ComponentSetValue2( comp_id, "on_collision_spawn_entity", true )
		ComponentSetValue2( comp_id, "spawn_entity_is_projectile", true )

		ComponentSetValue2( comp_id, "spawn_entity", result )
	end
end*/