package com.example.dojodungeons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button b = (Button) findViewById(R.id.button);//get id of button 1
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MenuRoom.class);
                startActivity(i);


                //Toast T = Toast.makeText(getApplicationContext(),"asdf",Toast.LENGTH_LONG);
                //T.show();



            }
        });




    }
}