package com.example.dojodungeons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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

    public  int stamina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_room);

        //initalise sene
        pl.droidsonroids.gif.GifImageView bkgrnd = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.imageView2);
        bkgrnd.setEnabled(false);
        bkgrnd.setFreezesAnimation(false);


        //Load Stamina from file

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

                Toast T = Toast.makeText(getApplicationContext(),"Your Stamina left: " + stamina,Toast.LENGTH_SHORT);

                bkgrnd.setEnabled(false);
                bkgrnd.setFreezesAnimation(false);
                bkgrnd.setImageResource(R.drawable.doho);
                b.setClickable(false);

                if(stamina > 0) {
                    stamina = stamina - 1;
                    FileOutputStream fos;

                    try {
                        fos = openFileOutput("stamina.txt", Context.MODE_PRIVATE);
                        fos.write(stamina);
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b.setClickable(true);
                            bkgrnd.setImageResource(R.drawable.dohochill);
                        }
                    }, 2000);

                    T.show();
                }

            }
        });

    }
}