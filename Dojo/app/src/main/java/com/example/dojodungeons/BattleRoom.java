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

        //initalise sene
        pl.droidsonroids.gif.GifImageView bkgrnd = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.imageView2);
        bkgrnd.setEnabled(false);
        bkgrnd.setFreezesAnimation(false);


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



        //stamina is now the correct value
        //Uncomment and move the code underneath whenever you want to reset stamina to 0

        /*
                stamina = 0;
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
         */

        //pl.droidsonroids.gif.GifImageView bkgrnd = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.imageView2);



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

                Toast T = Toast.makeText(getApplicationContext(),"Your Stamina: ",Toast.LENGTH_LONG);


                bkgrnd.setEnabled(false);
                bkgrnd.setFreezesAnimation(false);

                bkgrnd.setImageResource(R.drawable.hill_grass);

                bkgrnd.setImageResource(R.drawable.dohotitle);

                T.show();

            }
        });



    }
}