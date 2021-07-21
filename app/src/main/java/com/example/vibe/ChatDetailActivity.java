package com.example.vibe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vibe.Adapters.chatAdapter;
import com.example.vibe.Models.MessagesModel;
import com.example.vibe.databinding.ActivityChatDetailActicityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailActicityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    //
    //
    //
    // added 15th june
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailActicityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final String senderId = auth.getUid();

        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");
        //
        //
        //
        String phoneNumber = getIntent().getStringExtra("phoneNumber"); // added phone number 13th june
        String status = getIntent().getStringExtra("status");  //added status on 14th june


        binding.userName.setText(userName);

        // getting profile image from database
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //
        //
        // added phone number 13th june

        b = new Bundle();

        binding.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //
                //
                //added on 15th june
                b.putString("name",userName);
                b.putString("number",phoneNumber);
                b.putString("pic",profilePic);
                b.putString("status",status);

                friendProfile f = new friendProfile();
                f.setArguments(b);
                f.show(getSupportFragmentManager(),"My fragment");
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();

        final String senderRoom = senderId + receiveId;
        final String receiverRoom = receiveId + senderId;


        ///////////////
        ////////////////
//        final chatAdapter adapter = new chatAdapter(messagesModels,this,senderRoom,receiverRoom);
//        binding.chatRecyclerView.setAdapter(adapter);


        final chatAdapter cAdapter = new chatAdapter(messagesModels,this, receiveId,senderRoom,receiverRoom);
        binding.chatRecyclerView.setAdapter(cAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);



        //
        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            MessagesModel model = snapshot1.getValue(MessagesModel.class);

                            model.setMessageId(snapshot1.getKey());

                            messagesModels.add(model);
                        }
                        cAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //
        //
        //
        //
        //added 14th june
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etMessage.getText().toString().isEmpty()){
                    binding.etMessage.setError("Type a message!");
                    return;
                }

                String message = binding.etMessage.getText().toString();
                final MessagesModel model = new MessagesModel(senderId,message);
                model.setTimeStamp(new Date().getTime());

                binding.etMessage.setText("");

                // added 14th june
                String randomKey = database.getReference().push().getKey();

                database.getReference().child("chats")
                        .child(senderRoom)
                        .child(randomKey)
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .child(randomKey)
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });

            }
        });

//        binding.send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(binding.etMessage.getText().toString().isEmpty()){
//                    binding.etMessage.setError("Type a message!");
//                    return;
//                }
//
//                String message = binding.etMessage.getText().toString();
//                final MessagesModel model = new MessagesModel(senderId,message);
//                model.setTimeStamp(new Date().getTime());
//
//                binding.etMessage.setText("");
//
//                database.getReference().child("chats")
//                        .child(senderRoom)
//                        .push()
//                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        database.getReference().child("chats")
//                                .child(receiverRoom)
//                                .push()
//                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//
//                            }
//                        });
//                    }
//                });
//
//            }
//        });

    }
}