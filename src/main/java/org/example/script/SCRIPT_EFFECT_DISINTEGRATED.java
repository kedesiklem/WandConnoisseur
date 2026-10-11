package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EFFECT_DISINTEGRATED extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "effect_disintegrated.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "disintegrate";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<InheritTransformComponent
		_enabled="1" >
    </InheritTransformComponent>

    <GameEffectComponent
        effect="NONE"
        frames="5"
        disable_movement="0"
        ragdoll_effect="DISINTEGRATED"
        ragdoll_material="soil"
    >
	</GameEffectComponent >

</Entity>*/