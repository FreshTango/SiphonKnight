package com.example.android.siphonknight;

import java.util.Random;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class EvilSnowman implements Boss {

    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;
    protected int canAttack;
    protected int turnCounter;

    public EvilSnowman() {
        health = 80;
        maxHealth = 80;
        attack = 2;
        defense = 10;
        canAttack = 0;
        turnCounter = 0;
    }

    public int Attack() {
        turnCounter++;
        if (canAttack == 0) {
            if (health < 15 && Math.random() >= 0.4) {
                System.out.println("Boss used Winter Absorption!");
                health += 30;
                return 0;
            }
            if (turnCounter == 3 || turnCounter == 7 || turnCounter == 12 ||
                    turnCounter == 16 || turnCounter == 20 || turnCounter == 24) {
                System.out.println("Boss used Snowball Flurry!");
                return SnowballFlurry();
            }
            if (Math.random() >= 0.4) {
                System.out.println("Boss used Snowball Throw!");
                return SnowballThrow();
            }
            return attack;
        }
        System.out.println("Boss is unable to attack!");
        canAttack--;
        return 0;
    }

    public int SnowballThrow() {
        return attack * 3;
    }

    public int SnowballFlurry() {
        Random r = new Random();
        int flurryCount = r.nextInt(5)+1;
        return ((attack + 10) * flurryCount);
    }
}