package com.company;

import java.util.Scanner;

public class Fight {

    static int preparation;
    static int rounds;
    static Fighter[] fighters;
    static Weapon[] weapons;
    static Fighter winner;
    static boolean fightOver;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Fighter f1 = new Fighter("Peter", "Polizei", 155.7, 30, 20, 60, 80, 40);
        Fighter f2 = new Fighter("Alex", "Antifa", 76.3, 77, 90, 40, 40, 88);

        Weapon knife = new Weapon("Knife", 0.4, 1, 4, false, 0);
        Weapon ace = new Weapon("Ace  ", 4.7, 2, 6, false, 0);
        Weapon boe = new Weapon("Boe  ", 1.8, 3, 5, true, 3);
        weapons = new Weapon[]{knife, ace, boe};

        preparation = 10;
        rounds = 10;
        fighters = new Fighter[]{f1, f2};
        fightOver = false;

        System.out.println("\n- - - WELCOME TO YOUR FINAL BATTLEGROUND - - -\n");
        for (int i= 0; i<fighters.length; i++){
            System.out.println(fighters[i].name + ", choose your Weapon and make your tribe " + fighters[i].tribe.name + " proud! These are available:");
            for (int j=0; j<weapons.length; j++){
                System.out.println(j+1 + ":\t" + weapons[j].getSpecs());
            }
            fighters[i].weapon = weapons[input.nextInt()-1];
        }

        for (int i= 0; i<fighters.length; i++){
            System.out.println("\n\nAlright " + fighters[i].name + ", choose your preparation wisely for the next fight. You can choose to eat, sleep, train with your weapon or go fishing.");
            for (int j=0; j<preparation; j++){
                System.out.println("\n -\tRound " + (j+1) + " of " + preparation + "\t-\tYour current status is:\t" + fighters[i].getStats());
                System.out.print("(e)at\t(s)leep\t(t)rain\tgo (f)ishing: ");
                String in = input.next();
                switch (in){
                    case "e":
                        fighters[i].weight++;
                        fighters[i].force++;
                        fighters[i].endurance++;
                        fighters[i].agility--;
                        System.out.println("You had a proper meal. Yummy!");
                        break;
                    case "s":
                        fighters[i].confidence++;
                        fighters[i].agility++;
                        fighters[i].endurance++;
                        System.out.println("You had a long nice nap. Now rise and shine...");
                        break;
                    case "t":
                        fighters[i].weight--;
                        fighters[i].defense++;
                        fighters[i].agility++;
                        fighters[i].force++;
                        fighters[i].confidence++;
                        System.out.println("Well done! One day you will master this weapon entirely...");
                        break;
                    case "f":
                        fighters[i].endurance++;
                        fighters[i].confidence++;
                        System.out.print("You relaxed at the lake");
                        if (fighters[i].weapon.needsAmmo){
                            fighters[i].weapon.ammo++;
                            System.out.print(" and found a projectile for your weapon by the way. How nice!");
                        } else {
                            fighters[i].force++;
                            fighters[i].agility++;
                            System.out.print(", how beatiful the landscape is over there. And the fish is so healthy!");
                        }
                        System.out.println();
                        break;
                    default:
                        System.out.println("Ok, you chose to learn Java instead. A very... personal choice, I'd say.");
                }
            }
        }
        System.out.println("\n\n\n");
        System.out.print("NOW, PREPARE FOR BATTLE, " );
        for (int i = 0; i < fighters.length; i++) System.out.print(fighters[i].name + ", ");
        System.out.println(" AND WHOEVER ELSE IS WATCHING...\n");
        for (int j=0; j<rounds; j++) {
            for (int i = 0; i < fighters.length; i++) {
                System.out.println(fighters[i].name + ", your turn for Round " + (j+1) + " of the Fight:");
            }
        }
    }
}