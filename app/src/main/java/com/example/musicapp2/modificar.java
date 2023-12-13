package com.example.musicapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class modificar extends AppCompatActivity {
    Button atras, btnModificarUsuario, buscarBtn;
    EditText editTextNewEmail, editTextNewPassword, editTextNewStatus, editTextNewUserName, txtEmail;
    DatabaseReference databaseReference;
    String newEmail, newPassword, newStatus,newUserName;
    ListView listView;
    Users users;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar2);
        atras = findViewById(R.id.atras);
        editTextNewEmail = findViewById(R.id.txtMail);
        editTextNewPassword = findViewById(R.id.txtPassword);
        editTextNewStatus = findViewById(R.id.textstatus);
        editTextNewUserName = findViewById(R.id.textuserName);
        btnModificarUsuario = findViewById(R.id.modificarbtn);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        buscarBtn = findViewById(R.id.buscarBtn);
        txtEmail = findViewById(R.id.txtEmail);





        btnModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos datos desde las cajas de texto
                newEmail = editTextNewEmail.getText().toString();
                newPassword = editTextNewPassword.getText().toString();
                newStatus = editTextNewStatus.getText().toString();
                newUserName = editTextNewUserName.getText().toString();

                // Modificar el usuario con los nuevos datos
                modificarUsuario(newEmail, newPassword, newStatus, newUserName);
            }
        });



        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(modificar.this,listar.class);
                startActivity(intent);
                finish();
            }
        });



        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailToSearch = txtEmail.getText().toString();

                if (!TextUtils.isEmpty(userEmailToSearch)) {
                    buscarUsuarioPorEmail(userEmailToSearch);
                } else {
                    // Manejar el caso en el que el email esté vacío
                    Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private void buscarUsuarioPorEmail(String userEmail) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("user").orderByChild("mail").equalTo(newEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Usuario encontrado, puedes llenar las cajas de texto con sus datos actuales si es necesario
                    // Por ejemplo:
                    Users users = dataSnapshot.getChildren().iterator().next().getValue(Users.class);
                    assert users != null;
                    editTextNewEmail.setText(users.getMail());
                    editTextNewPassword.setText(users.getPassword());
                    editTextNewStatus.setText(users.getStatus());
                    editTextNewUserName.setText(users.getUserName());
                } else {
                    // Usuario no encontrado
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado con ese correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
                Toast.makeText(getApplicationContext(), "Error en la consulta a Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modificarUsuario(String newEmail, String newPassword, String newStatus, String newUserName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("user").orderByChild("mail").equalTo(newEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();

                    // Crear un mapa para almacenar los nuevos datos a actualizar
                    Map<String, Object> updates = new HashMap<>();

                    // Agregar los nuevos datos al mapa solo si no son nulos o cadenas vacías

                    if (!TextUtils.isEmpty(newPassword)) {
                        updates.put("password", newPassword);
                    }
                    if (!TextUtils.isEmpty(newStatus)) {
                        updates.put("status", newStatus);
                    }
                    if (!TextUtils.isEmpty(newUserName)) {
                        updates.put("userName", newUserName);
                    }

                    // Actualizar los datos del usuario en la base de datos
                    assert userKey != null;
                    databaseReference.child("user").child(userKey).updateChildren(updates);

                    // Mostrar un mensaje de éxito
                    Toast.makeText(getApplicationContext(), "Usuario modificado correctamente", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
                Toast.makeText(getApplicationContext(), "Error en la consulta a Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
