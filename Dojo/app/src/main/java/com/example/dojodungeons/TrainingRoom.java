package com.example.dojodungeons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TrainingRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_room);

        Button b = (Button) findViewById(R.id.button6);//get id of button 1
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MenuRoom.class);
                startActivity(i);
            }
        });


        Button g = (Button) findViewById(R.id.button4);//get id of button 1
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast T = Toast.makeText(getApplicationContext(),"These are quests you complete to gain Stamina",Toast.LENGTH_LONG);
                T.show();
            }
        });


        Button j = (Button) findViewById(R.id.button5);//get id of button 1
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast T = Toast.makeText(getApplicationContext(),"These are quests you complete to gain Stamina",Toast.LENGTH_LONG);
                T.show();
            }
        });










    }



}