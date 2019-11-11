package com.example.realtimepoints2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {


    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       button = (Button) findViewById(R.id.Simulator);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSimulator();
            }
        });


    }

    private void openSimulator() {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}

