package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_PHASING_ARC extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "phasing_arc.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Phasing Arc";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/phasing_arc.lua"
		execute_every_n_frame="12"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/phasing_arc.lua
/*dofile_once( "data/scripts/lib/utilities.lua" )

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )
local distance = 28

local velocity_comp = EntityGetFirstComponent( entity_id, "VelocityComponent")
if velocity_comp == nil then return end
local vel_x,vel_y = ComponentGetValueVector2( velocity_comp, "mVelocity")

local dir = 0 - math.atan2( vel_y, vel_x )

local ox = math.cos( dir ) * distance
local oy = 0 - math.sin( dir ) * distance

EntitySetTransform( entity_id, x + ox, y + oy )*/