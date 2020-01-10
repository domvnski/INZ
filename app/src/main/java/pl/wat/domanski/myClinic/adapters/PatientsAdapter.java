package pl.wat.domanski.myClinic.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.Patient;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientHolder> implements Filterable {
    private List<Patient> patients = new ArrayList<>();
    private List<Patient> patientsFull;
    OnItemClickListener listener;

    class PatientHolder extends RecyclerView.ViewHolder {
        private TextView textViewPersonName;

        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(patients.get(position));
                    }
                }
            });
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
        holder.textViewPersonName.setText(currentPatient.getLastName() + " " + currentPatient.getFirstName());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
        patientsFull = new ArrayList<>(patients);
        notifyDataSetChanged();
    }


    public Patient getPatientAt(int position) {
        return patients.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Patient patient);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return patientsFilter;
    }

    private Filter patientsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Patient> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(patientsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Patient patient : patientsFull) {
                    if ((patient.getFirstName().toLowerCase().contains(filterPattern)
                            || (patient.getLastName().toLowerCase().contains(filterPattern)))
                            && (!patient.getFirstName().toLowerCase().contains("***"))) {
                        filteredList.add(patient);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            patients.clear();
            patients.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
