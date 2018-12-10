package com.example.android.siphonknight;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Toast mainToast;
    MediaPlayer music;
    int menuSong;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    protected void doToast(String message) {
        if (this.mainToast != null) {
            this.mainToast.cancel();
        }
        this.mainToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        this.mainToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        RelativeLayout relativeLayout = findViewById(R.id.backgroundLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        menuSong = 1;
        if (menuSong == 1) {
            music = MediaPlayer.create(getApplicationContext(), R.raw.menu01);
            music.start();
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            mAuth.signOut();
        }
        ImageButton login = (ImageButton)findViewById(R.id.signinbutton);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build()
                );
                //music stops after login
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), 1);
            }
        });



        ImageButton play = (ImageButton)(findViewById(R.id.playbutton));
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (currentUser == null) {
                    // XXX write a Toast to warn the user about empty names.
                    doToast("Please Sign in First");
                }
                else {
                    doToast(currentUser.toString());
                    // XXX Launch TheGame activity
                    menuSong = 0;
                    //String name = userNameEditText.getText().toString();
                    Intent myintent = new Intent(MainActivity.this, WorldSelect.class);
                    //myintent.putExtra("player", name);
                    startActivityForResult(myintent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
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
