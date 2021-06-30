package com.example.vibe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibe.Models.MessagesModel;
import com.example.vibe.R;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessagesModel> messagesModels;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public GroupChatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel = messagesModels.get(position);

        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMsg.setText(messagesModel.getMessage());
        }
        else{
            ((ReceiverViewHolder)holder).receiverMsg.setText(messagesModel.getMessage());
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
