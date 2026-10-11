package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_ALCOHOL_BLAST extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "alcohol_blast.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_ALCOHOL_BLAST(){
        this.name = "Explosion of spirits";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(0)
            .setSpeedMax(0)
            .setDieOnLowVelocity(true)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(false)
            .setOnCollisionDie(true)
            .setLifetime(0);
            // shoot_light_flash_r="255"
            // shoot_light_flash_g="140"
            // shoot_light_flash_b="255"
            // shoot_light_flash_radius="140"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent>
    	</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
  	lob_min="0.8"
  	lob_max="1.0"
    speed_min="0"
    speed_max="0"
    die_on_low_velocity="1"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
	explosion_dont_damage_shooter="0"
    damage="0"
    on_collision_die="1"
    lifetime="0"
	shoot_light_flash_r="255"
    shoot_light_flash_g="140"
    shoot_light_flash_b="255"
    shoot_light_flash_radius="140"
	>
    <config_explosion
      never_cache="1"
      camera_shake="4.0"
      explosion_radius="12"
      explosion_sprite="data/particles/explosion_016.xml"
      explosion_sprite_lifetime="0"
	  ray_energy="400000"
      create_cell_probability="70"
      create_cell_material="alcohol"
      hole_destroy_liquid="0"
      damage="0.3"
      hole_enabled="1"
      hole_image="data/temp/explosion_hole.png"
      particle_effect="1"
      damage_mortals="1"
	  physics_explosion_power.min="0.4"
      physics_explosion_power.max="0.6"
      physics_throw_enabled="1"
      shake_vegetation="0"
	  spark_material="alcohol"
      material_sparks_enabled="1"
      material_sparks_count_max="50"
      material_sparks_count_min="40"
      light_fade_time="0.8"
      stains_enabled="1"
      stains_image="data/temp/explosion_stain.png"
	  audio_event_name="explosions/liquid"
	  >
    </config_explosion>
  </ProjectileComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/alcohol_blast.xml"
		>
	</VariableStorageComponent>

</Entity>*/