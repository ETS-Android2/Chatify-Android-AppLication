package com.example.vibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.vibe.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class friendProfile extends DialogFragment {

    TextView frndName,frndStatus,frndNumber;
    ImageView frndImg;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_friend_profile,container,false);

        frndName = view.findViewById(R.id.frndName);
        frndNumber = view.findViewById(R.id.frndNumber);
        frndStatus = view.findViewById(R.id.frndStatus);
        frndImg = view.findViewById(R.id.frndImg);

        String name = this.getArguments().getString("name");
        String number = this.getArguments().getString("number");
        String pic = this.getArguments().getString("pic");
        String status = this.getArguments().getString("status");

        frndName.setText(name);
        frndStatus.setText(status);
        frndNumber.setText(number);
        Picasso.get().load(pic).placeholder(R.drawable.avatar).into(frndImg);

        return view ;
    }
}
