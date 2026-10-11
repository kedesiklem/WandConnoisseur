package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_SOILBALL extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "soil.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_SOILBALL(){
        this.name = "Chunk of soil";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(100.0);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(0)
            .setSpeedMax(10)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(false)
            .setDieOnLowVelocity(true)
            .setOnCollisionDie(true)
            .setLifetime(1);
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" >
	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="100">
		</VelocityComponent>
	</Base>

	<ProjectileComponent
		_enabled="1"
		lob_min="0.8"
		lob_max="1.0"
		speed_min="0"
		speed_max="10"
		on_death_explode="1"
		on_death_gfx_leave_sprite="0"
		on_lifetime_out_explode="1"
		explosion_dont_damage_shooter="0"
		die_on_low_velocity="1"
		damage="0"
		on_collision_die="1"
		lifetime="1" >
	<config_explosion
		never_cache="0"
		camera_shake="0"
		explosion_radius="15"
		explosion_sprite=""
		damage="0"
		explosion_sprite_lifetime="0"
		create_cell_probability="100"
		create_cell_material="soil"
		hole_destroy_liquid="0"
		hole_enabled="1"
		particle_effect="0"
		physics_explosion_power.min="0"
		physics_explosion_power.max="0"
		physics_throw_enabled="0"
		shake_vegetation="0"
		sparks_enabled="0"
		stains_enabled="0"
		ray_energy="0">
	</config_explosion>
	</ProjectileComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/summon_generic">
  </AudioComponent>

  <VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/chunk_of_soil.xml"
		>
	</VariableStorageComponent>

</Entity>*/