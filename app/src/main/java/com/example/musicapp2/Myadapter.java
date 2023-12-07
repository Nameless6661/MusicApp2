package com.example.musicapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    Context context;
    ArrayList<Users> list;

    public Myadapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users user = list.get(position);
        holder.mail.setText(user.getMail());
       // holder.password.setText(user.getMail());
        //holder.profilepic.setText(user.getUserName());
        holder.status.setText(user.getStatus());
        //holder.userId.setText(user.getUserName());
        holder.userName.setText(user.getUserName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView profilepic,mail,userName,password,userId,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mail=itemView.findViewById(R.id.textmail);
            //password=itemView.findViewById(R.id.textpassword);
            //profilepic=itemView.findViewById(R.id.textpfp);
            status=itemView.findViewById(R.id.textstatus);
            //userId=itemView.findViewById(R.id.textuserid);
            userName=itemView.findViewById(R.id.textuserName);
        }
    }
}
