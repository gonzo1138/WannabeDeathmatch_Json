package com.company;

import java.util.Scanner;

public class Fight {

    //int preparation;
    //int rounds;
    //Fighter[] fighters;
    //Weapon[] weapons;
    int fight;
    Fighter winner;
    boolean fightOver;

    private int dice(int range){
        double cmp = 1.0 / (double) range;
        double rnd= Math.random();
        int cnt = 1;
        while (cmp<rnd){
            cmp += cmp;
            cnt++;
        }
        return cnt;
    }

    private boolean flip(double possibilityInPercent){
        boolean coin = false;
        double rnd = Math.random();
        if (rnd <= possibilityInPercent/100.0) coin = true;
        return coin;
    }

    private void shoot(Fighter shooter){
        double sidestep = 1;
        if (shooter.opponent.action.equals("d")){
            sidestep = shooter.opponent.agility/shooter.agility;
        }
        if (flip(((shooter.force*shooter.endurance)*shooter.confidence)*sidestep)){  // strike?
            shooter.opponent.health -= (shooter.confidence / 10) * shooter.weapon.damage;
            shooter.confidence++;
            shooter.opponent.confidence--;
        }
    }

    private void punch(Fighter puncher){
        double sidestep = 1;
        if (puncher.opponent.action.equals("d")){
            sidestep = puncher.opponent.agility/puncher.agility;
        }
        if (flip(((puncher.force*puncher.endurance)*puncher.confidence)*sidestep)) {  // strike?
            puncher.opponent.health -= (puncher.weight / 100) * (puncher.punch / puncher.opponent.defense);
            puncher.confidence++;
            puncher.opponent.confidence--;
        }
    }

    public Fight(Fighter[] fighters, Weapon[] weapons, int preparation, int rounds){

        Scanner input = new Scanner(System.in);
        //this.fighters = fighters;
        //this.weapons = weapons;
        //this.preparation = preparation;
        //this.rounds = rounds;
        fightOver = false;

        System.out.println("\n- - - WELCOME TO THE FINAL BATTLEGROUND - - -\n");

        // choose weapons ----------------------------------------------------------------------------------------------
        for (int i= 0; i<fighters.length; i++){
            System.out.println(fighters[i].name + ", choose your Weapon and make your tribe " + fighters[i].tribe.name + " proud! These are available:");
            for (int j=0; j<weapons.length; j++){
                System.out.println(j+1 + ":\t" + weapons[j].getSpecs());
            }
            fighters[i].weapon = weapons[input.nextInt()-1];
        }

        // prepare for fight -------------------------------------------------------------------------------------------
        for (int i= 0; i<fighters.length; i++){
            System.out.println("\n\nAlright " + fighters[i].name + ", choose your preparation wisely for the next fight. You can choose to eat, sleep, train yourself and with your weapon or go fishing.");
            for (int j=0; j<preparation; j++){
                System.out.println("\n -\tRound " + (j+1) + " of " + preparation);
                System.out.print("(e)at\t(s)leep\t(t)rain\tgo (f)ishing: ");
                String in = "";
                boolean answerNotGiven = true;          // input action (validator)
                while (answerNotGiven) {
                    in = input.next();
                    if (in.equals("e") || in.equals("s") || in.equals("t") || in.equals("f")) {
                        answerNotGiven = false;
                    } else {
                        System.out.println("What do you want to do again?!");
                    }
                }
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
                        fighters[i].confidence++;
                        fighters[i].endurance++;
                        if (fighters[i].weapon.needsAmmo){
                            String[] prep_projectile = {" and found a projectile for your weapon by the way. How nice!", " and enjoyed your time very much.", " and had an interesting chat with another fishermen."};
                            int result = dice(prep_projectile.length);
                            System.out.print(prep_projectile[result]);
                            if (result == 1){
                                fighters[i].weapon.ammo++;
                            } else if (flip(70)) {
                                System.out.println(" And the fish you cought is so healthy!");
                                fighters[i].health+=5;
                            }
                        } else {
                            System.out.print(", how beatiful the landscape is over there.");
                            fighters[i].endurance++;
                            if (flip(70)) {
                                System.out.println(" And the fish you cought is so healthy!");
                                fighters[i].health+=5;
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
        System.out.print("AND WHOEVER ELSE IS WATCHING...\n\nI want a save, clean fight. You can deliver a punch, use your weapon, deflect an incomming punch or shot, or try to distract your opponents by a prank.\n\n");
        //todo: or (g)ive up ?

        // start fighting ----------------------------------------------------------------------------------------------
        fight=1;
        Fighter[] opponents = new Fighter[2];
        while (fight<fighters.length){                      // every winner of a fight fights the next fighter
            opponents[0] = fighters[fight];                 // set two fighters and their opponents
            if (fight==1){
                opponents[0].opponent = fighters[fight-1];
            } else {
                opponents[0].opponent = winner;
            }
            opponents[1] = opponents[0].opponent;
            opponents[1].opponent = opponents[0];

            // get ready to rumble
            System.out.print(fight + ". fight is " + opponents[0].name + " vs. " + opponents[1].name + "\n\nNOOOOOOW GET READYYYY TOOO RUMMMMMMBLEEEEEEE!!!\n\n");
            for (int j=0; j<rounds; j++) {                  // iterate rounds of fight
                for (int i = 0; i < opponents.length; i++) {// iterate opponents per round
                    String in = "";
                    System.out.println("\n" + opponents[i].name + ", your turn for Round " + (j + 1) + " of " + rounds + " of the Fight."); //\tYour current status is:\t" + opponents[i].getStats());
                    System.out.print("Choose to (a)muse, (s)trike, (d)eflect or (f)ire your weapon: ");
                    boolean answerNotGiven = true;          // input action (validator)
                    while (answerNotGiven) {
                        in = input.next();
                        if (in.equals("a") || in.equals("s") || in.equals("d") || in.equals("f")) {
                            answerNotGiven = false;
                        } else {
                            System.out.println("What do you want to do again?!");
                        }
                    }
                    opponents[i].action = in;

                    switch (opponents[i].action) {          // comment on chosen action
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
                            System.out.println(opponents[i].name + " chooses to use the weapon at the opponent.");
                            break;
                        default:
                            System.out.println("---");  // shouldn't happen
                    }
                }
                System.out.println();
                /*                      cases: Att./Def.
                   | a | s | d | f      1-4: both do the same   (s vs s & f vs f = Attack vs Attack, d vs d & a vs a = demoralize)
                ---+---+---+---+---     5:  strike vs amuse     (Attack vs Deflect)
                 a | 1 | 5 |10 | 9      6:  strike vs deflect   (Attack vs Deflect)
                 s | 5 | 2 | 6 | 7      7:  strike vs fire      (Attack vs Attack)
                 d |10 | 6 | 3 | 8      8:  fire vs deflect     (Attack vs Deflect)
                 f | 9 | 7 | 8 | 4      9:  fire vs amuse       (Attack vs Deflect)
                                       10:  amuse vs deflect    (Attack vs Deflect)
                 */

                // "both do the same"-scenarios:
                if (opponents[0].action.equals(opponents[1].action)) {
                    if (opponents[0].action.equals("a")) {  // case 1: both amuse
                        System.out.println("Both fighters try to make the other laugh very hard.");
                        // by... dice
                        //opponents[0].name + " slips on a banana, a guy with a MAGA-hat threw at the fighting-ground. What's wrong with these people?!"
                    }
                    if (opponents[0].action.equals("s")) {  // case 2: both punch   (A vs A)
                        System.out.println("Both fighters punch each other... The crowd roars excessively.");
                        if (flip(50)) {    // flip who strikes punch first
                            punch(opponents[0]);
                            punch(opponents[1]);
                        } else {
                            punch(opponents[1]);
                            punch(opponents[0]);
                        }
                    }
                    if (opponents[0].action.equals("d")) {  // case 3: both deflect
                        System.out.println("Both fighters cover themselves while dancing around each other fearfully. The crowd starts booing...");
                    }
                    if (opponents[0].action.equals("f")) {  // case 4: both fire weapon (A vs A)
                        System.out.println("Both fighters fire their weapon at each other... The whole crowd holds it's breath.");
                        if (flip(50)) {    // flip who fires gun first
                            shoot(opponents[0]);
                            shoot(opponents[1]);
                        } else {
                            shoot(opponents[1]);
                            shoot(opponents[0]);
                        }
                    }
                }

                // Attack vs Attack (strike vs fire) - scenario:
                int strike = 0;
                int fire = 0;
                if (opponents[0].action.equals("s") && opponents[1].action.equals("f")) {   // case 5 & 6
                    strike = 0;
                    fire = 1;
                    System.out.println("Both fighters ran towards each other. " + opponents[fire].name + " draws his weapon while " + opponents[strike].name + " strikes out to punch " + opponents[fire].name + "... The athmosphere is cooking!");
                    if (flip(50)) {    // flip who strikes first, gun or fist
                        shoot(opponents[fire]);
                        punch(opponents[strike]);
                    } else {
                        punch(opponents[strike]);
                        shoot(opponents[fire]);
                    }
                }
                if (opponents[0].action.equals("f") && opponents[1].action.equals("s")) {   // or vice versa
                    fire = 0;
                    strike = 1;
                    System.out.println("Both fighters ran towards each other. " + opponents[fire].name + " draws his weapon while " + opponents[strike].name + " strikes out to punch " + opponents[fire].name + "... The athmosphere is cooking!");
                    if (flip(50)) {    // flip who strikes first, gun or fist
                        shoot(opponents[fire]);
                        punch(opponents[strike]);
                    } else {
                        punch(opponents[strike]);
                        shoot(opponents[fire]);
                    }
                }

                // set roles for Attack vs Deflect-scenarios:
                int attacker = 0;
                int defender = 0;
                if (opponents[0].action.equals("s")) {   // case 5 & 6
                    if (opponents[1].action.equals("a") || opponents[1].action.equals("d")) {
                        attacker = 0;
                        defender = 1;
                    }
                }
                if (opponents[1].action.equals("s")) {   // or vice versa
                    if (opponents[0].action.equals("a") || opponents[0].action.equals("d")) {
                        attacker = 1;
                        defender = 0;
                    }
                }
                if (opponents[0].action.equals("f")) {   // case 8 & 9
                    if (opponents[1].action.equals("d") || opponents[1].action.equals("a")) {
                        attacker = 0;
                        defender = 1;
                    }
                }
                if (opponents[1].action.equals("f")) {   // or vice versa
                    if (opponents[0].action.equals("d") || opponents[0].action.equals("a")) {
                        attacker = 1;
                        defender = 0;
                    }
                }
                if (opponents[0].action.equals("a")) {   // case 10
                    if (opponents[1].action.equals("d")) {
                        attacker = 0;
                        defender = 1;
                    }
                }
                if (opponents[1].action.equals("a")) {   // or vice versa
                    if (opponents[0].action.equals("d")) {
                        attacker = 1;
                        defender = 0;
                    }
                }

                // conduct Attack vs Deflect-scenarios:
                if (opponents[attacker].action.equals("s")) {       // cases 5-6
                    if (opponents[defender].action.equals("a")){    // case 5
                        //...
                    }
                    if (opponents[defender].action.equals("d")) {   // case 6
                        //...
                    }
                }
                if (opponents[attacker].action.equals("f")) {       // cases 8-9
                    if (opponents[defender].action.equals("d")) {   // case 8
                        //...
                    }
                    if (opponents[defender].action.equals("a")) {    // case 9
                        //...
                    }
                }
                if (opponents[attacker].action.equals("a") && opponents[defender].action.equals("d")) {   // case 10
                    System.out.println(opponents[attacker].name + " makes " + opponents[defender].name + " laugh so hard, that he has to duck and cover his eyes...");
                    //...
                }


                // end of round ----------------------------------------------------------------------------------------
                System.out.println(opponents[0].name + " current status is:\t\t" + fighters[0].getStats());
                System.out.println(opponents[1].name + " current status is:\t\t" + fighters[1].getStats());
            }

            // after-fight evaluation ----------------------------------------------------------------------------------
            if (!opponents[1].isAlive) winner = opponents[0];
            else if (!opponents[0].isAlive) winner = opponents[1];
            else {
                if (opponents[0].health > opponents[1].health) winner = opponents[0];
                if (opponents[0].health < opponents[1].health) winner = opponents[1];
                if (opponents[0].health == opponents[1].health){
                    System.out.println("The fight was draw. We have to repeat that brannigan immediately.");
                    fight--;
                }
            }
            if (opponents[0].health != opponents[1].health){
                if (fight+1 < fighters.length) System.out.println("And the winner is... " + winner + "!\nAlright, get yourself together for the next fight...\n");
            }
            fight++;
        }
        System.out.println("AND THE FINAL WINNER IS... " + winner.name.toUpperCase() + "!!!\n\n\t\tCongratulations...\n\nSee you all soon... good night, good fight!");
    }
}