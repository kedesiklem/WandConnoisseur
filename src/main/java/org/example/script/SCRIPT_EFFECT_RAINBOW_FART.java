package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EFFECT_RAINBOW_FART extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "farts.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Effect rainbow fart";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

	<InheritTransformComponent
		_enabled="1" >
    </InheritTransformComponent>

    <GameEffectComponent
        effect="RAINBOW_FARTS"
        frames="600"
    >
	</GameEffectComponent >

    <ParticleEmitterComponent
        _tags="fart"
        emitted_material_name="rainbow_gas"
        offset.x="-1"
        offset.y="-4"
        x_pos_offset_min="-1"
        x_pos_offset_max="1"
        y_pos_offset_min=""
        y_pos_offset_max="0"
        x_vel_min="-7"
        x_vel_max="7"
        y_vel_min="80"
        y_vel_max="180"
        count_min="3"
        count_max="7"
        lifetime_min="0.1"
        lifetime_max="0.2"
        create_real_particles="1"
        emit_cosmetic_particles="1"
        emission_interval_min_frames="0"
        emission_interval_max_frames="1"
        is_emitting="0" >
    </ParticleEmitterComponent>


</Entity>*/