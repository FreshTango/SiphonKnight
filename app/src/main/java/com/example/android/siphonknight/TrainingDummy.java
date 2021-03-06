package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class TrainingDummy implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;

    public TrainingDummy() {
        health = 30;
        maxHealth = 30;
        attack = 3;
        defense = 1;
        canAttack = 0;
    }

    public int Attack() {
        if (canAttack == 0) {
            if (Math.random() >= 0.5) {
                System.out.println("Boss used Bash!");
                return Bash();
            } else {
                return attack;
            }
        }
        System.out.println("Boss is unable to attack!");
        return 0;
    }

    public int Bash() {
        return attack * 2;
    }
}
