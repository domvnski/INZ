package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;

public class PatientDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private PatientsAdapter adapter;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        final Dialog dialog = getDialog();
        dialog.setCancelable(true);
        dialog.setTitle("Add a picture to your aircraft:");

        recyclerView =  root.findViewById(R.id.recyclerViewPatientsDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new PatientsAdapter();
        recyclerView.setAdapter(adapter);

//        FrameLayout host = getView().findViewById(R.id.nav_host_fragment);
//        host.removeAllViews();

        dialog.setTitle("Wybierz pacjenta");
        dialog.setContentView(recyclerView);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder= new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

}