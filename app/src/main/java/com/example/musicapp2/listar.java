package com.example.musicapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class listar extends AppCompatActivity {
    //RecyclerView recyclerView;
    ArrayList<Users> list;
    DatabaseReference databaseReference;
    Myadapter adapter;
    Button atras;
    FirebaseDatabase database;
    ListView listView;
    android.app.ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        atras = findViewById(R.id.atras);
        listView = findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listar.this,mas.class);
                startActivity(intent);
                finish();
            }
        });

        final ArrayList<String> list1 = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item2,list1);
        listView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Users users = ds.getValue(Users.class);
                        String txt = "Email:" + users.getMail() + "     " + "Contrasena:" + users.getPassword()+ " " + "Estatus:"+users.getStatus() + " " + "Username:"+users.getUserName();
                        list1.add(txt);
                    }
                    adapter.notifyDataSetChanged();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al obtener datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(listar.this, modificar.class);
                startActivity(intent);
            }
        });


    }



}


