package com.example.android.siphonknight;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WorldSelect extends MainActivity {

    protected String user;
    public Player p = new Player();

    private ListView lv;
    public static ArrayList<level> arrayList;

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

        user = getIntent().getExtras().getString("name");
        p.playerInit();
        p.setName(user);
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

    }
}
