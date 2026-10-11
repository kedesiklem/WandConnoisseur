package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_MAGIC_SHIELD extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "magic_shield.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_MAGIC_SHIELD(){
        this.name = "Magic Guard";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(0.0);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(0)
            .setSpeedMax(0)
            .setDieOnLowVelocity(false)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(true)
            // penetrate_entities="1"
            .setOnCollisionDie(false)
            .setLifetime(5);
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity
	name="$projectile_default"
	>
	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="0"
			air_friction="0"
			>
		</VelocityComponent>
	</Base>

	<ProjectileComponent
		_enabled="1"
		lob_min="0.8"
		lob_max="1.0"
		speed_min="0"
		speed_max="0"
		die_on_low_velocity="0"
		on_death_explode="0"
		on_death_gfx_leave_sprite="0"
		on_lifetime_out_explode="0"
		explosion_dont_damage_shooter="1"
		penetrate_entities="1"
		damage="0.0"
		on_collision_die="0"
		lifetime="5" >
	</ProjectileComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/magic_shield_start.lua"
		execute_on_added="1"
		>
	</LuaComponent>

	<AudioComponent
		file="data/audio/Desktop/projectiles.bank"
		event_root="player_projectiles/magic_shield"
		>
	</AudioComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/magic_shield_start.xml"
		>
	</VariableStorageComponent>
</Entity>*/