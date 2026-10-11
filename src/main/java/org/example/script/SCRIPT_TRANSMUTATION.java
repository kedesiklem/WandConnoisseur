package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_TRANSMUTATION extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "transmutation.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Chaotic transmutation";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity >
	<MagicConvertMaterialComponent
      kill_when_finished="0"
      from_material_tag="[chaotic_transmutation]"
      steps_per_frame="48"
      to_material="water"
      clean_stains="0"
      is_circle="1"
      radius="32"
	  loop="1"
	  _enabled="0"
	  _tags="transmutation"
	  >
    </MagicConvertMaterialComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/transmutation.lua"
		remove_after_executed="1"
		execute_every_n_frame="1"
		>
	</LuaComponent>
</Entity>*/

// data/scripts/projectiles/transmutation.lua
/*dofile_once("data/scripts/lib/utilities.lua")

local entity_id    = GetUpdatedEntityID()
local pos_x, pos_y = EntityGetTransform( entity_id )

EntitySetComponentsWithTagEnabled( entity_id, "transmutation", true )

local convertcomponents = EntityGetComponent( entity_id, "MagicConvertMaterialComponent" )

SetRandomSeed( pos_x + 436, pos_y - 3252 )
local material_options = { "water", "oil", "lava", "acid", "radioactive_liquid", "slime", "sand", "alcohol", "blood", "snow", "blood_worm", "blood_fungi", "burning_powder", "honey", "fungi", "diamond", "brass", "silver" }
local material_options_rare = { "acid", "magic_liquid_teleportation", "magic_liquid_polymorph", "magic_liquid_random_polymorph", "magic_liquid_berserk", "magic_liquid_charm", "magic_liquid_invisibility" }
local rare = false

local rnd = Random( 1, 100 )

if ( rnd > 98 ) then
	rare = true
end

local material_string = "water"

if (rare == false) then
	rnd = Random( 1, #material_options )
	material = material_options[rnd]
else
	rnd = Random( 1, #material_options_rare )
	material = material_options_rare[rnd]
end

material = CellFactory_GetType( material )

if ( convertcomponents ~= nil ) then
	for key,comp_id in pairs(convertcomponents) do
		local mat_name = tonumber( ComponentGetValue( comp_id, "from_material" ) )
		--local smoke_id = CellFactory_GetType( "smoke" )

		if (material == mat_name) then
			--ComponentSetValue( comp_id, "to_material", smoke_id )
		else
			ComponentSetValue( comp_id, "to_material", material )
		end
	end
end

edit_component( entity_id, "LuaComponent", function(comp,vars)
	EntitySetComponentIsEnabled( entity_id, comp, false )
end)*/