package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HOMING_SHORT extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "homing_short.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Short-range Homing";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<HomingComponent
		homing_targeting_coeff="480.0"
		homing_velocity_multiplier="0.83"
		detect_distance="60"
	>
	</HomingComponent>
</Entity>*/