package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.MainActivity;
import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PatientsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    private Drawable icon;
    private final ColorDrawable background = new ColorDrawable();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        background.setColor(getResources().getColor(R.color.colorPrimary));
        icon = ContextCompat.getDrawable(getContext(),
                R.drawable.ic_delete);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patients_fragment, container, false);


        FloatingActionButton fab = root.findViewById(R.id.fabAddPatient);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo resetowanie widoku?
//                 FrameLayout host = getView().findViewById(R.id.nav_host_fragment);
//                 host.removeAllViews();
                AddPatientFragment addPatientFragment = new AddPatientFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addPatientFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final PatientsAdapter adapter = new PatientsAdapter();
        recyclerView.setAdapter(adapter);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        clinicViewModel.getAllPatients().observe(this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                adapter.setPatients(patients);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;


                if (dX > 0) { // Swiping to the right
                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());

                } else if (dX < 0) { // Swiping to the left
                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX > 0) { // Swiping to the right
                    int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    int iconRight = itemView.getLeft() + iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());
                } else if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
                icon.draw(c);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Patient patientToDelete = adapter.getPatientAt(viewHolder.getAdapterPosition());
                deletePatient(patientToDelete);
                showUndoDeleteSnackbar(patientToDelete);

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PatientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Patient patient) {

                Bundle patientBundle = new Bundle();
                patientBundle.putString("PatientFirstName", patient.getFirstName());
                patientBundle.putString("PatientLastName", patient.getLastName());
                patientBundle.putLong("PatientBirthDate", patient.getBirthDate());
                patientBundle.putString("PatientNote", patient.getNote());
                patientBundle.putInt("PatientId", patient.getId());
                //set Fragmentclass Arguments
                AddPatientFragment addPatientFragment = new AddPatientFragment();
                addPatientFragment.setArguments(patientBundle);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addPatientFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return root;
    }

    private void deletePatient(Patient patient) {

        clinicViewModel.deletePatient(patient);
    }

    private void showUndoDeleteSnackbar(final Patient patient) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Pacjent Usunięty", Snackbar.LENGTH_LONG)
                .setAction("Cofnij", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicViewModel.insertPatient(patient);
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }


}
