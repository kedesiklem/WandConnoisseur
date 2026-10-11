package org.example.spells;

import org.example.config.EmoteConfig;
import org.example.main.*;
import org.example.main.Global.DamageType;
import org.example.projectiles.Projectile;
import org.example.script.Script;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.invoke.MethodHandles;
import javax.imageio.ImageIO;

public abstract class Spell{
    public enum SpellType {projectile, static_projectile, passif, utility, modifier, material, multicast, other};
    protected final static int recursionLimit = 2;
    protected boolean modded = false;
    protected boolean alwaysCast = false;
    protected String name = "spell_name";
    protected String[] alias = new String[0];
    protected static final String imagePath = "./src/main/java/org/example/image/spell/";
    protected static final String staticImageFile = "_unidentified.png";
    protected String imageFile = staticImageFile;
    protected static final SpellType staticType = SpellType.other;
    protected SpellType type = staticType;
    protected static final BufferedImage[] staticTypeImages = loadTypeImages();
    protected static final BufferedImage[] staticTypeImagesEmote = loadTypeImagesEmote();
    protected static final BufferedImage staticImage = loadImage(staticType, staticImageFile);
    protected BufferedImage image = staticImage;
    protected static final String staticEmote = EmoteConfig.getEmote(MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase());
    protected String emote = staticEmote;
    protected static final String defaultImage = "_unidentified.png";
    protected String description = "this spell does things";
    protected Projectile relatedProjectile = null;
    protected int relatedProjectileCount = 1;
    protected Script[] relatedScripts = new Script[0];
    protected Projectile.TriggerType triggerType = Projectile.TriggerType.none;
    protected int timerLength = 0;
    protected SpawnProbabilities spawnProbabilities = new SpawnProbabilities();
    protected boolean recursive = false;
    protected int price = 0;
    protected int manaCost = 0;
    protected boolean hasCharges = false;
    protected int maxCharges = 0;
    protected int chargesLeft = 0;
    protected boolean neverUnlimited = false;
    protected boolean autoStat = true;
    protected boolean setCastDelay = false;
    protected int castDelay = 0;
    protected boolean setRechargeTime = false;
    protected int rechargeTime = 0;
    protected DamageComponent damageComponent = new DamageComponent();
    protected int lifetime = 0;
    protected int critRate = 0;
    protected int pattern = 0;
    protected double spread = 0;
    protected boolean setRecoil = false;
    protected double recoil = 0.0;
    protected double screenshake = 0.0;
    protected double gravity = 0.0;
    protected boolean setFriendlyFire = false;
    protected boolean friendlyFire = false;
    protected boolean multiplySpeed = true;
    protected double speed = 1.0;
    protected boolean setGoreParticles = false;
    protected int goreParticles = 0;
    protected boolean setBounce = false;
    protected int bounce = 0;
    protected String material = "";
    protected int materialAmount = 0;
    protected String trailMaterial = "";
    protected int trailMaterialAmount = 0;

    public Spell(){
        this.initialization();
        if(this.alias.length == 0){
            this.alias = new String[]{this.getClass().getSimpleName(), this.name};
        }
        for(int i=0; i < this.alias.length; i++){
            this.alias[i] = this.alias[i].trim().toLowerCase();
        }
        this.alias = removeDuplicates(this.alias);
        if(this.hasCharges){
            this.chargesLeft = this.maxCharges;
        }
    }

    public Spell clone(){
        Class<? extends Spell> spellClass = this.getClass();
        Spell newSpell;

        try{
            newSpell = spellClass.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            return null;
        }

        newSpell.modded = this.modded;
        newSpell.alwaysCast = this.alwaysCast;
        newSpell.name = this.name;
        newSpell.alias = new String[this.alias.length];
        System.arraycopy(this.alias, 0, newSpell.alias, 0, newSpell.alias.length);
        newSpell.imageFile = this.imageFile;
        newSpell.image = this.image;
        newSpell.emote = this.emote;
        newSpell.description = this.description;
        newSpell.type = this.type;
        newSpell.relatedProjectile = this.relatedProjectile == null ? null : this.relatedProjectile.clone(); // clone
        newSpell.relatedProjectileCount = this.relatedProjectileCount;
        newSpell.relatedScripts = new Script[this.relatedScripts.length];
        for(int i=0; i < newSpell.relatedScripts.length; i++){
            newSpell.relatedScripts[i] = this.relatedScripts[i].clone();
        }
        newSpell.triggerType = this.triggerType;
        newSpell.timerLength = this.timerLength;
        newSpell.spawnProbabilities = this.spawnProbabilities.clone(); // clone
        newSpell.recursive = this.recursive;
        newSpell.price = this.price;
        newSpell.manaCost = this.manaCost;
        newSpell.hasCharges = this.hasCharges;
        newSpell.maxCharges = this.maxCharges;
        newSpell.chargesLeft = this.chargesLeft;
        newSpell.neverUnlimited = this.neverUnlimited;
        newSpell.autoStat = this.autoStat;
        newSpell.castDelay = this.castDelay;
        newSpell.setRechargeTime = this.setRechargeTime;
        newSpell.rechargeTime = this.rechargeTime;
        newSpell.damageComponent = this.damageComponent.clone(); // clone
        newSpell.lifetime = this.lifetime;
        newSpell.critRate = this.critRate;
        newSpell.pattern = this.pattern;
        newSpell.spread = this.spread;
        newSpell.setRecoil = this.setRecoil;
        newSpell.recoil = this.recoil;
        newSpell.screenshake = this.screenshake;
        newSpell.gravity = this.gravity;
        newSpell.setFriendlyFire = this.setFriendlyFire;
        newSpell.friendlyFire = this.friendlyFire;
        newSpell.multiplySpeed = this.multiplySpeed;
        newSpell.speed = this.speed;
        newSpell.setGoreParticles = this.setGoreParticles;
        newSpell.goreParticles = this.goreParticles;
        newSpell.setBounce = this.setBounce;
        newSpell.bounce = this.bounce;
        newSpell.material = this.material;
        newSpell.materialAmount = this.materialAmount;
        newSpell.trailMaterial = this.trailMaterial;
        newSpell.trailMaterialAmount = this.trailMaterialAmount;

        return newSpell;
    }

    // getters
    public boolean getIsModded(){
        return this.modded;
    }

    public boolean getAlwaysCast(){
        return this.alwaysCast;
    }

    public String getName(){
        return this.name;
    }

    public String[] getAlias(){
        return this.alias;
    }

    public String getShortestAlias(){
        String result = null;

        for(String alias : this.alias){
            if(result == null || alias.length() < result.length()){
                result = alias;
            }
        }

        return result == null ? "" : result;
    }

    public String getAliasString(){
        StringBuilder result = new StringBuilder("[");

        if(this.alias.length > 0){
            result.append("\"").append(this.alias[0]).append("\"");
        }
        for(int i=1; i < this.alias.length; i++){
            result.append(", \"").append(this.alias[i]).append("\"");
        }

        return result + "]";
    }

    public static String getImagePath(){
        return imagePath;
    }

    public String getEmote(){
        return this.emote;
    }

    public SpellType getType(){
        return this.type;
    }

    public Projectile getRelatedProjectile(){
        return this.relatedProjectile;
    }

    public int getRelatedProjectileCount(){
        return this.relatedProjectileCount;
    }

    public Projectile.TriggerType getTriggerType(){
        return this.triggerType;
    }

    public int getTimerLength(){
        return this.timerLength;
    }

    public double[] getSpawnProbability(){
        return this.spawnProbabilities.getSpawnProbability();
    }

    public String getTierString(boolean includeAll){
        return this.spawnProbabilities.toString(includeAll);
    }

    public double getTierTotal(){
        return this.spawnProbabilities.total();
    }

    public int getNbTier(){
        return this.spawnProbabilities.nbTier();
    }

    public boolean getRecursive(){
        return this.recursive;
    }

    public int getPrice(){
        return this.price;
    }

    public boolean getHasCharges(){
        return this.hasCharges;
    }

    public int getMaxCharges(){
        return this.maxCharges;
    }

    public boolean getNeverUnlimited(){
        return this.neverUnlimited;
    }

    public int getChargesLeft(){
        return this.chargesLeft;
    }

    public String getChargeString(){
        String result = "";

        if(this.hasCharges){
            result += this.maxCharges;
            if(this.neverUnlimited){
                result += " (never infinite)";
            }else{
                result += " (can be infinite)";
            }
        }else{
            result = "infinite";
        }

        return result;
    }

    public int getManaCost(){
        return this.manaCost;
    }

    public boolean getSetCastDelay(){
        return this.setCastDelay;
    }

    public int getCastDelay(){
        return this.castDelay;
    }

    public boolean getSetRechargeTime(){
        return this.setRechargeTime;
    }

    public int getRechargeTime(){
        return this.rechargeTime;
    }

    public DamageComponent getDamageComponent(){
        return this.damageComponent;
    }

    public BufferedImage getImage(){
        return this.image;
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

    public double getRecoil(){
        return this.recoil;
    }

    public boolean getSetRecoil(){
        return this.setRecoil;
    }

    public double getScreenshake(){
        return this.screenshake;
    }

    public double getGravity(){
        return this.gravity;
    }

    public boolean getSetFriendlyFire(){
        return this.setFriendlyFire;
    }

    public boolean getFriendlyFire(){
        return this.friendlyFire;
    }

    public boolean getMultiplySpeed(){
        return this.multiplySpeed;
    }

    public double getSpeed(){
        return this.speed;
    }

    public boolean getSetGoreParticles(){
        return this.setGoreParticles;
    }

    public int getGoreParticles(){
        return this.goreParticles;
    }

    public boolean getSetBounce(){
        return this.setBounce;
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

    public String getTrailMaterial(){
        return this.trailMaterial;
    }

    public int getTrailMaterialAmount(){
        return this.trailMaterialAmount;
    }

    public int getLastIteration(){
        return 0;
    }

    public String getInfoString(boolean importantOnly){
        StringBuilder result = new StringBuilder();
        StringBuilder innerString = new StringBuilder();

        result.append("Name: \"").append(this.name).append("\"");
        result.append("\nDescription: \"").append(this.description).append("\"");
        result.append("\nType: ").append(this.type);
        result.append("\nCharges: ").append(this.getChargeString());
        result.append("\nRecursive: ").append(this.recursive ? "Yes" : "No");
        result.append("\nMana cost: ").append(this.manaCost);
        result.append("\nSpawn probabilities: ").append(this.getTierString(false));
        result.append("\nPrice: ").append(this.price);
        if(this.relatedProjectile == null){
            if(this.triggerType != Projectile.TriggerType.none){result.append("\nTrigger type: ").append(this.triggerType);}
            if(this.triggerType == Projectile.TriggerType.timer){result.append("\nTimer lifetime: ").append(Global.delayFormat(this.timerLength));}
        }

        // cast state
        if(this.setCastDelay || this.castDelay != 0){innerString.append("\nCast delay: ").append(this.setCastDelay ? "=" : "").append(Global.delayFormat(this.castDelay));}
        if(this.setRechargeTime || this.rechargeTime != 0){innerString.append("\nRecharge time: ").append(this.setRechargeTime ? "=" : "").append(Global.delayFormat(this.rechargeTime));}
        for(DamageType damageType : DamageType.values()){
            if(this.damageComponent.getDamage(damageType) != 0.0){
                innerString.append("\n").append(damageType.getDisplayName()).append(" damage: ").append(this.damageComponent.getDamage(damageType));
            }
        }
        if(this.lifetime != 0){innerString.append("\nLifetime: ").append(this.lifetime).append("f");}
        if(this.critRate != 0){innerString.append("\nCrit rate: ").append(this.critRate).append("%");}
        if(this.pattern != 0){innerString.append("\nPattern: ").append(this.pattern).append("°");}
        if(this.spread != 0){innerString.append("\nSpread: ").append(this.spread).append("°");}
        if(this.setRecoil || this.recoil != 0){innerString.append("\nRecoil: ").append(this.setRecoil ? "=" : "").append(this.recoil);}
        if(this.screenshake != 0){innerString.append("\nScreen shake: ").append(this.screenshake);}
        if(this.gravity != 0){innerString.append("\nGravity: ").append(this.gravity);}
        if(this.setFriendlyFire){innerString.append("\nFriendly fire: ").append(this.friendlyFire ? "true" : "false");}
        if(this.multiplySpeed ? this.speed != 1.0 : this.speed != 0.0){innerString.append("\nSpeed: ").append(this.multiplySpeed ? "x" : "").append(this.speed);}
        if(this.setGoreParticles || this.goreParticles != 0){innerString.append("\nGore particles: ").append(this.setGoreParticles ? "=" : "").append(this.goreParticles);}
        if(this.setBounce || this.bounce != 0){innerString.append("\nBounce: ").append(this.setBounce ? "=" : "").append(this.bounce);}
        if(!this.material.equals("")){innerString.append("\nMaterial: \"").append(this.material).append("\"");}
        if(this.materialAmount != 0){innerString.append("\nMaterial amount: ").append(this.materialAmount);}
        if(!this.trailMaterial.equals("")){innerString.append("\nTrail material: \"").append(this.trailMaterial).append("\"");}
        if(this.trailMaterialAmount != 0){innerString.append("\nTrail material amount: ").append(this.trailMaterialAmount);}

        if(!innerString.isEmpty()){
            result.append("\n\n# Cast state").append(innerString);
            innerString.setLength(0);
        }

        // related projectile
        if(this.relatedProjectile != null){
            innerString.append(this.relatedProjectile.toString(importantOnly));
            if(!innerString.isEmpty()){
                result.append("\n\n# Related projectile\n").append(innerString);
                innerString.setLength(0);
            }
        }

        return result.toString();
    }

    // setters
    public void setName(String name){
        this.name = name;
    }

    public void setAlwaysCast(boolean alwaysCast){
        this.alwaysCast = alwaysCast;
    }

    public void setCharges(int charges){
        if(this.alwaysCast){
            return;
        }
        this.chargesLeft = Math.max(Math.min(charges, this.maxCharges), 0);
    }

    public void refillCharges(){
        if(this.alwaysCast){
            return;
        }
        if(this.hasCharges){
            this.chargesLeft = this.maxCharges;
        }
    }

    public void removeCharge(){
        if(this.alwaysCast){
            return;
        }
        if(this.hasCharges && this.chargesLeft > 0){
            this.chargesLeft--;
        }
    }

    public void removeAllCharges(){
        if(this.alwaysCast){
            return;
        }
        if(this.hasCharges){
            this.chargesLeft = 0;
        }
    }

    public void makeInfinite(){
        if(this.hasCharges && !this.neverUnlimited){
            this.refillCharges();
            this.hasCharges = false;
        }
    }

    private static BufferedImage[] loadTypeImages(){
        BufferedImage[] result = new BufferedImage[SpellType.values().length];
        int id = 0;

        for(SpellType spellType : SpellType.values()){
            result[id] = Global.loadImage(imagePath + getTypeImage(spellType));
            id++;
        }

        return result;
    }

    private static BufferedImage[] loadTypeImagesEmote(){
        BufferedImage[] result = new BufferedImage[SpellType.values().length];
        int id = 0;

        for(SpellType spellType : SpellType.values()){
            result[id] = Global.loadImage(imagePath + getTypeImageEmote(spellType));
            id++;
        }

        return result;
    }

    protected static BufferedImage loadImage(SpellType spellType, String imageFile){
        BufferedImage image = Global.loadImage(imagePath + imageFile);

        if(image == null){
            image = Global.loadImage(imagePath + defaultImage);
            if(image == null){
                System.out.println("no image found for " + imageFile);
                return null;
            }else{
                System.out.println("using default image found for " + imageFile);
            }
        }

        BufferedImage imageType = staticTypeImages[spellType.ordinal()];
        if(imageType == null){
            System.out.println("no type found for " + imageFile);
            return image;
        }

        return combineImages(imageType, image, null);
    }

    public void createEmote(){
        try{
            BufferedImage typeImage = staticTypeImagesEmote[this.type.ordinal()];
            if(typeImage == null){
                System.out.println("Error trying to create emote for spell \"" + this.name + "\" : type image is null.");
                return;
            }

            String name = this.getClass().getSimpleName().toLowerCase();
            if(name.equals("")){
                name = "spell";
            }
            name = Global.truncate(name, 32);
            ImageIO.write(Global.scaleImage(combineImages(typeImage, Global.loadImage(imagePath + this.imageFile), new Color(54, 43, 39)), 8), "png", new File(Global.getPathOutput() + name + ".png"));;
        }catch(Exception e){
            System.out.println("Error trying to create emote for spell \"" + this.name + "\" : " + e.getMessage());
        }
    }

    private static String getTypeImage(SpellType spellType){
        return switch(spellType){
            case projectile -> "item_bg_projectile.png";
            case static_projectile -> "item_bg_static_projectile.png";
            case passif -> "item_bg_passive.png";
            case utility -> "item_bg_utility.png";
            case modifier -> "item_bg_modifier.png";
            case material -> "item_bg_material.png";
            case multicast -> "item_bg_draw_many.png";
            default -> "item_bg_other.png";
        };
    }

    private String getTypeImage(){
        return getTypeImage(this.type);
    }

    private static String getTypeImageEmote(SpellType spellType){
        return switch(spellType){
            case projectile -> "item_bg_projectile_emote.png";
            case static_projectile -> "item_bg_static_projectile_emote.png";
            case passif -> "item_bg_passive_emote.png";
            case utility -> "item_bg_utility_emote.png";
            case modifier -> "item_bg_modifier_emote.png";
            case material -> "item_bg_material_emote.png";
            case multicast -> "item_bg_draw_many_emote.png";
            default -> "item_bg_other_emote.png";
        };
    }

    private String getTypeImageEmote(){
        return getTypeImageEmote(this.type);
    }

    /*public boolean imageInJLabel(){
        if(this.imageLabel == null){
            // TODO à refaire
            this.chargeLabel = new JLabel("0"){
                @Override
                protected void paintComponent(Graphics g){
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
                    );
                    g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF
                    );
                    g2d.setRenderingHint(
                        RenderingHints.KEY_FRACTIONALMETRICS,
                        RenderingHints.VALUE_FRACTIONALMETRICS_OFF
                    );
                    super.paintComponent(g);
                }
            };
            this.chargeLabel.setForeground(new Color(255, 255, 255));
            this.chargeLabel.setHorizontalAlignment(JLabel.LEFT);
            this.chargeLabel.setVerticalAlignment(JLabel.TOP);
            this.chargeLabel.setOpaque(false);
            //this.chargeLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            this.chargeLabel.setFont(Global.getPixelFont().deriveFont(Font.PLAIN, this.imageScaleFactor*7));

            this.updateChargeLabel();

            this.imageLabel = new JLabel();
            this.imageLabel.add(this.chargeLabel);
        }
        if(this.image != null){
            this.imageLabel.setIcon(new ImageIcon(scaleImage(this.image, this.imageScaleFactor)));
            this.imageLabel.setSize(this.image.getWidth()*this.imageScaleFactor, this.image.getHeight()*this.imageScaleFactor);
            this.chargeLabel.setSize(Global.getBaseImageSize()*this.imageScaleFactor, Global.getBaseImageSize()*this.imageScaleFactor);
            this.chargeLabel.setLocation((this.imageLabel.getWidth() - this.chargeLabel.getWidth())/2, (this.imageLabel.getHeight() - this.chargeLabel.getHeight())/2);
            return true;
        }else{
            return false;
        }
    }*/

    private static BufferedImage combineImages(BufferedImage img1, BufferedImage img2, Color backgroundColor){
        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combinedImage.createGraphics();

        if(backgroundColor != null){
            g2d.setColor(backgroundColor);
            g2d.fillRect(1, 1, width - 2, height - 2);
        }

        g2d.setComposite(AlphaComposite.SrcOver);
        int x1 = (width - img1.getWidth())/2;
        int y1 = (height - img1.getHeight())/2;
        int x2 = (width - img2.getWidth())/2;
        int y2 = (height - img2.getHeight())/2;

        g2d.drawImage(img1, x1, y1, null);
        g2d.drawImage(img2, x2, y2, null);
        g2d.dispose();

        return combinedImage;
    }

    public void copy(CardPool cardPool, CastState castState, Spell spell, int recursionLevel){
        copy(cardPool, castState, spell, recursionLevel, 1, false);
    }

    public void copyDrawDisabled(CardPool cardPool, CastState castState, Spell spell, int recursionLevel){
        copy(cardPool, castState, spell, recursionLevel, 1, true);
    }

    public void copy(CardPool cardPool, CastState castState, Spell spell, int recursionLevel, int iterationLevel, boolean disableDraw){
        int nextRecursionLevel = recursionLevel;

        if(spell == null){
            return;
        }

        if(spell.recursive){
            nextRecursionLevel = recursionLevel + 1;
        }

        if(nextRecursionLevel <= recursionLimit){
            cardPool.addCallStackCopy(spell, false);
            if(disableDraw){
                cardPool.disableDraw();
            }
            spell.doAction(cardPool, castState, nextRecursionLevel, iterationLevel);
            if(disableDraw){
                cardPool.enableDraw();
            }
            cardPool.removeCallStack();
        }else{
            cardPool.addCallStackCopy(spell, true);
        }
    }

    public void applyStats(CardPool cardPool, CastState castState){
        // cardPool
        if(this.setRechargeTime){
            cardPool.setRechargeTime(this.rechargeTime);
        }else if(this.rechargeTime != 0){
            cardPool.addRechargeTime(this.rechargeTime);
        }
        if(this.setRecoil){
            cardPool.setRecoil(this.recoil);
        }else if(this.recoil != 0){
            cardPool.addRecoil(this.recoil);
        }

        // castState
        if(this.setCastDelay){
            castState.setCastDelay(this.castDelay);
        }else if(this.castDelay != 0){
            castState.addCastDelay(this.castDelay);
        }
        if(!this.damageComponent.zero()){
            castState.addDamageComponent(this.damageComponent);
        }
        if(this.lifetime != 0){
            castState.addLifetime(this.lifetime);
        }
        if(this.critRate != 0){
            castState.addCritRate(this.critRate);
        }
        if(this.pattern != 0){
            castState.addPattern(this.pattern);
        }
        if(this.spread != 0){
            castState.addSpread(this.spread);
        }
        if(this.screenshake != 0){
            castState.addScreenshake(this.screenshake);
        }
        if(this.gravity != 0){
            castState.addGravity(this.gravity);
        }
        if(this.setFriendlyFire){
            castState.setFriendlyFire(this.friendlyFire);
        }
        if(this.multiplySpeed){
            if(this.speed != 1.0){
                castState.multiplySpeed(this.speed, 0.0, 20.0);
            }
        }else if(this.speed != 0.0){
            castState.addSpeed(this.speed, 0.0, 20.0);
        }
        if(this.setGoreParticles){
            castState.setGoreParticles(this.goreParticles);
        }else if(this.goreParticles != 0){
            castState.addGoreParticles(this.goreParticles);
        }
        if(this.setBounce){
            castState.setBounce(this.bounce);
        }else if(this.bounce != 0){
            castState.addBounce(this.bounce);
        }
        if(!this.material.equals("")){
            castState.setMaterial(this.material);
        }
        if(this.materialAmount != 0){
            castState.addMaterialAmount(this.materialAmount);
        }
        if(!this.trailMaterial.equals("")){
            castState.addTrailMaterial(this.trailMaterial);
        }
        if(this.trailMaterialAmount != 0){
            castState.addTrailMaterialAmount(this.trailMaterialAmount);
        }
        for(Script script : this.relatedScripts){
            castState.addScript(script);
        }
    }

    public void doAction(CardPool cardPool, CastState castState){
        this.doAction(cardPool, castState, 0, 1);
    }

    public void doAction(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel){
        if(this.autoStat){
            this.applyStats(cardPool, castState);
        }
        this.action(cardPool, castState, recursionLevel, iterationLevel);
    }

    protected void action(CardPool cardPool, CastState castState){
        this.action(cardPool, castState, 0, 1);
    }

    public boolean containsAlias(String alias){
        for(String s : this.alias){
            if(s.equals(alias)){
                return true;
            }
        }
        return false;
    }

    private String[] removeDuplicates(String[] wordList){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordList.length; i++){
            boolean found = false;
            for(int j=i + 1; j < wordList.length; j++){
                if(wordList[j].equals(wordList[i])){
                    found = true;
                    break;
                }
            }
            if(!found){
                if (sb.length() > 0){
                    sb.append(';');
                }
                sb.append(wordList[i]);
            }
        }
        return sb.toString().split(";");
    }

    protected abstract void initialization();
    protected abstract void action(CardPool cardPool, CastState castState, int recursionLevel, int iterationLevel);
}