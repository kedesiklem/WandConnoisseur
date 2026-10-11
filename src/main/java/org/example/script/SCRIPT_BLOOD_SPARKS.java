package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_BLOOD_SPARKS extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "blood_trail.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Blood sparks";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
  <ParticleEmitterComponent
		emitted_material_name="blood"
		offset.x="0"
		offset.y="0"
		x_pos_offset_min="-1"
		x_pos_offset_max="1"
		y_pos_offset_min="-1"
		y_pos_offset_max="1"
		gravity.y="0"
		x_vel_min="-2"
		x_vel_max="2"
		y_vel_min="-2"
		y_vel_max="2"
		count_min="1"
		count_max="2"
		is_trail="1"
		trail_gap="0.7"
		fade_based_on_lifetime="1"
		lifetime_min="0.8"
		lifetime_max="2.0"
		airflow_force="1.5"
		airflow_time="0.401"
		airflow_scale="0.05"
		create_real_particles="0"
		emit_cosmetic_particles="1"
		render_on_grid="1"
		emission_interval_min_frames="1"
		emission_interval_max_frames="2"
		is_emitting="1" >
	</ParticleEmitterComponent>
</Entity>*/