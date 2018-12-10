package com.example.android.siphonknight;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    protected RoundCornerProgressBar bossHealthBar;
    protected RoundCornerProgressBar playerHealthBar;
    protected Button attackButton;
    protected Button defenseButton;
    protected ImageButton victoryButton;
    protected ImageButton gameoverButton;

    protected ImageView bg;
    protected ImageView bossSprite;

    int currentStage;
    boolean playerTurn;
    String boss;

    // Boss defined stats.
    // Arrays are corresponding based on order (first boss is index 0 for all tables).
    int bossHealth;
    int maxBossHealth;
    int bossAttack;
    int bossDefense;

    //Player
    Player player = new Player();

    // Bosses
    TrainingDummy dummy = new TrainingDummy();
    BanditCaptain bandit = new BanditCaptain();
    BigSlime slime = new BigSlime();
    StoneGolem golem = new StoneGolem();
    EvilSnowman snowman = new EvilSnowman();
    IcicleGuardian guardian = new IcicleGuardian();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight);

        RelativeLayout relativeLayout = findViewById(R.id.backgroundLayout3);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(400);
        animationDrawable.setExitFadeDuration(200);
        animationDrawable.start();

        music.stop();
        music.reset();
        music = MediaPlayer.create(getApplicationContext(), R.raw.battle01);
        music.start();


        bg = findViewById(R.id.bgImage);
        bossSprite = findViewById(R.id.bossSprite);
        bossHealthVal = findViewById(R.id.bossHealthVal);
        maxBossHealthVal = findViewById(R.id.bossMaxHealthVal);
        playerHealthVal = findViewById(R.id.playerHealthVal);
        maxPlayerHealthVal = findViewById(R.id.playerMaxHealthVal);
        attackVal = findViewById(R.id.attackVal);
        defenseVal = findViewById(R.id.defenseVal);
        victoryButton = findViewById(R.id.victorySign);
        gameoverButton = findViewById(R.id.gameoverSign);
        bossHealthBar = findViewById(R.id.bossHealthBar);
        bossHealthBar.setProgressColor(Color.parseColor("#c83b3b"));
        bossHealthBar.setProgressBackgroundColor(Color.parseColor("#9d2e2e"));
        playerHealthBar = findViewById(R.id.playerHealthBar);
        playerHealthBar.setProgressColor(Color.parseColor("#0ea65d"));
        playerHealthBar.setProgressBackgroundColor(Color.parseColor("#097240"));
        attackButton = findViewById(R.id.attackButton);
        defenseButton = findViewById(R.id.defenseButton);

        // Abilities
        gameInit();

        boss = getIntent().getExtras().getString("boss");
        if (boss.equals("dummy")) {
                bossHealth = dummy.health;
                maxBossHealth = dummy.maxHealth;
                bossAttack = dummy.attack;
                bossDefense = dummy.defense;
                bossSprite.setBackgroundResource(R.drawable.dummy);
        } else if (boss.equals("bandit")) {
            bossHealth = bandit.health;
            maxBossHealth = bandit.maxHealth;
            bossAttack = bandit.attack;
            bossDefense = bandit.defense;
            bossSprite.setBackgroundResource(R.drawable.bandit);
        } else if (boss.equals("slime")){
            bossHealth = slime.health;
            maxBossHealth = slime.maxHealth;
            bossAttack = slime.attack;
            bossDefense = slime.defense;
            bossSprite.setBackgroundResource(R.drawable.slime);
        } else if (boss.equals("golem")){
            bossHealth = golem.health;
            maxBossHealth = golem.maxHealth;
            bossAttack = golem.attack;
            bossDefense = golem.defense;
            bossSprite.setBackgroundResource(R.drawable.golem);
            bg.setBackgroundResource(R.drawable.winter);
        } else if (boss.equals("snowman")){
            bossHealth = snowman.health;
            maxBossHealth = snowman.maxHealth;
            bossAttack = snowman.attack;
            bossDefense = snowman.defense;
            bossSprite.setBackgroundResource(R.drawable.snowman);
            bg.setBackgroundResource(R.drawable.winter);
        } else if (boss.equals("guardian")){
            bossHealth = guardian.health;
            maxBossHealth = guardian.maxHealth;
            bossAttack = guardian.attack;
            bossDefense = guardian.defense;
            bossSprite.setBackgroundResource(R.drawable.guardian);
            bg.setBackgroundResource(R.drawable.winter);
        }

        player.armorUpdate();
        player.weaponUpdate();
        player.trinketUpdate();

        playerHealthBar.setMax(player.maxHealth);
        bossHealthBar.setMax(maxBossHealth);

        updateValues();

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

        gameoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bck = new Intent();
                setResult(RESULT_OK, bck);
                finish();
            }
        });

        victoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bck = new Intent();
                setResult(RESULT_OK, bck);
                finish();
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
        if (boss.equals("dummy")) {
            damageDone = dummy.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Training dummy attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Training dummy attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
        if (boss.equals("bandit")) {
            damageDone = bandit.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Bandit Captain attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Bandit Captain attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
        if (boss.equals("slime")) {
            damageDone = slime.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Big Slime attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Big Slime attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
        if (boss.equals("golem")) {
            damageDone = golem.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Ancient Golem attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Ancient Golem attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
        if (boss.equals("snowman")) {
            damageDone = snowman.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Evil Snowman attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Evil Snowman attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
            updateValues();
            playerTurn = true;
        }
        if (boss.equals("guardian")) {
            damageDone = guardian.Attack();
            player.health -= (Math.abs(damageDone - player.defense));
            Toast.makeText(getApplicationContext(), "Icicle Guardian attacked for " + Math.abs(damageDone - player.defense) + " damage!", Toast.LENGTH_SHORT).show();
            System.out.println("Icicle Guardian attacked for " + Math.abs(damageDone - player.defense)+ " damage!");
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

    // Updates values.
    public void updateValues() {
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
            System.out.println("YOU DIED! GAME OVER");
            music.stop();
            music.reset();
            music = MediaPlayer.create(getApplicationContext(), R.raw.gameover);
            music.start();
            gameoverButton.setVisibility(View.VISIBLE);
        }
        if (bossHealth <= 0) {
            bossHealthVal.setText("0");
            music.stop();
            music.reset();
            music = MediaPlayer.create(getApplicationContext(), R.raw.victory);
            music.start();
            player.levelUp();
            victoryButton.setVisibility(View.VISIBLE);
        }
    }
}
