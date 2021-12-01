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
import java.util.Random;

public class TrainingRoom extends AppCompatActivity implements SensorEventListener {

    /* for database */
    String[] RunningQuest = {"Slow and Steady: take 5 steps", "100 meter dash: take 200 steps", "Walk it off: take 300 steps",
            "Taking the scenic route: take 400 steps", "And i would walk 500 more..: take 500 steps", "Adrenaline rush: take 600 steps",
            "Fancy Footwork: take 700 steps", "Jog your memory: take 800 steps", "Speed demon: take 900 steps"};

    int[] RunningGoals = {5,200,300,400,500,600,700,800,900};

    int[] RunningRewards = {1,1,1,1,2,2,2,2,3};

    String[] OtherQuest = {"Pull it together: Do 10 pull ups", "Pull some strings: Do 20 pull ups", "Pulling it off: Do 50 pull ups",
            "Pushover: Do 10 push ups", "Now you're pushing it: Do 20 push ups", "Push it to the limit: Do 50 push ups",
            "Sit Down: Do 10 sit ups", "Sitting on the job: Do 20 sit ups", "No time to sit around: Do 50 sit ups",
            "Crunchy: Do 10 crunches", "Crunch time: Do 20 crunches", "Crunch the numbers: Do 50 crunches"};
    int[] OtherRewards = {1,2,3,1,2,2,1,1,2,1,1,2};


    SensorManager sensorManager = null;
    boolean running = false;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;
    TextView tv_stepsTaken;
    TextView tv_totalMax;

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
        tv_totalMax = findViewById(R.id.tv_totalMax);

        loadData();
        resetSteps();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Some Randomness

        // Load Current RunningQuest
        int currentRunningQuest = 0;


        File file2 = getBaseContext().getFileStreamPath("currentRunningQuest.txt");

        if (file2.exists()) {

            FileInputStream fis;

            try {
                fis = openFileInput("currentRunningQuest.txt");
                currentRunningQuest = fis.read();
                fis.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        if( currentRunningQuest > 8 ){
            currentRunningQuest = 0;
        }

        tv_totalMax.setText(String.valueOf(RunningGoals[currentRunningQuest]));

        //Save current running quest
        FileOutputStream fos2;

        try {
            fos2 = openFileOutput("currentRunningQuest.txt", Context.MODE_PRIVATE);
            fos2.write(currentRunningQuest);
            fos2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }


        // Load Current OtherQuest
        int currentOtherQuest = 0;


        File file3 = getBaseContext().getFileStreamPath("currentOtherQuest.txt");

        if (file3.exists()) {

            FileInputStream fis;

            try {
                fis = openFileInput("currentOtherQuest.txt");
                currentOtherQuest = fis.read();
                fis.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if( currentOtherQuest > 11 ){
            currentOtherQuest = 0;
        }

        //Save current Other quest
        FileOutputStream fos3;

        try {
            fos3 = openFileOutput("currentOtherQuest.txt", Context.MODE_PRIVATE);
            fos3.write(currentOtherQuest);
            fos3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

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
        g.setText(RunningQuest[currentRunningQuest]);
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

                //get current running quest
                int temp = 0;

                File file2 = getBaseContext().getFileStreamPath("currentRunningQuest.txt");

                if (file2.exists()) {

                    FileInputStream fis;

                    try {
                        fis = openFileInput("currentRunningQuest.txt");
                        temp = fis.read();
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                if ((int)totalSteps >= RunningGoals[temp]) {

                    // Add to and Store Stamina Value
                    staminaClick += RunningRewards[temp];
                    FileOutputStream fos;

                    try {
                        fos = openFileOutput("stamina.txt", Context.MODE_PRIVATE);
                        fos.write(staminaClick);
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                    Toast T = Toast.makeText(getApplicationContext(), String.valueOf(staminaClick), Toast.LENGTH_LONG);
                    T.show();

                    temp += 1;
                    if (temp > 8){
                        temp = 0;
                    }
                    tv_totalMax.setText(String.valueOf(RunningGoals[temp]));
                    //Save current running quest
                    FileOutputStream fos2;

                    try {
                        fos2 = openFileOutput("currentRunningQuest.txt", Context.MODE_PRIVATE);
                        fos2.write(temp);
                        fos2.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    g.setText(RunningQuest[temp]);

                    previousTotalSteps = totalSteps;
                    tv_stepsTaken.setText("0");
                    saveData();
                }
            }
        });


        Button j = (Button) findViewById(R.id.button5);
        j.setText(OtherQuest[currentOtherQuest]);
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

                //get current other quest
                int temp = 0;

                File file3 = getBaseContext().getFileStreamPath("currentOtherQuest.txt");

                if (file3.exists()) {

                    FileInputStream fis;

                    try {
                        fis = openFileInput("currentOtherQuest.txt");
                        temp = fis.read();
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                // Add to and Store Stamina Value
                staminaClick += OtherRewards[temp];
                FileOutputStream fos;

                try {
                    fos = openFileOutput("stamina.txt", Context.MODE_PRIVATE);
                    fos.write(staminaClick);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }

                //Get new OtherQuest
                temp += 1;
                if( temp > 11 ){
                  temp = 0;
                }
                FileOutputStream fos3;

                try {
                    fos3 = openFileOutput("currentOtherQuest.txt", Context.MODE_PRIVATE);
                    fos3.write(temp);
                    fos3.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                j.setText(OtherQuest[temp]);

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
        editor.clear().apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", (previousTotalSteps));
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