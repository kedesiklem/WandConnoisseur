package org.example.main;

import org.example.main.Global.DamageType;
import org.example.menu.MenuTree;
import org.example.projectiles.Projectile;
import org.example.script.Script;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class ProjectileCount{
    private Projectile projectile;
    private int count;

    public ProjectileCount(Projectile projectile){
        this.projectile = projectile;
        this.count = 1;
    }

    public ProjectileCount(Projectile projectile, int count){
        this.projectile = projectile;
        this.count = count;
    }

    public ProjectileCount clone(){
        return new ProjectileCount(this.projectile.clone(), this.count);
    }

    public boolean equals(ProjectileCount projectileCount){
        if(this.count != projectileCount.count){
            return false;
        }

        if(this.projectile == null){
            return projectileCount.projectile == null;
        }
        if(projectileCount.projectile == null){
            return false;
        }

        if(this.projectile.getClass() != projectileCount.projectile.getClass()){
            return false;
        }

        if(this.projectile.getTriggerCastState() == null){
            return projectileCount.projectile.getTriggerCastState() == null;
        }
        if(projectileCount.projectile.getTriggerCastState() == null){
            return false;
        }

        return this.projectile.getTriggerCastState().equals(projectileCount.projectile.getTriggerCastState());
    }

    // getters
    public Projectile getProjectile(){
        return this.projectile;
    }

    public int getCount(){
        return this.count;
    }

    // setters
    public void setProjectile(Projectile projectile){
        this.projectile = projectile;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void addCount(){
        this.count++;
    }
}

class CastStateProjectile{
    private final ArrayList<ProjectileCount> projectileCounts = new ArrayList<>();
    private final ArrayList<ProjectileCount> brokenProjectileCounts = new ArrayList<>();
    private boolean isBroken = false;
    private final Hasher hasher = new Hasher();
    private boolean hashUpdated = false;

    public CastStateProjectile(){
        // empty
    }

    public CastStateProjectile clone(){
        CastStateProjectile newCastStateProjectile = new CastStateProjectile();

        for(ProjectileCount projectileCount : this.projectileCounts){
            newCastStateProjectile.projectileCounts.add(projectileCount.clone());
        }
        for(ProjectileCount projectileCount : this.brokenProjectileCounts){
            newCastStateProjectile.brokenProjectileCounts.add(projectileCount.clone());
        }
        newCastStateProjectile.isBroken = this.isBroken;

        return newCastStateProjectile;
    }

    public boolean equals(CastStateProjectile castStateProjectile){
        if(this.projectileCounts.size() != castStateProjectile.projectileCounts.size() || this.brokenProjectileCounts.size() != castStateProjectile.brokenProjectileCounts.size()){
            return false;
        }

        for(int i=0; i < this.projectileCounts.size(); i++){
            if(!this.projectileCounts.get(i).equals(castStateProjectile.projectileCounts.get(i))){
                return false;
            }
        }

        for(int i=0; i < this.brokenProjectileCounts.size(); i++){
            if(!this.brokenProjectileCounts.get(i).equals(castStateProjectile.brokenProjectileCounts.get(i))){
                return false;
            }
        }

        return true;
    }

    public long getHash(){
        if(!this.getHashUpdated()){
            this.computeHash();
        }

        return this.hasher.getValue();
    }

    private void computeHash(){
        this.hasher.reset();

        this.hasher.add(this.projectileCounts.size());
        for(ProjectileCount projectileCount : this.projectileCounts){
            this.hasher.add(projectileCount.getProjectile().getClass());
            if(projectileCount.getProjectile().getTriggerCastState() != null){
                this.hasher.add(projectileCount.getProjectile().getTriggerCastState().getHash());
            }
        }

        this.hasher.add(this.brokenProjectileCounts.size());
        for(ProjectileCount projectileCount : this.brokenProjectileCounts){
            this.hasher.add(projectileCount.getProjectile().getClass());
            if(projectileCount.getProjectile().getTriggerCastState() != null){
                this.hasher.add(projectileCount.getProjectile().getTriggerCastState().getHash());
            }
        }

        this.hashUpdated = true;
    }

    public boolean getHashUpdated(){
        if(!this.hashUpdated){
            return false;
        }

        for(ProjectileCount projectileCount : this.projectileCounts){
            if(projectileCount.getProjectile().getTriggerCastState() != null && projectileCount.getProjectile().getTriggerCastState().getHashUpdated()){
                return false;
            }
        }

        for(ProjectileCount projectileCount : this.brokenProjectileCounts){
            this.hasher.add(projectileCount.getProjectile().getClass());
            if(projectileCount.getProjectile().getTriggerCastState() != null && projectileCount.getProjectile().getTriggerCastState().getHashUpdated()){
                return false;
            }
        }

        return true;
    }

    public boolean isBroken(){
        return this.isBroken;
    }

    public void breakTree(){
        this.isBroken = true;
    }

    public void addProjectile(Projectile projectile){
        this.hashUpdated = false;
        ArrayList<ProjectileCount> currentProjectileCounts = this.isBroken ? this.brokenProjectileCounts : this.projectileCounts;

        if(projectile.getProjectileComponent() == null){
            this.isBroken = true;
        }

        if(projectile.getTriggerType() == Projectile.TriggerType.none){
            for(ProjectileCount projectileCount : currentProjectileCounts){
                if(projectileCount.getProjectile().getClass() == projectile.getClass()){
                    projectileCount.addCount();
                    return;
                }
            }
        }

        currentProjectileCounts.add(new ProjectileCount(projectile));
    }

    public void addProjectile(Projectile projectile, int count){
        if(count <= 0){
            return;
        }

        this.hashUpdated = false;
        ArrayList<ProjectileCount> currentProjectileCounts = this.isBroken ? this.brokenProjectileCounts : this.projectileCounts;

        if(projectile.getProjectileComponent() == null){
            this.isBroken = true;
        }

        if(projectile.getTriggerType() == Projectile.TriggerType.none){
            for(ProjectileCount projectileCount : currentProjectileCounts){
                if(projectileCount.getProjectile().getClass() == projectile.getClass()){
                    projectileCount.addCount(count);
                    return;
                }
            }
        }

        currentProjectileCounts.add(new ProjectileCount(projectile, count));
    }

    public ProjectileCount[] getProjectileCountArray(){
        return this.projectileCounts.toArray(new ProjectileCount[0]);
    }

    public ProjectileCount[] getBrokenProjectileCountArray(){
        return this.brokenProjectileCounts.toArray(new ProjectileCount[0]);
    }

    public ArrayList<ProjectileCount> getProjectileCount(){
        return this.projectileCounts;
    }

    public ArrayList<ProjectileCount> getBrokenProjectileCount(){
        return this.brokenProjectileCounts;
    }

    public boolean splitSolo(ArrayList<Projectile> solo, ArrayList<ProjectileCount> multi){
        if(solo == null || multi == null){
            return false;
        }

        boolean skipFlag;

        for(ProjectileCount projectileCount : this.projectileCounts){
            if(projectileCount.getCount() > 1){
                multi.add(projectileCount.clone());
            }else{
                solo.add(projectileCount.getProjectile());
            }
        }

        for(int i=0; i < solo.size(); i++){
            skipFlag = false;
            for(ProjectileCount projectileCount : multi){
                if(solo.get(i).getClass() == projectileCount.getProjectile().getClass()){
                    solo.remove(i);
                    projectileCount.addCount();
                    i--;
                    skipFlag = true;
                    break;
                }
            }
            if(skipFlag){
                continue;
            }
            for(int j=i+1; j < solo.size(); j++){
                if(solo.get(i).getClass() == solo.get(j).getClass()){
                    solo.remove(j);
                    multi.add(new ProjectileCount(solo.remove(i), 2));
                    i--;
                    break;
                }
            }
        }

        return true;
    }

    public boolean splitSoloBroken(ArrayList<Projectile> solo, ArrayList<ProjectileCount> multi){
        if(solo == null || multi == null){
            return false;
        }

        boolean skipFlag;

        for(ProjectileCount projectileCount : this.brokenProjectileCounts){
            if(projectileCount.getCount() > 1){
                multi.add(projectileCount.clone());
            }else{
                solo.add(projectileCount.getProjectile());
            }
        }

        for(int i=0; i < solo.size(); i++){
            skipFlag = false;
            for(ProjectileCount projectileCount : multi){
                if(solo.get(i).getClass() == projectileCount.getProjectile().getClass()){
                    solo.remove(i);
                    projectileCount.addCount();
                    i--;
                    skipFlag = true;
                    break;
                }
            }
            if(skipFlag){
                continue;
            }
            for(int j=i+1; j < solo.size(); j++){
                if(solo.get(i).getClass() == solo.get(j).getClass()){
                    solo.remove(j);
                    multi.add(new ProjectileCount(solo.remove(i), 2));
                    i--;
                    break;
                }
            }
        }

        return true;
    }
}

class ScriptCount{
    private Script script;
    private int count;

    public ScriptCount(Script script){
        this.script = script;
        this.count = 1;
    }

    public ScriptCount(Script script, int count){
        this.script = script;
        this.count = count;
    }

    public ScriptCount clone(){
        return new ScriptCount(this.script.clone(), this.count);
    }

    public boolean equals(ScriptCount scriptCount){
        if(this.count != scriptCount.count){
            return false;
        }

        if(this.script == null){
            return scriptCount.script == null;
        }
        if(scriptCount.script == null){
            return false;
        }

        return this.script.getClass() == scriptCount.script.getClass();
    }

    // getters
    public Script getScript(){
        return this.script;
    }

    public int getCount(){
        return this.count;
    }

    // setters
    public void setScript(Script script){
        this.script = script;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void addCount(){
        this.count++;
    }
}

class CastStateScript{
    private final ArrayList<ScriptCount> scriptCounts = new ArrayList<>();
    private final Hasher hasher = new Hasher();
    private boolean hashUpdated = false;

    public CastStateScript(){
        // empty
    }

    public CastStateScript clone(){
        CastStateScript newCastStateScript = new CastStateScript();

        for(ScriptCount scriptCount : this.scriptCounts){
            newCastStateScript.scriptCounts.add(scriptCount.clone());
        }

        return newCastStateScript;
    }

    public boolean equals(CastStateScript castStateScript){
        if(this.scriptCounts.size() != castStateScript.scriptCounts.size()){
            return false;
        }

        for(int i=0; i < this.scriptCounts.size(); i++){
            if(!this.scriptCounts.get(i).equals(castStateScript.scriptCounts.get(i))){
                return false;
            }
        }

        return true;
    }

    public long getHash(){
        if(!this.hashUpdated){
            this.computeHash();
        }

        return this.hasher.getValue();
    }

    private void computeHash(){
        this.hasher.reset();

        this.hasher.add(this.scriptCounts.size());
        for(ScriptCount scriptCount : this.scriptCounts){
            this.hasher.add(scriptCount.getScript().getClass());
        }

        this.hashUpdated = true;
    }

    public boolean getHashUpdated(){
        return this.hashUpdated;
    }

    public void addScript(Script script){
        this.hashUpdated = false;
        for(ScriptCount scriptCount : this.scriptCounts){
            if(scriptCount.getScript().getClass() == script.getClass()){
                scriptCount.addCount();
                return;
            }
        }
        this.scriptCounts.add(new ScriptCount(script));
    }

    public void addScript(Script script, int count){
        if(count <= 0){
            return;
        }
        this.hashUpdated = false;
        for(ScriptCount scriptCount : this.scriptCounts){
            if(scriptCount.getScript().getClass() == script.getClass()){
                scriptCount.addCount(count);
                return;
            }
        }
        this.scriptCounts.add(new ScriptCount(script, count));
    }

    public ScriptCount[] getScriptCount(){
        return this.scriptCounts.toArray(new ScriptCount[0]);
    }

    public boolean splitSolo(ArrayList<Script> solo, ArrayList<ScriptCount> multi){
        if(solo == null || multi == null){
            return false;
        }

        for(ScriptCount scriptCount : this.scriptCounts){
            if(scriptCount.getCount() > 1){
                multi.add(scriptCount.clone());
            }else{
                solo.add(scriptCount.getScript());
            }
        }

        return true;
    }
}

public class CastState{
    private static final BufferedImage failedImage = Global.loadImage("./src/main/java/org/example/image/other/failed_16.png");
    private static final BufferedImage triggerImage = Global.loadImage("./src/main/java/org/example/image/other/projectile_trigger.png");
    private static final BufferedImage timerImage = Global.loadImage("./src/main/java/org/example/image/other/projectile_timer.png");
    private static final BufferedImage expirationImage = Global.loadImage("./src/main/java/org/example/image/other/projectile_expiration.png");
    private CastStateProjectile castStateProjectile = new CastStateProjectile();
    private CastStateScript castStateScript = new CastStateScript();
    private int castDelay = 0;
    private DamageComponent damageComponent = new DamageComponent();
    private int lifetime = 0;
    private int critRate = 0;
    private int pattern = 0;
    private double spread = 0.0;
    private double screenshake = 0.0;
    private double gravity = 0.0;
    private boolean friendlyFire = false;
    private double speedMultiplier = 1.0;
    private int goreParticles = 0;
    private int bounce = 0;
    private String material = "";
    private int materialAmount = 0;
    private StringList trailMaterial = new StringList();
    private int trailMaterialAmount = 0;
    private String globalValues = ""; // only for hash
    private final Hasher hasher = new Hasher();
    private boolean hashUpdated = false;

    public CastState(){
        // nothing
    }

    public CastState clone(){
        CastState castState = new CastState();

        castState.castStateProjectile = this.castStateProjectile.clone();
        castState.castStateScript = this.castStateScript.clone();
        castState.castDelay = this.castDelay;
        castState.damageComponent = this.damageComponent.clone();
        castState.lifetime = this.lifetime;
        castState.critRate = this.critRate;
        castState.pattern = this.pattern;
        castState.spread = this.spread;
        castState.screenshake = this.screenshake;
        castState.gravity = this.gravity;
        castState.friendlyFire = this.friendlyFire;
        castState.speedMultiplier = this.speedMultiplier;
        castState.goreParticles = this.goreParticles;
        castState.bounce = this.bounce;
        castState.material = this.material;
        castState.materialAmount = this.materialAmount;
        castState.trailMaterial = this.trailMaterial.clone();
        castState.trailMaterialAmount = this.trailMaterialAmount;
        castState.globalValues = this.globalValues;

        return castState;
    }

    public boolean equals(CastState castState){
        if(castState == null){
            return false;
        }
        if(!this.castStateProjectile.equals(castState.castStateProjectile)){
            return false;
        }
        if(!this.castStateScript.equals(castState.castStateScript)){
            return false;
        }
        if(this.castDelay != castState.castDelay){
            return false;
        }
        if(!this.damageComponent.equals(castState.damageComponent)){
            return false;
        }
        if(this.lifetime != castState.lifetime){
            return false;
        }
        if(this.critRate != castState.critRate){
            return false;
        }
        if(this.pattern != castState.pattern){
            return false;
        }
        if(this.spread != castState.spread){
            return false;
        }
        if(this.screenshake != castState.screenshake){
            return false;
        }
        if(this.gravity != castState.gravity){
            return false;
        }
        if(this.friendlyFire != castState.friendlyFire){
            return false;
        }
        if(this.speedMultiplier != castState.speedMultiplier){
            return false;
        }
        if(this.goreParticles != castState.goreParticles){
            return false;
        }
        if(this.bounce != castState.bounce){
            return false;
        }
        if(!this.material.equals(castState.material)){
            return false;
        }
        if(this.materialAmount != castState.materialAmount){
            return false;
        }
        if(!this.trailMaterial.equals(castState.trailMaterial)){
            return false;
        }
        if(this.trailMaterialAmount != castState.trailMaterialAmount){
            return false;
        }

        return this.globalValues.equals(castState.globalValues);
    }

    public static boolean staticEquals(CastState first, CastState second){
        if(first == null && second == null){
            return true;
        }
        if(first == null){
            return false;
        }

        return first.equals(second);
    }

    public long getHash(){
        if(!this.getHashUpdated()){
            this.computeHash();
        }
;
        return this.hasher.getValue();
    }

    public static long staticHash(CastState castState){
        return castState.getHash();
    }

    private void computeHash(){
        this.hasher.reset();

        this.hasher.add(this.castStateProjectile.getHash());
        this.hasher.add(this.castStateScript.getHash());
        this.hasher.add(this.castDelay);
        this.hasher.add(this.damageComponent.getArray(false));
        this.hasher.add(this.lifetime);
        this.hasher.add(this.critRate);
        this.hasher.add(this.pattern);
        this.hasher.add(this.spread);
        this.hasher.add(this.screenshake);
        this.hasher.add(this.gravity);
        this.hasher.add(this.friendlyFire);
        this.hasher.add(this.speedMultiplier);
        this.hasher.add(this.goreParticles);
        this.hasher.add(this.bounce);
        this.hasher.add(this.material);
        this.hasher.add(this.materialAmount);
        this.hasher.add(this.trailMaterial.getArray());
        this.hasher.add(this.trailMaterialAmount);
        this.hasher.add(this.globalValues);

        this.hashUpdated = true;
    }

    public boolean getHashUpdated(){
        return this.hashUpdated && this.castStateScript.getHashUpdated() && this.castStateProjectile.getHashUpdated();
    }

    // getters
    public CastStateProjectile getCastStateProjectile(boolean clone){
        return clone ? this.castStateProjectile.clone() : this.castStateProjectile;
    }

    public CastStateScript getCastStateScript(boolean clone){
        return clone ? this.castStateScript.clone() : this.castStateScript;
    }

    public int getCastDelay(){
        return this.castDelay;
    }

    public DamageComponent getDamageComponent(){
        return this.damageComponent;
    }

    public int getLifetime(){
        return this.lifetime;
    }

    public int getCritRate(){
        return this.critRate;
    }

    public int getPattern(){
        return this.pattern;
    }

    public double getSpread(){
        return this.spread;
    }

    public double getScreenshake(){
        return this.screenshake;
    }

    public double getGravity(){
        return this.gravity;
    }

    public boolean getFriendlyFire(){
        return this.friendlyFire;
    }

    public double getSpeedMultiplier(){
        return this.speedMultiplier;
    }

    public int getGoreParticles(){
        return this.goreParticles;
    }

    public int getBounce(){
        return this.bounce;
    }

    public String getMaterial(){
        return this.material;
    }

    public int getMaterialAmount(){
        return this.materialAmount;
    }

    public String[] getTrailMaterial(){
        return this.trailMaterial.getArray();
    }

    public int getTrailMaterialAmount(){
        return this.trailMaterialAmount;
    }

    // setters
    public void setCastDelay(int castDelay){
        this.hashUpdated = false;
        this.castDelay = castDelay;
    }

    public void setDamage(DamageComponent damageComponent){
        this.hashUpdated = false;
        this.damageComponent.setDamage(damageComponent);
    }

    public void setDamageComponent(DamageComponent damageComponent){
        this.hashUpdated = false;
        this.damageComponent = damageComponent;
    }

    public void setLifetime(int lifetime){
        this.hashUpdated = false;
        this.lifetime = lifetime;
    }

    public void setCritRate(int critRate){
        this.hashUpdated = false;
        this.critRate = critRate;
    }

    public void setPattern(int pattern){
        this.hashUpdated = false;
        this.pattern = pattern;
    }

    public void setSpread(double spread){
        this.hashUpdated = false;
        this.spread = spread;
    }

    public void setGravity(double gravity){
        this.hashUpdated = false;
        this.gravity = gravity;
    }

    public void setFriendlyFire(boolean friendlyFire){
        this.hashUpdated = false;
        this.friendlyFire = friendlyFire;
    }

    public void setSpeedMultiplier(double speedMultiplier){
        this.hashUpdated = false;
        this.speedMultiplier = speedMultiplier;
    }

    public void setGoreParticles(int goreParticles){
        this.hashUpdated = false;
        this.goreParticles = goreParticles;
    }

    public void setBounce(int bounce){
        this.hashUpdated = false;
        this.bounce = bounce;
    }

    public void setMaterial(String material){
        this.hashUpdated = false;
        this.material = material;
    }

    public void setMaterialAmount(int materialAmount){
        this.hashUpdated = false;
        this.materialAmount = materialAmount;
    }

    public void setTrailMaterialAmount(int trailMaterialAmount){
        this.hashUpdated = false;
        this.trailMaterialAmount = trailMaterialAmount;
    }

    public void setGlobalValues(String globalValues){
        this.hashUpdated = false;
        this.globalValues = globalValues;
    }

    // adders
    public void addCastDelay(int castDelay){
        this.hashUpdated = false;
        this.castDelay += castDelay;
    }

    public void addDamageComponent(DamageComponent damageComponent){
        this.hashUpdated = false;
        this.damageComponent.add(damageComponent);
    }

    public void addLifetime(int lifetime){
        this.hashUpdated = false;
        this.lifetime += lifetime;
    }

    public void addCritRate(int critRate){
        this.hashUpdated = false;
        this.critRate += critRate;
    }

    public void addPattern(int pattern){
        this.hashUpdated = false;
        this.pattern += pattern;
    }

    public void addSpread(double spread){
        this.hashUpdated = false;
        this.spread += spread;
    }

    public void addScreenshake(double screenshake){
        this.hashUpdated = false;
        this.screenshake += screenshake;
        if(this.screenshake < 0.0){
            this.screenshake = 0.0;
        }
    }

    public void addGravity(double gravity){
        this.hashUpdated = false;
        this.gravity += gravity;
    }

    public void multiplySpeed(double speed, double min, double max){
        this.hashUpdated = false;
        this.speedMultiplier *= speed;
        if(this.speedMultiplier > max){
            this.speedMultiplier = max;
        }else if(this.speedMultiplier < min){
            this.speedMultiplier = min;
        }
    }

    public void multiplySpeed(double speed){
        this.hashUpdated = false;
        this.speedMultiplier *= speed;
    }

    public void addSpeed(double speed, double min, double max){
        this.hashUpdated = false;
        this.speedMultiplier += speed;
        if(this.speedMultiplier > max){
            this.speedMultiplier = max;
        }else if(this.speedMultiplier < min){
            this.speedMultiplier = min;
        }
    }

    public void addSpeed(double speed){
        this.hashUpdated = false;
        this.speedMultiplier += speed;
    }

    public void addGoreParticles(int goreParticles){
        this.hashUpdated = false;
        this.goreParticles += goreParticles;
    }

    public void addBounce(int bounce){
        this.hashUpdated = false;
        this.bounce += bounce;
    }

    public void addMaterialAmount(int materialAmount){
        this.hashUpdated = false;
        this.materialAmount += materialAmount;
    }

    public void addTrailMaterial(String trailMaterial){
        this.hashUpdated = false;
        this.trailMaterial.add(trailMaterial);
    }

    public void addTrailMaterialAmount(int trailMaterialAmount){
        this.hashUpdated = false;
        this.trailMaterialAmount += trailMaterialAmount;
    }

    public void addProjectile(Projectile projectile){
        this.hashUpdated = false;
        this.castStateProjectile.addProjectile(projectile);
    }

    public void addProjectile(Projectile projectile, int count){
        this.hashUpdated = false;
        this.castStateProjectile.addProjectile(projectile, count);
    }

    public void addScript(Script script){
        this.hashUpdated = false;
        this.castStateScript.addScript(script);
    }

    public void addScript(Script script, int count){
        this.hashUpdated = false;
        this.castStateScript.addScript(script, count);
    }

    public void addScript(Script[] scripts){
        this.hashUpdated = false;
        for(Script script : scripts){
            this.addScript(script);
        }
    }

    public CastState addProjectileTrigger(Projectile projectile, int timer, Projectile.TriggerType triggerType){
        this.hashUpdated = false;
        CastState newCastState = new CastState();

        if(projectile.getProjectileComponent() == null || this.castStateProjectile.isBroken()){
            newCastState.castStateProjectile.breakTree();
        }

        projectile.addTrigger(triggerType, timer, newCastState);
        this.addProjectile(projectile);

        return newCastState;
    }

    public CastState addProjectileTrigger(Projectile projectile, Projectile.TriggerType triggerType){
        this.hashUpdated = false;
        return this.addProjectileTrigger(projectile, 0, triggerType);
    }

    public String toString(boolean discordFormat){
        StringBuilder result = new StringBuilder();
        StringBuilder innerBuilder = new StringBuilder();
        ArrayList<Projectile> soloProjectiles = null;
        ArrayList<Projectile> brokenSoloProjectiles = null;
        ArrayList<ProjectileCount> multiProjectiles = null;
        ArrayList<ProjectileCount> brokenMultiProjectiles = null;
        ArrayList<Script> soloScript = null;
        ArrayList<ScriptCount> multiScript = null;

        if(discordFormat){
            soloProjectiles = new ArrayList<>();
            brokenSoloProjectiles = new ArrayList<>();
            multiProjectiles = new ArrayList<>();
            brokenMultiProjectiles = new ArrayList<>();
            soloScript = new ArrayList<>();
            multiScript = new ArrayList<>();

            this.castStateProjectile.splitSolo(soloProjectiles, multiProjectiles);
            this.castStateProjectile.splitSoloBroken(brokenSoloProjectiles, brokenMultiProjectiles);
            this.castStateScript.splitSolo(soloScript, multiScript);

            for(Projectile projectile : soloProjectiles){
                innerBuilder.append(projectile.getEmote());
            }
            if(brokenSoloProjectiles.size() > 0){
                innerBuilder.append("(");
                for(Projectile projectile : brokenSoloProjectiles){
                    innerBuilder.append(projectile.getEmote());
                }
                innerBuilder.append(")");
            }
            for(ProjectileCount projectileCount : multiProjectiles){
                if(!innerBuilder.isEmpty()){
                    innerBuilder.append("\n");
                }
                innerBuilder.append(projectileCount.getProjectile().getEmote()).append(" x").append(projectileCount.getCount());
            }
            for(ProjectileCount projectileCount : brokenMultiProjectiles){
                if(!innerBuilder.isEmpty()){
                    innerBuilder.append("\n");
                }
                innerBuilder.append("(").append(projectileCount.getProjectile().getEmote()).append(" x").append(projectileCount.getCount()).append(")");
            }
            if(!innerBuilder.isEmpty()){
                if(!result.isEmpty()){
                    result.append("\n");
                }
                result.append("# Projectiles\n").append(innerBuilder);
                innerBuilder.setLength(0);
            }
        }

        if(this.castDelay != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Cast delay: ").append(Global.delayFormat(this.castDelay));}
        for(DamageType damageType : DamageType.values()){
            if(this.damageComponent.getDamage(damageType) != 0.0){
                innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append(damageType.getDisplayName()).append(" damage: ").append(this.damageComponent.getDamage(damageType));
            }
        }
        if(this.lifetime != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Lifetime: ").append(Global.delayFormat(this.lifetime));}
        if(this.critRate != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Crit rate: ").append(this.critRate).append("%");}
        if(this.pattern != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Pattern: ").append(this.pattern).append(discordFormat ? "°" : " deg");}
        if(this.spread != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Spread: ").append(this.spread).append(discordFormat ? "°" : " deg");}
        if(this.screenshake != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Screenshake: ").append(this.screenshake);}
        if(this.gravity != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Gravity: ").append(this.gravity);}
        if(this.friendlyFire){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Friendly fire: true");}
        if(this.speedMultiplier != 1.0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Speed multiplier: x").append(this.speedMultiplier);}
        if(this.goreParticles != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Gore particles: ").append(this.goreParticles);}
        if(this.bounce != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Bounce: ").append(this.bounce);}
        if(!this.material.equals("")){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Material: \"").append(this.material).append("\"");}
        if(this.materialAmount != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Material amount: ").append(this.materialAmount);}
        if(this.trailMaterial.size() != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Trail material: ").append(this.trailMaterial.toString());}
        if(this.trailMaterialAmount != 0){innerBuilder.append(innerBuilder.isEmpty() ? "" : "\n").append("Trail material amount: ").append(this.trailMaterialAmount);}

        if(!discordFormat){
            return innerBuilder.toString();
        }

        if(!innerBuilder.isEmpty()){
            if(!result.isEmpty()){
                result.append("\n");
            }
            result.append("# Stats\n").append(innerBuilder);
            innerBuilder.setLength(0);
        }

        for(Script script : soloScript){
            innerBuilder.append(script.getEmote());
        }
        for(ScriptCount scriptCount : multiScript){
            if(!innerBuilder.isEmpty()){
                innerBuilder.append("\n");
            }
            innerBuilder.append(scriptCount.getScript().getEmote()).append(" (x").append(scriptCount.getCount()).append(")");
        }
        if(!innerBuilder.isEmpty()){
            if(!result.isEmpty()){
                result.append("\n");
            }
            result.append("# Scripts\n").append(innerBuilder);
            innerBuilder.setLength(0);
        }

        return result.toString();
    }

    public MenuTree toMenuTree(String id, String title){
        MenuTree menu = new MenuTree("", title, this.toString(true), null, "");
        CastState innerCastState;

        for(ProjectileCount projectileCount : this.castStateProjectile.getProjectileCount()){
            innerCastState = projectileCount.getProjectile().getTriggerCastState();
            if(innerCastState != null){
                menu.addChild(innerCastState.toMenuTree(projectileCount.getProjectile().getName() + " " + projectileCount.getProjectile().getTriggerType()));
            }
        }
        for(ProjectileCount projectileCount : this.castStateProjectile.getBrokenProjectileCount()){
            innerCastState = projectileCount.getProjectile().getTriggerCastState();
            if(innerCastState != null){
                menu.addChild(innerCastState.toMenuTree(projectileCount.getProjectile().getName() + " " + projectileCount.getProjectile().getTriggerType()));
            }
        }

        return menu;
    }

    private MenuTree toMenuTree(String title){
        return this.toMenuTree("", title);
    }

    public static boolean saveToImage(String globalValues, String[] castValues, CastState[] castStates, String filename){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));
        addToImage(0, 0, imageBuilder, globalValues, castValues, castStates);
        return imageBuilder.saveAsPNG(filename);
    }

    public boolean saveToImage(String filename){
        ImageBuilder image = new ImageBuilder(new Color(0, 0, 0));
        image.setFont(Global.getPixelFont().deriveFont((float)15));
        this.toImageNode(image, 0, 0, false);
        return image.saveAsPNG(filename);
    }

    public BufferedImage toImage(){
        ImageBuilder image = new ImageBuilder(new Color(0, 0, 0));
        image.setFont(Global.getPixelFont().deriveFont((float)15));
        this.toImageNode(image, 0, 0, false);
        return image.toPNG();
    }

    public static void addToImage(int x, int y, ImageBuilder imageBuilder, String globalValues, String[] castValues, CastState[] castStates){
        imageBuilder.setFont(Global.getPixelFont().deriveFont((float)15));
        int imageSize = 16;
        int arrowSize = 40;
        AtomicInteger nextY = new AtomicInteger(y);
        int tempY = imageBuilder.drawTextRectangle(globalValues.split("\\r?\\n"), x, nextY.get(), 5, Color.WHITE, Color.WHITE).y;
        int[][] maxPoints = new int[castStates.length][3]; // maxX, minY, maxY
        nextY.set(tempY + arrowSize);

        Consumer<Integer> print = index -> {
            maxPoints[index][1] = nextY.get();

            imageBuilder.drawSingleTurnArrow(x + imageSize/2, tempY + 1, x + arrowSize, nextY.get() + imageSize/2, new Color(128, 152, 198), false, false);
            Point maxPoint = imageBuilder.drawText(((index + 1) + ")\n" + (index < castValues.length ? castValues[index] : "")).split("\\r?\\n"), x + arrowSize, nextY.get(), 0, 0, Color.WHITE);

            maxPoints[index][0] = maxPoint.x;

            maxPoint = castStates[index].toImage(imageBuilder, x + arrowSize, maxPoint.y);

            maxPoints[index][0] = Math.max(maxPoints[index][0], maxPoint.x);
            nextY.set(maxPoint.y + 40);
            maxPoints[index][2] = maxPoint.y;
        };

        BiConsumer<int[], Integer> repeat = (range, count) -> {
            int maxX = maxPoints[range[0]][0];
            for(int i=range[0] + 1; i <= range[1]; i++){
                maxX = Math.max(maxX, maxPoints[i][0]);
            }
            maxX += 10;
            maxPoints[range[0]][0] = imageBuilder.drawCurlyBrackets(maxX, maxPoints[range[0]][1], maxX + 20, maxPoints[range[1]][2], "x" + count, Color.WHITE, false, true).x;
        };

        RecursivePatternMatcher<CastState> recursivePatternMatcher = new RecursivePatternMatcher<>(castStates, print, repeat, CastState::staticHash, CastState::staticEquals);
        recursivePatternMatcher.process();
    }

    public static BufferedImage toImage(String globalValues, String[] castValues, CastState[] castStates){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));

        addToImage(0, 0, imageBuilder, globalValues, castValues, castStates);

        return imageBuilder.toPNG();
    }

    public static void addToImage(int x, int y, ImageBuilder imageBuilder, CastState[] castStates){
        imageBuilder.setFont(Global.getPixelFont().deriveFont((float)15));

        for(CastState castState : castStates){
            y = castState.toImage(imageBuilder, x, y).y + 40;
        }
    }

    public static BufferedImage toImage(CastState[] castStates){
        ImageBuilder imageBuilder = new ImageBuilder(new Color(0, 0, 0));

        addToImage(0, 0, imageBuilder, castStates);

        return imageBuilder.toPNG();
    }

    private Point toImage(ImageBuilder image, int x, int y){
        if(image == null){
            return new Point(x, y);
        }

        return this.toImageNode(image, x, y, false);
    }

    private Point toImageNode(ImageBuilder image, int x, int y, boolean brokenProjectileTree){
        String[] castStateInfo = this.toString(false).split("\\r?\\n");
        Projectile currentProjectile;
        BufferedImage currentImage;
        int imageSize = 16;
        int arrowSizeX = 40;
        int boxOutsideMargin = 10;
        int boxInsideMargin = 5;
        int nextX = x + boxInsideMargin;
        int nextY = y + boxInsideMargin;
        int triggerY = y;
        Point tempPoint = new Point(x + boxInsideMargin, y + boxInsideMargin);
        ArrayList<Projectile> soloProjectiles = new ArrayList<>();
        ArrayList<Projectile> brokenSoloProjectiles = new ArrayList<>();
        ArrayList<ProjectileCount> multiProjectiles = new ArrayList<>();
        ArrayList<ProjectileCount> brokenMultiProjectiles = new ArrayList<>();
        ArrayList<Script> soloScript = new ArrayList<>();
        ArrayList<ScriptCount> multiScript = new ArrayList<>();
        boolean isEmpty = true;
        boolean drawUnder = false;

        if(castStateInfo.length == 1 && castStateInfo[0].equals("")){
            castStateInfo = new String[0];
        }

        this.castStateProjectile.splitSolo(soloProjectiles, multiProjectiles);
        this.castStateProjectile.splitSoloBroken(brokenSoloProjectiles, brokenMultiProjectiles);
        this.castStateScript.splitSolo(soloScript, multiScript);

        for(ArrayList<Projectile> projectileList : Arrays.asList(soloProjectiles, brokenSoloProjectiles)){
            for(Projectile projectile : projectileList){
                currentImage = projectile.getImage();
                if(currentImage != null){
                    image.addImage(currentImage, nextX, y + boxInsideMargin, projectile.getClass().getSimpleName());
                    if(brokenProjectileTree || projectileList == brokenSoloProjectiles){
                        image.addImage(failedImage, nextX, y + boxInsideMargin, "FAILED_16");
                    }
                    nextX += currentImage.getWidth();
                    nextY = Math.max(nextY, y + boxInsideMargin + currentImage.getHeight());
                }
                isEmpty = false;
            }
        }

        for(ArrayList<ProjectileCount> projectileCountList : Arrays.asList(multiProjectiles, brokenMultiProjectiles)){
            for(ProjectileCount projectileCount : projectileCountList){
                currentImage = projectileCount.getProjectile().getImage();
                if(currentImage != null){
                    image.addImage(currentImage, x + boxInsideMargin, nextY, projectileCount.getProjectile().getClass().getSimpleName());
                    if(brokenProjectileTree || projectileCountList == brokenMultiProjectiles){
                        image.addImage(failedImage, x + boxInsideMargin, nextY, "FAILED_16");
                    }
                    tempPoint = image.drawText("x" + projectileCount.getCount(), x + boxInsideMargin + currentImage.getWidth(), nextY, Color.WHITE);
                    nextX = Math.max(nextX, tempPoint.x);
                    nextY += currentImage.getHeight();
                }
                isEmpty = false;
            }
        }

        for(String info : castStateInfo){
            tempPoint = image.drawText(info, x + boxInsideMargin, nextY, Color.WHITE);
            nextX = Math.max(nextX, tempPoint.x);
            nextY = Math.max(nextY, tempPoint.y);
            isEmpty = false;
        }

        tempPoint.setLocation(x + boxInsideMargin, nextY);
        for(Script script : soloScript){
            currentImage = script.getImage();
            if(currentImage != null){
                image.addImage(currentImage, tempPoint.x, tempPoint.y, script.getClass().getSimpleName());
                tempPoint.setLocation(tempPoint.x + currentImage.getWidth(), tempPoint.y);
                nextX = Math.max(nextX, tempPoint.x);
                nextY = Math.max(nextY, tempPoint.y + currentImage.getHeight());
            }
            isEmpty = false;
        }

        for(ScriptCount scriptCount : multiScript){
            currentImage = scriptCount.getScript().getImage();
            if(currentImage != null){
                image.addImage(currentImage, x + boxInsideMargin, nextY, scriptCount.getScript().getClass().getSimpleName());
                tempPoint = image.drawText("x" + scriptCount.getCount(), x + boxInsideMargin + currentImage.getWidth(), nextY, Color.WHITE);
                nextX = Math.max(nextX, tempPoint.x);
                nextY += currentImage.getHeight();
            }
            isEmpty = false;
        }

        if(isEmpty){
            tempPoint = image.drawText("<empty>", x + boxInsideMargin, nextY, Color.RED);
            nextX = Math.max(nextX, tempPoint.x);
            nextY = Math.max(nextY, tempPoint.y);
        }

        nextX += boxInsideMargin;
        nextY += boxInsideMargin;
        nextX = Math.max(nextX, x + imageSize);
        nextY = Math.max(nextY, y + imageSize);
        image.drawRectangle(x, y, nextX, nextY, brokenProjectileTree ? Color.RED : Color.WHITE, false);

        Projectile lastProjectile = null;
        CastState lastCastState = null;
        int castStateCount = 1;
        Point nextPoint = new Point(nextX, nextY);
        int maxX = nextX;
        for(ArrayList<ProjectileCount> projectileCountsList : Arrays.asList(this.castStateProjectile.getProjectileCount(), this.castStateProjectile.getBrokenProjectileCount())){
            for(ProjectileCount projectileCount : projectileCountsList){
                currentProjectile = projectileCount.getProjectile();
                if(currentProjectile.getTriggerCastState() != null){
                    if(lastProjectile != null && currentProjectile.getClass() == lastProjectile.getClass() && currentProjectile.getTriggerCastState().getHash() == lastCastState.getHash() && currentProjectile.getTriggerCastState().equals(lastCastState)){
                        castStateCount++;
                        continue;
                    }
                    if(castStateCount > 1){
                        triggerY = image.drawCurlyBrackets(nextX + arrowSizeX, triggerY + boxOutsideMargin, nextPoint.x, triggerY + boxOutsideMargin + 20, "x" + castStateCount, Color.WHITE, false, false).y;
                    }
                    lastProjectile = currentProjectile;
                    lastCastState = currentProjectile.getTriggerCastState();
                    castStateCount = 1;

                    if(triggerY != y){
                        triggerY += boxOutsideMargin;
                    }
                    if(!brokenProjectileTree){
                        brokenProjectileTree = currentProjectile.getProjectileComponent() == null || projectileCountsList == this.castStateProjectile.getBrokenProjectileCount();
                        drawUnder = brokenProjectileTree;
                    }

                    image.drawArrow(nextX, y + imageSize/2, nextX + arrowSizeX, triggerY + imageSize/2, brokenProjectileTree ? Color.RED : Color.WHITE, true, drawUnder);
                    currentImage = currentProjectile.getImage();
                    if(currentImage != null){
                        image.addImage(currentImage, nextX + arrowSizeX, triggerY, currentProjectile.getClass().getSimpleName());
                        switch(currentProjectile.getTriggerType()){
                            case trigger -> {if(triggerImage != null){image.addImage(triggerImage, nextX + arrowSizeX, triggerY, "TRIGGER");}}
                            case timer -> {if(timerImage != null){image.addImage(timerImage, nextX + arrowSizeX, triggerY, "TIMER");}}
                            case expiration -> {if(expirationImage != null){image.addImage(expirationImage, nextX + arrowSizeX, triggerY, "EXPIRATION");}}
                        }
                    }
                    nextPoint = currentProjectile.getTriggerCastState().toImageNode(image, nextX + arrowSizeX + imageSize, triggerY, brokenProjectileTree);
                    maxX = Math.max(maxX, nextPoint.x);
                    triggerY = Math.max(triggerY, nextPoint.y);
                    nextY = Math.max(nextY, nextPoint.y);
                }
            }
        }
        if(castStateCount > 1){
            nextY = Math.max(nextY, image.drawCurlyBrackets(nextX + arrowSizeX, triggerY + boxOutsideMargin, nextPoint.x, triggerY + boxOutsideMargin + 20, "x" + castStateCount, Color.WHITE, false, false).y);
        }

        return new Point(maxX, nextY);
    }
}