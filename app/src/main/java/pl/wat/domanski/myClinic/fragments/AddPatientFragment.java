package pl.wat.domanski.myClinic.fragments;

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


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.database.PatientAddress;
import pl.wat.domanski.myClinic.database.PatientContact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPatientFragment extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthDate;
    private EditText editTextPesel;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private EditText editTextZipCode;
    private EditText editTextCity;
    private EditText editTextNote;
    private Button buttonSavePatient;

    String firstName, lastName, textBirthDate, pesel, phone, email, address, zipcode, city, note;
    Long longBirthDate = new Long(0);

    ClinicViewModel clinicViewModel;
    Patient patient;
    private PatientAddress patientAddress;
    private PatientContact patientContact;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_patient_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);

        editTextFirstName = root.findViewById(R.id.addPatientFirstnameEditText);
        editTextLastName = root.findViewById(R.id.addPatientLastNameEditText);
        editTextBirthDate = root.findViewById(R.id.addPatientBirthDateEditText);
        editTextPesel = root.findViewById(R.id.addPatientPeselEditText);
        editTextPhone = root.findViewById(R.id.addPatientPhoneEditText);
        editTextEmail = root.findViewById(R.id.addPatientEmailEditText);
        editTextAddress = root.findViewById(R.id.addPatientAdressEditText);
        editTextZipCode = root.findViewById(R.id.addPatientZipCodeEditText);
        editTextCity = root.findViewById(R.id.addPatientCityEditText);
        editTextNote = root.findViewById(R.id.addPatientNoteEditText);
        buttonSavePatient = root.findViewById(R.id.buttonSaveNewPatient);

        fillContent();


        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDataSaved = saveData();
                if (isDataSaved) goBack();
            }
        });

        return root;
    }

    private void fillContent() {
        try {
            String firstName = getArguments().getString("PatientFirstname");
            String lastName = getArguments().getString("PatientLastname");
            String pesel = getArguments().getString("PatientPesel");
            String phone = getArguments().getString("PatientPhone");
            String email = getArguments().getString("PatientEmail");
            String address = getArguments().getString("PatientAddress");
            String zipcode = getArguments().getString("PatientZipcode");
            String city = getArguments().getString("PatientCity");
            Long birthDate = getArguments().getLong("PatientBirthdate");
            String note = getArguments().getString("PatientNote");

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            String textBirthDate = simpleDate.format(new Date(birthDate));

            editTextFirstName.setText(firstName);
            editTextLastName.setText(lastName);
            editTextBirthDate.setText(textBirthDate);
            editTextPesel.setText(pesel);
            editTextPhone.setText(phone);
            editTextEmail.setText(email);
            editTextAddress.setText(address);
            editTextZipCode.setText(zipcode);
            editTextCity.setText(city);
            editTextNote.setText(note);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void showDatePicker() {
        int mYear = 1990;
        int mMonth = 0;
        int mDay = 1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        String textMonth = Integer.toString(monthOfYear);
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
        PatientsFragment patientsFragment = new PatientsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, patientsFragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean saveData() {
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        textBirthDate = editTextBirthDate.getText().toString();
        pesel = editTextPesel.getText().toString();
        phone = editTextPhone.getText().toString();
        email = editTextEmail.getText().toString();
        address = editTextAddress.getText().toString();
        zipcode = editTextZipCode.getText().toString();
        city = editTextCity.getText().toString();
        note = editTextNote.getText().toString();

        longBirthDate = new Long(0);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        try {
            birthDate = format.parse(textBirthDate);
            longBirthDate = birthDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (validatePatientData()) {
            try {
                int patientBundleId = getArguments().getInt("PatientId");
                int patientAddressBundleId = getArguments().getInt("PatientAddressId");
                int patientContactBundleId = getArguments().getInt("PatientContactId");

                patient = new Patient(firstName, lastName, longBirthDate, pesel, note);
                patientContact = new PatientContact(patientBundleId, phone, email);
                patientAddress = new PatientAddress(patientBundleId, address, zipcode, city);

                patientAddress.setId(patientAddressBundleId);
                patientContact.setId(patientContactBundleId);
                patient.setId(patientBundleId);

                clinicViewModel.updatePatient(patient);
                clinicViewModel.updatePatientAddress(patientAddress);
                clinicViewModel.updatePatientContact(patientContact);

                Toast.makeText(getActivity(), "Pacjent zaktualizowany", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException ex) {

                patient = new Patient(firstName, lastName, longBirthDate, pesel, note);
                patientContact = new PatientContact(patient.getId(), phone, email);
                patientAddress = new PatientAddress(patient.getId(), address, zipcode, city);

                clinicViewModel.insertPatient(patient, patientAddress, patientContact);

                Toast.makeText(getActivity(), "Pacjent Zapisany", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else return false;
    }

    private boolean validatePatientData() {

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || textBirthDate.trim().isEmpty()
                || pesel.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()
                || address.trim().isEmpty() || zipcode.trim().isEmpty() || city.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (pesel.length() != 11) {
            Toast.makeText(getActivity(), "Nieprawidłowy PESEL!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() < 9 || (phone.length() >= 11 && !phone.contains("+"))) {
            Toast.makeText(getActivity(), "Nieprawidłowy numer telefonu!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(getActivity(), "Nieprawidłowy email!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (zipcode.length() < 5) {
            Toast.makeText(getActivity(), "Nieprawidłowy kod pocztowy!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
