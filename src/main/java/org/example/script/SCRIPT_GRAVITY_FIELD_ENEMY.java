package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_GRAVITY_FIELD_ENEMY extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "gravity_field_enemy.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Personal gravity field";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

    <HitEffectComponent
        effect_hit="LOAD_UNIQUE_CHILD_ENTITY"
        value_string="data/entities/misc/gravity_field_enemy.xml" >
	</HitEffectComponent >

</Entity>*/

// data/entities/misc/gravity_field_enemy.xml
/*<Entity>

	<InheritTransformComponent />

	<LuaComponent
		script_source_file="data/scripts/projectiles/projectile_gravity_small.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>

	<ParticleEmitterComponent
		emitted_material_name="spark_purple_bright"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="1.5"
		count_min="2"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.max="72"
		cosmetic_force_create="0"
		airflow_force="0.5"
		airflow_time="0.01"
		airflow_scale="0.05"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		is_emitting="1"
		draw_as_long="1"
		attractor_force="16"
		>
	</ParticleEmitterComponent>

  	<ParticleEmitterComponent
		emitted_material_name="spark_purple_bright"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="1.5"
		count_min="10"
		count_max="20"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.min="72"
		area_circle_radius.max="72"
		cosmetic_force_create="0"
		airflow_force="0.3"
		airflow_time="0.01"
		airflow_scale="0.05"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

	<Base file="data/entities/projectiles/deck/base_field.xml">
		<SpriteComponent
			image_file="data/particles/area_indicator_072_purple_dark.png"
			z_index="1.2"
			offset_x="36"
			offset_y="36"
			_enabled="0"
			>
		</SpriteComponent>

		<SpriteParticleEmitterComponent
			_enabled="0"
			>
		</SpriteParticleEmitterComponent>

		<ProjectileComponent
			damage_game_effect_entities=""
			>
			<config_explosion
				explosion_sprite="data/particles/blast_out.xml"
				>
			</config_explosion>
		</ProjectileComponent>
	</Base>

</Entity>*/

// data/scripts/projectiles/projectile_gravity_small.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local projectiles = EntityGetWithTag( "projectile" )

if ( #projectiles > 0 ) then
	for i,projectile_id in ipairs(projectiles) do
		local px, py = EntityGetTransform( projectile_id )

		local distance = math.abs( x - px ) + math.abs( y - py )
		local distance_full = 72

		if ( distance < distance_full * 1.25 ) and ( entity_id ~= projectile_id ) then
			distance = math.sqrt( ( x - px ) ^ 2 + ( y - py ) ^ 2 )
			direction = 0 - math.atan2( ( y - py ), ( x - px ) )

			if ( distance < distance_full ) then
				local velocitycomponents = EntityGetComponent( projectile_id, "VelocityComponent" )

				local gravity_percent = math.max(( distance_full - distance ) / distance_full, 0.2)
				local gravity_coeff = 112

				if ( velocitycomponents ~= nil ) then
					edit_component( projectile_id, "VelocityComponent", function(comp,vars)
						local vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")

						local offset_x = math.cos( direction ) * ( gravity_coeff * gravity_percent )
						local offset_y = 0 - math.sin( direction ) * ( gravity_coeff * gravity_percent )

						vel_x = vel_x + offset_x
						vel_y = vel_y + offset_y

						ComponentSetValueVector2( comp, "mVelocity", vel_x, vel_y)
					end)
				end
			end
		end
	end
end*/