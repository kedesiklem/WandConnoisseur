package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_FREEZING_GAZE extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "freezing_gaze_solo.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_FREEZING_GAZE(){
        this.name = "Freezing gaze";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setAirFriction(-1.0)
            .setGravityY(0.0)
            .setMass(0.04);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(220)
            .setSpeedMax(220)
            .setSpreadRad(0.0)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setOnCollisionDie(true)
            // die_on_liquid_collision="1"
            .setLifetime(25)
            // velocity_sets_scale="1"
            // ragdoll_force_multiplier="0.01"
            // ragdoll_fx_on_collision="BLOOD_SPRAY"
            // hit_particle_force_multiplier="0.25"
            // camera_shake_when_shot="5.0"
            // bounces_left="10"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_laser.xml"
            // shoot_light_flash_radius="180"
            // shoot_light_flash_r="30"
            // shoot_light_flash_g="170"
            // shoot_light_flash_b="210"
            .setKnockback(1.0);
            // physics_impulse_coeff="2000"
            // damage_game_effect_entities="data/entities/misc/effect_frozen_short.xml,"
            // penetrate_entities="1"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
        this.projectileComponent.getDamageComponent().setDamage(10.0, DamageType.ICE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			air_friction="-1"
			gravity_y="0"
			mass="0.04"
		>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.8"
    lob_max="1.0"
    speed_min="220"
    speed_max="220"
    direction_random_rad="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    on_collision_die="1"
    die_on_liquid_collision="1"
    lifetime="25"
    damage="0.0"
    velocity_sets_scale="1"
    ragdoll_force_multiplier="0.01"
    ragdoll_fx_on_collision="BLOOD_SPRAY"
    hit_particle_force_multiplier="0.25"
    camera_shake_when_shot="5.0"
    bounces_left="10"
    muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_laser.xml"
    shoot_light_flash_radius="180"
    shoot_light_flash_r="30"
    shoot_light_flash_g="170"
    shoot_light_flash_b="210"
    knockback_force="1.0"
    physics_impulse_coeff="2000"
    damage_game_effect_entities="data/entities/misc/effect_frozen_short.xml,"
    penetrate_entities="1"
	>
	<damage_by_type ice="0.4" >
    </damage_by_type>
    <config_explosion
      never_cache="1"
	  >
    </config_explosion>
  </ProjectileComponent>

  <LightComponent
    _enabled="1"
    radius="150"
    r="30"
    g="90"
    b="30">
  </LightComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_blue"
    offset.x="0"
    offset.y="0"
    gravity.y="0.0"
    x_vel_min="0"
    x_vel_max="0"
    y_vel_min="-2"
    y_vel_max="2"
    count_min="1"
    count_max="2"
    is_trail="0"
    trail_gap="0.8"
    lifetime_min="0.1"
    lifetime_max="0.8"
    emit_real_particles="0"
    render_on_grid="1"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="2"
    fade_based_on_lifetime="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/freezing_gaze_beam.xml"
		>
	</VariableStorageComponent>

    <!-- NOTE( Petri ): 24.5.2023 - combined all these into one. Thanks to Letaali. -->
    <MagicConvertMaterialComponent
      from_material_array="water,water_ice,water_salt,water_fading,water_static,fire,lava,water_swamp,radioactive_liquid,acid,blood_cold,blood,poison,slime"
      to_material_array="ice_static,ice_static,ice_static,ice_static,ice_static,air,rock_static,ice_static,ice_radioactive_static,ice_acid_static,ice_cold_static,ice_blood_static,ice_poison_static,ice_slime_static"
      steps_per_frame="20"
      loop="1"
      is_circle="1"
      radius="20" >
    </MagicConvertMaterialComponent>


    <MagicConvertMaterialComponent
      kill_when_finished="0"
      extinguish_fire="1"
      is_circle="1"
      loop="1"
      radius="30" >
    </MagicConvertMaterialComponent>

  <AudioComponent
    file="data/audio/Desktop/projectiles.bank"
    event_root="player_projectiles/freezing_gaze">
  </AudioComponent>

</Entity>*/