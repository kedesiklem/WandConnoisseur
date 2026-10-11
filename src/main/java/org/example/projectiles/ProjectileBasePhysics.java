package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public abstract class ProjectileBasePhysics extends Projectile{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "_unidentified.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public ProjectileBasePhysics(){
        this.name = "Base Physics";
        this.image = staticImage;
        this.emote = staticEmote;
        this.velocityComponent = (this.velocityComponent == null ? new VelocityComponent() : this.velocityComponent)
            .setAffectPhysicsBodies(true);
        this.projectileComponent = (this.projectileComponent == null ? new ProjectileComponent() : this.projectileComponent)
            .setOnLifetimeOutExplode(true)
            .setOnDeathExplode(true)
            .setLifetime(60);
            // on_death_gfx_leave_sprite="1"
            // do_moveto_update="0"
    }
}

/*<Entity tags="hittable,projectile">

  <!-- physical presence -->

  <PhysicsBodyComponent
    uid="1"
    allow_sleep="1"
    angular_damping="0"
    fixed_rotation="0"
    is_bullet="1"
    linear_damping="0"
    auto_clean="0"
    on_death_leave_physics_body="1"
    hax_fix_going_through_ground="1"
    >
  </PhysicsBodyComponent>

  <PhysicsImageShapeComponent
    body_id="1"
    centered="1"
	material="fuse"
	>
  </PhysicsImageShapeComponent>

  <PhysicsThrowableComponent
    throw_force_coeff="1" >
  </PhysicsThrowableComponent>

  <VelocityComponent
    affect_physics_bodies="1">
  </VelocityComponent>

  <!-- bomb logic -->

  <DamageModelComponent
    _tags="enabled_in_world"
    air_needed="0"
    blood_material=""
    drop_items_on_death="0"
    falling_damages="0"
    fire_damage_amount="0.2"
    fire_probability_of_ignition="0"
    hp="0.5"
    is_on_fire="0"
    materials_create_messages="0"
    materials_damage="1"
    materials_that_damage="lava"
    materials_how_much_damage="0.001"
    ragdoll_filenames_file=""
    ragdoll_material="" >
  </DamageModelComponent>

  <ProjectileComponent
    on_lifetime_out_explode="1"
    on_death_explode="1"
    lifetime="60"
    on_death_gfx_leave_sprite="1"
    do_moveto_update="0"
    ><config_explosion
      never_cache="0"
      damage="2"
      camera_shake="20"
      explosion_radius="20"
      explosion_sprite="data/particles/explosion_032.xml"
      load_this_entity="data/entities/particles/particle_explosion/main_small.xml"
      explosion_sprite_lifetime="0"
      create_cell_probability="0"
      hole_destroy_liquid="0"
      hole_enabled="1"
      ray_energy="6000000"
      particle_effect="1"
      damage_mortals="1"
      physics_explosion_power.min="1.2"
      physics_explosion_power.max="1.6"
      physics_throw_enabled="1"
      shake_vegetation="1"
      sparks_count_min="12"
      sparks_count_max="15"
      sparks_enabled="1"
      stains_enabled="1"
      stains_radius="15" >
    </config_explosion>
  </ProjectileComponent>

</Entity>*/