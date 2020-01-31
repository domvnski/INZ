package pl.wat.domanski.myClinic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicRepository;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Doctor;
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.database.Visit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitHolder> {
    private List<Visit> visits = new ArrayList<>();
    ClinicViewModel clinicViewModel;
    OnItemClickListener listener;
    FragmentActivity activity;

    class VisitHolder extends RecyclerView.ViewHolder {
        private TextView textViewPatientName;
        private TextView textViewDoctorName;
        private TextView textViewDate;
        private TextView textViewStartTime;
        private TextView textViewEndTime;
        private TextView textViewDescription;
        private ImageView imageViewDelete;
        private ImageView imageViewEdit;

        public VisitHolder(@NonNull View itemView) {
            super(itemView);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientName);
            textViewDoctorName = itemView.findViewById(R.id.textViewDoctorName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewDelete = itemView.findViewById(R.id.imageViewDeleteVisit);
            imageViewEdit = itemView.findViewById(R.id.imageViewEditVisit);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(visits.get(position));
                    }
                }
            });

            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(visits.get(position));
                    }
                }
            });
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
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");

        clinicViewModel.getPatientById(patientId, new ClinicRepository.OnPatientLoaded() {
            @Override
            public void loaded(Patient patient) {
                if (patient != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.textViewPatientName.setText(patient.getFirstName() + " " + patient.getLastName());
                        }
                    });
                }
            }
        });
        clinicViewModel.getDoctorById(doctorId, new ClinicRepository.OnDoctorLoaded() {
            @Override
            public void loaded(Doctor doctor) {
                if (doctor != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.textViewDoctorName.setText("Dr " + doctor.getFirstName() + " " + doctor.getLastName());
                        }
                    });
                }
            }
        });

        holder.textViewDate.setText(simpleDate.format((new Date(currentVisit.getDate()))) + ", ");
        holder.textViewStartTime.setText(simpleTime.format((new Date(currentVisit.getTimeStart()))) + " - ");
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

    public void setClinicViewModel(ClinicViewModel clinicViewModel) {
        this.clinicViewModel = clinicViewModel;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public interface OnItemClickListener {
        void onDeleteClick(Visit visit);

        void onEditClick(Visit visit);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
