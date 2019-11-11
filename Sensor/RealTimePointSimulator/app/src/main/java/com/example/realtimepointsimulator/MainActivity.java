package com.example.realtimepointsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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