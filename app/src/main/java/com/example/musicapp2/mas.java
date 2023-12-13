package com.example.musicapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class mas extends AppCompatActivity {

    TextView loginbut;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Button listButton, elibutton ,modbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas);
        loginbut = findViewById(R.id.loginbut);
        listButton = findViewById(R.id.listbutton);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        elibutton = findViewById(R.id.elibutton);
        modbutton = findViewById(R.id.modbutton);




    loginbut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mas.this,login.class);
            startActivity(intent);
            finish();
        }
    });

    listButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mas.this,RetreiveData.class);
            startActivity(intent);
            finish();

        }
    });

        elibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mas.this,eliminar.class);
                startActivity(intent);
                finish();

            }
        });

        modbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mas.this, modificar.class);
                startActivity(intent);

            }
        });




    }


}