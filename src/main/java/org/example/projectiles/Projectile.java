package org.example.projectiles;

import org.example.config.EmoteConfig;
import org.example.main.CastState;
import org.example.main.Global;
import org.example.main.ProjectileComponent;
import org.example.main.VelocityComponent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.invoke.MethodHandles;
import javax.imageio.ImageIO;

public abstract class Projectile{
    public enum TriggerType {none, trigger, timer, expiration};
    protected TriggerType triggerType = TriggerType.none;
    protected int timer = 0;
    protected CastState triggerCastState = null;
    protected static final String imagePath = "./src/main/java/org/example/image/spell/";
    protected static final String staticImageFile = "_unidentified.png";
    protected static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);
    protected BufferedImage image = staticImage;
    protected boolean modded = false;
    protected String name = "projectile_name";
    protected static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    protected String emote = staticEmote;
    protected VelocityComponent velocityComponent = null;
    protected ProjectileComponent projectileComponent = null;

    public Projectile clone(){
        Class<? extends Projectile> projectileClass = this.getClass();
        Projectile newProjectile;

        try{
            newProjectile = projectileClass.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            return null;
        }

        newProjectile.triggerType = this.triggerType;
        newProjectile.timer = this.timer;
        if(this.triggerCastState != null){
            newProjectile.triggerCastState = this.triggerCastState.clone(); // clone
        }
        newProjectile.image = this.image;
        newProjectile.name = this.name;
        newProjectile.emote = this.emote;
        newProjectile.velocityComponent = this.velocityComponent == null ? null : this.velocityComponent.clone();
        newProjectile.projectileComponent = this.projectileComponent == null ? null : this.projectileComponent.clone();

        return newProjectile;
    }

    // getters
    public boolean getIsModded(){
        return this.modded;
    }

    public TriggerType getTriggerType(){
        return this.triggerType;
    }

    public int getTimer(){
        return this.timer;
    }

    public CastState getTriggerCastState(){
        return this.triggerCastState;
    }

    public String getName(){
        return this.name;
    }

    public String getEmote(){
        return this.emote;
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public VelocityComponent getVelocityComponent(){
        return this.velocityComponent;
    }

    public ProjectileComponent getProjectileComponent(){
        return this.projectileComponent;
    }

    public boolean addTrigger(TriggerType triggerType, int timer, CastState castState){
        if(triggerType == TriggerType.none){
            this.triggerCastState = null;
        }else{
            if(castState == null){
                return false;
            }
            this.triggerCastState = castState;
        }
        this.triggerType = triggerType;
        if(triggerType == TriggerType.timer){
            this.timer = timer;
        }else{
            this.timer = 0;
        }

        return true;
    }

    public boolean addTrigger(TriggerType triggerType, CastState castState){
        return addTrigger(triggerType, 0, castState);
    }

    public void createEmote(){
        try{
            String name = this.getClass().getSimpleName().toLowerCase();
            if(name.equals("")){
                name = "projectile";
            }
            name = Global.truncate(name, 32);
            ImageIO.write(Global.scaleImage(this.getImage(), 8),"png", new File(Global.getPathOutput() + name + ".png"));
        }catch(Exception e){
            System.out.println("Error trying to create emote for spell \"" + this.name + "\" : " + e.getMessage());
        }
    }

    public String toString(boolean importantOnly){
        StringBuilder result = new StringBuilder();

        if(this.triggerType != TriggerType.none){result.append(result.isEmpty() ? "" : "\n").append("Trigger type: ").append(this.triggerType);}
        if(this.triggerType == TriggerType.timer){result.append(result.isEmpty() ? "" : "\n").append("Timer length: ").append(this.timer);}
        if(!importantOnly && this.velocityComponent != null){
            result.append(result.isEmpty() ? "" : "\n").append(this.velocityComponent.toString());
        }
        if(this.projectileComponent != null){
            result.append(result.isEmpty() ? "" : "\n").append(this.projectileComponent.toString(importantOnly));
        }

        return result.toString();
    }
}