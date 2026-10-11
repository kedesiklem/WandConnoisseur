package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_TOUCH_GRASS extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "touch_grass.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_TOUCH_GRASS(){
        this.name = "Touch of Grass";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity tags="projectile_player" >
	<InheritTransformComponent>
    </InheritTransformComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/touch_grass.lua"
		execute_on_added="1"
		call_init_function="1"
	></LuaComponent>

	<!-- this one with convert_entities="1" has to be the first in order -->
	<MagicConvertMaterialComponent
		from_any_material="1"
        to_material="grass_holy"
        steps_per_frame="7"
        loop="0"
		is_circle="1"
        radius="30"
		convert_entities="1"
    ></MagicConvertMaterialComponent>

	<MagicConvertMaterialComponent
		from_any_material="1"
        to_material="grass_holy"
        steps_per_frame="7"
        loop="0"
		is_circle="1"
        radius="40"
    ></MagicConvertMaterialComponent>

	<LifetimeComponent
		lifetime="6"
	></LifetimeComponent>

	<AudioComponent
		file="data/audio/Desktop/projectiles.bank"
		event_root="player_projectiles/touch"
	></AudioComponent>

</Entity>*/