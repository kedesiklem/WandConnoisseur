package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_LUMINOUS_DRILL extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "luminous_drill.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_LUMINOUS_DRILL(){
        this.name = "Luminous Drill";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setMass(1.65);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(1400)
            .setSpeedMax(1400)
            .setFriction(0.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            // ground_collision_fx="1"
            .setOnCollisionDie(true)
            .setLifetime(2)
            // velocity_sets_scale="0"
            .setLifetimeRandomness(0);
            // ragdoll_force_multiplier="0.01"
            // hit_particle_force_multiplier="0.25"
            // ground_penetration_coeff="4"
            // ground_penetration_max_durability_to_destroy="14"
        this.projectileComponent.getDamageComponent().setDamage(10.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			mass="1.65"
			>
		</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
    lob_min="0.8"
    lob_max="1.0"
    speed_min="1400"
    speed_max="1400"
    friction="0.0"
    direction_random_rad="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    ground_collision_fx="1"
    on_collision_die="1"
    lifetime="2"
    damage="0.4"
    velocity_sets_scale="0"
    lifetime_randomness="0"
    ragdoll_force_multiplier="0.01"
    hit_particle_force_multiplier="0.25"
	ground_penetration_coeff="4"
	ground_penetration_max_durability_to_destroy="14"
    >
  </ProjectileComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    count_min="20"
    count_max="20"
    offset.x="0"
    offset.y="0"
    lifetime_min="0.02"
    lifetime_max="0.02"
    create_real_particles="0"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
	is_trail="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    count_min="20"
    count_max="20"
    offset.x="16"
    offset.y="0"
    lifetime_min="0.02"
    lifetime_max="0.02"
    create_real_particles="0"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
	is_trail="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    count_min="10"
    count_max="20"
    offset.x="0"
    offset.y="0"
    lifetime_min="0.15"
    lifetime_max="0.32"
    create_real_particles="0"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
	fade_based_on_lifetime="1"
	draw_as_long="1"
	is_trail="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_green"
    count_min="10"
    count_max="20"
    offset.x="12"
    offset.y="0"
    lifetime_min="0.15"
    lifetime_max="0.32"
    create_real_particles="0"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="1"
	fade_based_on_lifetime="1"
	draw_as_long="1"
	is_trail="1"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/luminous_drill">
  </AudioComponent>

    <VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/luminous_drill.xml"
		>
	</VariableStorageComponent>
</Entity>*/