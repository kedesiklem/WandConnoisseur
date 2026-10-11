package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_DESTRUCTION extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "destruction.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_DESTRUCTION(){
        this.name = "Destruction";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity tags="projectile_player" >
	<InheritTransformComponent>
    </InheritTransformComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/destruction.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>

	<AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/destruction"
      set_latest_event_position="1" >
	</AudioComponent>
</Entity>*/