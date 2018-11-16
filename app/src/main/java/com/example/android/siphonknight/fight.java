package com.example.android.siphonknight;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class fight extends MainActivity {

    protected TextView bossHealthVal;
    protected TextView maxBossHealthVal;
    protected TextView playerHealthVal;
    protected TextView maxPlayerHealthVal;
    protected TextView attackVal;
    protected TextView defenseVal;
    protected TextView playerLevelText;
    protected TextView bossLevel;
    protected RoundCornerProgressBar bossHealthBar;
    protected RoundCornerProgressBar playerHealthBar;
    protected Button attackButton;
    protected Button defenseButton;
    protected Button siphoningSlash;
    protected Button enragedFlurry;
    protected Button venomousStab;
    protected Button sacrificialMight;
    protected Button sacrificialProtection;
    protected Button reverseSweep;
    protected Button berserk;
    protected Button vengeance;

    protected Button resetButton;

    int currentStage;
    boolean playerTurn;

    // Boss defined stats.
    // Arrays are corresponding based on order (first boss is index 0 for all tables).
    int bossHealth;
    int maxBossHealth;
    int bossAttack;
    int bossDefense;

    //Player
    Player player = new Player();

    //Bosses
    TrainingDummy trainingDummy = new TrainingDummy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight);

        music.stop();
        music.reset();
        music = MediaPlayer.create(getApplicationContext(), R.raw.battle01);
        music.start();

        playerLevelText = findViewById(R.id.playerLevel);
        bossLevel = findViewById(R.id.bossLevel);
        resetButton = findViewById(R.id.resetButton);

        bossHealthVal = findViewById(R.id.bossHealthVal);
        maxBossHealthVal = findViewById(R.id.bossMaxHealthVal);
        playerHealthVal = findViewById(R.id.playerHealthVal);
        maxPlayerHealthVal = findViewById(R.id.playerMaxHealthVal);
        attackVal = findViewById(R.id.attackVal);
        defenseVal = findViewById(R.id.defenseVal);

        bossHealthBar = findViewById(R.id.bossHealthBar);
        //bossHealthBar.setIconImageResource(R.drawable.hpicon);
        bossHealthBar.setProgressColor(Color.parseColor("#c83b3b"));
        bossHealthBar.setProgressBackgroundColor(Color.parseColor("#9d2e2e"));
       // bossHealthBar.setIconBackgroundColor(Color.parseColor("#741515"));

        playerHealthBar = findViewById(R.id.playerHealthBar);
        //playerHealthBar.setIconImageResource(R.drawable.hpicon);
        playerHealthBar.setProgressColor(Color.parseColor("#0ea65d"));
        playerHealthBar.setProgressBackgroundColor(Color.parseColor("#097240"));
       // playerHealthBar.setIconBackgroundColor(Color.parseColor("#00502a"));
        attackButton = findViewById(R.id.attackButton);
        defenseButton = findViewById(R.id.defenseButton);

        // Abilities
//        siphoningSlash = findViewById(R.id.siphoningSlash);
//        enragedFlurry = findViewById(R.id.enragedFlurry);
//        venomousStab = findViewById(R.id.venomousStab);
//        sacrificialMight = findViewById(R.id.sacMight);
//        sacrificialProtection = findViewById(R.id.sacProtect);
//        reverseSweep = findViewById(R.id.reverseSweep);
//        berserk = findViewById(R.id.berserk);
//        vengeance = findViewById(R.id.vengeance);

        gameInit();
        levelInit();
        player.armorUpdate();
        player.weaponUpdate();

        playerHealthBar.setMax(player.maxHealth);
        bossHealthBar.setMax(maxBossHealth);

        updateValues();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Resetting game.", Toast.LENGTH_SHORT).show();
                System.out.println("Resetting game.");
                gameInit();
                levelInit();
                player.armorUpdate();
                player.weaponUpdate();
                updateValues();
            }
        });

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (playerTurn) {
                int damageDone = Math.abs(player.attack - bossDefense);
                bossHealth -= damageDone;
                Toast.makeText(getApplicationContext(),
                        "Player basic attacked! Dealt " + damageDone + " damage!", Toast.LENGTH_SHORT).show();
                System.out.println("Player basic attacked! Dealt " + damageDone + " damage!");
                updateValues();
            }
            playerTurn = false;
            bossTurn();
            }
        });

        defenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerTurn) {
                    if (player.health < player.maxHealth) {
                        player.health += (Math.round(player.maxHealth * 0.15));
                    }
                   int oldDefense = player.defense;
                    player.defense += Math.round(player.defense * 0.5);
                Toast.makeText(getApplicationContext(),
                        "Player defended and healed for " + Math.round(player.maxHealth * 0.15) + " health! Def up 50%!", Toast.LENGTH_SHORT).show();
                System.out.println("Player defended and healed for " + Math.round(player.maxHealth * 0.15) + " health! Defense up to " + player.defense + ".");
                playerTurn = false;
                updateValues();
                bossTurn();
                player.defense = oldDefense;
                updateValues();
                }
            }
        });
    }

    // Main method for bosses and when they attack.
    public void bossTurn() {
        int damageDone;
        if (currentStage == 0) {
            damageDone = trainingDummy.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Training dummy attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Training dummy attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
    }

    // Starts the game.
    public void gameInit() {
        playerTurn = true;
        player.playerInit();
        currentStage = 0;
    }

    public void levelInit() {
        if (currentStage == 0) {
            bossHealth = trainingDummy.health;
            maxBossHealth = trainingDummy.maxHealth;
            bossAttack = trainingDummy.attack;
            bossDefense = trainingDummy.defense;
        }
    }

    // Updates values.
    public void updateValues() {
        // Updates text.
        playerLevelText.setText(Integer.toString(player.level));
        bossLevel.setText(Integer.toString(currentStage));
        attackVal.setText(Integer.toString(player.attack));
        defenseVal.setText(Integer.toString(player.defense));
        bossHealthVal.setText(Integer.toString(bossHealth));
        maxBossHealthVal.setText(Integer.toString(maxBossHealth));
        playerHealthVal.setText(Integer.toString(player.health));
        maxPlayerHealthVal.setText(Integer.toString(player.maxHealth));
        playerHealthBar.setProgress(player.health);
        bossHealthBar.setProgress(bossHealth);

        // Sets health to MAX so it doesn't go over.
        if (player.health > player.maxHealth) {
            player.health = player.maxHealth;
        }
        if (bossHealth > maxBossHealth) {
            bossHealth = maxBossHealth;
        }

        // Game over check.
        if (player.health <= 0) {
            playerHealthVal.setText("0");
            Toast.makeText(getApplicationContext(),
                    "YOU DIED! GAME OVER", Toast.LENGTH_LONG).show();
            System.out.println("YOU DIED! GAME OVER");
        }
        if (bossHealth <= 0) {
            bossHealthVal.setText("0");
            Toast.makeText(getApplicationContext(),
                    "Boss down! Advancing to the next boss!", Toast.LENGTH_LONG).show();
                    System.out.println("Boss down! Advancing to the next boss!");
            Intent bck = new Intent();
            setResult(RESULT_OK, bck);
            finish();

        }
    }
}
