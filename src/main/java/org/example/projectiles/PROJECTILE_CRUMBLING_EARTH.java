package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_CRUMBLING_EARTH extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "crumbling_earth.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_CRUMBLING_EARTH(){
        this.name = "Earthquake";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(10.0)
            .setMass(0.07);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            .setLifetime(30)
            // muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_launcher_trailer.xml"
            // shoot_light_flash_radius="120"
            // shoot_light_flash_r="255"
            // shoot_light_flash_g="240"
            // shoot_light_flash_b="30"
            .setSpeedMin(110)
            .setSpeedMax(110)
            .setFriction(1.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(true)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(true)
            .setExplosionDontDamageShooter(true)
            .setOnCollisionDie(true);
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

  <Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
    		gravity_y="10"
			mass="0.07"
			>
    	</VelocityComponent>
	</Base>

    <ProjectileComponent
      lifetime="30"
	  muzzle_flash_file="data/entities/particles/muzzle_flashes/muzzle_flash_magic_launcher_trailer.xml"
	  shoot_light_flash_radius="120"
	  shoot_light_flash_r="255"
	  shoot_light_flash_g="240"
	  shoot_light_flash_b="30"
	  speed_min="110"
    speed_max="110"
	friction="1"
    direction_random_rad="0.00"
    on_death_explode="1"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="1"
    explosion_dont_damage_shooter="1"
    on_collision_die="1"
      >
	  <config_explosion
		never_cache="1"
      camera_shake="0"
      explosion_radius="2"
      explosion_sprite=""
      explosion_sprite_lifetime="0"
      create_cell_probability="0"
      hole_destroy_liquid="0"
	  explosion_sprite_emissive="0"
	  explosion_sprite_additive="0"
      hole_enabled="0"
      ray_energy="0"
      damage="0"
      particle_effect="0"
      damage_mortals="0"
	  physics_explosion_power.min="0"
      physics_explosion_power.max="0"
	  load_this_entity="data/entities/projectiles/deck/crumbling_earth_effect.xml"
	  physics_throw_enabled="0"
      shake_vegetation="0"
      sparks_enabled="0"
      stains_enabled="0"
	  >
      </config_explosion>
    </ProjectileComponent>

	<AudioComponent
      file="data/audio/Desktop/projectiles.bank"
      event_root="player_projectiles/crumbling_earth">
  </AudioComponent>

</Entity>*/