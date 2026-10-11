package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HOMING_ROTATE extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "homing_rotate.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Rotate towards foes";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<HomingComponent
		just_rotate_towards_target="1"
		max_turn_rate="0.2"
	>
	</HomingComponent>
</Entity>*/