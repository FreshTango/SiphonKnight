package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class StoneGolem implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;
    protected int turnCounter;

    public StoneGolem() {
        health = 80;
        maxHealth = 80;
        attack = 10;
        defense = 30;
        canAttack = 0;
        turnCounter = 0;
    }

    public int Attack() {
        turnCounter++;
        if (canAttack == 0) {
            if (health < 15 && Math.random() >= 0.4) {
                System.out.println("Boss used Ancient Smash!");
                return AncientSmash();
            }
            if (turnCounter == 3 || turnCounter == 7 || turnCounter == 12 ||
                    turnCounter == 16 || turnCounter == 20 || turnCounter == 24) {
                System.out.println("Boss used Sharpening Stone!");
                SharpeningStone();
                return 0;
            }
            if (Math.random() >= 0.4) {
                System.out.println("Boss used Stone Punch!");
                return StonePunch();
            }
            return attack;
        }
        System.out.println("Boss is unable to attack!");
        canAttack--;
        return 0;
    }

    public int StonePunch() {
        return attack + 15;
    }

    public int AncientSmash() {
        return defense;
    }

    public void SharpeningStone() {
        attack += Math.round(attack * 0.2);
        defense += Math.round(defense * 0.2);
    }
}