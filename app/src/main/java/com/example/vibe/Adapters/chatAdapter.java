package com.example.vibe.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibe.Models.MessagesModel;
import com.example.vibe.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter{

    ArrayList<MessagesModel> messagesModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    //////////////
    String senderRoom,receiverRoom;

    public chatAdapter(ArrayList<MessagesModel> messagesModels, Context context, String recId, String senderRoom, String receiverRoom) {
        this.messagesModels = messagesModels;
        this.context = context;
        this.recId = recId;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }

    public chatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    public chatAdapter(ArrayList<MessagesModel> messagesModels, Context context, String recId) {
        this.messagesModels = messagesModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return  new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return  new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(messagesModels.get(position).getuId().equals((FirebaseAuth.getInstance().getUid())))
        {
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel = messagesModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;

                                database.getReference().child("chats")
                                        .child(senderRoom)
                                        .child(messagesModel.getMessageId())
                                        .setValue(null);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        //
        //
        //
        //
        // added on 14th june reactions

        int reactions[] = new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry,
        };

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            if(holder.getClass()== SenderViewHolder.class) {
                if (pos >=0 && pos <= 6) {
                    ((SenderViewHolder) holder).feelingS.setImageResource(reactions[pos]);
                    ((SenderViewHolder) holder).feelingS.setVisibility(View.VISIBLE);
                }
                else{
                    ((SenderViewHolder) holder).feelingS.setVisibility(View.VISIBLE);
                    ((SenderViewHolder) holder).feelingS.setVisibility(View.GONE);
                }
            }
            else {
                if (pos >= 0 && pos <= 6) {
                    ((ReceiverViewHolder) holder).feelingR.setImageResource(reactions[pos]);
                    ((ReceiverViewHolder) holder).feelingR.setVisibility(View.VISIBLE);
                }
                else{
                    ((ReceiverViewHolder) holder).feelingR.setVisibility(View.VISIBLE);
                    ((ReceiverViewHolder) holder).feelingR.setVisibility(View.GONE);
                }
            }
            messagesModel.setFeeling(pos);

            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(senderRoom)
                    .child(messagesModel.getMessageId())
                    .setValue(messagesModel);

            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(receiverRoom)
                    .child(messagesModel.getMessageId())
                    .setValue(messagesModel);

            return true; // true is closing popup, false is requesting a new selection
        });


        if(holder.getClass()== SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMsg.setText(messagesModel.getMessage());

            // added 14th june
            if(messagesModel.getFeeling() >= 0)
            {
//                messagesModel.setFeeling(reactions[messagesModel.getFeeling()]);
                ((SenderViewHolder) holder).feelingS.setImageResource(reactions[messagesModel.getFeeling()]);
                ((SenderViewHolder) holder).feelingS.setVisibility(View.VISIBLE);
            }
            else
            {
                ((SenderViewHolder) holder).feelingS.setVisibility(View.GONE);
            }

            // added 14th june

            ((SenderViewHolder)holder).senderMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    popup.onTouch(view,motionEvent);
                    return false;
                }
            });

        }
        else{
            ((ReceiverViewHolder)holder).receiverMsg.setText((messagesModel).getMessage());

            // added 14th june
            if(messagesModel.getFeeling() >= 0)
            {
//                messagesModel.setFeeling(reactions[messagesModel.getFeeling()]);
                ((ReceiverViewHolder) holder).feelingR.setImageResource(reactions[messagesModel.getFeeling()]);
                ((ReceiverViewHolder) holder).feelingR.setVisibility(View.VISIBLE);
            }
            else
            {
                ((ReceiverViewHolder) holder).feelingR.setVisibility(View.GONE);
            }

            // added 14th june
            ((ReceiverViewHolder)holder).receiverMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    popup.onTouch(view,motionEvent);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg,receiverTime;
        ImageView feelingR;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
            feelingR = itemView.findViewById(R.id.feelingR);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        ImageView feelingS;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
            feelingS = itemView.findViewById(R.id.feelingS);
        }
    }

}
