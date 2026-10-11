package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_RUBBER_BALL extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "rubber_ball.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_RUBBER_BALL(){
        this.name = "Bouncing burst";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(250.0)
            .setAirFriction(0.6)
            .setMass(0.03);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(650)
            .setSpeedMax(750)
            .setFriction(1.0)
            .setSpreadRad(0.01)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            // on_collision_remove_projectile="0"
            .setLifetime(750)
            .setDamageScaledBySpeed(true)
            .setLifetimeRandomness(7)
            // ragdoll_force_multiplier="0"
            // hit_particle_force_multiplier="0.1"
            // create_shell_casing="0"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_medium.xml"
            // shoot_light_flash_r="255"
            // shoot_light_flash_g="210"
            // shoot_light_flash_b="70"
            // shoot_light_flash_radius="80"
            .setDieOnLowVelocity(true)
            .setDieOnLowVelocityLimit(5)
            // bounce_always="1"
            // bounces_left="10"
            // bounce_energy="0.9"
            // bounce_fx_file="data/entities/particles/bounce_effects/rubber_ball.xml"
            // velocity_sets_rotation="1"
            // velocity_updates_animation="1"
            // velocity_sets_scale="1"
            // velocity_sets_scale_coeff="0.6"
            .setKnockback(0.4);
            // physics_impulse_coeff="1200"
        this.projectileComponent.getDamageComponent().setDamage(3.0, DamageType.PROJECTILE);
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.SLICE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
		  gravity_y="250"
		  air_friction="0.6"
		  mass="0.03"
		  >
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.5"
    lob_max="0.7"
    speed_min="650"
    speed_max="750"
    friction="1"
    direction_random_rad="0.01"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    on_collision_remove_projectile="0"
    lifetime="750"
    damage="0.12"
    damage_scaled_by_speed="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0"
    hit_particle_force_multiplier="0.1"
    create_shell_casing="0"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_medium.xml"
    shoot_light_flash_r="255"
    shoot_light_flash_g="210"
    shoot_light_flash_b="70"
    shoot_light_flash_radius="80"
    die_on_low_velocity="1"
    die_on_low_velocity_limit="5"
    bounce_always="1"
    bounces_left="10"
    bounce_energy="0.9"
	bounce_fx_file="data/entities/particles/bounce_effects/rubber_ball.xml"
    velocity_sets_rotation="1"
    velocity_updates_animation="1"
    velocity_sets_scale="1"
    velocity_sets_scale_coeff="0.6"
	knockback_force="0.4"
	physics_impulse_coeff="1200"
     >
	<damage_by_type
		slice="0"
		>
	</damage_by_type>
    <config_explosion
		damage="0"
		explosion_radius="1"
		>
    </config_explosion>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="1"
    alpha="1"
    image_file="data/projectiles_gfx/rubber_ball.xml"
    next_rect_animation=""
    rect_animation=""

     >
  </SpriteComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/bullet_rubber_ball">
  </AudioComponent>

  <ParticleEmitterComponent
		emitted_material_name="plasma_fading_green"
		offset.x="0"
		offset.y="0"
		x_pos_offset_min="0"
		x_pos_offset_max="0"
		y_pos_offset_min="0"
		y_pos_offset_max="0"
		gravity.y="0"
		x_vel_min="-3"
		x_vel_max="3"
		y_vel_min="-3"
		y_vel_max="3"
		count_min="1"
		count_max="1"
		lifetime_min="0.2"
		lifetime_max="0.5"
		airflow_force="1.0"
		airflow_time="0.401"
		airflow_scale="0.25"
		is_trail="1"
		trail_gap="1.0"
		create_real_particles="0"
		emit_cosmetic_particles="1"
		render_on_grid="1"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		fade_based_on_lifetime="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/rubber_ball.xml"
		>
	</VariableStorageComponent>

</Entity>*/