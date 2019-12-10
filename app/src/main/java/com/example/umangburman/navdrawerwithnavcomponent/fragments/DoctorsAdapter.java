package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorHolder> {
    private List<Doctor> doctors = new ArrayList<>();

    class DoctorHolder extends RecyclerView.ViewHolder {
        private TextView textViewPersonName;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);
        }
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        return new DoctorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor currentDoctor = doctors.get(position);
        holder.textViewPersonName.setText(currentDoctor.getLastName() + " " + currentDoctor.getFirstName() + " " + currentDoctor.getBirthDate());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setDoctors(List<Doctor> doctors){
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    public Doctor getDoctorAt(int position){
        return doctors.get(position);
    }



}
