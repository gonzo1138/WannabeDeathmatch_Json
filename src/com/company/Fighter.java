package com.company;

public class Fighter {
    String name;
    Tribe tribe;
    Weapon weapon;
    double weight;  // ~50-200 kg
    double defense; // 0-100
    double agility; // 0-100
    double force;   // 0-100
    double confidence;  // 0-100
    double endurance;   // 0-100
    boolean isUp;

    public Fighter () {}

    public Fighter(String name, String tribe, double weight, double defense, double agility, double force, double confidence, double endurance){
        this.name = name;
        this.tribe = new Tribe(tribe);
        this.weight = weight;
        this.defense = defense;
        this.agility = agility;
        this.force = force;
        this.confidence = confidence;
        this.endurance = endurance;
        this.isUp = true;
    }

    public double recreate(int amount){

    }

    public String getStats(){
        return "WEIGHT: " + weight + "DEFENSE: " + defense + "AGILITY: " + agility + "FORCE: " + force + "CONFIDENCE: " + confidence + "ENDURANCE: " + endurance;
    }
}