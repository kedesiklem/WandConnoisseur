package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_DISC_BULLET extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "disc_bullet.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_DISC_BULLET(){
        this.name = "Disc projectile";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(250.0)
            .setAirFriction(0.6)
            .setMass(0.05);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.5"
            // lob_max="0.7"
            .setSpeedMin(350)
            .setSpeedMax(450)
            .setFriction(1.0)
            .setSpreadRad(0.01)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="1"
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
            // shoot_light_flash_g="240"
            // shoot_light_flash_b="30"
            // shoot_light_flash_radius="64"
            .setDieOnLowVelocity(true)
            .setDieOnLowVelocityLimit(5.0)
            // bounces_left="2"
            // bounce_at_any_angle="1"
            // collide_with_shooter_frames="6"
            .setFriendlyFire(true)
            // velocity_sets_rotation="1"
            // velocity_updates_animation="1"
            // velocity_sets_scale="0"
            // ragdoll_fx_on_collision="BLOOD_EXPLOSION"
            .setKnockback(1.3);
            // physics_impulse_coeff="1500"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
        this.projectileComponent.getDamageComponent().setDamage(20.0, DamageType.SLICE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player,disc_bullet" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="250"
			air_friction="0.6"
			mass="0.05"
			>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.5"
    lob_max="0.7"
    speed_min="350"
    speed_max="450"
    friction="1"
    direction_random_rad="0.01"
    on_death_explode="0"
    on_death_gfx_leave_sprite="1"
    on_lifetime_out_explode="0"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
    on_collision_remove_projectile="0"
    lifetime="750"
    damage="0"
    damage_scaled_by_speed="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0"
    hit_particle_force_multiplier="0.1"
    create_shell_casing="0"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_medium.xml"
    shoot_light_flash_r="255"
    shoot_light_flash_g="240"
    shoot_light_flash_b="30"
    shoot_light_flash_radius="64"
    die_on_low_velocity="1"
    die_on_low_velocity_limit="5"
    bounces_left="2"
    bounce_at_any_angle="1"
    collide_with_shooter_frames="6"
    friendly_fire="1"
    velocity_sets_rotation="1"
    velocity_updates_animation="1"
    velocity_sets_scale="0"
    ragdoll_fx_on_collision="BLOOD_EXPLOSION"
	knockback_force="1.3"
    physics_impulse_coeff="1500"
    >
	<damage_by_type
		slice="0.8"
		>
	</damage_by_type>
    <config_explosion>
    </config_explosion>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="1"
    alpha="1"
    image_file="data/projectiles_gfx/disc_bullet.xml"
    next_rect_animation=""
    rect_animation=""
     >
  </SpriteComponent>

  <SpriteParticleEmitterComponent
    sprite_file="data/particles/discbullet_trail.xml"
    delay="0"
    lifetime="1"
	additive="1"
    color.r="1" color.g="1" color.b="1" color.a="1"
    color_change.r="0" color_change.g="0" color_change.b="0" color_change.a="-4"
    velocity.x="0" velocity.y="0"
    gravity.x="0" gravity.y="10"
    velocity_slowdown="0"
    rotation="0"
    angular_velocity="0"
    use_velocity_as_rotation="0"
    scale.x="1" scale.y="1"
    scale_velocity.x="0" scale_velocity.y="0"
    emission_interval_min_frames="5"
    emission_interval_max_frames="5"
    count_min="1" count_max="1"
	is_emitting="1"
	render_back="1"
    >
  </SpriteParticleEmitterComponent>

    <AudioComponent
        file="data/audio/Desktop/projectiles.bank"
        event_root="player_projectiles/bullet_disc">
    </AudioComponent>

    <AudioLoopComponent
        file="data/audio/Desktop/projectiles.bank"
        event_name="player_projectiles/bullet_disc/loop"
        set_speed_parameter="1"
        auto_play="1">
    </AudioLoopComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/disc_bullet.xml"
		>
	</VariableStorageComponent>

</Entity>*/