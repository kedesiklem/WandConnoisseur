package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.ProjectileComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_TELEPORTATION_FIELD extends ProjectileBaseField{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "teleportation_field.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_TELEPORTATION_FIELD(){
        this.name = "Circle of displacement";
        this.image = staticImage;
        this.emote = staticEmote;
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // damage_game_effect_entities="data/entities/misc/effect_teleportation_enemy.xml,"
            .setFriendlyFire(true);
            // collide_with_shooter_frames="0"
    }
}

/*<Entity name="$projectile_default">

  <GameAreaEffectComponent
      radius="28"
    />

	<ParticleEmitterComponent
		emitted_material_name="spark_blue"
		gravity.y="0.0"
		lifetime_min="0.1"
		lifetime_max="0.3"
		count_min="2"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.max="28"
		cosmetic_force_create="0"
		airflow_force="0.41"
		airflow_time="0.6"
		airflow_scale="0.05"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

  	<ParticleEmitterComponent
		emitted_material_name="spark_blue"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="1.5"
		count_min="4"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.min="28"
		area_circle_radius.max="28"
		cosmetic_force_create="0"
		airflow_force="0.3"
		airflow_time="0.01"
		airflow_scale="0.05"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

	<Base file="data/entities/projectiles/deck/base_field.xml">
		<SpriteComponent
			image_file="data/projectiles_gfx/blast_teleportation.xml"
			>
		</SpriteComponent>

		<SpriteParticleEmitterComponent
			sprite_file="data/particles/teleparticle.xml"
			lifetime="0.4"
			scale.x="1" scale.y="0.2"
			color_change.a="-0.2"
			scale_velocity.x="-0.2" scale_velocity.y="1"
			randomize_velocity.min_x="0"
			randomize_velocity.max_x="0"
			randomize_velocity.min_y="-40"
			randomize_velocity.max_y="40"
			velocity_always_away_from_center="0"
			randomize_rotation.min="0"
			randomize_rotation.max="0"
			randomize_angular_velocity.min="0"
			randomize_angular_velocity.max="0"
			>
		</SpriteParticleEmitterComponent>

		<ProjectileComponent
			damage_game_effect_entities="data/entities/misc/effect_teleportation_enemy.xml,"
			friendly_fire="1"
			collide_with_shooter_frames="0"
			>
			<config_explosion
				explosion_sprite="data/particles/blast_out_teleportation.xml"
				>
			</config_explosion>
		</ProjectileComponent>
	</Base>
</Entity>*/