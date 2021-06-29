package com.example.vibe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.vibe.Fragments.CallFragment;
import com.example.vibe.Fragments.ChatFragment;
import com.example.vibe.databinding.ActivityCallBinding;
import com.example.vibe.databinding.ActivityFriendProfileBinding;
import com.squareup.picasso.Picasso;

public class CallActivity extends AppCompatActivity {

    ActivityCallBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        String friendName = getIntent().getStringExtra("userName");
        String friendPic = getIntent().getStringExtra("profilePic");
        String friendNumber = getIntent().getStringExtra("phoneNumber");
//        String friendStatus = getIntent().getStringExtra("status");

        binding.frndNumber.setText(friendNumber);
        binding.frndName.setText(friendName);
        Picasso.get().load(friendPic).placeholder(R.drawable.avatar).into(binding.frndImg);
//        binding.frndStatus.setText(friendStatus);

        binding.callFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ friendNumber));
                if(ActivityCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CallActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else{
                    startActivity(i);
                }
            }
        });

        binding.backToCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(CallActivity.this, MainActivity.class);
                startActivity(ii);
            }
        });
    }
}