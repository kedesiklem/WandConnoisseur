package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_MATERIAL_CEMENT extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "material_cement.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_MATERIAL_CEMENT(){
        this.name = "Cement";
        this.image = staticImage;
        this.emote = staticEmote;
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            // projectile_type="MATERIAL_PARTICLE"
            // lob_min="1.0"
            // lob_max="1.0"
            .setSpeedMin(123)
            .setSpeedMax(135)
            .setFriction(3.0)
            .setSpreadRad(0.0)
            .setOnDeathExplode(false)
            // on_death_gfx_leave_sprite="0"
            .setOnLifetimeOutExplode(false)
            // on_death_emit_particle="1"
            // on_death_emit_particle_count="1"
            // on_death_emit_particle_type="cement"
            // on_death_particle_check_concrete="1"
            .setExplosionDontDamageShooter(true)
            // die_on_liquid_collision="1"
            .setOnCollisionDie(true)
            .setLifetime(360)
            // velocity_sets_scale="1"
            .setLifetimeRandomness(7);
            // ragdoll_force_multiplier="0.01"
            // hit_particle_force_multiplier="0.25 "
            // camera_shake_when_shot="1.0"
            // ground_collision_fx="0"
            // ragdoll_fx_on_collision="NORMAL"
        this.projectileComponent.getDamageComponent().setDamage(0.0, DamageType.PROJECTILE);
    }
}

/*<Entity name="$projectile_default" tags="projectile_player" >

    <Base file="data/entities/base_projectile.xml" >
    </Base>

  <ProjectileComponent
    _enabled="1"
    projectile_type="MATERIAL_PARTICLE"
    lob_min="1.0"
    lob_max="1.0"
    speed_min="123"
    speed_max="135"
    friction="3.0"
    direction_random_rad="0.0"
    on_death_explode="0"
    on_death_gfx_leave_sprite="0"
    on_lifetime_out_explode="0"
    on_death_emit_particle="1"
    on_death_emit_particle_count="1"
    on_death_emit_particle_type="cement"
    on_death_particle_check_concrete="1"
    explosion_dont_damage_shooter="1"
    die_on_liquid_collision="1"
    on_collision_die="1"
    lifetime="360"
    damage="0"
    velocity_sets_scale="1"
    lifetime_randomness="7"
    ragdoll_force_multiplier="0.01"
    hit_particle_force_multiplier="0.25 "
    camera_shake_when_shot="1.0"
    ground_collision_fx="0"
    ragdoll_fx_on_collision="NORMAL" >
  </ProjectileComponent>

  <SpriteComponent
    _enabled="1"
    alpha="1"
    image_file="data/projectiles_gfx/dirt.xml"

     >
  </SpriteComponent>

	<VariableStorageComponent
		name="projectile_file"
		value_string="data/entities/projectiles/deck/material_cement.xml"
		>
	</VariableStorageComponent>

</Entity>*/