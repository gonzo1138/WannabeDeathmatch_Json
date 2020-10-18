package com.company;

public class Main {

    public static void main(String[] args) {

        // Crate as many fighters as you like (but at least 2) and show them the way to the arena:
        Fighter f1 = new Fighter("Peter", "Polizei", 155.7, 2, 6, 8, 5);
        Fighter f2 = new Fighter("Alex", "Antifa", 76.3, 9, 4, 4, 8);
        Fighter[] fighters = new Fighter[]{f1, f2};

        // Create as many weapons as you desire and fill the arsenal with them:
        Weapon knife = new Weapon("Knife", 0.4, 1, 4, false, 0);
        Weapon ace = new Weapon("Ace  ", 4.7, 2, 6, false, 0);
        Weapon boe = new Weapon("Boe  ", 1.8, 3, 5, true, 3);
        Weapon[] weapons = new Weapon[]{knife, ace, boe};

        // Call a fight with fighters, weapons, a number of rounds of preparation for all and the count of rounds of fight per two players (every winner fights the next player):
        Fight fight = new Fight(fighters, weapons, 3, 5);
    }
}
