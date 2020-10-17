package com.company;

public class Fighter {
    // fix values:
    String name;
    Tribe tribe;
    Weapon weapon;
    Fighter opponent;
    String action;
    double weight;  // ~50-200 kg
    // variable values:
    double force;   // 0-10
    double agility; // 0-10
    double confidence;  // 0-10
    double endurance;   // 0-10
    // calculated:
    double punch;
    double defense;
    double health;
    boolean isAlive;

    public Fighter() {}

    public Fighter(String name, String tribe, double weight, double agility, double force, double confidence, double endurance){
        this.name = name;
        this.tribe = new Tribe(tribe);

        this.weight = weight;
        this.agility = agility;
        this.force = force;
        this.confidence = confidence;
        this.endurance = endurance;

        this.punch = 100;
        this.defense= 100;
        this.health = 100;
        this.isAlive = true;
    }

    public String getStats(){
        return "WEIGHT: " + weight + "\tDEFENSE: " + defense + "\tAGILITY: " + agility + "\tFORCE: " + force + "\tCONFIDENCE: " + confidence + "\tENDURANCE: " + endurance;
    }
}