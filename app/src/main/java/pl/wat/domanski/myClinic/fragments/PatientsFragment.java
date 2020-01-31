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
import pl.wat.domanski.myClinic.adapters.PatientsAdapter;
import pl.wat.domanski.myClinic.database.ClinicRepository;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.database.PatientAddress;
import pl.wat.domanski.myClinic.database.PatientContact;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PatientsFragment extends Fragment {

    ClinicViewModel clinicViewModel;
    PatientsAdapter adapter = new PatientsAdapter();
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patients_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        closeKeyboard();

        searchView = root.findViewById(R.id.searchViewPatients);
        FloatingActionButton fab = root.findViewById(R.id.fabAddPatient);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerViewPatients);

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
                AddEditPatientFragment addEditPatientFragment = new AddEditPatientFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addEditPatientFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        clinicViewModel.getAllPatients().observe(this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                adapter.getFilter().filter(" ");
                adapter.setPatients(patients);
            }
        });

        adapter.setOnItemClickListener(new PatientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Patient patient) {

                Bundle patientBundle = new Bundle();
                patientBundle.putString("PatientFirstname", patient.getFirstName());
                patientBundle.putString("PatientLastname", patient.getLastName());
                patientBundle.putLong("PatientBirthdate", patient.getBirthDate());
                patientBundle.putString("PatientPesel", patient.getPesel());
                patientBundle.putString("PatientNote", patient.getNote());
                patientBundle.putInt("PatientId", patient.getId());
                patientBundle.putLong("PatientCreateDate", patient.getCreateDate());


                clinicViewModel.getPatientAddressByPatientId(patient.getId(), new ClinicRepository.OnPatientAddressLoaded() {
                    @Override
                    public void loaded(PatientAddress patientAddress) {
                        if (patientAddress != null) {
                            patientBundle.putInt("PatientAddressId", patientAddress.getId());
                            patientBundle.putString("PatientAddress", patientAddress.getAddress());
                            patientBundle.putString("PatientCity", patientAddress.getCity());
                            patientBundle.putString("PatientZipcode", patientAddress.getZipCode());
                        }
                    }
                });

                clinicViewModel.getPatientContactByPatientId(patient.getId(), new ClinicRepository.OnPatientContactLoaded() {
                    @Override
                    public void loaded(PatientContact patientContact) {
                        if (patientContact != null) {
                            patientBundle.putInt("PatientContactId", patientContact.getId());
                            patientBundle.putString("PatientEmail", patientContact.getEmail());
                            patientBundle.putString("PatientPhone", patientContact.getPhone());
                        }
                    }
                });

                PatientInfoFragment patientInfoFragment = new PatientInfoFragment();
                patientInfoFragment.setArguments(patientBundle);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, patientInfoFragment)
                        .addToBackStack(null)
                        .commit();
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
