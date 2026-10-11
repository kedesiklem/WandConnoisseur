package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HITFX_CRITICAL_OIL extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "critical_oil.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Critical on oiled enemies";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

    <HitEffectComponent
        condition_effect="OILED"
        effect_hit="CRITICAL_HIT_BOOST"
        value="100" >
	</HitEffectComponent >

</Entity>*/