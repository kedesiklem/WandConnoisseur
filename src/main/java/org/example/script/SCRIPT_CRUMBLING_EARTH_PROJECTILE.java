package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_CRUMBLING_EARTH_PROJECTILE extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "crumbling_earth_projectile.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Earthquake shot";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<LuaComponent
		script_source_file="data/scripts/projectiles/crumbling_earth_projectile.lua"
		execute_every_n_frame="99999999"
		execute_on_removed="1"
		>
	</LuaComponent>
</Entity>*/

// data/scripts/projectiles/crumbling_earth_projectile.lua
/*x, y = EntityGetTransform( GetUpdatedEntityID() )
EntityLoad( "data/entities/misc/loose_chunks_projectile.xml", x, y )*/