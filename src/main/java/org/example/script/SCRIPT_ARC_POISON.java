package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_ARC_POISON extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "arc_poison.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Poison Arc";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<ArcComponent
		lifetime="120"
		material="poison" >
	</ArcComponent>
</Entity>*/