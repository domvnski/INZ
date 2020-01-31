package pl.wat.domanski.myClinic.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.wat.domanski.myClinic.database.Doctor;
import pl.wat.domanski.myClinic.dialogs.VisitsDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorInfoFragment extends Fragment {
    private ImageButton imageButtonVisits, imageButtonDelete, imageButtonEdit, imageButtonCall, imageButtonSMS, imageButtonEmail;
    private TextView textViewDoctorFirstName, textViewDoctorLastName, textViewDoctorPhone, textViewDoctorEmail, textViewDoctorSpecialization, textViewDoctorPwzNumber, textViewDoctorCreateDate;

    private String doctorFirstName, doctorLastName, doctorPhone, doctorEmail, doctorSpecialization, doctorPwzNumber;
    private int doctorId;
    private long doctorCreateDate;
    ClinicViewModel clinicViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.doctor_info_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);

        imageButtonVisits = root.findViewById(R.id.imageButtonDoctorInfoVisits);
        imageButtonDelete = root.findViewById(R.id.imageButtonDoctorInfoDelete);
        imageButtonEdit = root.findViewById(R.id.imageButtonDoctorInfoEdit);
        imageButtonCall = root.findViewById(R.id.imageButtonDoctorInfoCall);
        imageButtonSMS = root.findViewById(R.id.imageButtonDoctorInfoSMS);
        imageButtonEmail = root.findViewById(R.id.imageButtonDoctorInfoEmail);

        textViewDoctorFirstName = root.findViewById(R.id.textViewDoctorInfoFirstName);
        textViewDoctorLastName = root.findViewById(R.id.textViewDoctorInfoLastName);
        textViewDoctorPhone = root.findViewById(R.id.textViewDoctorInfoPhone);
        textViewDoctorEmail = root.findViewById(R.id.textViewDoctorInfoEmail);
        textViewDoctorSpecialization = root.findViewById(R.id.textViewDoctorInfoSpecialization);
        textViewDoctorCreateDate = root.findViewById(R.id.textViewDoctorInfoCreateDate);
        textViewDoctorPwzNumber = root.findViewById(R.id.textViewDoctorInfoPWZNumber);

        fillContent();

        imageButtonVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle VisitsDialogBundle = new Bundle();
                VisitsDialogBundle.putInt("VisitDoctorId", doctorId);

                VisitsDialogFragment visitsDialogFragment = new VisitsDialogFragment();
                visitsDialogFragment.setArguments(VisitsDialogBundle);

                visitsDialogFragment.show(getActivity().getSupportFragmentManager(), "myTag");
            }
        });

        imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.getDoctorById(doctorId, new ClinicRepository.OnDoctorLoaded() {
                    @Override
                    public void loaded(Doctor doctor) {
                        if (doctor != null) {
                            showConfirmDeleteSnackbar(doctor);
                        }
                    }
                });
            }
        });

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditDoctorFragment();
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", doctorPhone, null)));
                ;
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
            doctorId = getArguments().getInt("DoctorId");
            doctorFirstName = getArguments().getString("DoctorFirstname");
            doctorLastName = getArguments().getString("DoctorLastname");
            doctorPhone = getArguments().getString("DoctorPhone");
            doctorEmail = getArguments().getString("DoctorEmail");
            doctorSpecialization = getArguments().getString("DoctorSpecialization");
            doctorPwzNumber = getArguments().getString("DoctorPwzNumber");
            doctorCreateDate = getArguments().getLong("DoctorCreateDate");

            Date date = new Date(doctorCreateDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String textCreateDate = simpleDateFormat.format(date);

            textViewDoctorFirstName.setText("Dr " + doctorFirstName);
            textViewDoctorLastName.setText(doctorLastName);
            textViewDoctorPhone.setText(doctorPhone);
            textViewDoctorEmail.setText(doctorEmail);
            textViewDoctorSpecialization.setText(doctorSpecialization);
            textViewDoctorPwzNumber.setText(doctorPwzNumber);
            textViewDoctorCreateDate.setText(textCreateDate);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{textViewDoctorEmail.getText().toString()});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Brak zainstalowanych klientów poczty.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToEditDoctorFragment() {
        Bundle doctorBundle = new Bundle();
        doctorBundle.putInt("DoctorId", doctorId);
        doctorBundle.putString("DoctorFirstname", doctorFirstName);
        doctorBundle.putString("DoctorLastname", doctorLastName);
        doctorBundle.putString("DoctorPhone", doctorPhone);
        doctorBundle.putString("DoctorEmail", doctorEmail);
        doctorBundle.putString("DoctorSpecialization", doctorSpecialization);
        doctorBundle.putString("DoctorPwzNumber", doctorPwzNumber);

        AddEditDoctorFragment addEditDoctorFragment = new AddEditDoctorFragment();
        addEditDoctorFragment.setArguments(doctorBundle);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, addEditDoctorFragment)
                .addToBackStack(null)
                .commit();

    }

    private void deleteDoctor(Doctor doctor) {
        clinicViewModel.deleteDoctor(doctor);
    }

    private void goBack() {
        DoctorsFragment doctorsFragment = new DoctorsFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host_fragment, doctorsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showConfirmDeleteSnackbar(final Doctor doctor) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Czy na pewno chcesz usunąć tego lekarza?", Snackbar.LENGTH_LONG)
                .setAction("TAK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDoctor(doctor);
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
                    0);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + doctorPhone)));
        }
    }
}
