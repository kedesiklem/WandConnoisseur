package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_HITFX_TOXIC_CHARM extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "charm_on_toxic.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Charm on toxic sludge";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

    <HitEffectComponent
        condition_status="RADIOACTIVE"
        effect_hit="LOAD_UNIQUE_GAME_EFFECT"
        value_string="data/entities/misc/effect_charm_short.xml" >
	</HitEffectComponent >

</Entity>*/

// data/entities/misc/effect_charm_short.xml
/*<Entity>
	<Base file="data/entities/particles/charm.xml" >
		<SpriteParticleEmitterComponent
		    emission_interval_min_frames="5"
    		emission_interval_max_frames="15"
			randomize_position_inside_hitbox="1"  >
		</SpriteParticleEmitterComponent>
	</Base>

	<InheritTransformComponent
		_enabled="1" >
    </InheritTransformComponent>

    <GameEffectComponent
        effect="CHARM"
        frames="3600"
    >
	</GameEffectComponent >

	<AudioComponent
		file="data/audio/Desktop/misc.bank"
		event_root="game_effect/charm" >
	</AudioComponent>
</Entity>*/