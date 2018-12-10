package com.example.android.siphonknight;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class askname extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askname);
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
        }
        else if (userNameEditText.getText().toString() == "nope"){
            this.doToast("That's Not a Real Name");
        }
        else {
            // XXX Launch TheGame activity
            menuSong = 0;
            String name = userNameEditText.getText().toString();
            Intent myintent = new Intent(askname.this, WorldSelect.class);
            myintent.putExtra("player", name);
            startActivityForResult(myintent, 1);


        }
    }
}
