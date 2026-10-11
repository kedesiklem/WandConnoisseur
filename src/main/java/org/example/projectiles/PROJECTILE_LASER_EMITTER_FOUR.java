package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_LASER_EMITTER_FOUR extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "laser_emitter_four.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_LASER_EMITTER_FOUR(){
        this.name = "Plasma Beam Cross";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(7.0)
            .setMass(0.05);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(40)
            .setSpeedMax(40)
            .setDieOnLowVelocity(false)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(false)
            .setLifetime(100)
            .setKnockback(1.3);
        this.projectileComponent.getDamageComponent().setDamage(20.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default"
   tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
    		gravity_y="0"
			air_friction="7"
			mass="0.05"
			>
    	</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
	lob_min="0.8"
  	lob_max="1.0"
  	speed_min="40"
  	speed_max="40"
    die_on_low_velocity="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
	explosion_dont_damage_shooter="1"
    damage="0.8"
    on_collision_die="0"
    lifetime="100"
	knockback_force="1.3"
	>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="0"
    alpha="1"
    image_file="data/projectiles_gfx/orb_blue.xml"
    next_rect_animation=""
    offset_x="6"
    offset_y="6"
    rect_animation="spawn"
	emissive="1"
    additive="1"

	update_transform_rotation="0"
	>
  </SpriteComponent>

	<ParticleEmitterComponent
		emitted_material_name="spark_blue"
		gravity.y="0.10"
		offset.x="0"
		offset.y="0"
		gravity.y="0.10"
		x_pos_offset_min="-1"
		y_pos_offset_min="-1"
		x_pos_offset_max="1"
		y_pos_offset_max="1"
		x_vel_min="-4"
		x_vel_max="4"
		y_vel_min="-4"
		y_vel_max="4"
		count_min="1"
		count_max="5"
		lifetime_min="0.6"
		lifetime_max="0.8"
		render_on_grid="1"
		airflow_force="0.1"
		airflow_time="0.101"
		airflow_scale="2.01"
		fade_based_on_lifetime="1"
		create_real_particles="0"
		emit_cosmetic_particles="1"
		emission_interval_min_frames="0"
		emission_interval_max_frames="1"
		is_emitting="1" >
    </ParticleEmitterComponent>

  <LightComponent
    _enabled="1"
    radius="150"
    r="30"
    g="30"
    b="90">
  </LightComponent>

	<AudioComponent
		file="data/audio/Desktop/projectiles.bank"
		event_root="projectiles/orb_b" >
	</AudioComponent>

	<LaserEmitterComponent
		is_emitting="0"
		>
		<laser
			damage_to_entities="0.08"
			max_cell_durability_to_destroy="11"
			damage_to_cells="12000"
			root_entity_is_responsible_for_damage="1"
			max_length="112"
			beam_radius="3.5"
			hit_particle_chance="20"
			beam_particle_chance="90"
			beam_particle_type="plasma_fading"
			>
		</laser>
	</LaserEmitterComponent>

	<LaserEmitterComponent
		is_emitting="0"
		laser_angle_add_rad="1.570796"
		>
		<laser
			damage_to_entities="0.08"
			max_cell_durability_to_destroy="11"
			damage_to_cells="12000"
			root_entity_is_responsible_for_damage="1"
			max_length="112"
			beam_radius="3.5"
			hit_particle_chance="20"
			beam_particle_chance="90"
			beam_particle_type="plasma_fading"
			>
		</laser>
	</LaserEmitterComponent>

	<LaserEmitterComponent
		is_emitting="0"
		laser_angle_add_rad="3.14159"
		>
		<laser
			damage_to_entities="0.08"
			max_cell_durability_to_destroy="11"
			damage_to_cells="12000"
			root_entity_is_responsible_for_damage="1"
			max_length="112"
			beam_radius="3.5"
			hit_particle_chance="20"
			beam_particle_chance="90"
			beam_particle_type="plasma_fading"
			>
		</laser>
	</LaserEmitterComponent>

	<LaserEmitterComponent
		is_emitting="0"
		laser_angle_add_rad="4.712"
		>
		<laser
			damage_to_entities="0.08"
			max_cell_durability_to_destroy="11"
			damage_to_cells="12000"
			root_entity_is_responsible_for_damage="1"
			max_length="112"
			beam_radius="3.5"
			hit_particle_chance="20"
			beam_particle_chance="90"
			beam_particle_type="plasma_fading"
			>
		</laser>
	</LaserEmitterComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/laser_emitter_start.lua"
		execute_every_n_frame="2"
		remove_after_executed="1"
		>
	</LuaComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/orb_laseremitter_four.xml"
		>
	</VariableStorageComponent>
 </Entity>*/