package com.example.android.siphonknight;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

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
            String name = userNameEditText.getText().toString();
            Intent myintent = new Intent(MainActivity.this, WorldSelect.class);
            myintent.putExtra("player", name);
            startActivityForResult(myintent, 1);


        }
    }

}
