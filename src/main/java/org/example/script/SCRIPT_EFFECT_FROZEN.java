package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EFFECT_FROZEN extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "frozen.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Effect frozen";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
    <InheritTransformComponent>
    </InheritTransformComponent>

    <GameEffectComponent
        effect="FROZEN"
        frames="120"
        disable_movement="1"
        ragdoll_effect="FROZEN"
        ragdoll_material="ice_glass_b2"
    >
	</GameEffectComponent >

    <AudioComponent
        file="data/audio/Desktop/misc.bank"
        event_root="game_effect/frozen" >
    </AudioComponent>

</Entity>*/