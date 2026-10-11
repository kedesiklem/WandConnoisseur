package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.Global;
import org.example.main.Global.DamageType;
import org.example.main.ProjectileComponent;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;

public class PROJECTILE_MATERIAL_FEUR extends ProjectileBase{
    static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    static final String staticImageFile = "material_feur.png";
    static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);

    public PROJECTILE_MATERIAL_FEUR(){
        this.modded = true;
        this.name = "Feur";
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