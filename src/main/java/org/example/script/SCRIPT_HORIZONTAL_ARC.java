package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HORIZONTAL_ARC extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "horizontal_arc.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Horizontal path";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/horizontal_arc.lua"
		execute_every_n_frame="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/horizontal_arc.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

edit_component( entity_id, "VelocityComponent", function(comp,vars)
	local vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")

	vel_x = vel_x
	vel_y = 0

	ComponentSetValueVector2( comp, "mVelocity", vel_x, vel_y)
end)*/