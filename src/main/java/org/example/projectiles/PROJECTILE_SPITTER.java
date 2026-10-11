package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_SPITTER extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "spitter.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_SPITTER(){
        this.name = "Spitter Bolt";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(200.0)
            .setAirFriction(2.7)
            .setMass(0.02);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(400)
            .setSpeedMax(600)
            .setFriction(1.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            .setLifetime(25)
            // bounce_always="1"
            // bounces_left="10"
            // bounce_energy="0.5"
            // bounce_fx_file="data/entities/particles/bounce_effects/spitter.xml"
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7)
            // ragdoll_force_multiplier="0.01"
            // hit_particle_force_multiplier="0.1"
            // velocity_sets_rotation="1"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_small_pink.xml"
            // shoot_light_flash_radius="80"
            // shoot_light_flash_r="245"
            // shoot_light_flash_g="120"
            // shoot_light_flash_b="220"
            .setKnockback(1.0);
            // physics_impulse_coeff="3500"
        this.projectileComponent.getDamageComponent().setDamage(7.5, DamageType.PROJECTILE);
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
    speed_min="400"
    speed_max="600"
	friction="1"
    direction_random_rad="0.00"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    lifetime="25"
    damage="0.3"
	bounce_always="1"
    bounces_left="10"
    bounce_energy="0.5"
	bounce_fx_file="data/entities/particles/bounce_effects/spitter.xml"
    velocity_sets_scale="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0.01"
    hit_particle_force_multiplier="0.1"
	velocity_sets_rotation="1"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_small_pink.xml"
    shoot_light_flash_radius="80"
	shoot_light_flash_r="245"
	shoot_light_flash_g="120"
	shoot_light_flash_b="220"
	knockback_force="1.0"
	physics_impulse_coeff="3500"
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
    image_file="data/projectiles_gfx/spitter.xml"
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
	r="80"
	g="10"
	b="40">
  </LightComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/spitter.xml"
		>
	</VariableStorageComponent>

</Entity>*/