package org.example.script;

import org.example.config.EmoteConfig;
import org.example.main.Global;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.invoke.MethodHandles;
import javax.imageio.ImageIO;

public abstract class Script{
    protected boolean modded = false;
    protected String name = "script_name";
    protected static final String imagePath = "./src/main/java/org/example/image/spell/";
    protected static final String staticImageFile = "_unidentified.png";
    protected static final BufferedImage staticImage = Global.loadImage(imagePath + staticImageFile);
    protected BufferedImage image = staticImage;
    protected static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    protected String emote = staticEmote;

    public Script(){
        this.initialization();
    }

    public String getName(){
        return this.name;
    }

    public Script clone(){
        Class<? extends Script> scriptClass = this.getClass();
        Script newScript;

        try{
            newScript = scriptClass.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            return null;
        }

        newScript.image = this.image;
        newScript.name = this.name;
        newScript.emote = this.emote;

        return newScript;
    }

    // getters
    public boolean getIsModded(){
        return this.modded;
    }

    public String getEmote(){
        return this.emote;
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public void createEmote(){
        try{
            String name = this.getClass().getSimpleName().toLowerCase();
            if(name.equals("")){
                name = "script";
            }
            name = Global.truncate(name, 32);
            ImageIO.write(Global.scaleImage(this.getImage(), 8),"png", new File(Global.getPathOutput() + name + ".png"));
        }catch(Exception e){
            System.out.println("Error trying to create emote for script \"" + this.name + "\" : " + e.getMessage());
        }
    }

    protected abstract void initialization();
}