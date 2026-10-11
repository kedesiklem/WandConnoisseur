package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LIQUID_TO_EXPLOSION extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "liquid_to_explosion.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Liquid Detonation";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<MagicConvertMaterialComponent
      kill_when_finished="0"
	  from_material_tag="[liquid_common]"
      steps_per_frame="48"
      to_material="gunpowder_unstable_big"
      clean_stains="0"
      is_circle="1"
      radius="48"
	  loop="1"
	  >
    </MagicConvertMaterialComponent>
</Entity>*/