package com.example.android.siphonknight;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class Player {

    protected int level;
    protected int attack;
    protected int defense;
    protected int baseAttack;
    protected int baseDefense;
    protected int health;
    protected int maxHealth;
    protected int experience;
    protected int maxExperience;
    protected String name;
    protected String armor;
    protected String weapon;
    protected String trinket;

    public Player() {
        playerInit();
    }

    public void playerInit() {
        level = 1;
        attack = 2;
        baseAttack = 2;
        defense = 1;
        baseDefense = 1;
        health = 20;
        maxHealth = 20;
        experience = 0;
        maxExperience = 100;
        armor = "Apprentice Armor";
        weapon = "Apprentice Sword";
        trinket = "";
    }

    public void setName(String nam){
        name = nam;
    }

    // Updates the current armor equipped and associated stats.
    public void armorUpdate() {
        if (armor == "Apprentice Armor") {
            defense = baseDefense + 1;
        }
    }

    // Updates the current weapon equipped and associated stats.
    public void weaponUpdate() {
        if (weapon == "Apprentice Sword") {
            attack = baseAttack + 3;
        }
    }
}
