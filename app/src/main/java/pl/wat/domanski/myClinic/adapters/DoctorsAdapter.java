package pl.wat.domanski.myClinic.adapters;

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
import pl.wat.domanski.myClinic.database.Doctor;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorHolder> implements Filterable {
    private List<Doctor> doctors = new ArrayList<>();
    private List<Doctor> doctorsFull;
    OnItemClickListener listener;

    class DoctorHolder extends RecyclerView.ViewHolder {
        private TextView textViewPersonName;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(doctors.get(position));
                    }
                }
            });
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
        holder.textViewPersonName.setText(currentDoctor.getLastName() + " " + currentDoctor.getFirstName());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
        doctorsFull = new ArrayList<>(doctors);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return doctorsFilter;
    }

    private Filter doctorsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Doctor> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(doctorsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Doctor doctor : doctorsFull) {
                    if (doctor.getFirstName().toLowerCase().contains(filterPattern)
                            || (doctor.getLastName().toLowerCase().contains(filterPattern))
                            || (doctor.getEmail().toLowerCase().contains(filterPattern))
                            || (doctor.getPhone().contains(filterPattern))) {
                        filteredList.add(doctor);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            doctors.clear();
            doctors.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
