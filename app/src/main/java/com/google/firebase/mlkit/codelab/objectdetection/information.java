package com.google.firebase.mlkit.codelab.objectdetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.View;
import android.widget.Button;

public class information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Button btn2 = (Button) findViewById(R.id.btn);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(information.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
