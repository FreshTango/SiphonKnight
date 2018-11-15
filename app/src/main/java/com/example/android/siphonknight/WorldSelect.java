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

public class WorldSelect extends AppCompatActivity {

    protected String user;
    public Player p = new Player();

    private ListView lv;
    public static ArrayList<level> arrayList;

    TrainingDummy train = new TrainingDummy();

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
        arrayList.add(new level("level 1", train));
        arrayList.add(new level("level 2", train));
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
