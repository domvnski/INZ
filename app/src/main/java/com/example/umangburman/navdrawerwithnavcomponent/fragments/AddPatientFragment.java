package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPatientFragment extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthDate;
    private EditText editTextNote;
    private Button buttonSavePatient;
    private Button buttonCancel;

    ClinicViewModel clinicViewModel;
    Patient patient;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_patient_fragment, container, false);

        editTextFirstName = root.findViewById(R.id.addPatientFirstnameEditText);
        editTextLastName = root.findViewById(R.id.addPatientLastNameEditText);
        editTextBirthDate = root.findViewById(R.id.addPatientBirthDateEditText);
        editTextNote = root.findViewById(R.id.addPatientNoteEditText);
        buttonSavePatient = root.findViewById(R.id.buttonSaveNewPatient);
        buttonCancel = root.findViewById(R.id.buttonCancel);


        try {
            String firstName = getArguments().getString("PatientFirstName");
            String lastName = getArguments().getString("PatientLastName");
            Long birthDate = getArguments().getLong("PatientBirthDate");
            String note = getArguments().getString("PatientNote");

            Date date = new Date(birthDate);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String textBirthDate = format.format(date);

            editTextFirstName.setText(firstName);
            editTextLastName.setText(lastName);
            editTextBirthDate.setText(textBirthDate);
            editTextNote.setText(note);

        } catch (NullPointerException ex) {
//            Toast.makeText(getActivity(), "exception:" + ex, Toast.LENGTH_SHORT).show();
        }


        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPatientSaved = savePatient();
                if (isPatientSaved) goBack();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
                Toast.makeText(getActivity(), "Anulowano", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String textMonth = Integer.toString(monthOfYear + 1);
                        String textDay = Integer.toString(dayOfMonth);
                        if (monthOfYear < 10) {
                            textMonth = "0" + monthOfYear;
                        }
                        if (dayOfMonth < 10) {
                            textDay = "0" + dayOfMonth;
                        }

                        editTextBirthDate.setText(textDay + "/" + textMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void goBack() {

        PatientsFragment patientFragment = new PatientsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, patientFragment)
                .addToBackStack(null)
                .commit();
    }


    private boolean savePatient() {

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String textBirthDate = editTextBirthDate.getText().toString();
        Long longBirthDate = new Long(0);


        String note = editTextNote.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        try {
            birthDate = format.parse(textBirthDate);
            longBirthDate = birthDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);
            try {
                int bundleId = getArguments().getInt("PatientId");
                patient = new Patient(firstName, lastName, longBirthDate, note);
                patient.setId(bundleId);
                clinicViewModel.updatePatient(patient);
                Toast.makeText(getActivity(), "Pacjent zaktualizowany", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException ex) {
                patient = new Patient(firstName, lastName, longBirthDate, note);
                clinicViewModel.insertPatient(patient);
                Toast.makeText(getActivity(), "Pacjent Zapisany", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

}
