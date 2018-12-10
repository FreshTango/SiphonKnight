package com.example.android.siphonknight;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldSelect extends MainActivity {

    protected String user;
    public Player p;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected ImageButton oneButton;
    protected ImageButton twoButton;
    protected ImageButton threeButton;
    protected ImageButton fourButton;
    protected ImageButton fiveButton;
    protected ImageButton sixButton;

    public static ArrayList<level> arrayList;

    private DocumentReference mDocRef;
    String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.world_select);

        RelativeLayout relativeLayout = findViewById(R.id.backgroundLayout2);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        oneButton = (ImageButton) findViewById(R.id.level1button);
        twoButton = (ImageButton) findViewById(R.id.level2button);
        threeButton = (ImageButton) findViewById(R.id.level3button);
        fourButton = (ImageButton) findViewById(R.id.level4button);
        fiveButton = (ImageButton) findViewById(R.id.level5button);
        sixButton = (ImageButton) findViewById(R.id.level6button);

        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "dummy");
                startActivity(myintent);

            }
        });
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "bandit");
                startActivity(myintent);

            }
        });
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "slime");
                startActivity(myintent);

            }
        });
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "golem");
                startActivity(myintent);

            }
        });
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "snowman");
                startActivity(myintent);

            }
        });
        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                myintent.putExtra("boss", "guardian");
                startActivity(myintent);

            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

            }
        };
        uid = currentUser.getUid();
        mDocRef = FirebaseFirestore.getInstance().collection("users").document(uid);
        user = getIntent().getExtras().getString("name");
        if(user != "nope"){
            p = new Player();
            p.playerInit();
            p.setName(user);
            savePlayer();
        }
        else{
             loadPlayer();
        }
        ImageButton logout = (ImageButton)(findViewById(R.id.logoutbutton));
        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mAuth.signOut();
                Intent myintent = new Intent(WorldSelect.this, MainActivity.class);
                startActivity(myintent);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            savePlayer();
        }
    }

    public void savePlayer(){
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("HealthKey", p.health);
        dataToSave.put("LvlKey", p.level);
        dataToSave.put("AtkKey", p.attack);
        dataToSave.put("DefKey", p.defense);
        dataToSave.put("bAtkKey", p.baseAttack);
        dataToSave.put("bDefKey", p.baseDefense);
        dataToSave.put("mHealthKey", p.maxHealth);
        dataToSave.put("ExpKey", p.experience);
        dataToSave.put("mExpKey", p.maxExperience);
        dataToSave.put("wAtkKey", p.weaponAttack);
        dataToSave.put("wDefKey", p.weaponDefense);
        dataToSave.put("aAtkKey", p.armorAttack);
        dataToSave.put("aDefKey", p.armorDefense);
        dataToSave.put("tAtkKey", p.trinketAttack);
        dataToSave.put("tDefKey", p.trinketDefense);
        dataToSave.put("NameKey", p.name);
        dataToSave.put("ArmorKey", p.armor);
        dataToSave.put("WeaponKey", p.weapon);
        dataToSave.put("TrinketKey", p.trinket);
        dataToSave.put("BSkillKey", p.buffSkill);
        dataToSave.put("1SkillKey", p.firstSkill);
        dataToSave.put("2SkillKey", p.secondSkill);


        mDocRef.set(dataToSave);
    }

    public void loadPlayer(){
       mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()){
                   p.health = (int) documentSnapshot.get("HealthKey");
                   p.level = (int) documentSnapshot.get("LevelKey");
                   p.attack = (int) documentSnapshot.get("AtkKey");
                   p.defense = (int) documentSnapshot.get("DefKey");
                   p.baseAttack = (int) documentSnapshot.get("bAtkKey");
                   p.baseDefense = (int) documentSnapshot.get("bDefKey");
                   p.maxHealth = (int) documentSnapshot.get("mHealthKey");
                   p.experience = (int) documentSnapshot.get("ExpKey");
                   p.maxExperience = (int) documentSnapshot.get("mExpKey");
                   p.weaponAttack = (int) documentSnapshot.get("wAtkKey");
                   p.weaponDefense = (int) documentSnapshot.get("wDefKey");
                   p.armorAttack = (int) documentSnapshot.get("aAtkKey");
                   p.armorDefense = (int) documentSnapshot.get("aDefKey");
                   p.trinketAttack = (int) documentSnapshot.get("tAtkKey");
                   p.trinketDefense = (int) documentSnapshot.get("mExpKey");
                   p.name = (String) documentSnapshot.get("NameKey");
                   p.armor = (String) documentSnapshot.get("ArmorKey");
                   p.weapon = (String) documentSnapshot.get("WeaponKey");
                   p.trinket = (String) documentSnapshot.get("TrinketKey");
                   p.buffSkill = (String) documentSnapshot.get("BSkillKey");
                   p.firstSkill = (String) documentSnapshot.get("1SkillKeyKey");
                   p.secondSkill = (String) documentSnapshot.get("2SkillKey");
               }
           }
       });
    }
}
