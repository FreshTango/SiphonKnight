package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class BigSlime implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;
    protected int turnCounter;

    public BigSlime() {
        health = 50;
        maxHealth = 50;
        attack = 3;
        defense = 3;
        turnCounter = 1;
    }

    public int Attack() {
        if (canAttack == 0) {
            if (health < maxHealth) {
                System.out.println("Big Slime healed!");
                health += (turnCounter * 2);
            }
            System.out.println("Boss used Slimy Slap!");
            return SlimySlap();
        }
        System.out.println("Boss is unable to attack!");
        canAttack--;
        return 0;
    }

    public int SlimySlap() {
        turnCounter++;
        if (canAttack == 0) {
            return attack + ((turnCounter - 1) * 2);
        } else {
            return 0;
        }
    }
}