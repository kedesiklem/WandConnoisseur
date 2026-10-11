package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_CHAIN_BOLT extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "chain_bolt.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_CHAIN_BOLT(){
        this.name = "Chain Bolt";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setGravityY(0.0);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // lob_min="0.8"
            // lob_max="1.0"
            .setSpeedMin(40)
            .setSpeedMax(40)
            .setDieOnLowVelocity(false)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            .setOnCollisionDie(true)
            // shoot_light_flash_radius="15"
            // shoot_light_flash_r="250"
            // shoot_light_flash_g="80"
            // shoot_light_flash_b="255"
            .setLifetime(44);
            // penetrate_entities="1"
        this.projectileComponent.getDamageComponent().setDamage(25.0, DamageType.PROJECTILE);
    }
}

/*<Entity
  name="$projectile_default" tags="projectile_player"
   >

	<Base file="data/entities/base_projectile.xml" >
		<VelocityComponent
			gravity_y="0"
			>
    	</VelocityComponent>
	</Base>

  <ProjectileComponent
    _enabled="1"
  	lob_min="0.8"
  	lob_max="1.0"
    speed_min="40"
    speed_max="40"
    die_on_low_velocity="0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    on_collision_die="1"
	shoot_light_flash_radius="15"
	shoot_light_flash_r="250"
    shoot_light_flash_g="80"
    shoot_light_flash_b="255"
	damage="1.0"
    lifetime="44"
	penetrate_entities="1"
	>
  </ProjectileComponent>

  <SpriteComponent
    _enabled="0"
    alpha="1"
    image_file="data/projectiles_gfx/slow_bullet.xml"
    next_rect_animation=""
    offset_x="0"
    offset_y="0"
	additive="1"
    rect_animation="fireball"

     >
  </SpriteComponent>

  <ParticleEmitterComponent
    emitted_material_name="spark_purple_bright"
    offset.x="0"
    offset.y="0"
	gravity.y="0.0"
	x_vel_min="0"
    x_vel_max="0"
    y_vel_min="-2"
    y_vel_max="2"
    count_min="1"
    count_max="2"
	is_trail="1"
	trail_gap="0.5"
    lifetime_min="0.1"
    lifetime_max="0.8"
    emit_real_particles="0"
	render_on_grid="1"
    emit_cosmetic_particles="1"
    emission_interval_min_frames="1"
    emission_interval_max_frames="2"
    is_emitting="1" >
  </ParticleEmitterComponent>

  <LuaComponent
	script_source_file="data/scripts/projectiles/chain_bolt.lua"
	execute_every_n_frame="10"
	>
  </LuaComponent>

	<VariableStorageComponent
		name="prev_entity_id"
		value_int="0"
		>
	</VariableStorageComponent>

	<VariableStorageComponent
		name="prev_prev_entity_id"
		value_int="0"
		>
	</VariableStorageComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/chain_bolt.xml"
		>
	</VariableStorageComponent>

</Entity>*/