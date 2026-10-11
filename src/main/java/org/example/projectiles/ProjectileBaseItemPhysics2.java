package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public abstract class ProjectileBaseItemPhysics2 extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "_unidentified.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public ProjectileBaseItemPhysics2(){
        this.name = "Base Item Physics 2";
        this.image = staticImage;
        this.emote = staticEmote;
    }
}

/*<Entity tags="hittable,teleportable_NOT,prop,prop_physics">
  <PhysicsBody2Component
  	allow_sleep="1"
  	angular_damping="0"
  	linear_damping="0"
    kill_entity_after_initialized="1"
	>
  </PhysicsBody2Component>

  <PhysicsImageShapeComponent
  	is_root="1"
    centered="1"
    image_file="data/temp/landmine.png"
    material="steel" >
  </PhysicsImageShapeComponent>

</Entity>*/