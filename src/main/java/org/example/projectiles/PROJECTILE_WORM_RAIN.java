package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_WORM_RAIN extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "worm_rain.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_WORM_RAIN(){
        this.name = "Matosade";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity
	name="$projectile_default"
	tags="player_projectile"
	>

	<LuaComponent
		script_source_file="data/scripts/projectiles/worm_rain.lua"
		execute_every_n_frame="40"
		>
	</LuaComponent>

	<LifetimeComponent
		lifetime="400"
		>
	</LifetimeComponent>

	<LoadEntitiesComponent
		entity_file="data/entities/projectiles/remove_ground.xml"
		count.min="1"
		count.max="1"
		kill_entity="0"
		timeout_frames="2">
	</LoadEntitiesComponent>
</Entity>*/