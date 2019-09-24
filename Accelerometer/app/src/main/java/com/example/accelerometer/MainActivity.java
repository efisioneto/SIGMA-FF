package com.example.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import java.util.List;
import android.hardware.Sensor;


public class MainActivity extends Activity implements SensorEventListener {


    TextView xValue, yValue, zValue;
    private static final String TAG ="MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        Log.d(TAG,"onCreate: Sensor iniciado");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG,"onCreate: Listener Registrado");
    }

    @Override

    public void onAccuracyChanged(Sensor sensor, int i){

    }

    @Override

    public void onSensorChanged(SensorEvent sensorEvent){

        Log.d(TAG,"onSensorChanged: X:"+ sensorEvent.values[0]+"  Y: "+ sensorEvent.values[1]+"   Z:"+sensorEvent.values[2]);

        xValue.setText("xValue: "+sensorEvent.values[0]);
        yValue.setText("yValue: "+sensorEvent.values[1]);
        zValue.setText("zValue: "+sensorEvent.values[2]);
    }

}