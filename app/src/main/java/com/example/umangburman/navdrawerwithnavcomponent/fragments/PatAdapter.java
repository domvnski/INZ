package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatAdapter extends RecyclerView.Adapter<PatHolder> {

    Context c;
    private List<Patient> patients = new ArrayList<>();
    Patient patient1 = new Patient("Patryk", "Strzechowski", new Date().getTime(), "note");
    Patient patient2 = new Patient("Patryk1", "Strzechowski2", new Date().getTime(), "note");
    Patient patient3 = new Patient("Patryk2", "Strzechowski2", new Date().getTime(), "note");


    public PatAdapter(Context c, List<Patient> patients) {
        this.c = c;
        this.patients = patients;
        this.patients.add(patient1);
        this.patients.add(patient2);
        this.patients.add(patient3);
    }

    @NonNull
    @Override
    public PatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        PatHolder patHolder = new PatHolder(itemView);
        return patHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatHolder holder, int position) {
        Patient currentPatient = patients.get(position);
        holder.textViewPersonName.setText(currentPatient.getLastName() + " " + currentPatient.getFirstName());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }
}
