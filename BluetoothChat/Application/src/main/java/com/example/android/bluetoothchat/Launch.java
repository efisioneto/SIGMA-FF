package com.example.android.bluetoothchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launch extends AppCompatActivity {

    private Button simulator, realpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        simulator = (Button) findViewById(R.id.Simulator);
        realpoints = (Button) findViewById(R.id.RealPoints);

        realpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRealPoints();
            }
        });
        simulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimulator();
            }
        });

    }

    private void openRealPoints() {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);


    }

    private void openSimulator() {
        Intent intent =new Intent(this,Simulator.class);
        startActivity(intent);

    }


}
