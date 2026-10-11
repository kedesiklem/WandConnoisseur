package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_METEOR_RAIN extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "meteor_rain.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_METEOR_RAIN(){
        this.name = "Meteorisade";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(0.0)
            .setMass(0.0);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.1"
            // lob_max="1.0"
            .setSpeedMin(0)
            .setSpeedMax(0)
            .setDieOnLowVelocity(false)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(false)
            .setLifetime(100)
            .setKnockback(0.0);
            // damage_every_x_frames="25"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity
	name="$projectile_default"
	tags="player_projectile"
	>

	<VelocityComponent
		gravity_y="0"
		air_friction="0"
		mass="0.00"
		>
	</VelocityComponent>

	<ProjectileComponent
		_enabled="1"
		lob_min="0.1"
		lob_max="1.0"
		speed_min="0"
		speed_max="0"
		die_on_low_velocity="0"
		on_death_explode="0"
		on_death_gfx_leave_sprite="0"
		on_lifetime_out_explode="0"
		explosion_dont_damage_shooter="1"
		damage="0"
		on_collision_die="0"
		lifetime="100"
		knockback_force="0"
		damage_every_x_frames="25"
		>
		<config_explosion
			never_cache="1"
			camera_shake="0"
			explosion_radius="0"
			explosion_sprite=""
			explosion_sprite_lifetime="0.0"
			create_cell_probability="0"
			create_cell_material=""
			ray_energy="0"
			hole_enabled="0"
			particle_effect="0"
			damage_mortals="0"
			physics_throw_enabled="0"
			shake_vegetation="0"
			sparks_enabled="0"
			light_fade_time="0.1"
			stains_enabled="0"
			>
		</config_explosion>
	</ProjectileComponent>

	<LuaComponent
		script_source_file="data/scripts/projectiles/meteor_rain.lua"
		execute_every_n_frame="30"
		>
	</LuaComponent>

	<LifetimeComponent
		lifetime="600"
		>
	</LifetimeComponent>
</Entity>*/