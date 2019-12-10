package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientHolder> {
    private List<Patient> patients = new ArrayList<>();

    class PatientHolder extends RecyclerView.ViewHolder {
        private TextView textViewPersonName;

        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);
        }
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        return new PatientHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        Patient currentPatient = patients.get(position);
        holder.textViewPersonName.setText(currentPatient.getLastName() + " " + currentPatient.getFirstName() + " " + currentPatient.getBirthDate());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(List<Patient> patients){
        this.patients = patients;
        notifyDataSetChanged();
    }

    public Patient getPatientAt(int position){
        return patients.get(position);
    }



}
