package com.example.dojodungeons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//new stuff
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;


public class BattleRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_room);

        Button b = (Button) findViewById(R.id.button3);//get id of button 1 asdf
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MenuRoom.class);
                startActivity(i);
            }
        });
        Button g = (Button) findViewById(R.id.button2);//get id of button 1
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast T = Toast.makeText(getApplicationContext(),"This is where you spend Stamina to Fight Enemies",Toast.LENGTH_LONG);
                T.show();
            }
        });


    }
}