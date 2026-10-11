package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_DARKFLAME extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "darkflame.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_DARKFLAME(){
        this.name = "Path of dark flame";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0)
            .setAirFriction(0.0)
            .setMass(0.03);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            .setSpeedMin(250)
            .setSpeedMax(250)
            .setDieOnLowVelocity(false)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true)
            // collide_with_entities="0"
            .setLifetime(100);
            // shoot_light_flash_r="190"
            // shoot_light_flash_g="40"
            // shoot_light_flash_b="245"
            // shoot_light_flash_radius="140"
        this.projectileComponent.getDamageComponent().setDamage(20.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
    		gravity_y="0"
			air_friction="0"
			mass="0.03"
			>
    	</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
  	speed_min="250"
  	speed_max="250"
    die_on_low_velocity="0"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
	  explosion_dont_damage_shooter="1"
    damage="0.8"
    on_collision_die="1"
	collide_with_entities="0"
    lifetime="100"
	shoot_light_flash_r="190"
    shoot_light_flash_g="40"
    shoot_light_flash_b="245"
    shoot_light_flash_radius="140"
	>
    <config_explosion
      never_cache="1"
      camera_shake="0"
	  damage="0"
      explosion_radius="1"
      explosion_sprite="data/particles/darkflame.xml"
      explosion_sprite_lifetime="0.0"
      create_cell_probability="0"
      create_cell_material=""
	  ray_energy="0"
      hole_destroy_liquid="0"
      hole_enabled="0"
      hole_image=""
      particle_effect="0"
      damage_mortals="0"
	  physics_explosion_power.min="0"
      physics_explosion_power.max="0"
      physics_throw_enabled="0"
      shake_vegetation="1"
      sparks_enabled="0"
      sparks_count_max="0"
      sparks_count_min="0"
      light_fade_time="0.1"
	  light_enabled="1"
	  light_r="80"
	  light_g="5"
	  light_b="120"
	  light_radius_coeff="64"
      stains_enabled="0"
      stains_image="data/temp/explosion_stain.png" >
    </config_explosion>
  </ProjectileComponent>

	<SpriteComponent
		_enabled="1"
		alpha="1"
		image_file="data/projectiles_gfx/darkflame.xml"
		next_rect_animation=""
		offset_x="6"
		offset_y="6"
		rect_animation="spawn"


		update_transform_rotation="0"
		>
	</SpriteComponent>

	<LuaComponent
		_enabled="1"
		script_source_file="data/scripts/projectiles/darkflame.lua"
		execute_every_n_frame="1">
	</LuaComponent>

	<LightComponent
		_enabled="1"
		radius="64"
		r="80"
		g="5"
		b="120"
		fade_out_time="0.5"
		>
	</LightComponent>

  <AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="projectiles/rocket">
  </AudioComponent>

  <VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/darkflame.xml"
		>
	</VariableStorageComponent>

</Entity>*/