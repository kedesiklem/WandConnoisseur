package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_CURSE_WITHER_ELECTRICITY extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "curse_wither_electricity.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Weakening Curse - Electricity";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

    <HitEffectComponent
        effect_hit="LOAD_UNIQUE_CHILD_ENTITY"
        value_string="data/entities/misc/curse_wither_electricity.xml" >
	</HitEffectComponent >

</Entity>*/

// data/entities/misc/curse_wither_electricity.xml
/*<Entity>
	<InheritTransformComponent />

	<VariableStorageComponent
		_tags="effect_curse_wither_type"
		value_string="electricity"
		>
	</VariableStorageComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/curse_wither_start.lua"
		execute_every_n_frame="1"
		remove_after_executed="1"
		>
	</LuaComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/curse_wither_end.lua"
		execute_every_n_frame="-1"
		execute_on_removed="1"
		>
	</LuaComponent>

	<LifetimeComponent
		_tags="effect_curse_lifetime"
		lifetime="300"
		>
	</LifetimeComponent>

	<UIIconComponent
		icon_sprite_file="data/ui_gfx/status_indicators/curse_wither_electricity.png"
		name="$status_curse_wither_electricity"
		description="$statusdesc_curse_wither_electricity"
		display_above_head="1"
		display_in_hud="0"
		is_perk="1"
		>
	</UIIconComponent>
</Entity>*/

// data/scripts/projectiles/curse_wither_start.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local root_id = EntityGetRootEntity( entity_id )

local comp = EntityGetFirstComponent( entity_id, "VariableStorageComponent", "effect_curse_wither_type" )

if ( comp ~= nil ) then
	local name = ComponentGetValue2( comp, "value_string" )

	comp = EntityGetFirstComponent( root_id, "DamageModelComponent" )

	if ( comp ~= nil ) then
		local mult = ComponentObjectGetValue2( comp, "damage_multipliers", name )
		mult = mult + 0.25
		ComponentObjectSetValue2( comp, "damage_multipliers", name, mult )

		--[[
		if ( mult > 0 ) then
			mult = mult + 0.25
			ComponentObjectSetValue2( comp, "damage_multipliers", name, mult )
		else
			EntityKill( entity_id )
		end
		]]--
	end
end*/

// data/scripts/projectiles/curse_wither_end.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id = GetUpdatedEntityID()
local root_id = EntityGetRootEntity( entity_id )

local comp = EntityGetFirstComponent( entity_id, "VariableStorageComponent", "effect_curse_wither_type" )

if ( comp ~= nil ) then
	local name = ComponentGetValue2( comp, "value_string" )

	comp = EntityGetFirstComponent( root_id, "DamageModelComponent" )

	if ( comp ~= nil ) then
		local mult = ComponentObjectGetValue2( comp, "damage_multipliers", name )
		mult = mult - 0.25
		ComponentObjectSetValue2( comp, "damage_multipliers", name, mult )

		--[[
		if ( mult > 0 ) then
			mult = mult - 0.25
			ComponentObjectSetValue2( comp, "damage_multipliers", name, mult )
		end
		]]--
	end
end*/