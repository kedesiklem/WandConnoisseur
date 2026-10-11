package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_EFFECT_METEOR_RAIN extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "meteor_rain.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Meteorisade";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity
	name="$projectile_default"
	tags="player_projectile"
	>

	<InheritTransformComponent />

	<LuaComponent
		script_source_file="data/scripts/projectiles/meteor_rain.lua"
		execute_every_n_frame="30"
		>
	</LuaComponent>

	<LifetimeComponent
		lifetime="600"
		>
	</LifetimeComponent>
</Entity>*/

// data/scripts/projectiles/meteor_rain.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

SetRandomSeed( GameGetFrameNum() + GetUpdatedComponentID(), pos_x + pos_y + entity_id )

local count = Random(1,3)

for i=1,count do
	local angle = math.pi * ( Random( 1, 200 ) * 0.01 )
	local x = pos_x + math.cos( angle ) * 300
	local y = pos_y - math.sin( angle ) * 300
	local vx = math.cos( angle + math.pi ) * 900
	local vy = 0 - math.sin( angle + math.pi ) * 900

	shoot_projectile_from_projectile( entity_id, "data/entities/projectiles/deck/meteor_rain_meteor.xml", x, y, vx, vy )
end

GameScreenshake( 15 )*/