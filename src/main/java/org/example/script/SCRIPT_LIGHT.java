package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class SCRIPT_LIGHT extends Script{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "light.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    @Override
    protected void initialization(){
        this.name = "Light";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity>

	<LightComponent
		_enabled="1"
		radius="200" >
	</LightComponent>

	<_Transform
        scale.x="5" scale.y="5" >
    </_Transform>

	<SpriteComponent
		alpha="0.55"
		image_file="data/particles/fog_of_war_hole_128.xml"
		smooth_filtering="1"
		fog_of_war_hole="1" >
	</SpriteComponent>

</Entity>*/

// data/particles/fog_of_war_hole_128.xml
/*<Sprite
  default_animation="explosion"
  filename="data/particles/fog_of_war_hole_128.png"
  offset_x="65"
  offset_y="65" >

  <RectAnimation
    frame_count="1"
    frame_height="129"
    frame_wait="0.02"
    frame_width="129"
    frames_per_row="1"
    loop="0"
    name="explosion"
    pos_x="0"
    pos_y="0"
    shrink_by_one_pixel="1" >

  </RectAnimation>

</Sprite>*/