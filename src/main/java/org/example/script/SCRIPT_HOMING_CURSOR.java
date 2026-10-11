package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HOMING_CURSOR extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "homing_cursor.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Aiming Arc";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<LuaComponent
		script_source_file="data/scripts/projectiles/homing_cursor.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>
</Entity>*/