package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_SUMMON_WANDGHOST extends ProjectileBaseWandGhost{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "summon_wandghost.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_SUMMON_WANDGHOST(){
        this.name = "Summon Taikasauva";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity tags="mortal,hittable,homing_target,wand_ghost" name="$animal_wand_ghost">

	<Base file="data/entities/base_wand_ghost.xml" >
		<GenomeDataComponent
			herd_id="player"
			food_chain_rank="7"
			is_predator="1" >
		</GenomeDataComponent>
	</Base>

	<LuaComponent
		_enabled="1"
		remove_after_executed="1"
		script_source_file="data/scripts/animals/wand_ghost.lua" >
	</LuaComponent>

	<AudioLoopComponent
		file="data/audio/Desktop/animals.bank"
		event_name="animals/ghost/movement_loop"
		set_speed_parameter="1"
		auto_play="1">
	</AudioLoopComponent>

</Entity>*/