package pl.wat.domanski.myClinic.dialogs;

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
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.fragments.DialogContract;
import pl.wat.domanski.myClinic.fragments.PatientsAdapter;

import java.util.List;

public class PatientDialogFragment extends DialogFragment {

    private PatientsAdapter adapter;
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
        adapter = new PatientsAdapter();
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        clinicViewModel.getAllPatients().observe(this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                adapter.getFilter().filter(" ");
                adapter.setPatients(patients);
            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PatientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Patient patient) {
                mHost.sendPatientData(patient);
                dismiss();
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Wybierz pacjenta");
        return builder.create();
    }
}