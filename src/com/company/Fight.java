package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Fight {

    int preparation;
    int rounds;
    int fight;
    Fighter[] fighters;
    Weapon[] weapons;
    Fighter winner;
    boolean fightOver;

    private int dice(int range){
        double cmp = 1 / range;
        double rnd= Math.random();
        int cnt = 0;
        while (cmp<rnd){
            cmp += cmp;
            cnt++;
        }
        return cnt;
    }

    private boolean flip(double possibilityInPercent){
        boolean coin = false;
        double rnd = Math.random();
        if (rnd <= possibilityInPercent/100) coin = true;
        return coin;
    }

    public Fight(Fighter[] fighters, Weapon[] weapons, int preparation, int rounds){

        Scanner input = new Scanner(System.in);
        fightOver = false;

        System.out.println("\n- - - WELCOME TO THE FINAL BATTLEGROUND - - -\n");

        // choose weapons
        for (int i= 0; i<fighters.length; i++){
            System.out.println(fighters[i].name + ", choose your Weapon and make your tribe " + fighters[i].tribe.name + " proud! These are available:");
            for (int j=0; j<weapons.length; j++){
                System.out.println(j+1 + ":\t" + weapons[j].getSpecs());
            }
            fighters[i].weapon = weapons[input.nextInt()-1];
        }

        // prepare for fight
        for (int i= 0; i<fighters.length; i++){
            System.out.println("\n\nAlright " + fighters[i].name + ", choose your preparation wisely for the next fight. You can choose to eat, sleep, train with your weapon or go fishing.");
            for (int j=0; j<preparation; j++){
                System.out.println("\n -\tRound " + (j+1) + " of " + preparation);
                System.out.print("(e)at\t(s)leep\t(t)rain\tgo (f)ishing: ");
                String in = input.next();
                switch (in){
                    case "e":
                        System.out.println("You had a proper meal. Yummy!");
                        fighters[i].weight++;
                        fighters[i].force++;
                        fighters[i].endurance++;
                        fighters[i].agility--;
                        break;
                    case "s":
                        System.out.println("You had a long nice nap. Now rise and shine...");
                        fighters[i].confidence++;
                        fighters[i].agility++;
                        fighters[i].endurance++;
                        break;
                    case "t":
                        System.out.println("Well done! One day you will master this weapon entirely...");
                        fighters[i].weight--;
                        fighters[i].defense++;
                        fighters[i].agility++;
                        fighters[i].force++;
                        fighters[i].confidence++;
                        break;
                    case "f":
                        System.out.print("You relaxed at the lake");
                        if (fighters[i].weapon.needsAmmo){
                            String[] prep_projectile = {" and found a projectile for your weapon by the way. How nice!", " and enjoyed your time very much.", " and had an interesting chat with another fishermen."};
                            int prep_projectile_result = dice(prep_projectile.length);
                            if (prep_projectile_result == 1){
                                fighters[i].confidence++;
                                fighters[i].endurance++;
                                fighters[i].weapon.ammo++;
                            } else {
                                fighters[i].confidence++;
                                fighters[i].endurance++;
                                if (flip(70)) {
                                    System.out.println(" And the fish you cought is so healthy!");
                                    fighters[i].health+=10;
                                }
                            }
                        } else {
                            System.out.print(", how beatiful the landscape is over there.");
                            fighters[i].confidence++;
                            fighters[i].endurance++;
                            if (flip(70)) {
                                System.out.println(" And the fish you cought is so healthy!");
                                fighters[i].health+=10;
                            }
                        }
                        System.out.println();
                        break;
                    default:
                        String[] prep_def = {"What are you doing there with your hand in you trousers?", "Ok, you chose to learn Java instead. A very... personal choice, I'd say.", "Let's say, you practiced breathing. Fine."};
                        System.out.println(prep_def[dice(prep_def.length)]);
                }
            }
            System.out.println("Your current status is:\t\t" + fighters[i].getStats());
        }
        System.out.print("\n\n\n");

        System.out.print("NOW, PREPARE FOR BATTLE, " );
        for (int i = 0; i < fighters.length; i++) {
            System.out.print(fighters[i].name.toUpperCase() + ", ");
        }
        System.out.print("AND WHOEVER ELSE IS WATCHING...\n\nI want a save, clean fight. You can deliver a punch, use your weapon, deflect an incomming punch or shot, or try to distract your opponent by a prank.\n\n");

        // start fighting
        fight=1;
        while (fight<fighters.length){                      // every winner of a fight fights the next fighter
            Fighter fighter1 = fighters[fight];             // set fighters and their opponents
            Fighter fighter2;
            if (fight==1){
                fighter1.opponent = fighters[fight-1];
            } else {
                fighter1.opponent = winner;
            }
            fighter2 = fighter1.opponent;
            fighter2.opponent = fighter1;
            Fighter[] opponents = {fighter1, fighter2};

            System.out.print(fight + ". fight is " + fighter1.name + " vs. " + fighter2.name + "\n\nNOOOOOOW GET READYYYY TOOO RUMMMMMMBLEEEEEEE!!!\n\n");      // get ready to rumble
            for (int j=0; j<rounds; j++) {
                for (int i = 0; i < opponents.length; i++) {
                    String in = "";
                    System.out.println("\n" + opponents[i].name + ", your turn for Round " + (j + 1) + " of " + rounds + " of the Fight."); //\tYour current status is:\t" + opponent[i].getStats());
                    System.out.print("Choose to (a)muse, (s)trike, (d)eflect or (f)ire your weapon: ");
                    boolean answerNotGiven = true;
                    while(answerNotGiven){
                        in = input.next();
                        if (in.equals("a")||in.equals("s")||in.equals("d")||in.equals("f")){
                            answerNotGiven = false;
                        } else {
                            System.out.println("What do you want to do again?!");
                        }
                    }
                    opponents[i].action = in;

                    // comment on chosen action
                    switch (in) {
                        case "a":
                            System.out.println(opponents[i].name + " chooses to make the opponent laugh.");
                            break;
                        case "s":
                            System.out.println(opponents[i].name + " chooses to punch the opponent.");
                            break;
                        case "d":
                            System.out.println(opponents[i].name + " chooses to deflect an incoming attack.");
                            break;
                        case "f":
                            System.out.println(opponents[i].name + " chooses to shoot the weapon at the opponent.");
                            break;
                        default:
                            System.out.println("---");  // shouldn't happen
                    }
                }
                /*                      cases: Att./Def.
                   | a | s | d | f      1-4: both do the same   (p vs p & f vs f = Attack vs Attack)
                ---+---+---+---+---     5:  strike vs amuse
                 a | 1 | 5 |10 | 9      6:  strike vs deflect
                 s | 5 | 2 | 6 | 7      7:  strike vs fire      (A vs A)
                 d |10 | 6 | 3 | 8      8:  fire vs deflect
                 f | 9 | 7 | 8 | 4      9:  fire vs amuse
                                       10:  amuse vs deflect
                 */

                // both do the same
                if (fighter1.action.equals(fighter2.action)){
                    if (fighter1.action.equals("a")) {
                        // case 1: both amuse
                        System.out.println("Both fighters try to make the other laugh ver hard.");
                        // by... dice
                        //fighter1.name + " slips on a banana, a guy with a MAGA-hat threw at the fighting-ground. What's wrong with these people?!"
                    }
                    if (fighter1.action.equals("s")) {
                        // case 2: both punch   (A vs A)
                        System.out.println("Both fighters punch each other... The crowd roars excessively.");
                        //fighter 1 p 2
                        fighter2.health -= (fighter1.weight/100)*(fighter1.punch/fighter2.defense);
                        fighter2.confidence--;
                        fighter1.confidence++;
                        //fighter 2 p 1
                        fighter1.health -= (fighter2.weight/100)*(fighter2.punch/fighter1.defense);
                        fighter1.confidence--;
                        fighter2.confidence++;
                    }
                    if (fighter1.action.equals("d")) {
                        // case 3: both deflect
                        System.out.println("Both fighters cover themselves while dancing around each other fearfully. The crowd starts booing...");
                    }
                    if (fighter1.action.equals("f")) {
                        // case 4: both fire weapon (A vs A)
                        System.out.println("Both fighters fire their weapon at each other... The whole crowd holds it's breath.");
                        // fighter 1 shoots 2
                        fighter2.health -= (fighter1.confidence/10)*fighter1.weapon.damage;
                        fighter1.health -= (fighter2.confidence/10)*fighter2.weapon.damage;
                    }
                    System.out.println(fighter1.getStats() + "\n" + fighter2.getStats());
                }


                // set roles for AvsD-rounds :
                if (fighter1.action.equals("s")){
                    if (fighter2.action.equals("a")) {
                        attacker = fighter1;
                        defender = fighter2;
                    }
                    if (fighter2.action.equals("d")){
                        attacker = fighter1;
                        defender = fighter2;
                    }
                }
                if (fighter2.action.equals("s")){
                    if (fighter1.action.equals("a")) {
                        attacker = fighter2;
                        defender = fighter1;
                    }
                    if (fighter1.action.equals("d")){
                        attacker = fighter2;
                        defender = fighter1;
                    }
                }

                // attack vs defense
                /*
                // attacker punches
                if (attacker.action.equals("p")){
                    if (defender.action.equals("d")){
                        // case 4
                        System.out.println("p vs d");
                        // ...
                    } else if (defender.action.equals("a")){
                        // case 5
                        System.out.println("p vs a");
                        // ...
                    }
                // attacker amuses
                } else if (attacker.action.equals("a") && defender.action.equals("d")){
                    // case 6
                    System.out.println(attacker.name + " makes " + defender.name + " laugh so hard, that he has to duck and cover his eyes...");
                    //...
                }
                */
            }
            System.out.println(opponents[i].name + " current status is:\t\t" + fighters[i].getStats());

            // evaluation
            if (!fighter2.isAlive) winner = fighter1;
            else if (!fighter1.isAlive) winner = fighter2;
            else {
                if (fighter1.health > fighter2.health) winner = fighter1;
                if (fighter1.health < fighter2.health) winner = fighter2;
                if (fighter1.health == fighter2.health){
                    System.out.println("The fight was draw. We have to repeat that brannigan immediately.");
                    fight--;
                }
            }
            if (fighter1.health != fighter2.health){
                if (fight+1 < fighters.length) System.out.println("And the winner is... " + winner + "!\nAlright, get yourself together for the next fight...\n");
            }
            fight++;
        }
        System.out.println("AND THE FINAL WINNER IS... " + winner.name.toUpperCase() + "!!!\n\n\t\tCongratulations...\n\nSee you all soon... good night, good fight!");
    }
}