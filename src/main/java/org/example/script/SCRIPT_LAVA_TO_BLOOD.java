package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LAVA_TO_BLOOD extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "lava_to_blood.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Lava to blood";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="lava"
      steps_per_frame="48"
      to_material="blood"
      clean_stains="0"
      is_circle="1"
      radius="48"
	  loop="1"
	  >
    </MagicConvertMaterialComponent>
</Entity>*/