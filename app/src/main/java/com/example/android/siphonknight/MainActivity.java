package com.example.android.siphonknight;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Toast mainToast;
    MediaPlayer music;
    int menuSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        menuSong = 1;
        if (menuSong == 1) {
            music = MediaPlayer.create(getApplicationContext(), R.raw.menu01);
            music.start();
        }
    }

    protected void doToast(String message) {
        if (this.mainToast != null) {
            this.mainToast.cancel();
        }
        this.mainToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        this.mainToast.show();
    }

    public void playButtonIsPressed(View view) {
        EditText userNameEditText = (EditText) findViewById(R.id.name);
        if (userNameEditText.getText().toString().isEmpty()) {
            // XXX write a Toast to warn the user about empty names.
            this.doToast("No Name Entered");
        } else {
            // XXX Launch TheGame activity
            menuSong = 0;
            String name = userNameEditText.getText().toString();
            Intent myintent = new Intent(MainActivity.this, WorldSelect.class);
            myintent.putExtra("player", name);
            startActivityForResult(myintent, 1);


        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

