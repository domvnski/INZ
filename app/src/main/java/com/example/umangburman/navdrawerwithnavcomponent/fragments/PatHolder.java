package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;

public class PatHolder extends RecyclerView.ViewHolder {

    TextView textViewPersonName;



    public PatHolder(@NonNull View itemView) {
        super(itemView);
        textViewPersonName = itemView.findViewById(R.id.textViewPersonName);

    }
}
