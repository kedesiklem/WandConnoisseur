package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public abstract class ProjectileBaseItem extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "_unidentified.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public ProjectileBaseItem(){
        this.name = "Base item";
        this.image = staticImage;
        this.emote = staticEmote;
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            .setLifetime(-1)
            // penetrate_entities="1"
            .setDamageScaledBySpeed(true)
            .setDamageScaleMaxSpeed(200.0);
            // never_hit_player="1"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
        this.projectileComponent.getDamageComponent().setDamage(12.5, DamageType.FIRE);
    }
}

/*<!-- used for throwable items that cannot collide with enemies but which we want to damage enemies with -->
<Entity>
	<ProjectileComponent
		_tags="enabled_in_world"
		lifetime="-1"
		damage="0"
		penetrate_entities="1"
		damage_scaled_by_speed="1"
		damage_scale_max_speed="200"
		never_hit_player="1"
		>
		<damage_by_type
			melee="0.5"
			>
		</damage_by_type>
	</ProjectileComponent>
</Entity>*/