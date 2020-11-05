package com.company;

public class Tribe {
    String name;
    int age;
    int members;
    int reputation;
    double inspiringConfidence;

    public Tribe(String name, int age, int members, int reputation){
        this.name = name;
        this.age = age;
        this.members = members;
        this.reputation = (age*members*reputation);
        inspiringConfidence=reputation/100;
    }

    public Tribe(String name){
        this.name = name;
        this.age = (int) (Math.random()*10);
        this.members =  (int) (Math.random()*100);
        this.reputation = (age*members*reputation);
        inspiringConfidence=reputation/100;
    }
}
