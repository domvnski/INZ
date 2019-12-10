package com.example.umangburman.navdrawerwithnavcomponent.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClinicViewModel extends AndroidViewModel {

    private ClinicRepository clinicRepository;
    private LiveData<List<Patient>> allPatients;
    private LiveData<List<Doctor>> allDoctors;


    private ClinicService clinicService;
    private ClinicDao clinicDao;
    private ClinicDatabase clinicDatabase;

    public ClinicViewModel(@NonNull Application application) {
        super(application);

        clinicRepository = new ClinicRepository(application);
        allPatients = clinicRepository.getAllPatients();
        allDoctors = clinicRepository.getAllDoctors();
    }

    public void insertPatient(Patient patient){
        clinicRepository.insertPatient(patient);
    }

    public void updatePatient(Patient patient){
        clinicRepository.updatePatient(patient);
    }

    public void deletePatient(Patient patient){
        clinicRepository.deletePatient(patient);
    }

    public void deleteAllPatients(){
        clinicRepository.deleteAllPatients();
    }

    public LiveData<List<Patient>> getAllPatients(){
        return  allPatients;
    }

    public void insertDoctor(Doctor doctor){
        clinicRepository.insertDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor){
        clinicRepository.updateDoctor(doctor);
    }

    public void deleteDoctor(Doctor doctor){
        clinicRepository.deleteDoctor(doctor);
    }

    public void deleteAllDoctors(){
        clinicRepository.deleteAllDoctors();
    }

    public LiveData<List<Doctor>> getAllDoctors(){
        return  allDoctors;
    }


}
