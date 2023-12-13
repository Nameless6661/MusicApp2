package com.example.musicapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class Myadapter extends FirebaseRecyclerAdapter<Users, Myadapter.MyViewHolder> {
    public RetreiveData context;


    public Myadapter(@NonNull FirebaseRecyclerOptions<Users> options, RetreiveData context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int i, @NonNull Users users) {
        getRef(i).getKey();

        myViewHolder.userName.setText(users.getUserName());
        myViewHolder.mail.setText(users.getMail());
        myViewHolder.password.setText(users.getPassword());
        myViewHolder.status.setText(users.getStatus());

        myViewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.edit))
                        .setExpanded(false)
                        .create();

                View holderView = (LinearLayout) dialogPlus.getHolderView();

                EditText username = holderView.findViewById(R.id.username);
                EditText mail = holderView.findViewById(R.id.email);
                EditText password = holderView.findViewById(R.id.password);
                EditText status = holderView.findViewById(R.id.estatus);

                username.setText(users.getUserName());
                mail.setText(users.getMail());
                password.setText(users.getPassword());
                status.setText(users.getStatus());

                Button Update = holderView.findViewById(R.id.update);

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();

                        map.put("userName",username.getText().toString());
                        map.put("mail",mail.getText().toString());
                        map.put("password",password.getText().toString());
                        map.put("status",status.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("user")
                                .child(getRef(i).getKey())
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

                dialogPlus.show();
            }
        });

        myViewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("user")
                        .child(getRef(i).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Usuario Eliminado",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    @NonNull
    @Override
    public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_modificar,parent,false);
        return new Myadapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName, mail, password, status;
        ImageView Edit, Delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.username);
            mail=itemView.findViewById(R.id.email);
            password=itemView.findViewById(R.id.password);
            status=itemView.findViewById(R.id.estatus);

            Edit = itemView.findViewById(R.id.edit);
            Delete = itemView.findViewById(R.id.delete);
        }
    }
}
