package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_AUTOAIM extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "autoaim.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Auto-Aim";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
    <InheritTransformComponent>
    </InheritTransformComponent>

    <LuaComponent
		script_source_file="data/scripts/projectiles/autoaim.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

	<ParticleEmitterComponent
		_tags="autoaim_fx"
		_enabled="0"
		emitted_material_name="spark_white"
		offset.x="0"
		offset.y="0"
		gravity.y="50"
		x_vel_min="-8"
		x_vel_max="8"
		y_vel_min="-8"
		y_vel_max="8"
		count_min="10"
		count_max="15"
		lifetime_min="0.2"
		lifetime_max="0.8"
		emitter_lifetime_frames="4"
		create_real_particles="0"
		emit_cosmetic_particles="1"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		is_emitting="1" >
	</ParticleEmitterComponent>
</Entity>*/

// data/scripts/projectiles/autoaim.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local range = 200
local steering_strength = 0.8 -- how much to lerp towards target (0...1)
local scatter = 0.1 -- how much the velocity direction is randomized. radians

local entity_id = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetFirstHitboxCenter( entity_id )

-- get shooter
local comp = EntityGetFirstComponent( entity_id, "ProjectileComponent" )
if comp == nil then return end
local who_shot = ComponentGetValue2( comp, "mWhoShot")

-- locate nearest enemy
local enemy, enemy_x, enemy_y
local min_dist = 9999
for _,id in pairs(EntityGetInRadiusWithTag(pos_x, pos_y, range, "mortal")) do
	-- is target a valid enemy
	if EntityGetComponent(id, "GenomeDataComponent") ~= nil and EntityGetHerdRelation(who_shot, id) < 70 and id ~= who_shot then
		local x, y = EntityGetFirstHitboxCenter( id )
		local dist = get_distance(pos_x, pos_y, x, y)
		if dist < min_dist then
			min_dist = dist
			enemy = id
			enemy_x = x
			enemy_y = y
		end
	end
end

if not enemy then return end

-- check los
if RaytraceSurfacesAndLiquiform(pos_x, pos_y, enemy_x, enemy_y) then return end

-- direction
local dir_x, dir_y = vec_sub(enemy_x, enemy_y, pos_x, pos_y)
dir_x,dir_y = vec_normalize(dir_x, dir_y)

-- get velocity and rotate it to align with ray
local vel_x,vel_y = GameGetVelocityCompVelocity(entity_id)
if vel_x == nil then return end
local speed = get_magnitude(vel_x, vel_y)

-- lerp direction and restore speed
vel_x,vel_y = vec_normalize(vel_x, vel_y)
vel_x = lerp(dir_x, vel_x, steering_strength)
vel_y = lerp(dir_y, vel_y, steering_strength)
vel_x,vel_y = vec_normalize(vel_x, vel_y)
vel_x = vel_x * speed
vel_y = vel_y * speed

-- scatter
SetRandomSeed(pos_x + GameGetFrameNum(), pos_y)
vel_x,vel_y = vec_rotate(vel_x,vel_y, rand(-scatter, scatter))

-- set velocity
comp = EntityGetFirstComponent( entity_id, "VelocityComponent" )
ComponentSetValueVector2( comp, "mVelocity", vel_x, vel_y)

-- FX
EntitySetComponentsWithTagEnabled( entity_id, "autoaim_fx", true )

--[[
-- DEBUG
if true then
	local frames = 30
	GameCreateSpriteForXFrames("data/particles/radar_enemy_strong.png", enemy_x, enemy_y, true, 0, 0, frames)
	GameCreateSpriteForXFrames("data/particles/radar_enemy_medium.png", pos_x + vel_x * 20, pos_y + vel_y * 20, true, 0, 0, frames)
	GameCreateSpriteForXFrames("data/particles/radar_enemy_faint.png", pos_x, pos_y, true, 0, 0, frames)
end
--]]*/