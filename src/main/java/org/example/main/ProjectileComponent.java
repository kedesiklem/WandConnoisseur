package org.example.main;

// https://noita.wiki.gg/wiki/Documentation:_ProjectileComponent
public class ProjectileComponent{
    protected int lifetime = -1; // lifetime
    protected int lifetimeRandomness = 0; // lifetime_randomness
    protected boolean onLifetimeOutExplode = false; // on_lifetime_out_explode
    protected boolean collideWithWorld = true; // collide_with_world
    protected double speedMin = 60; // speed_min
    protected double speedMax = 60; // speed_max
    protected double friction = 0.0; // friction
    protected double spreadRad = 0.0; // direction_random_rad
    protected double formationRad = 0.0; // direction_nonrandom_rad
    // lob_min float 0.5
    // lob_max float 0.8
    // camera_shake_when_shot float 0
    // shoot_light_flash_radius float 0
    // shoot_light_flash_r unsigned int 255
    // shoot_light_flash_g unsigned int 180
    // shoot_light_flash_b unsigned int 150
    // create_shell_casing bool false
    // shell_casing_material std::string "brass"
    // muzzle_flash_file std::string ""
    // bounces_left int 0
    // bounce_energy float 0.5
    // bounce_always bool false
    // bounce_at_any_angle bool false
    // attach_to_parent_trigger bool false
    // bounce_fx_file std::string ""
    // angular_velocity float 0
    // velocity_sets_rotation bool true
    // velocity_sets_scale bool false
    // velocity_sets_scale_coeff float 1
    // velocity_sets_y_flip bool false
    // velocity_updates_animation float 0
    // ground_penetration_coeff float 0
    // ground_penetration_max_durability_to_destroy int 0
    // go_through_this_material std::string ""
    // do_moveto_update bool true
    // on_death_duplicate_remaining int 0
    // on_death_gfx_leave_sprite bool true
    protected boolean onDeathExplode = false; // on_death_explode
    // on_death_emit_particle bool false
    // on_death_emit_particle_count int 1
    // die_on_liquid_collision bool false
    protected boolean dieOnLowVelocity = false; // die_on_low_velocity
    protected double dieOnLowVelocityLimit = 50.0; // die_on_low_velocity_limit
    // on_death_emit_particle_type std::string ""
    // on_death_particle_check_concrete bool false
    // ground_collision_fx bool true
    protected boolean explosionDontDamageShooter = false; // explosion_dont_damage_shooter
    // on_death_item_pickable_radius float 0
    // penetrate_world bool false
    // penetrate_world_velocity_coeff float 0.6
    // penetrate_entities bool false
    protected boolean onCollisionDie = true; // on_collision_die
    // on_collision_remove_projectile bool false
    // on_collision_spawn_entity bool true
    // spawn_entity std::string ""
    // spawn_entity_is_projectile bool false
    // physics_impulse_coeff float 300
    // damage_every_x_frames int -1
    protected boolean damageScaledBySpeed = false; // damage_scaled_by_speed
    protected double damageScaleMaxSpeed = 0.0;// damage_scale_max_speed
    // collide_with_entities bool true
    // collide_with_tag std::string "hittable"
    // dont_collide_with_tag std::string ""
    // collide_with_shooter_frames int -1
    protected boolean friendlyFire = false; // friendly_fire
    protected DamageComponent damageComponent = new DamageComponent(); // damage damage_by_type
    protected double knockback = 0.0; // knockback_force
    // ragdoll_force_multiplier float 0.025
    // hit_particle_force_multiplierfloat 0.1
    // blood_count_multiplier float 1
    // damage_game_effect_entities std::string ""
    // never_hit_player bool false
    // collect_materials_to_shooter bool false
    // play_damage_sounds bool true
    // mLastFrameDamaged int -1024
    // config ConfigGunActionInfo
    // config_explosion ConfigExplosion
    // damage_critical ConfigDamageCritical
    // projectile_type PROJECTILE_TYPE::Enum PROJECTILE
    // shell_casing_offset vec2 (0, 0)
    // ragdoll_fx_on_collision RAGDOLL_FX::Enum NORMAL
    // mWhoShot EntityID
    // mWhoShotEntityTypeID EntityTypeID
    // mShooterHerdId int 0
    // mStartingLifetime int 0
    // mEntityThatShot EntityID
    // mTriggers ProjectileTriggers
    // mDamagedEntities VEC_ENTITY
    // mInitialSpeed float -1

    public ProjectileComponent(int lifetime, int lifetimeRandomness, boolean onLifetimeOutExplode, boolean collideWithWorld, double speedMin, double speedMax, double friction, double spreadRad, double formationRad, boolean onDeathExplode, boolean dieOnLowVelocity, double dieOnLowVelocityLimit, boolean explosionDontDamageShooter, boolean onCollisionDie, boolean damageScaledBySpeed, double damageScaleMaxSpeed, boolean friendlyFire, DamageComponent damageComponent, double knockback){
        this.lifetime = lifetime;
        this.lifetimeRandomness = lifetimeRandomness;
        this.onLifetimeOutExplode = onLifetimeOutExplode;
        this.collideWithWorld = collideWithWorld;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.friction = friction;
        this.spreadRad = spreadRad;
        this.formationRad = formationRad;
        this.onDeathExplode = onDeathExplode;
        this.dieOnLowVelocity = dieOnLowVelocity;
        this.dieOnLowVelocityLimit = dieOnLowVelocityLimit;
        this.explosionDontDamageShooter = explosionDontDamageShooter;
        this.onCollisionDie = onCollisionDie;
        this.damageScaledBySpeed = damageScaledBySpeed;
        this.damageScaleMaxSpeed = damageScaleMaxSpeed;
        this.friendlyFire = friendlyFire;
        this.damageComponent = damageComponent;
        this.knockback = knockback;
    }

    public ProjectileComponent(){
        // empty
    }

    public ProjectileComponent clone(){
        return new ProjectileComponent(this.lifetime, this.lifetimeRandomness, this.onLifetimeOutExplode, this.collideWithWorld, this.speedMin, this.speedMax, this.friction, this.spreadRad, this.formationRad, this.onDeathExplode, this.dieOnLowVelocity, this.dieOnLowVelocityLimit, this.explosionDontDamageShooter, this.onCollisionDie, this.damageScaledBySpeed, this.damageScaleMaxSpeed, this.friendlyFire, this.damageComponent == null ? null : this.damageComponent.clone(), this.knockback);
    }

    // getters
    public int getLifetime(){
        return this.lifetime;
    }

    public int getLifetimeRandomness(){
        return this.lifetimeRandomness;
    }

    public int getLifetimeMin(){
        return this.lifetime - this.lifetimeRandomness;
    }

    public int getLifetimeMax(){
        return this.lifetime + this.lifetimeRandomness;
    }

    public String getLifetimeString(){
        int lifetimeMin = this.lifetime - this.lifetimeRandomness;
        int lifetimeMax = this.lifetime + this.lifetimeRandomness;

        return lifetimeMin == lifetimeMax ? "" + lifetimeMin : "[" + lifetimeMin + ", " + lifetimeMax + "]";
    }

    public boolean getOnLifetimeOutExplode(){
        return this.onLifetimeOutExplode;
    }

    public boolean getCollideWithWorld(){
        return this.collideWithWorld;
    }

    public double getSpeedMin(){
        return this.speedMin;
    }

    public double getSpeedMax(){
        return this.speedMax;
    }

    public String getSpeedString(){
        return this.speedMin == this.speedMax ? "" + this.speedMin : "[" + this.speedMin + ", " + this.speedMax + "]";
    }

    public double getFriction(){
        return this.friction;
    }

    public double getSpreadRad(){
        return this.spreadRad;
    }

    public double getFormationRad(){
        return this.formationRad;
    }

    public boolean getOnDeathExplode(){
        return this.onDeathExplode;
    }

    public boolean getDieOnLowVelocity(){
        return this.dieOnLowVelocity;
    }

    public double getDieOnLowVelocityLimit(){
        return this.dieOnLowVelocityLimit;
    }

    public boolean getExplosionDontDamageShooter(){
        return this.explosionDontDamageShooter;
    }

    public boolean getOnCollisionDie(){
        return this.onCollisionDie;
    }

    public boolean getDamageScaledBySpeed(){
        return this.damageScaledBySpeed;
    }

    public double getDamageScaleMaxSpeed(){
        return this.damageScaleMaxSpeed;
    }

    public boolean getFriendlyFire(){
        return this.friendlyFire;
    }

    public DamageComponent getDamageComponent(){
        return this.damageComponent;
    }

    public double getKnockback(){
        return this.knockback;
    }

    // setters
    public ProjectileComponent setLifetime(int lifetime){
        this.lifetime = lifetime;
        return this;
    }

    public ProjectileComponent setLifetimeRandomness(int lifetimeRandomness){
        this.lifetimeRandomness = lifetimeRandomness;
        return this;
    }

    public ProjectileComponent setOnLifetimeOutExplode(boolean onLifetimeOutExplode){
        this.onLifetimeOutExplode = onLifetimeOutExplode;
        return this;
    }

    public ProjectileComponent setCollideWithWorld(boolean collideWithWorld){
        this.collideWithWorld = collideWithWorld;
        return this;
    }

    public ProjectileComponent setSpeedMin(double speedMin){
        this.speedMin = speedMin;
        return this;
    }

    public ProjectileComponent setSpeedMax(double speedMax){
        this.speedMax = speedMax;
        return this;
    }

    public ProjectileComponent setFriction(double friction){
        this.friction = friction;
        return this;
    }

    public ProjectileComponent setSpreadRad(double spreadRad){
        this.spreadRad = spreadRad;
        return this;
    }

    public ProjectileComponent setFormationRad(double formationRad){
        this.formationRad = formationRad;
        return this;
    }

    public ProjectileComponent setOnDeathExplode(boolean onDeathExplode){
        this.onDeathExplode = onDeathExplode;
        return this;
    }

    public ProjectileComponent setDieOnLowVelocity(boolean dieOnLowVelocity){
        this.dieOnLowVelocity = dieOnLowVelocity;
        return this;
    }

    public ProjectileComponent setDieOnLowVelocityLimit(double dieOnLowVelocityLimit){
        this.dieOnLowVelocityLimit = dieOnLowVelocityLimit;
        return this;
    }

    public ProjectileComponent setExplosionDontDamageShooter(boolean explosionDontDamageShooter){
        this.explosionDontDamageShooter = explosionDontDamageShooter;
        return this;
    }

    public ProjectileComponent setOnCollisionDie(boolean onCollisionDie){
        this.onCollisionDie = onCollisionDie;
        return this;
    }

    public ProjectileComponent setDamageScaledBySpeed(boolean damageScaledBySpeed){
        this.damageScaledBySpeed = damageScaledBySpeed;
        return this;
    }

    public ProjectileComponent setDamageScaleMaxSpeed(double damageScaleMaxSpeed){
        this.damageScaleMaxSpeed = damageScaleMaxSpeed;
        return this;
    }

    public ProjectileComponent setFriendlyFire(boolean friendlyFire){
        this.friendlyFire = friendlyFire;
        return this;
    }

    public ProjectileComponent setDamageComponent(DamageComponent damageComponent){
        this.damageComponent = damageComponent;
        return this;
    }

    public ProjectileComponent setKnockback(double knockback){
        this.knockback = knockback;
        return this;
    }

    public String toString(boolean importantOnly){
        StringBuilder result = new StringBuilder();

        result.append(result.isEmpty() ? "" : "\n").append("Lifetime: ").append(this.getLifetimeString());
        result.append(result.isEmpty() ? "" : "\n").append("Speed: ").append(this.getSpeedString());
        result.append(result.isEmpty() ? "" : "\n").append("Damage: ").append(this.getDamageComponent().toString());
        if(!importantOnly){
            result.append(result.isEmpty() ? "" : "\n").append("On lifetime out explode: ").append(this.getOnLifetimeOutExplode());
            result.append(result.isEmpty() ? "" : "\n").append("collide with world: ").append(this.getCollideWithWorld());
            result.append(result.isEmpty() ? "" : "\n").append("Friction: ").append(this.getFriction());
            result.append(result.isEmpty() ? "" : "\n").append("Spread: ").append(Math.toDegrees(this.getSpreadRad())).append("°");
            result.append(result.isEmpty() ? "" : "\n").append("Formation: ").append(Math.toDegrees(this.getFormationRad())).append("°");
            result.append(result.isEmpty() ? "" : "\n").append("On death explode: ").append(this.getOnDeathExplode());
            if(this.getDieOnLowVelocity()){result.append(result.isEmpty() ? "" : "\n").append("Die on low velocity limit: ").append(this.getDieOnLowVelocityLimit());}
            result.append(result.isEmpty() ? "" : "\n").append("Explosion dont damage shooter: ").append(this.getExplosionDontDamageShooter());
            result.append(result.isEmpty() ? "" : "\n").append("On collision die: ").append(this.getOnCollisionDie());
            result.append(result.isEmpty() ? "" : "\n").append("Damage scaled by speed: ").append(this.getDamageScaledBySpeed());
            result.append(result.isEmpty() ? "" : "\n").append("Friendly fire: ").append(this.getFriendlyFire());
            result.append(result.isEmpty() ? "" : "\n").append("Knockback: ").append(this.getKnockback());
        }

        return result.toString();
    }
}
