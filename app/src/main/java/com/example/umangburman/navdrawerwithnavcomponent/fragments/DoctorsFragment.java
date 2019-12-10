package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Doctor;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DoctorsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    private Drawable icon;
    private final ColorDrawable background = new ColorDrawable();
    private TextView textEmptyRecycler;

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
        View root = inflater.inflate(R.layout.doctors_fragment, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fabAddDoctor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDoctorFragment addDoctorFragment = new AddDoctorFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addDoctorFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final DoctorsAdapter adapter = new DoctorsAdapter();
        recyclerView.setAdapter(adapter);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        clinicViewModel.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                adapter.setDoctors(doctors);
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
                Doctor doctorToDelete = adapter.getDoctorAt(viewHolder.getAdapterPosition());
                deleteDoctor(doctorToDelete);
                showUndoDeleteSnackbar(doctorToDelete);

            }
        }).attachToRecyclerView(recyclerView);


        return root;
    }


    private void deleteDoctor(Doctor doctor) {

        clinicViewModel.deleteDoctor(doctor);
    }

    private void showUndoDeleteSnackbar(final Doctor doctor) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Doktor Usunięty", Snackbar.LENGTH_LONG)
                .setAction("Cofnij", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicViewModel.insertDoctor(doctor);
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }

}
