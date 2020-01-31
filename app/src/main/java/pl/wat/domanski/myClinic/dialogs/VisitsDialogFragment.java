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
import pl.wat.domanski.myClinic.adapters.VisitsAdapter;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Visit;

import java.util.List;

public class VisitsDialogFragment extends DialogFragment {

    private VisitsAdapter adapter;
    private ClinicViewModel clinicViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.visits_dialog, null);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewVisitsDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        adapter = new VisitsAdapter();
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        adapter.setClinicViewModel(clinicViewModel);
        adapter.setActivity(getActivity());

        int patientId = 0, doctorId = 0;
        try {
            patientId = getArguments().getInt("VisitPatientId");
            doctorId = getArguments().getInt("VisitDoctorId");

        } catch (Exception ex) {

        }

        if (patientId == 0 && doctorId != 0) {
            clinicViewModel.getVisitsByDoctorId(doctorId).observe(this, new Observer<List<Visit>>() {
                @Override
                public void onChanged(List<Visit> visits) {
                    adapter.setVisits(visits);
                }
            });
        } else {
            clinicViewModel.getVisitsByPatientId(patientId).observe(this, new Observer<List<Visit>>() {
                @Override
                public void onChanged(List<Visit> visits) {
                    adapter.setVisits(visits);
                }
            });
        }

        recyclerView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Wizyty");
        return builder.create();
    }
}