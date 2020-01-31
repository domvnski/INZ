package pl.wat.domanski.myClinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.adapters.DoctorsAdapter;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Doctor;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DoctorsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    private DoctorsAdapter adapter = new DoctorsAdapter();
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.doctors_fragment, container, false);

        closeKeyboard();
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);

        searchView = root.findViewById(R.id.searchViewDoctors);
        FloatingActionButton fab = root.findViewById(R.id.fabAddDoctor);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewDoctors);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditDoctorFragment addEditDoctorFragment = new AddEditDoctorFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addEditDoctorFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        adapter.setOnItemClickListener(new DoctorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor) {

                Bundle doctorBundle = new Bundle();
                doctorBundle.putInt("DoctorId", doctor.getId());
                doctorBundle.putString("DoctorFirstname", doctor.getFirstName());
                doctorBundle.putString("DoctorLastname", doctor.getLastName());
                doctorBundle.putString("DoctorPhone", doctor.getPhone());
                doctorBundle.putString("DoctorEmail", doctor.getEmail());
                doctorBundle.putString("DoctorSpecialization", doctor.getSpecialization());
                doctorBundle.putString("DoctorPwzNumber", doctor.getPwzNumber());
                doctorBundle.putLong("DoctorCreateDate", doctor.getCreateDate());


                DoctorInfoFragment doctorInfoFragment = new DoctorInfoFragment();
                doctorInfoFragment.setArguments(doctorBundle);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, doctorInfoFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        clinicViewModel.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                adapter.getFilter().filter(" ");
                adapter.setDoctors(doctors);
            }
        });

        return root;
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
