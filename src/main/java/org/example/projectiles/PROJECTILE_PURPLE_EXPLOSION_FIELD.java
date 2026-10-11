package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_PURPLE_EXPLOSION_FIELD extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "purple_explosion_field.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_PURPLE_EXPLOSION_FIELD(){
        this.name = "Glittering field";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(0)
            .setSpeedMax(0)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(false)
            .setOnCollisionDie(false)
            .setLifetime(600);
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="0"
			>
    	</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
  	lob_min="0.8"
  	lob_max="1.0"
    speed_min="0"
    speed_max="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
	explosion_dont_damage_shooter="0"
    on_collision_die="0"
	damage="0"
    lifetime="600" >
  </ProjectileComponent>

  <LuaComponent
	script_source_file="data/scripts/projectiles/purple_explosion_field.lua"
	execute_every_n_frame="11"
	>
  </LuaComponent>

  <ParticleEmitterComponent
		emitted_material_name="spark_purple"
		x_pos_offset_min="-60"
		x_pos_offset_max="60"
		y_pos_offset_min="-60"
		y_pos_offset_max="60"
		x_vel_min="-2"
		x_vel_max="2"
		y_vel_min="-2"
		y_vel_max="2"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="20.5"
		count_min="2"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		cosmetic_force_create="0"
		airflow_force="0.05"
		airflow_time="0.11"
		airflow_scale="0.05"
		emission_interval_min_frames="3"
		emission_interval_max_frames="6"
		emit_cosmetic_particles="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

</Entity>*/

// data/scripts/projectiles/purple_explosion_field.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

local area = 70
local enemies = EntityGetInRadiusWithTag( pos_x, pos_y, area, "homing_target" )

SetRandomSeed( GameGetFrameNum(), pos_x + pos_y + entity_id )

-- print( Random(0-area, area) )
-- print( Random(0-area, area) )
pos_x = pos_x + Random(0-area, area)
pos_y = pos_y + Random(0-area, area)

if ( #enemies > 0 ) and ( Random( 1, 5 ) == 2 ) then
	local rnd = Random( 1, #enemies )
	local enemy_id = enemies[rnd]

	local ex, ey = EntityGetTransform( enemy_id )

	pos_x = ex
	pos_y = ey
end

local projectile = shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/purple_explosion.xml", pos_x, pos_y, 0, 0 )*/