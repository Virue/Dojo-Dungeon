package com.example.dojodungeons;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//new stuff
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

public class TrainingRoom extends AppCompatActivity implements SensorEventListener {

    /* for database */


    SensorManager sensorManager = null;
    boolean running = false;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;
    TextView tv_stepsTaken;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_room);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, Sensor.TYPE_STEP_COUNTER);
        }
        tv_stepsTaken = findViewById(R.id.tv_stepsTaken);
        loadData();
        resetSteps();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

       //Load Stamina from file
        int stamina  = 0;

        File file = getBaseContext().getFileStreamPath("stamina.txt");

        if (file.exists()) {

            FileInputStream fis;

            try {
                fis = openFileInput("stamina.txt");
                stamina = fis.read();
                fis.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }




        Button b = (Button) findViewById(R.id.button6);//get id of button 1
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MenuRoom.class);
                startActivity(i);
            }
        });


        Button g = (Button) findViewById(R.id.button4);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Load Stamina Value
                int staminaClick  = 0;

                File file = getBaseContext().getFileStreamPath("stamina.txt");

                if (file.exists()) {

                    FileInputStream fis;

                    try {
                        fis = openFileInput("stamina.txt");
                        staminaClick = fis.read();
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                // Add to and Store Stamina Value
                staminaClick += 2;
                FileOutputStream fos;

                try {
                    fos = openFileOutput("test2.txt", Context.MODE_PRIVATE);
                    fos.write(staminaClick);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }

                Toast T= Toast.makeText(getApplicationContext(), String.valueOf(staminaClick), Toast.LENGTH_LONG);
                T.show();
            }
        });


        Button j = (Button) findViewById(R.id.button5);
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Load Stamina Value
                int staminaClick  = 0;

                File file = getBaseContext().getFileStreamPath("stamina.txt");

                if (file.exists()) {

                    FileInputStream fis;

                    try {
                        fis = openFileInput("stamina.txt");
                        staminaClick = fis.read();
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                // Add to and Store Stamina Value
                staminaClick += 3;
                FileOutputStream fos;

                try {
                    fos = openFileOutput("test2.txt", Context.MODE_PRIVATE);
                    fos.write(staminaClick);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                Toast T = Toast.makeText(getApplicationContext(),String.valueOf(staminaClick),Toast.LENGTH_LONG);
                T.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(this, "No step sensor detected", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void resetSteps() {
        tv_stepsTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Long click to reset steps", Toast.LENGTH_LONG).show();
            }
        });

        tv_stepsTaken.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                tv_stepsTaken.setText("0");
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", 0f);
        Log.d("MainActivity", String.valueOf(savedNumber));
        previousTotalSteps = savedNumber;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            int currentSteps = (int) (totalSteps - previousTotalSteps);
            tv_stepsTaken.setText(String.valueOf(currentSteps));
            CircularProgressBar progress_circular = findViewById(R.id.progress_circular);
            progress_circular.setProgressWithAnimation((float) currentSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}