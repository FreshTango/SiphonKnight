package com.example.android.siphonknight;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Khiem Tang on 10/22/2018.
 */

public class Player implements Serializable {

    protected int level;
    protected int attack;
    protected int defense;
    protected int baseAttack;
    protected int baseDefense;
    protected int health;
    protected int maxHealth;
    protected int experience;
    protected int maxExperience;

    protected int weaponAttack;
    protected int weaponDefense;
    protected int armorAttack;
    protected int armorDefense;
    protected int trinketAttack;
    protected int trinketDefense;

    protected boolean blizzard;
    protected boolean doom;

    protected String name;
    protected String armor;
    protected String weapon;
    protected String trinket;
    protected String buffSkill;
    protected String firstSkill;
    protected String secondSkill;

    public Player() {}

    public void playerInit() {
        //General stats.
        level = 1;
        attack = 2;
        defense = 1;

        //Status effects.
        blizzard = false;
        doom = false;

        //Statistics without any changes.
        baseAttack = 2;
        baseDefense = 1;

        // Weapon stat increases.
        weaponAttack = 0;
        weaponDefense = 0;
        //Armor stat increases.
        armorAttack = 0;
        armorDefense = 0;
        //Trinket stat increases.
        trinketAttack = 0;
        trinketDefense = 0;

        health = 20;
        maxHealth = 20;
        experience = 0;
        maxExperience = 100;

        //Currently equipped skills.
        buffSkill = "";
        firstSkill = "";
        secondSkill = "";

        //Currently equipped items.
        armor = "Apprentice Armor";
        weapon = "Apprentice Sword";
        trinket = "";
    }

    public void setName(String playerName){
        name = playerName;
    }

    public void levelUp() {
        Random r = new Random();
        level++;
        // Sets statistics to old temporarily.
        health = maxHealth;
        attack = baseAttack;
        defense = baseDefense;
        maxHealth += r.nextInt(10) + 5;
        baseAttack += r.nextInt(3) + 1;
        baseDefense += r.nextInt(2) + 1;
        health = maxHealth;
        attack = baseAttack;
        defense = baseDefense;
        gearUpdate();
    }

    // Updates the current armor equipped and associated stats.
    public void armorUpdate() {
        if (armor == "Apprentice Armor") {
            armorDefense = 1;
        }
        if (armor == "Ancient Stone Vestments") {
            armorAttack = -6;
            armorDefense = 12;
        }
        if (armor == "Frostbite Raiment") {
            armorDefense = 5;
        }
        gearUpdate();
    }

    // Updates the current weapon equipped and associated stats.
    public void weaponUpdate() {
        if (weapon == "Apprentice Sword") {
            weaponAttack = 3;
        }
        if (weapon == "Silver Sword") {
            weaponAttack = 5;
        }
        if (weapon == "Onion Sword") {
            weaponAttack = level;
        }
        if (weapon == "Bandit Shiv") {
            weaponAttack = 7;
        }
        if (weapon == "Frozen Snowball") {
            weaponAttack = 13;
        }
        if (weapon == "Icicle Spear") {
            weaponAttack = 10;
        }
        gearUpdate();
    }

    // Updates the current trinket equipped and associated stats.
    public void trinketUpdate() {
        if (trinket == "Necklace of the Ancients") {
            trinketDefense = 5;
        }
        if (trinket == "Snowflake Earrings") {
            trinketAttack = 10;
        }
        gearUpdate();
    }

    // Combines all stat changes from items to adjust actual statistics.
    public void gearUpdate() {
        attack = baseAttack + (weaponAttack + armorAttack + trinketAttack);
        defense = baseDefense + (weaponDefense + armorDefense + trinketDefense);
    }
}
