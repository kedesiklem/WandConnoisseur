package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_WATER_TO_POISON extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "water_to_poison.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Water to poison";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  >
    </MagicConvertMaterialComponent>

	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water_ice"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  >
    </MagicConvertMaterialComponent>

	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water_static"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  >
    </MagicConvertMaterialComponent>

	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water_temp"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  >
    </MagicConvertMaterialComponent>

	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water_swamp"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  >
    </MagicConvertMaterialComponent>

   <MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material="water_salt"
      steps_per_frame="3"
      to_material="poison"
      clean_stains="0"
      is_circle="1"
      radius="32"
    >
    </MagicConvertMaterialComponent>
</Entity>*/