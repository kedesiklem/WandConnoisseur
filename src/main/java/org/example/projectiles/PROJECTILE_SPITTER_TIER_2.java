package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_SPITTER_TIER_2 extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "spitter_green.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_SPITTER_TIER_2(){
        this.name = "Large spitter bolt";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(200.0)
            .setAirFriction(2.7)
            .setMass(0.02);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(600)
            .setSpeedMax(800)
            .setFriction(1.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            .setLifetime(30)
            // bounce_always="1"
            // bounces_left="10"
            // bounce_energy="0.6"
            // bounce_fx_file="data/entities/particles/bounce_effects/spitter_green.xml"
            // velocity_sets_scale="1"
            .setLifetimeRandomness(12)
            // ragdoll_force_multiplier="0.05"
            // hit_particle_force_multiplier="0.3"
            // velocity_sets_rotation="1"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_small.xml"
            // shoot_light_flash_radius="80"
            // shoot_light_flash_r="125"
            // shoot_light_flash_g="245"
            // shoot_light_flash_b="50"
            .setKnockback(1.0);
            // physics_impulse_coeff="2500"
        this.projectileComponent.getDamageComponent().setDamage(12.5, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
		  gravity_y="200"
		  air_friction="2.7"
		  mass="0.02"
		  >
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.5"
    lob_max="0.7"
    speed_min="600"
    speed_max="800"
	friction="1"
    direction_random_rad="0.00"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    lifetime="30"
    damage="0.5"
	bounce_always="1"
    bounces_left="10"
    bounce_energy="0.6"
	bounce_fx_file="data/entities/particles/bounce_effects/spitter_green.xml"
    velocity_sets_scale="1"
    lifetime_randomness="12"
    ragdoll_force_multiplier="0.05"
    hit_particle_force_multiplier="0.3"
	velocity_sets_rotation="1"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_small.xml"
    shoot_light_flash_radius="80"
	shoot_light_flash_r="125"
	shoot_light_flash_g="245"
	shoot_light_flash_b="50"
	knockback_force="1.0"
	physics_impulse_coeff="2500"
	>
    <config_explosion
      never_cache="1"
      damage="0.0"
      camera_shake="0"
      explosion_radius="2"
      explosion_sprite=""
      explosion_sprite_lifetime="0"
      create_cell_probability="0"
      hole_destroy_liquid="0"
      hole_enabled="0"
      ray_energy="400000"
      particle_effect="0"
      damage_mortals="1"
	  physics_explosion_power.min="0"
      physics_explosion_power.max="0"
      physics_throw_enabled="0"
      shake_vegetation="1"
      sparks_enabled="0"
      material_sparks_enabled="1"
      material_sparks_count_max="2"
      material_sparks_count_min="0"
      light_enabled="0"
      stains_enabled="1"
      stains_radius="3" >
    </config_explosion>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="1"
    alpha="1"
    image_file="data/projectiles_gfx/spitter_green.xml"
    next_rect_animation=""
    rect_animation="fireball"
	emissive="1"
	additive="1"

     >
  </SpriteComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/bullet_smg">
  </AudioComponent>

  <LightComponent
    _enabled="1"
    radius="60"
	fade_out_time="0.1"
	r="40"
	g="80"
	b="10">
  </LightComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    offset.x="0"
    offset.y="0"
    x_pos_offset_min="-1"
    y_pos_offset_min="-1"
    x_pos_offset_max="1"
    y_pos_offset_max="1"
    x_vel_min="-20"
    x_vel_max="20"
    y_vel_min="-20"
    y_vel_max="20"
    count_min="1"
    count_max="6"
    lifetime_min="0.6"
    lifetime_max="1.1"
    create_real_particles="0"
    emit_cosmetic_particles="1"
	render_on_grid="1"
	gravity.y="20"
	fade_based_on_lifetime="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    offset.x="0"
    offset.y="0"
    count_min="1"
    count_max="8"
    gravity.y="-10"
    lifetime_min="0.25"
    lifetime_max="0.5"
    airflow_force="1.2"
    airflow_time="0.1"
    airflow_scale="0.03"
    render_on_grid="1"
    fade_based_on_lifetime="1"
    create_real_particles="0"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
	x_pos_offset_min="-1"
    y_pos_offset_min="-1"
    x_pos_offset_max="1"
    y_pos_offset_max="1"
    is_emitting="1"
	>
  </ParticleEmitterComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/spitter_tier_2.xml"
		>
	</VariableStorageComponent>

</Entity>*/