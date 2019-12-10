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
import com.example.umangburman.navdrawerwithnavcomponent.database.Doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDoctorFragment extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthDate;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private Button buttonSaveDoctor;
    private Button buttonCancel;

    ClinicViewModel clinicViewModel;
    Doctor doctor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_doctor_fragment, container, false);

        editTextFirstName = root.findViewById(R.id.addDoctorFirstnameEditText);
        editTextLastName = root.findViewById(R.id.addDoctorLastNameEditText);
        editTextBirthDate = root.findViewById(R.id.addDoctorBirthDateEditText);
        editTextPhone = root.findViewById(R.id.addDoctorPhoneEditText);
        editTextEmail = root.findViewById(R.id.addDoctorEmailEditText);
        buttonSaveDoctor = root.findViewById(R.id.buttonSaveNewDoctor);
        buttonCancel = root.findViewById(R.id.buttonCancel);


        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDoctor();
                goBack();

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
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            editTextBirthDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                    + "/" + String.valueOf(year));
        }
    };

    private void goBack() {

        DoctorsFragment doctorsFragment= new DoctorsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, doctorsFragment)
                .addToBackStack(null)
                .commit();
    }


    private void saveDoctor() {

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String textBirthDate = editTextBirthDate.getText().toString();
        Long longBirthDate = new Long(0);
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        try {
            birthDate = format.parse(textBirthDate);
            longBirthDate = birthDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() /*todo jak walidowac longa */) {
            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_SHORT).show();
            return;
        }

        doctor = new Doctor(firstName, lastName, longBirthDate, phone, email);
        clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);
        clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);

        clinicViewModel.insertDoctor(doctor);
        Toast.makeText(getActivity(), "Doktor Zapisany", Toast.LENGTH_SHORT).show();

    }
}