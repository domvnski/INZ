package pl.wat.domanski.myClinic.fragments;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;


import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicRepository;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Doctor;
import pl.wat.domanski.myClinic.database.Patient;
import pl.wat.domanski.myClinic.database.Visit;
import pl.wat.domanski.myClinic.dialogs.DialogContract;
import pl.wat.domanski.myClinic.dialogs.DoctorDialogFragment;
import pl.wat.domanski.myClinic.dialogs.PatientDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddEditVisitFragment extends Fragment implements DialogContract {

    private EditText editTextPatient, editTextDate, editTextStartTime, editTextEndTime, editTextDoctor, editTextDescription;
    private String patientName, doctorName, visitDescription, textVisitDate, textVisitTimeStart, textVisitTimeEnd;
    private long visitDate, visitStartTime, visitEndTime;
    private int dialogPatientId, dialogDoctorId, visitId;
    private Button buttonSaveVisit;
    private Visit visit;

    ClinicViewModel clinicViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_visit_fragment, container, false);
        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);

        editTextPatient = root.findViewById(R.id.EditTextAddVisitPatient);
        editTextDate = root.findViewById(R.id.EditTextAddVisitDate);
        editTextStartTime = root.findViewById(R.id.EditTextAddVisitStartTime);
        editTextEndTime = root.findViewById(R.id.EditTextAddVisitEndTime);
        editTextDoctor = root.findViewById(R.id.EditTextAddVisitDoctor);
        editTextDescription = root.findViewById(R.id.EditTextAddVisitDescription);
        buttonSaveVisit = root.findViewById(R.id.buttonSaveNewVisit);

        fillContent();


        editTextPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PatientDialogFragment patientDialogFragment = new PatientDialogFragment();
                patientDialogFragment.setTargetFragment(AddEditVisitFragment.this, 0);
                patientDialogFragment.show(getActivity().getSupportFragmentManager(), "myTag");
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTimePicker();
            }
        });

        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndTimePicker();
            }
        });

        editTextDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DoctorDialogFragment doctorDialogFragment = new DoctorDialogFragment();
                doctorDialogFragment.setTargetFragment(AddEditVisitFragment.this, 0);
                doctorDialogFragment.show(getActivity().getSupportFragmentManager(), "myTag");
            }
        });

        buttonSaveVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isVisitSaved = saveVisit();
                if (isVisitSaved) goBack();
            }
        });

        return root;
    }

    private void fillContent() {
        try {
            visitId = getArguments().getInt("VisitId");
            dialogPatientId = getArguments().getInt("VisitPatientId");
            dialogDoctorId = getArguments().getInt("VisitDoctorId");
            visitDate = getArguments().getLong("VisitDate");
            visitStartTime = getArguments().getLong("VisitStartTime");
            visitEndTime = getArguments().getLong("VisitEndTime");
            visitDescription = getArguments().getString("VisitDescription");

            clinicViewModel.getPatientById(dialogPatientId, new ClinicRepository.OnPatientLoaded() {
                @Override
                public void loaded(Patient patient) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (patient != null) {
                                patientName = patient.getFirstName() + " " + patient.getLastName();
                                editTextPatient.setText(patientName);
                            }
                        }
                    });
                }
            });

            clinicViewModel.getDoctorById(dialogDoctorId, new ClinicRepository.OnDoctorLoaded() {
                @Override
                public void loaded(Doctor doctor) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (doctor != null) {
                                doctorName = doctor.getFirstName() + " " + doctor.getLastName();
                                editTextDoctor.setText(doctorName);
                            }
                        }
                    });

                }
            });

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");

            textVisitDate = simpleDate.format(new Date(visitDate));
            textVisitTimeStart = simpleTime.format(new Date(visitStartTime));
            textVisitTimeEnd = simpleTime.format(new Date(visitEndTime));

            editTextDate.setText(textVisitDate);
            editTextStartTime.setText(textVisitTimeStart);
            editTextEndTime.setText(textVisitTimeEnd);

            editTextDescription.setText(visitDescription);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }


    private void showStartTimePicker() {
        int mHour = 12;
        int mMinute = 0;

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

    private void showEndTimePicker() {
        int mHour = 12;
        int mMinute = 0;

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

                        editTextEndTime.setText(textHour + ":" + textMinute);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.setTitle("Wybierz godzinę:");
        timePickerDialog.show();
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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
                        editTextDate.setText(textDay + "/" + textMonth + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Wybierz datę:");
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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

    @Override
    public void sendPatientData(Patient patient) {
        dialogPatientId = patient.getId();
        editTextPatient.setText(patient.getFirstName() + " " + patient.getLastName());
    }

    @Override
    public void sendDoctorData(Doctor doctor) {
        dialogDoctorId = doctor.getId();
        editTextDoctor.setText("Dr " + doctor.getFirstName() + " " + doctor.getLastName());
    }

    private boolean saveVisit() {
        textVisitDate = editTextDate.getText().toString();
        textVisitTimeStart = editTextStartTime.getText().toString();
        textVisitTimeEnd = editTextEndTime.getText().toString();
        visitDescription = editTextDescription.getText().toString();

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        Date date, timeStart, timeEnd;

        try {
            date = simpleDate.parse(textVisitDate);
            visitDate = date.getTime();

            timeStart = simpleTime.parse(textVisitTimeStart);
            visitStartTime = timeStart.getTime();

            timeEnd = simpleTime.parse(textVisitTimeEnd);
            visitEndTime = timeEnd.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (validateVisitData()) {

            if (visitId != 0) {
                visit = new Visit(dialogPatientId, dialogDoctorId, visitDate, visitStartTime, visitEndTime, visitDescription);
                visit.setId(visitId);

                clinicViewModel.updateVisit(visit, new ClinicRepository.OnVisitExecuted() {
                    @Override
                    public void executed(boolean executed) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (executed) {
                                    Toast.makeText(getActivity(), "Wizyta Zapisana", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Istnieje już wizyta w podanym terminie", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });


            } else {
                visit = new Visit(dialogPatientId, dialogDoctorId, visitDate, visitStartTime, visitEndTime, visitDescription);

                clinicViewModel.insertVisit(visit, new ClinicRepository.OnVisitExecuted() {
                    @Override
                    public void executed(boolean executed) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (executed) {
                                    Toast.makeText(getActivity(), "Wizyta Zapisana", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Istnieje już wizyta w podanym terminie", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });


            }
            return true;

        } else return false;

    }

    private boolean validateVisitData() {

        if (editTextPatient.getText().toString().trim().trim().isEmpty() || editTextDoctor.getText().toString().trim().trim().isEmpty()
                || editTextDate.getText().toString().trim().trim().isEmpty() || editTextStartTime.getText().toString().trim().trim().isEmpty()
                || editTextEndTime.getText().toString().trim().trim().isEmpty() || editTextDescription.getText().toString().trim().trim().isEmpty()) {

            Toast.makeText(getActivity(), "Uzupełnij wszystkie wymagane pola!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (visitStartTime > visitEndTime) {

            Toast.makeText(getActivity(), "Nieprawidłowy czas wizyty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
