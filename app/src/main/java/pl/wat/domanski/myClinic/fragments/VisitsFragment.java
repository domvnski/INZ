package pl.wat.domanski.myClinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
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


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.adapters.VisitsAdapter;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Visit;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VisitsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    private CalendarView calendarVisits;
    private TextView textViewNoVisits;
    private long longDate = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.visits_fragment, container, false);
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        closeKeyboard();

        calendarVisits = root.findViewById(R.id.calendarVisits);
        textViewNoVisits = root.findViewById(R.id.textViewVisitsNoVisits);
        FloatingActionButton fab = root.findViewById(R.id.fabAddVisit);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewVisits);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditVisitFragment addEditVisitFragment = new AddEditVisitFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addEditVisitFragment, "myTag")
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final VisitsAdapter adapter = new VisitsAdapter();
        recyclerView.setAdapter(adapter);

        showTodayVisits(adapter);
        adapter.setClinicViewModel(clinicViewModel);
        adapter.setActivity(getActivity());
        adapter.setOnItemClickListener(new VisitsAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Visit visit) {
                showConfirmDeleteSnackbar(visit);
            }

            @Override
            public void onEditClick(Visit visit) {
                goToEditVisitFragment(visit);
            }
        });

        calendarVisits.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                showVisitsByDate(year, month, dayOfMonth, adapter);
            }
        });

        return root;

    }

    private void showVisitsByDate(int year, int month, int dayOfMonth, VisitsAdapter adapter) {
        month += 1;
        String textDate = dayOfMonth + "/" + month + "/" + year;

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        Date date;

        try {
            date = simpleDate.parse(textDate);
            longDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        clinicViewModel.getVisitsByDate(longDate).observe(VisitsFragment.this, new Observer<List<Visit>>() {
            @Override
            public void onChanged(List<Visit> visits) {
                if (visits.size() != 0) textViewNoVisits.setVisibility(View.INVISIBLE);
                else textViewNoVisits.setVisibility(View.VISIBLE);

                adapter.setVisits(visits);
            }
        });
    }

    private void goToEditVisitFragment(Visit visit) {
        Bundle visitBundle = new Bundle();
        visitBundle.putInt("VisitId", visit.getId());
        visitBundle.putInt("VisitPatientId", visit.getPatientId());
        visitBundle.putInt("VisitDoctorId", visit.getDoctorId());
        visitBundle.putLong("VisitDate", visit.getDate());
        visitBundle.putLong("VisitStartTime", visit.getTimeStart());
        visitBundle.putLong("VisitEndTime", visit.getTimeEnd());
        visitBundle.putString("VisitDescription", visit.getDescription());

        AddEditVisitFragment addEditVisitFragment = new AddEditVisitFragment();
        addEditVisitFragment.setArguments(visitBundle);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, addEditVisitFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showTodayVisits(VisitsAdapter adapter) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth += 1;
        String textCurrentDate = (mDay + "/" + mMonth + "/" + mYear);
        long longCurrentDate = 0;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        try {
            birthDate = format.parse(textCurrentDate);
            longCurrentDate = birthDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        clinicViewModel.getVisitsByDate(longCurrentDate).observe(VisitsFragment.this, new Observer<List<Visit>>() {
            @Override
            public void onChanged(List<Visit> visits) {
                if (visits.size() != 0) textViewNoVisits.setVisibility(View.INVISIBLE);
                else textViewNoVisits.setVisibility(View.VISIBLE);
                adapter.setVisits(visits);
            }
        });
    }

    private void showConfirmDeleteSnackbar(final Visit visit) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Czy na pewno chcesz usunąć wizytę?", Snackbar.LENGTH_LONG)
                .setAction("TAK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicViewModel.deleteVisit(visit);
                        Toast.makeText(getActivity(), "Usunięto", Toast.LENGTH_SHORT).show();
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}