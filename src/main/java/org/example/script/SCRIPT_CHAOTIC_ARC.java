package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_CHAOTIC_ARC extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "chaotic_arc.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaotic path";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/chaotic_arc.lua"
		execute_every_n_frame="2"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/chaotic_arc.lua
/*dofile_once( "data/scripts/lib/utilities.lua" )

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum(), pos_x + pos_y + entity_id )

edit_component( entity_id, "VelocityComponent", function(comp,vars)
	local vel_x,vel_y = ComponentGetValueVector2( comp, "mVelocity")

	local scale = math.max( math.abs( vel_x ), math.abs( vel_y ) ) * 0.4
	local random_adjustment = Random( 0 - scale, scale )

	vel_x = vel_x + random_adjustment
	vel_y = vel_y + random_adjustment

	ComponentSetValueVector2( comp, "mVelocity", vel_x, vel_y)
end)*/