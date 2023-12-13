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
    String userEmailToDelete;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);
        btneliminar = findViewById(R.id.eliminarbtn);
        atras = findViewById(R.id.atras);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txt = findViewById(R.id.txt); // Reemplaza con el ID de tu EditText


        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eliminarUsuario();
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
        userEmailToDelete = txt.getText().toString();
        if (!TextUtils.isEmpty(userEmailToDelete)) {
            Query query = databaseReference.child("user").orderByChild("mail").equalTo(userEmailToDelete);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Obtén la clave del usuario que coincida con el email
                        String userKey = userSnapshot.getKey();

                        // Elimina el usuario utilizando la clave obtenida
                        assert userKey != null;
                        databaseReference.child("user").child(userKey).removeValue();

                        // Muestra un mensaje de éxito
                        progressDialog.setMessage("Estableciendo la cuenta");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores si es necesario
                    Toast.makeText(getApplicationContext(), "Error en la consulta a Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });

        
        } else {
            // Manejar el caso en el que el email esté vacío
            Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
        }
    }
}
