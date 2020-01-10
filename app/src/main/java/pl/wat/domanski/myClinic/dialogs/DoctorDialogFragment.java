package pl.wat.domanski.myClinic.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Doctor;

import java.util.List;

public class DoctorDialogFragment extends DialogFragment {

    private DoctorsAdapter adapter;
    private ClinicViewModel clinicViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogContract mHost = (DialogContract)getTargetFragment();
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.people_dialog, null);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewPeopleDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        adapter = new DoctorsAdapter();
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        clinicViewModel.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                adapter.setDoctors(doctors);
            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DoctorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor) {
                mHost.sendDoctorData(doctor);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Wybierz lekarza");
        return builder.create();
    }
}