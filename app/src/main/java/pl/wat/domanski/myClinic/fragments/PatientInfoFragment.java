package pl.wat.domanski.myClinic.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicRepository;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.database.PatientAddress;
import pl.wat.domanski.myClinic.database.PatientContact;
import pl.wat.domanski.myClinic.dialogs.VisitsDialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientInfoFragment extends Fragment {
    private ImageButton imageButtonVisits, imageButtonCall, imageButtonAnonymize, imageButtonDelete, imageButtonEdit, imageButtonSMS, imageButtonEmail;
    private TextView textViewPatientFirstName, textViewPatientLastName, textViewPatientBirthdate, textViewPatientPesel, textViewPatientSex, textViewPatientPhone, textViewPatientEmail, textViewPatientAddress, textViewPatientZipCode, textViewPatientCity, textViewPatientNote, textViewPatientCreateDate;
    private String patientFirstName, patientLastName, patientPesel, patientPhone, patientEmail, patientSex = "", patientAddress, patientZipcode, patientCity, patientNote;
    private long patientBirthDate, patientCreateDate;
    private int patientId, patientAddressId, patientContactId;
    ClinicViewModel clinicViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patient_info_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);

        imageButtonVisits = root.findViewById(R.id.imageButtonPatientInfoVisits);
        imageButtonAnonymize = root.findViewById(R.id.imageButtonPatientInfoAnonymize);
        imageButtonDelete = root.findViewById(R.id.imageButtonPatientInfoDelete);
        imageButtonEdit = root.findViewById(R.id.imageButtonPatientInfoEdit);
        imageButtonCall = root.findViewById(R.id.imageButtonPatientInfoCall);
        imageButtonSMS = root.findViewById(R.id.imageButtonPatientInfoSMS);
        imageButtonEmail = root.findViewById(R.id.imageButtonPatientInfoEmail);

        textViewPatientFirstName = root.findViewById(R.id.textViewPatientInfoFirstName);
        textViewPatientLastName = root.findViewById(R.id.textViewPatientInfoLastName);
        textViewPatientBirthdate = root.findViewById(R.id.textViewPatientInfoBirthDate);
        textViewPatientPesel = root.findViewById(R.id.textViewPatientInfoPesel);
        textViewPatientSex = root.findViewById(R.id.textViewPatientInfoSex);
        textViewPatientPhone = root.findViewById(R.id.textViewPatientInfoPhone);
        textViewPatientEmail = root.findViewById(R.id.textViewPatientInfoEmail);
        textViewPatientAddress = root.findViewById(R.id.textViewPatientInfoAddress);
        textViewPatientZipCode = root.findViewById(R.id.textViewPatientInfoZipcode);
        textViewPatientCity = root.findViewById(R.id.textViewPatientInfoCity);
        textViewPatientNote = root.findViewById(R.id.textViewPatientInfoNote);
        textViewPatientCreateDate = root.findViewById(R.id.textViewPatientInfoCreateDate);

        fillContent();


        imageButtonVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle VisitsDialogBundle = new Bundle();
                VisitsDialogBundle.putInt("VisitPatientId", patientId);

                VisitsDialogFragment visitsDialogFragment = new VisitsDialogFragment();
                visitsDialogFragment.setArguments(VisitsDialogBundle);

                visitsDialogFragment.show(getActivity().getSupportFragmentManager(), "myTag");
            }
        });

        imageButtonAnonymize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.getPatientById(patientId, new ClinicRepository.OnPatientLoaded() {
                    @Override
                    public void loaded(Patient patient) {
                        if (patient != null) {
                            showConfirmAnonymizeSnackbar(patient);
                        }
                    }
                });
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.getPatientById(patientId, new ClinicRepository.OnPatientLoaded() {
                    @Override
                    public void loaded(Patient patient) {
                        if (patient != null) {
                            showConfirmDeleteSnackbar(patient);
                        }
                    }
                });
            }
        });

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditPatientFragment();
            }
        });


        imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        imageButtonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", patientPhone, null)));
            }
        });

        imageButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        return root;
    }

    private void fillContent() {
        try {
            patientId = getArguments().getInt("PatientId");
            patientAddressId = getArguments().getInt("PatientAddressId");
            patientContactId = getArguments().getInt("PatientContactId");
            patientFirstName = getArguments().getString("PatientFirstname");
            patientLastName = getArguments().getString("PatientLastname");
            patientPesel = getArguments().getString("PatientPesel");
            patientPhone = getArguments().getString("PatientPhone");
            patientEmail = getArguments().getString("PatientEmail");
            patientAddress = getArguments().getString("PatientAddress");
            patientZipcode = getArguments().getString("PatientZipcode");
            patientCity = getArguments().getString("PatientCity");
            patientBirthDate = getArguments().getLong("PatientBirthdate");
            patientNote = getArguments().getString("PatientNote");
            patientCreateDate = getArguments().getLong("PatientCreateDate");

            Date dateBirthDate = new Date(patientBirthDate);
            Date dateCreateDate = new Date(patientCreateDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String textBirthDate = simpleDateFormat.format(dateBirthDate);
            String textCreateDate = simpleDateTimeFormat.format(dateCreateDate);

            if (Integer.valueOf(patientPesel.substring(9, 10)) % 2 == 0) patientSex = "kobieta";
            else patientSex = "mężczyzna";

            textViewPatientFirstName.setText(patientFirstName);
            textViewPatientLastName.setText(patientLastName);
            textViewPatientBirthdate.setText(textBirthDate);
            textViewPatientPesel.setText(patientPesel);
            textViewPatientSex.setText(patientSex);
            textViewPatientPhone.setText(patientPhone);
            textViewPatientEmail.setText(patientEmail);
            textViewPatientAddress.setText(patientAddress);
            textViewPatientZipCode.setText(patientZipcode);
            textViewPatientCity.setText(patientCity);
            textViewPatientNote.setText(patientNote);
            textViewPatientCreateDate.setText(textCreateDate);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{textViewPatientEmail.getText().toString()});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void anonymizePatient(Patient currentPatient) {

        String anonymousField = "*****";

        Patient anonymousPatient = new Patient(anonymousField, anonymousField, new Long(0), anonymousField, anonymousField);
        PatientAddress anonymousPatientAddress = new PatientAddress(currentPatient.getId(), anonymousField, anonymousField, anonymousField);
        PatientContact anonymousPatientContact = new PatientContact(currentPatient.getId(), anonymousField, anonymousField);

        anonymousPatient.setId(currentPatient.getId());

        clinicViewModel.getPatientAddressByPatientId(currentPatient.getId(), new ClinicRepository.OnPatientAddressLoaded() {
            @Override
            public void loaded(PatientAddress patientAddress) {
                if (patientAddress != null) {
                    anonymousPatientAddress.setId(patientAddress.getId());
                }
            }
        });

        clinicViewModel.getPatientContactByPatientId(currentPatient.getId(), new ClinicRepository.OnPatientContactLoaded() {
            @Override
            public void loaded(PatientContact patientContact) {
                if (patientContact != null) {
                    anonymousPatientContact.setId(patientContact.getId());
                }
            }
        });

        clinicViewModel.updatePatient(anonymousPatient);
        clinicViewModel.updatePatientAddress(anonymousPatientAddress);
        clinicViewModel.updatePatientContact(anonymousPatientContact);
    }

    private void goToEditPatientFragment() {
        Bundle patientBundle = new Bundle();
        patientBundle.putInt("PatientId", patientId);
        patientBundle.putInt("PatientAddressId", patientAddressId);
        patientBundle.putInt("PatientContactId", patientContactId);
        patientBundle.putString("PatientFirstname", patientFirstName);
        patientBundle.putString("PatientLastname", patientLastName);
        patientBundle.putLong("PatientBirthdate", patientBirthDate);
        patientBundle.putString("PatientPesel", patientPesel);
        patientBundle.putString("PatientPhone", patientPhone);
        patientBundle.putString("PatientEmail", patientEmail);
        patientBundle.putString("PatientAddress", patientAddress);
        patientBundle.putString("PatientZipcode", patientZipcode);
        patientBundle.putString("PatientCity", patientCity);
        patientBundle.putString("PatientNote", patientNote);

        AddPatientFragment addPatientFragment = new AddPatientFragment();
        addPatientFragment.setArguments(patientBundle);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, addPatientFragment)
                .addToBackStack(null)
                .commit();
    }

    private void deletePatient(Patient patient) {
        clinicViewModel.deletePatient(patient);
    }

    private void goBack() {
        PatientsFragment patientFragment = new PatientsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, patientFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showConfirmDeleteSnackbar(final Patient patient) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Czy na pewno chcesz usunąć tego pacjenta?", Snackbar.LENGTH_LONG)
                .setAction("TAK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePatient(patient);
                        goBack();
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void showConfirmAnonymizeSnackbar(final Patient patient) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Czy na pewno chcesz zanimizować tego pacjenta? Operacja jest nieodwracalna.", Snackbar.LENGTH_LONG)
                .setAction("TAK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        anonymizePatient(patient);
                        goBack();
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + patientPhone)));
        }
    }
}
