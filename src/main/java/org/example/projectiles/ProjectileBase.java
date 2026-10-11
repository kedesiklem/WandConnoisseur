package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public abstract class ProjectileBase extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "_unidentified.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public ProjectileBase(){
        this.name = "Projectile base";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setMass(0.05)
            .setAffectPhysicsBodies(true);
    }
}

/*<Entity tags="teleportable_NOT,projectile">

	<VelocityComponent
		mass="0.05"
		affect_physics_bodies="1" >
	</VelocityComponent>

	<AudioComponent
		file="data/audio/Desktop/projectiles.bank"
		audio_physics_material="projectile"
		event_root="projectiles/hit" >
	</AudioComponent>

</Entity>*/