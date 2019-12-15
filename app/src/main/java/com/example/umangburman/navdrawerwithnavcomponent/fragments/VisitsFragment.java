package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;
import com.example.umangburman.navdrawerwithnavcomponent.database.Visit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VisitsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    CalendarView calendarVisits;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.visits_fragment, container, false);

        calendarVisits = root.findViewById(R.id.calendarVisits);

        FloatingActionButton fab = root.findViewById(R.id.fabAddVisit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVisitFragment addVisitFragment = new AddVisitFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addVisitFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewVisits);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final VisitsAdapter adapter = new VisitsAdapter();
        recyclerView.setAdapter(adapter);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        clinicViewModel.getAllVisits().observe(this, new Observer<List<Visit>>() {
            @Override
            public void onChanged(List<Visit> visits) {
                adapter.setVisits(visits);
            }
        });

//        calendarVisits.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { todo
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                final String date;
//                date = dayOfMonth + "/" + month + "/" + year;
//                Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
//                final Bundle visitBundle = new Bundle();
//                visitBundle.putString("visitDate", date);
//                AddVisitFragment addVisitFragment = new AddVisitFragment();
//                addVisitFragment.setArguments(visitBundle);
//            }
//        });


        return root;

    }

}
