package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_SPORE_POD extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "spore_pod.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_SPORE_POD(){
        this.name = "Prickly Spore Pod";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setAirFriction(0.0)
            .setMass(0.06);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.95"
            // lob_max="1.0"
            .setSpeedMin(210)
            .setSpeedMax(240)
            .setFriction(0.6)
            .setSpreadRad(0.05)
            // on_death_gfx_leave_sprite="0"
            .setLifetime(100)
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7)
            // ragdoll_force_multiplier="0.04"
            // hit_particle_force_multiplier="5.5 "
            // camera_shake_when_shot="1.0"
            // shoot_light_flash_radius="80"
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_launcher.xml"
            .setKnockback(1.7)
            // on_collision_spawn_entity="1"
            // spawn_entity="data/entities/projectiles/deck/spore_pod_growing.xml"
            .setOnCollisionDie(true)
            // on_collision_remove_projectile="0"
            .setOnLifetimeOutExplode(false);
        this.projectileComponent.getDamageComponent().setDamage(2.5, DamageType.PROJECTILE);
    }
}

/*<!-- Description:
When this projectile lands, it is switched to spore_pod_growing which moves away from surfaces, leaving a trail of material.
After a while it explodes into a bunch of spore_pod_spike entities (via spore_pod_spikes.xml).
-->

<Entity name="$projectile_default" >
	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			air_friction="0.0"
			mass="0.06"
			>
		</VelocityComponent>
	</Base>

	<ProjectileComponent
		lob_min="0.95"
		lob_max="1.0"
		speed_min="210"
		speed_max="240"
		friction="0.6"
		direction_random_rad="0.05"
		on_death_gfx_leave_sprite="0"
		lifetime="100"
		damage="0.1"
		velocity_sets_scale="1"
		lifetime_randomness="7"
		ragdoll_force_multiplier="0.04"
		hit_particle_force_multiplier="5.5 "
		camera_shake_when_shot="1.0"
		shoot_light_flash_radius="80"
		muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_launcher.xml"
		knockback_force="1.7"
		on_collision_spawn_entity="1"
		spawn_entity="data/entities/projectiles/deck/spore_pod_growing.xml"
		on_collision_die="1"
		on_collision_remove_projectile="0"
		on_lifetime_out_explode="0"
		>
	</ProjectileComponent>

	<SpriteComponent
		image_file="data/projectiles_gfx/spore_pod.xml"
		rect_animation="pod"
		>
	</SpriteComponent>

	<ParticleEmitterComponent
		emitted_material_name="smoke"
		offset.x="0"
		offset.y="0"
		x_pos_offset_min="-1"
		y_pos_offset_min="-1"
		x_pos_offset_max="1"
		y_pos_offset_max="1"
		x_vel_min="-10"
		x_vel_max="10"
		y_vel_min="-10"
		y_vel_max="10"
		count_min="0"
		count_max="1"
		lifetime_min="0.1"
		lifetime_max="0.3"
		create_real_particles="1"
		emit_cosmetic_particles="0"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

	<LightComponent
		radius="40" >
	</LightComponent>

	<AudioComponent
		file="data/audio/Desktop/projectiles.bank"
		event_root="player_projectiles/spore_pod">
	</AudioComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/spore_pod.xml"
		>
	</VariableStorageComponent>
</Entity>*/