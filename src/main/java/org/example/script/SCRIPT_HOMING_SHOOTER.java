package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HOMING_SHOOTER extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "homing_shooter.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Boomerang";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<HomingComponent
		target_who_shot="1"
		homing_targeting_coeff="30.0"
		homing_velocity_multiplier="0.99"
		detect_distance="300"
	>
	</HomingComponent>
</Entity>*/