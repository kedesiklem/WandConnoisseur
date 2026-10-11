package org.example.main;

import org.example.main.Global.DamageType;
import org.example.main.Global.DamageTypeDoublePair;

import java.util.Arrays;

public class DamageComponent{
    private double[] damage = new double[DamageType.values().length];

    public DamageComponent(DamageTypeDoublePair[] damages){
        this.setDamage(damages);
    }

    public DamageComponent(DamageTypeDoublePair damage){
        this.setDamage(damage);
    }

    public DamageComponent(){
        // empty
    }

    public DamageComponent clone(){
        DamageComponent damageComponent = new DamageComponent();

        damageComponent.damage = Arrays.copyOf(this.damage, this.damage.length);

        return damageComponent;
    }

    public boolean equals(DamageComponent damageComponent){
        for(int i=0; i < this.damage.length; i++){
            if(this.damage[i] != damageComponent.damage[i]){
                return false;
            }
        }

        return true;
    }

    public double[] getArray(boolean clone){
        return clone ? Arrays.copyOf(this.damage, this.damage.length) : this.damage;
    }

    public double getDamage(DamageType damageType){
        return this.damage[damageType.ordinal()];
    }

    public double getTotal(){
        double total = 0.0;

        for(double value : this.damage){
            total += value;
        }

        return total;
    }

    public void setDamage(double damage, DamageType damageType){
        this.damage[damageType.ordinal()] = damage;
    }

    public void setDamage(DamageComponent damageComponent){
        this.damage = Arrays.copyOf(damageComponent.damage, damageComponent.damage.length);
    }

    public void setDamage(DamageTypeDoublePair damageType){
        this.damage[damageType.damageType().ordinal()] = damageType.value();
    }

    public void setDamage(DamageTypeDoublePair[] damageTypes){
        for(DamageTypeDoublePair damageType : damageTypes){
            this.damage[damageType.damageType().ordinal()] = damageType.value();
        }
    }

    public void add(double damage, DamageType damageType){
        this.damage[damageType.ordinal()] += damage;
    }

    public void add(DamageComponent damageComponent){
        for(int i=0; i < this.damage.length; i++){
            this.damage[i] += damageComponent.damage[i];
        }
    }

    public boolean zero(){
        for(double value : this.damage){
            if(value != 0.0){
                return false;
            }
        }

        return true;
    }

    public int nbType(){
        int total = 0;

        for(double value : this.damage){
            if(value != 0.0){
                total++;
            }
        }

        return total;
    }

    public String toString(){
        switch(this.nbType()){
            case 0 -> {
                return "0";
            }
            case 1 -> {
                int i = 0;

                for(DamageType damageType : DamageType.values()){
                    if(this.damage[i] != 0){
                        return damageType.toString().toLowerCase() + ": " + this.damage[i];
                    }
                    i++;
                }
            }
            default -> {
                StringBuilder result = new StringBuilder();
                int i = 0;

                for(DamageType damageType : DamageType.values()){
                    if(this.damage[i] != 0){
                        if(!result.isEmpty()){
                            result.append(", ");
                        }
                        result.append(damageType.toString().toLowerCase()).append(": ").append(this.damage[i]);
                    }
                    i++;
                }

                return "[" + result + "]";
            }
        }

        return "";
    }
}