package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class BanditCaptain implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;

    public BanditCaptain() {
        health = 40;
        maxHealth = 40;
        attack = 3;
        defense = 1;
        canAttack = 0;
    }

    public int Attack() {
        if (canAttack == 0) {
            if (Math.random() >= 0.4) {
                System.out.println("Boss used Raging Stab!");
                canAttack = 2;
                return RagingStab();
            } else {
                return attack;
            }
        }
        System.out.println("Boss is unable to attack!");
        canAttack--;
        return 0;
    }

    public int RagingStab() {
        canAttack--;
        if (canAttack == 0) {
            return attack * 3;
        } else {
            return 0;
        }
    }
}
