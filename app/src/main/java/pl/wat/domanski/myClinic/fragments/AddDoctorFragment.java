package pl.wat.domanski.myClinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Doctor;

public class AddDoctorFragment extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextSpecialization;
    private EditText editTextPwzNumber;
    private Button buttonSaveDoctor;

    String firstName, lastName, phone, email, specialization, pwzNumber;

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

        clinicViewModel = ViewModelProviders.of(getActivity()).get(ClinicViewModel.class);

        editTextFirstName = root.findViewById(R.id.editTextAddDoctorFirstname);
        editTextLastName = root.findViewById(R.id.editTextAddDoctorLastName);
        editTextPhone = root.findViewById(R.id.editTextAddDoctorPhone);
        editTextEmail = root.findViewById(R.id.editTextAddDoctorEmail);
        editTextSpecialization = root.findViewById(R.id.editTextAddDoctorSpecialization);
        editTextPwzNumber = root.findViewById(R.id.editTextAddDoctorPwzNumber);
        buttonSaveDoctor = root.findViewById(R.id.buttonSaveNewDoctor);

        fillContent();

        buttonSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDataSaved = saveDoctor();
                if (isDataSaved) goBack();
            }
        });

        return root;
    }

    private void fillContent() {
        try {
             firstName = getArguments().getString("DoctorFirstname");
             lastName = getArguments().getString("DoctorLastname");
             phone = getArguments().getString("DoctorPhone");
             email = getArguments().getString("DoctorEmail");
             specialization = getArguments().getString("DoctorSpecialization");
             pwzNumber= getArguments().getString("DoctorPwzNumber");


            editTextFirstName.setText(firstName);
            editTextLastName.setText(lastName);
            editTextPhone.setText(phone);
            editTextEmail.setText(email);
            editTextSpecialization.setText(specialization);
            editTextPwzNumber.setText(pwzNumber);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void goBack() {
        DoctorsFragment doctorsFragment = new DoctorsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, doctorsFragment)
                .addToBackStack(null)
                .commit();
    }


    private boolean saveDoctor() {
        if (validateDoctorData()) {
            doctor = new Doctor(firstName, lastName, phone, email, specialization, pwzNumber);
            try {
                int doctorBundleId = getArguments().getInt("DoctorId");
                doctor.setId(doctorBundleId);

                clinicViewModel.updateDoctor(doctor);

                Toast.makeText(getActivity(), "Lekarz zaktualizowany", Toast.LENGTH_SHORT).show();

            } catch (NullPointerException ex) {
                clinicViewModel.insertDoctor(doctor);

                Toast.makeText(getActivity(), "Lekarz Zapisany", Toast.LENGTH_SHORT).show();
            }
            return true;

        } else return false;
    }

    private boolean validateDoctorData() {

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || phone.trim().isEmpty()
                || email.trim().isEmpty() || specialization.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_LONG).show();
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

        if (pwzNumber.length()!=7) {
            Toast.makeText(getActivity(), "Nieprawidłowy numer PWZ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
