package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;
import com.example.umangburman.navdrawerwithnavcomponent.database.Visit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitHolder> {
    private List<Visit> visits = new ArrayList<>();
    OnItemClickListener listener;

    class VisitHolder extends RecyclerView.ViewHolder {
        private TextView textViewPatientName;
        private TextView textViewDoctorName;
        private TextView textViewDate;
        private TextView textViewStartTime;
        private TextView textViewEndTime;
        private TextView textViewDescription;

        public VisitHolder(@NonNull View itemView) {
            super(itemView);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientName);
            textViewDoctorName = itemView.findViewById(R.id.textViewDoctorName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(patients.get(position));
//                    }
//                }
//            });
        }
    }

    @NonNull
    @Override
    public VisitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visit_item, parent, false);
        return new VisitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitHolder holder, int position) {
        Visit currentVisit = visits.get(position);
        int patientId = currentVisit.getPatientId();
        int doctorId = currentVisit.getDoctorId();

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm:ss");

        holder.textViewPatientName.setText(Integer.toString(currentVisit.getPatientId()));
        holder.textViewDoctorName.setText(Integer.toString(currentVisit.getDoctorId()));
        holder.textViewDate.setText(simpleDate.format((new Date(currentVisit.getDate()))));
        holder.textViewStartTime.setText(simpleTime.format((new Date(currentVisit.getTimeStart()))));
        holder.textViewEndTime.setText(simpleTime.format((new Date(currentVisit.getTimeEnd()))));
        holder.textViewDescription.setText(currentVisit.getDescription());
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
        notifyDataSetChanged();
    }

    public Visit getVisitAt(int position) {
        return visits.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Visit visit);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
