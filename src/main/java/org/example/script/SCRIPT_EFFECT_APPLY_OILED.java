package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EFFECT_APPLY_OILED extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "oiled.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Oiled";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

	<InheritTransformComponent
		_enabled="1" >
    </InheritTransformComponent>

    <GameEffectComponent
        effect="OILED"
        frames="720"
    >
	</GameEffectComponent >

</Entity>*/