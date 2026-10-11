package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_BOUNCE_SMALL_EXPLOSION extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "bounce_small_explosion.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Sparkly bounce";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >

	<LuaComponent
		script_source_file="data/scripts/projectiles/bounce_small_explosion.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

</Entity>*/

// data/scripts/projectiles/bounce_small_explosion.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local x, y = EntityGetTransform( entity_id )

local parent_id = EntityGetParent( entity_id )

local target_id = 0

if ( parent_id ~= NULL_ENTITY ) then
	target_id = parent_id
else
	target_id = entity_id
end

if ( target_id ~= NULL_ENTITY ) then
	local projectile_components = EntityGetComponent( target_id, "ProjectileComponent" )

	if( projectile_components == nil ) then return end

	if ( #projectile_components > 0 ) then
		edit_component( target_id, "ProjectileComponent", function(comp,vars)
			vars.bounce_fx_file = "data/entities/projectiles/deck/bounce_small_explosion.xml"
		end)
	end
end*/