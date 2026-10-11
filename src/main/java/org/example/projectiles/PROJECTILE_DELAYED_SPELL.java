package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_DELAYED_SPELL extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "delayed_spell.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_DELAYED_SPELL(){
        this.name = "Delayed spellcast";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setMass(0.04);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(26)
            .setSpeedMax(26)
            .setSpreadRad(0.0)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(false)
            // die_on_liquid_collision="0"
            .setLifetime(100)
            // ragdoll_force_multiplier="0.0"
            // hit_particle_force_multiplier="0.0"
            // camera_shake_when_shot="5.0"
            // bounces_left="0"
            // muzzle_flash_file=""
            // shoot_light_flash_radius="1"
            .setKnockback(0.0);
            // physics_impulse_coeff="0"
            // penetrate_entities="1"
            // damage_every_x_frames="25"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="0"
			mass="0.04"
		>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.8"
    lob_max="1.0"
    speed_min="26"
    speed_max="26"
    direction_random_rad="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    explosion_dont_damage_shooter="1"
    on_collision_die="0"
	die_on_liquid_collision="0"
    lifetime="100"
    damage="0"
    ragdoll_force_multiplier="0.0"
    hit_particle_force_multiplier="0.0"
    camera_shake_when_shot="5.0"
    bounces_left="0"
	muzzle_flash_file=""
	shoot_light_flash_radius="1"
	knockback_force="0"
	physics_impulse_coeff="0"
	penetrate_entities="1"
	damage_every_x_frames="25"
	>
    <config_explosion />
  </ProjectileComponent>

  <LightComponent
    _enabled="1"
    radius="20"
    r="30"
    g="90"
    b="30">
  </LightComponent>

  <ParticleEmitterComponent
    emitted_material_name="plasma_fading_green"
    offset.x="0"
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
	area_circle_radius.max="4"
	fade_based_on_lifetime="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/delayed_spell">
  </AudioComponent>

  <AudioLoopComponent
		file="data/audio/Desktop/projectiles.bank"
		event_name="player_projectiles/delayed_spell/loop"
		auto_play="1">
	</AudioLoopComponent>

</Entity>*/