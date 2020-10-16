package com.company;

public class Weapon {
    String name;
    double weight;  // 1-10
    int range;  // 1 (short), 2 (wider), 3 (projectile)
    double damage;  // 1-10;
    double inspiringConfidence;
    boolean needsAmmo;
    int ammo;

    public Weapon(String name, double weight, int range, double damage, boolean needsAmmo, int ammo){
        this.name = name;
        this.weight = weight;
        this.range = range;
        this.damage = damage;
        this.needsAmmo = needsAmmo;
        this.ammo = ammo;
        inspiringConfidence = damage/weight;
        if (needsAmmo) inspiringConfidence = (inspiringConfidence * ammo)/ammo;
    }

    public String getSpecs(){
        String weaponClass;
        if (range==1) weaponClass =      "close-combat weapon";
        else if (range==2) weaponClass = "thrusting weapon   ";
        else if (range==3) weaponClass = "projectile weapon  ";
        else weaponClass = "multiuse";
        String ammoStatus = "";
        if (needsAmmo) ammoStatus += "\tAMMO: " + ammo;
        return name + "\t\tWEIGHT: " + weight + "\tTYPE: " + weaponClass + "\tDAMAGE: " + damage + ammoStatus;
    }
}
