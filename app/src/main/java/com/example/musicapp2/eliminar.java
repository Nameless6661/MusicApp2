package com.example.musicapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class eliminar extends AppCompatActivity {
    Button btneliminar, atras;
    EditText txt;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);
        btneliminar = findViewById(R.id.eliminarbtn);
        atras = findViewById(R.id.atras);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EditText txt = findViewById(R.id.txt); // Reemplaza con el ID de tu EditText




        Button btneliminar = findViewById(R.id.eliminarbtn);
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });



        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("user").removeValue();
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eliminar.this,mas.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void eliminarUsuario() {
        // Obtén el email del usuario desde el EditText
        String userEmailToDelete = txt.getText().toString();

        // Verifica si el email no está vacío antes de continuar
        if (!TextUtils.isEmpty(userEmailToDelete)) {
            // Crea una consulta para encontrar el nodo del usuario por su email
            Query query = databaseReference.child("user").orderByChild("email").equalTo(userEmailToDelete);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Obtén la clave del usuario que coincida con el email
                        String userKey = userSnapshot.getKey();

                        // Elimina el usuario utilizando la clave obtenida
                        databaseReference.child("user").child(userKey).removeValue();

                        // Muestra un mensaje de éxito
                        progressDialog.setMessage("Estableciendo la cuenta");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores si es necesario
                }
            });
        } else {
            // Manejar el caso en el que el email esté vacío
            Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
        }
    }
}
