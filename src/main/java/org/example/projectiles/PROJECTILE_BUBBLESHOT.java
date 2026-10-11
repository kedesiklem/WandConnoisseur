package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_BUBBLESHOT extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "bubbleshot.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_BUBBLESHOT(){
        this.name = "Bubble Spark";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(1.0)
            .setMass(0.02);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(200)
            .setSpeedMax(300)
            .setFriction(1.0)
            .setSpreadRad(0.4)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            .setLifetime(100)
            // bounce_always="1"
            // bounces_left="20"
            // bounce_energy="0.5"
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7)
            // ragdoll_force_multiplier="0.01"
            // hit_particle_force_multiplier="0.1"
            // velocity_sets_rotation="1"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_small.xml"
            // shoot_light_flash_radius="64"
            // shoot_light_flash_r="70"
            // shoot_light_flash_g="190"
            // shoot_light_flash_b="255"
            .setKnockback(0.5);
            // physics_impulse_coeff="2000"
        this.projectileComponent.getDamageComponent().setDamage(5.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="0"
			air_friction="1.0"
			mass="0.02"
			>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.5"
    lob_max="0.7"
    speed_min="200"
    speed_max="300"
	friction="1"
    direction_random_rad="0.40"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    lifetime="100"
    damage="0.2"
	bounce_always="1"
    bounces_left="20"
    bounce_energy="0.5"
    velocity_sets_scale="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0.01"
    hit_particle_force_multiplier="0.1"
	velocity_sets_rotation="1"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_small.xml"
    shoot_light_flash_radius="64"
	shoot_light_flash_r="70"
	shoot_light_flash_g="190"
	shoot_light_flash_b="255"
	knockback_force="0.5"
  physics_impulse_coeff="2000"
	>
    <config_explosion
      never_cache="1"
      damage="0"
      camera_shake="0"
      explosion_radius="4"
      explosion_sprite="data/particles/background_cleaner_explosion.xml"
      explosion_sprite_lifetime="0"
      create_cell_probability="0"
      hole_destroy_liquid="1"
      hole_enabled="1"
      ray_energy="400000"
      particle_effect="0"
      damage_mortals="1"
	  physics_explosion_power.min="0.05"
      physics_explosion_power.max="0.1"
      physics_throw_enabled="1"
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
    image_file="data/projectiles_gfx/background_cleaner.xml"
    next_rect_animation=""
    rect_animation="fireball"
	emissive="1"
	additive="1"

     >
  </SpriteComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/bullet_bubble">
  </AudioComponent>

  <LightComponent
    _enabled="1"
    radius="60"
	fade_out_time="0.1"
	r="10"
	g="40"
	b="80">
  </LightComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/bubbleshot.xml"
		>
	</VariableStorageComponent>

</Entity>*/