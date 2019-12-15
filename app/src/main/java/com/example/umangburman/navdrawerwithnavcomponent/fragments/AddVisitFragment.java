package com.example.umangburman.navdrawerwithnavcomponent.fragments;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.umangburman.navdrawerwithnavcomponent.R;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicRepository;
import com.example.umangburman.navdrawerwithnavcomponent.database.ClinicViewModel;
import com.example.umangburman.navdrawerwithnavcomponent.database.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddVisitFragment extends Fragment {

    private EditText editTextPatient, editTextDate, editTextStartTime, editTextEndTime, editTextDoctor, editTextDescription;
    private Button buttonSaveVisit;
    private Button buttonCancel;

    ClinicViewModel clinicViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_visit_fragment, container, false);

        editTextPatient = root.findViewById(R.id.EditTextAddVisitPatient);
        editTextDate = root.findViewById(R.id.EditTextAddVisitDate);
        editTextStartTime = root.findViewById(R.id.EditTextAddVisitStartTime);
        editTextEndTime = root.findViewById(R.id.EditTextAddVisitEndTime);
        editTextDoctor = root.findViewById(R.id.EditTextAddVisitDoctor);
        editTextDescription = root.findViewById(R.id.textViewDescription);
        buttonSaveVisit = root.findViewById(R.id.buttonSaveNewVisit);
        buttonCancel = root.findViewById(R.id.buttonCancelNewVisit);

//        final FragmentManager fragmentManager = getFragmentManager();
        editTextPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PatientDialogFragment patientDialogFragment = new PatientDialogFragment();
                patientDialogFragment.show(getActivity().getSupportFragmentManager(), "myTag");
            }
        });


//        clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);
//        LiveData<Patient> patient = clinicViewModel.getPatientById(1);
        // editTextPatient.setText(patient.getValue().getFirstName() + patient.getValue().getLastName());


// todo
//        try {
//            String textVisitDate = getArguments().getString("visitDate");
//
////            Date visitDate;
////            Long longVisitDate = new Long(0);
////
////            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
////            try {
////                visitDate = format.parse(textVisitDate);
////                longVisitDate = visitDate.getTime();
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
//
//
//            editTextDate.setText(textVisitDate);
//
//        } catch (NullPointerException ex) {
//            Toast.makeText(getActivity(), "exception:" + ex, Toast.LENGTH_SHORT).show();
//        }

        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSaveVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveVisit();

//                boolean isPatientSaved = savePatient();
//                if (isPatientSaved) goBack();
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


    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String textHour = Integer.toString(hourOfDay);
                        String textMinute = Integer.toString(minute);
                        if (hourOfDay < 10) {
                            textHour = "0" + hourOfDay;
                        }
                        if (minute < 10) {
                            textMinute = "0" + minute;
                        }

                        editTextStartTime.setText(textHour + ":" + textMinute);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.setTitle("Wybierz godzinę:");
        timePickerDialog.show();
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

                        editTextDate.setText(textDay + "/" + textMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void goBack() {

        VisitsFragment visitsFragment = new VisitsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, visitsFragment)
                .addToBackStack(null)
                .commit();
    }


//    private boolean saveVisit() {
//
//        String firstName = editTextFirstName.getText().toString();
//        String lastName = editTextLastName.getText().toString();
//        String textBirthDate = editTextBirthDate.getText().toString();
//        Long longBirthDate = new Long(0);
//
//
//        String note = editTextNote.getText().toString();
//
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        Date birthDate;
//        try {
//            birthDate = format.parse(textBirthDate);
//            longBirthDate = birthDate.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
//            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);
//            try {
//                int bundleId = getArguments().getInt("id");
//                patient = new Patient(firstName, lastName, longBirthDate, note);
//                patient.setId(bundleId);
//                clinicViewModel.updatePatient(patient);
//                Toast.makeText(getActivity(), "Pacjent zaktualizowany", Toast.LENGTH_SHORT).show();
//            } catch (NullPointerException ex) {
//                patient = new Patient(firstName, lastName, longBirthDate, note);
//                clinicViewModel.insertPatient(patient);
//                Toast.makeText(getActivity(), "Pacjent Zapisany", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return true;
//    }

}
