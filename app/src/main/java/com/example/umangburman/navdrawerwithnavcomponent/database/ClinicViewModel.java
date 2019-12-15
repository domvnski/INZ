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
    private LiveData<List<Visit>> allVisits;

    public ClinicViewModel(@NonNull Application application) {
        super(application);

        clinicRepository = new ClinicRepository(application);
        allPatients = clinicRepository.getAllPatients();
        allDoctors = clinicRepository.getAllDoctors();
        allVisits = clinicRepository.getAllVisits();

    }

    public void insertPatient(Patient patient) {
        clinicRepository.insertPatient(patient);
    }

    public void updatePatient(Patient patient) {
        clinicRepository.updatePatient(patient);
    }

    public void deletePatient(Patient patient) {
        clinicRepository.deletePatient(patient);
    }

    public void deleteAllPatients() {
        clinicRepository.deleteAllPatients();
    }

    public LiveData<Patient> getPatientById(int id) {
        return clinicRepository.getPatientById(id);
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public void insertDoctor(Doctor doctor) {
        clinicRepository.insertDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        clinicRepository.updateDoctor(doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        clinicRepository.deleteDoctor(doctor);
    }

    public void deleteAllDoctors() {
        clinicRepository.deleteAllDoctors();
    }

    public LiveData<List<Doctor>> getAllDoctors() {
        return allDoctors;
    }

    public void insertVisit(Visit visit) {
        clinicRepository.insertVisit(visit);
    }

    public void updateVisit(Visit visit) {
        clinicRepository.updateVisit(visit);
    }

    public void deleteVisit(Visit visit) {
        clinicRepository.deleteVisit(visit);
    }

    public void deleteAllVisits() {
        clinicRepository.deleteAllVisits();
    }

    public LiveData<List<Visit>> getAllVisits() {
        return allVisits;
    }


}
