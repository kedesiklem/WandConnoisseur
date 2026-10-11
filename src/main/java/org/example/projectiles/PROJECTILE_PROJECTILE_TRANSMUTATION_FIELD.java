package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.ProjectileComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_PROJECTILE_TRANSMUTATION_FIELD extends ProjectileBaseField{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "projectile_transmutation_field.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_PROJECTILE_TRANSMUTATION_FIELD(){
        this.name = "Projectile transmutation field";
        this.image = staticImage;
        this.emote = staticEmote;
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent);
        // damage_game_effect_entities=""
    }
}

/*<Entity tags="projectile_player" >

	<LuaComponent
		script_source_file="data/scripts/projectiles/projectile_transmutation.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>

	<ParticleEmitterComponent
		emitted_material_name="spark_purple_bright"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="1.5"
		count_min="2"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.max="48"
		cosmetic_force_create="0"
		airflow_force="0.5"
		airflow_time="0.01"
		airflow_scale="0.05"
		emission_interval_min_frames="1"
		emission_interval_max_frames="1"
		emit_cosmetic_particles="1"
		is_emitting="1" >
	</ParticleEmitterComponent>

  	<ParticleEmitterComponent
		emitted_material_name="spark_purple_bright"
		gravity.y="0.0"
		lifetime_min="0.5"
		lifetime_max="1.5"
		count_min="4"
		count_max="4"
		render_on_grid="1"
		fade_based_on_lifetime="1"
		area_circle_radius.min="48"
		area_circle_radius.max="48"
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
			image_file="data/particles/area_indicator_096_purple.png"
			z_index="1.2"
			offset_x="48"
			offset_y="48"
			>
		</SpriteComponent>

		<SpriteParticleEmitterComponent
			_enabled="0"
			>
		</SpriteParticleEmitterComponent>

		<ProjectileComponent
			damage_game_effect_entities=""
			>
			<config_explosion
				explosion_sprite="data/particles/blast_out.xml"
				>
			</config_explosion>
		</ProjectileComponent>

		<AudioLoopComponent
		  file="data/audio/Desktop/projectiles.bank"
		  event_name="player_projectiles/field_transmutation/loop"
		  auto_play="1" >
	  </AudioLoopComponent>
	</Base>

</Entity>*/