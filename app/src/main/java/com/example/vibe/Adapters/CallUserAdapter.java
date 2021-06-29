package com.example.vibe.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.vibe.CallActivity;
import com.example.vibe.MainActivity;
import com.example.vibe.Models.Users;
import com.example.vibe.R;
import com.example.vibe.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CallUserAdapter extends RecyclerView.Adapter<CallUserAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;

    public CallUserAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_call_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users users = list.get(position);

        // using images which is in firebase
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar).into(holder.image);
        // getting username
        holder.userName.setText(users.getUserName());
        // getting phone number
        holder.phoneNumber.setText(users.getPhoneNumber());

        String phone = users.getPhoneNumber();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CallActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("profilePic",users.getProfilePic());
                intent.putExtra("userName",users.getUserName());
                //
                //
                intent.putExtra("phoneNumber",users.getPhoneNumber());// added phone number 13th june
                intent.putExtra("status",users.getStatus());
                context.startActivity(intent);


//                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phone));
//                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
//                }
//                else{
//                    context.startActivity(i);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView userName, phoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userNameList);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
        }
    }
}
