package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_SINEWAVE extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "sinewave.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Slithering path";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >


  <SineWaveComponent
    _enabled="1"
    sinewave_freq="1.0"
    sinewave_m="0.6"
    lifetime="-1"
	 >
  </SineWaveComponent>

</Entity>*/