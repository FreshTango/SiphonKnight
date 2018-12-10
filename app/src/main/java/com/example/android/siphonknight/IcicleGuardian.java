package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class IcicleGuardian implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;
    protected int turnCounter;

    protected boolean blizzard;
    protected boolean brainFreeze;

    public IcicleGuardian() {
        health = 125;
        maxHealth = 125;
        attack = 2;
        defense = 12;
        canAttack = 0;
        turnCounter = 0;
        blizzard = false;
        brainFreeze = false;
    }

    public int Attack() {
        turnCounter++;
        if (canAttack == 0) {
            if (turnCounter == 1) {
                System.out.println("Boss used Blizzard!");
                blizzard = true;
                return 0;
            }
            if (health < 13) {
                System.out.println("Boss used Absolute Zero!");
                return AbsoluteZero();
            }
            if (turnCounter == 4 || turnCounter == 7 || turnCounter == 10 ||
                    turnCounter == 13 || turnCounter == 16 || turnCounter == 19 || turnCounter == 21) {
                System.out.println("Boss used Brain Freeze!");
                brainFreeze = true;
                return 0;
            }
            if (Math.random() >= 0.4) {
                System.out.println("Boss used Frost Slash!");
                return FrostSlash();
            }
            return attack;
        }
        System.out.println("Boss is unable to attack!");
        canAttack--;
        return 0;
    }

    public int FrostSlash() {
        return (attack * 3) + 5;
    }

    public int AbsoluteZero() {
        return (maxHealth - health);
    }
}