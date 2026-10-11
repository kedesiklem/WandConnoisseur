package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_ANTIHEAL extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "antiheal.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_ANTIHEAL(){
        this.name = "Deadly Heal";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(200.0)
            .setAirFriction(1.2)
            .setMass(0.07);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(600)
            .setSpeedMax(650)
            .setFriction(1.0)
            .setSpreadRad(0.01)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            .setLifetime(40)
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7)
            // ragdoll_force_multiplier="0.04"
            // hit_particle_force_multiplier="0.1"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_laser.xml"
            // shoot_light_flash_r="120"
            // shoot_light_flash_g="255"
            // shoot_light_flash_b="80"
            // shoot_light_flash_radius="72"
            .setKnockback(1.8);
            // physics_impulse_coeff="1500"
        this.projectileComponent.getDamageComponent().setDamage(30.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="200"
			air_friction="1.2"
			mass="0.07"
			>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    lob_min="0.5"
    lob_max="0.7"
    speed_min="600"
    speed_max="650"
    friction="1"
    direction_random_rad="0.01"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    lifetime="40"
    damage="1.20"
    velocity_sets_scale="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0.04"
    hit_particle_force_multiplier="0.1"
	muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_laser.xml"
    shoot_light_flash_r="120"
    shoot_light_flash_g="255"
    shoot_light_flash_b="80"
    shoot_light_flash_radius="72"
	knockback_force="1.8"
    physics_impulse_coeff="1500"
    >
    <config_explosion
    never_cache="1"
    damage="0"
    camera_shake="0.5"
    explosion_radius="2"
    explosion_sprite="data/particles/explosion_016_plasma.xml"
    explosion_sprite_lifetime="0"
    create_cell_probability="0"
    hole_destroy_liquid="0"
    explosion_sprite_additive="1"
    hole_enabled="1"
    ray_energy="400000"
    max_durability_to_destroy="8"
    particle_effect="0"
    damage_mortals="1"
    physics_explosion_power.min="0.02"
    physics_explosion_power.max="0.1"
    physics_throw_enabled="1"
    shake_vegetation="1"
    sparks_count_max="20"
    sparks_count_min="7"
    sparks_enabled="0"
    material_sparks_enabled="1"
    material_sparks_count_max="2"
    material_sparks_count_min="0"
    light_enabled="1"
    light_r="40"
    light_g="10"
    light_b="90"
    stains_enabled="1"
    stains_radius="3"
    >
    </config_explosion>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="1"
    alpha="1"
    image_file="data/projectiles_gfx/light_arrow.xml"
    next_rect_animation=""
    rect_animation=""
    additive="1"
    >
  </SpriteComponent>

	<ParticleEmitterComponent
		emitted_material_name="spark_blue"
		offset.x="0"
		offset.y="0"
		x_pos_offset_min="0"
		x_pos_offset_max="0"
		y_pos_offset_min="0"
		y_pos_offset_max="0"
		x_vel_min="0"
		x_vel_max="0"
		y_vel_min="0"
		y_vel_max="0"
		gravity.y="0.0"
		count_min="1"
		count_max="1"
		lifetime_min="1.0"
		lifetime_max="1.5"
		is_trail="1"
		trail_gap="1.0"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		airflow_force="0.5"
		airflow_time="0.001"
		airflow_scale="0.0833"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		create_real_particles="0"
		is_emitting="1"
    >
	</ParticleEmitterComponent>

  <AudioComponent
    file="data/audio/Desktop/projectiles.bank"
    event_root="player_projectiles/bullet"
    >
  </AudioComponent>

  <LightComponent
    radius="40"
  	r="10"
  	g="40"
  	b="120"
    >
  </LightComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/healhurt.xml"
		>
	</VariableStorageComponent>

	<HitEffectComponent
        effect_hit="LOAD_CHILD_ENTITY_LIMIT_TO_X"
        value="6"
        value_string="data/entities/misc/effect_healhurt.xml" >
	</HitEffectComponent >

</Entity>*/