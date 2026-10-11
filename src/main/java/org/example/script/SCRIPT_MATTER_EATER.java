package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_MATTER_EATER extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "matter_eater.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Matter eater";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>
	<InheritTransformComponent>
    </InheritTransformComponent>

    <CellEaterComponent
		eat_probability="90"
        radius="8"
		ignored_material="rock_static_cursed"
		ignored_material_tag="[matter_eater_ignore_list]"
		>
    </CellEaterComponent>

	<LifetimeComponent
		lifetime="200"
		randomize_lifetime.min="-50"
		randomize_lifetime.max="50"
		>
	</LifetimeComponent>
</Entity>*/