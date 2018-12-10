package com.example.android.siphonknight;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

    private ListView lv;
    public static ArrayList<level> arrayList;

    private DocumentReference mDocRef;
    String uid;
    TrainingDummy dummy = new TrainingDummy();
    BanditCaptain bandit = new BanditCaptain();
    BigSlime slime = new BigSlime();
    StoneGolem golem = new StoneGolem();
    EvilSnowman snowman = new EvilSnowman();
    IcicleGuardian guardian = new IcicleGuardian();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.world_select);
        /*
        if (currentUser == null){
            doToast("thefuck");
        }
        else{
            doToast(currentUser.toString());
        }*/
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
        lv = (ListView) findViewById(R.id.stagelist);
        String[] stringArray = getResources().getStringArray(R.array.level_names);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                stringArray);
        lv.setAdapter(arrayAdapter);
        arrayList = new ArrayList<level>();
        arrayList.add(new level("Level 1", dummy));
        arrayList.add(new level("Level 2", bandit));
        arrayList.add(new level("Level 3", slime));
        arrayList.add(new level("Level 4", golem));
        arrayList.add(new level("Level 5", snowman));
        arrayList.add(new level("Level 6", guardian));
        music.stop();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //need to figure out how to keep information
                Intent myintent = new Intent(WorldSelect.this, fight.class);
                startActivityForResult(myintent, 1);
            }
        });
        Button logout = (Button)(findViewById(R.id.logoutbutton));
        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mAuth.signOut();
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
