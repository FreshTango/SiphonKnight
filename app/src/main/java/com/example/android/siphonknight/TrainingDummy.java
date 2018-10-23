package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class TrainingDummy {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;

    public TrainingDummy() {
        health = 30;
        maxHealth = 30;
        attack = 3;
        defense = 1;
    }

    public int Attack() {
        if (Math.random() >= 0.5) {
            System.out.println("Boss used bash!");
            return Bash();
        } else {
            return attack;
        }

    }

    public int Bash() {
        return attack * 2;
    }
}
