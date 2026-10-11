package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_TELEPORT_PROJECTILE_STATIC extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "teleport_projectile_static.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_TELEPORT_PROJECTILE_STATIC(){
        this.name = "Return";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(1.7)
            .setMass(0.04);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(0)
            .setSpeedMax(0)
            .setFriction(1.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(false)
            // on_collision_remove_projectile="0"
            .setLifetime(240)
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7);
            // ragdoll_force_multiplier="0.005"
            // hit_particle_force_multiplier="0.1"
            // create_shell_casing="0"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_launcher_blue.xml"
            // shoot_light_flash_radius="120"
            // shoot_light_flash_r="140"
            // shoot_light_flash_g="210"
            // shoot_light_flash_b="255"
            // penetrate_entities="1"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
		  gravity_y="0"
		  air_friction="1.7"
		  mass="0.04"
		  >
		</VelocityComponent>
	</Base>

  <TeleportProjectileComponent
    min_distance_from_wall="4">
  </TeleportProjectileComponent>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.5"
    lob_max="0.7"
    speed_min="0"
    speed_max="0"
    friction="1"
    direction_random_rad="0.00"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="0"
    on_collision_remove_projectile="0"
    lifetime="240"
    damage="0"
    velocity_sets_scale="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0.005"
    hit_particle_force_multiplier="0.1"
    create_shell_casing="0"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_launcher_blue.xml"
    shoot_light_flash_radius="120"
	shoot_light_flash_r="140"
	shoot_light_flash_g="210"
	shoot_light_flash_b="255"
	penetrate_entities="1"
	>
    <config_explosion
      never_cache="1"
      damage="0.0"
      camera_shake="0.5"
      explosion_radius="2"
      explosion_sprite="data/particles/explosion_008.xml"
      explosion_sprite_lifetime="0"
      create_cell_probability="0"
      hole_destroy_liquid="0"
	  explosion_sprite_additive="1"
      hole_enabled="1"
      ray_energy="40000"
      particle_effect="0"
      damage_mortals="1"
	  physics_explosion_power.min="0"
      physics_explosion_power.max="0"
      physics_throw_enabled="0"
      shake_vegetation="1"
      sparks_count_max="20"
      sparks_count_min="7"
      sparks_enabled="0"
      material_sparks_enabled="1"
      material_sparks_count_max="2"
      material_sparks_count_min="0"
      light_enabled="0"
      stains_enabled="1"
      stains_radius="3" >
    </config_explosion>
  </ProjectileComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/teleport">
  </AudioComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_blue"
    offset.x="2"
    offset.y="0"
	gravity.y="0.0"
	x_vel_min="-20"
    x_vel_max="20"
    y_vel_min="-20"
    y_vel_max="20"
    count_min="1"
    count_max="1"
    lifetime_min="0.65"
    lifetime_max="1.2"
    emit_real_particles="0"
	render_on_grid="1"
	airflow_force="0.3"
	airflow_time="0.01"
	airflow_scale="0.05"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="2"
	area_circle_radius.max="2"
	fade_based_on_lifetime="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

</Entity>*/