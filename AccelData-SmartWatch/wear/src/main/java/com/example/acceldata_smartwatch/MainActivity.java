package com.example.acceldata_smartwatch;

import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

// Imports needed for this specific app:
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MainActivity extends WearableActivity implements SensorEventListener {
    // Create our views/variables
    private TextView xvalue;
    private TextView yvalue;
    private TextView zvalue;
    private Button startButton;
    public boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Objects
        xvalue = (TextView) findViewById(R.id.XVALUE);
        yvalue = (TextView) findViewById(R.id.YVALUE);
        zvalue = (TextView) findViewById(R.id.ZVALUE);

        // Enables Always-on
        setAmbientEnabled();


        // My Accel Sensor Code
        // https://developer.android.com/reference/android/widget/TextView
        // https://developer.android.com/reference/android/hardware/SensorManager
        SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); // This is kinda like the "mom" class, it allows us to access sensor related settings
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // This is our Accelerometer Sensor, the SensorManager assigns it
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL); // Our SensorManager then registers our class (MainActivity) as the listener!


        // Button code - https://developer.android.com/reference/android/widget/Button
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // https://stackoverflow.com/questions/11169360/android-remove-button-dynamically
                ViewGroup layout = (ViewGroup) startButton.getParent(); // Finds the current view group object button is in
                layout.removeView(startButton); // Removes the buttons
                started = true;
            }
        });
    }

    @Override
    /**
     * When ever there is a change in values, this function gets called by the system
     * Changes our TextView objects to display our accel's x,y, and z values
     */
    public void onSensorChanged(SensorEvent event) {
        if (started) {
            xvalue.setText(String.valueOf("X = " + (event.values[0]) + " m/s^2"));
            yvalue.setText(String.valueOf("Y = " + (event.values[1]) + " m/s^2"));
            zvalue.setText(String.valueOf("Z = " + (event.values[2]) + " m/s^2"));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // We don't really care about this, we're only concerned with the data changing
    }
}
