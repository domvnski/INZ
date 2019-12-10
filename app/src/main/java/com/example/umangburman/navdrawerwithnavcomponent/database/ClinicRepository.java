package com.example.umangburman.navdrawerwithnavcomponent.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClinicRepository {

    private ClinicDao clinicDao;
    private LiveData<List<Patient>> allPatients;
    private LiveData<List<Doctor>> allDoctors;
    private LiveData<List<Appointment>> allAppointments;

    public ClinicRepository(@NonNull Application application) {
        ClinicDatabase clinicDatabase = ClinicDatabase.getDatabase(application);
        clinicDao = clinicDatabase.clinicDao();
        allPatients = clinicDao.getAllPatients();
        allDoctors = clinicDao.getAllDoctors();
//        allAppointments = clinicDao.getAllAppoitments();

    }

    public void insertPatient(Patient patient) {
        new InsertPatientAsyncTask(clinicDao).execute(patient);
    }

    public void updatePatient(Patient patient) {
        new UpdatePatientAsyncTask(clinicDao).execute(patient);
    }

    public void deletePatient(Patient patient) {
        new DeletePatientAsyncTask(clinicDao).execute(patient);
    }

    public void deleteAllPatients() {
        new DeleteAllPatientsAsyncTask(clinicDao).execute();
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }


    public void insertDoctor(Doctor doctor) {
        new InsertDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        new UpdateDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        new DeleteDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void deleteAllDoctors() {
        new InsertDoctorAsyncTask(clinicDao).execute();
    }

    public LiveData<List<Doctor>> getAllDoctors() {
        return allDoctors;
    }


//    public void insertAppoitment(Appointment appointment) {
//    }
//
//    public void updateAppoitment(Appointment appointment) {
//    }
//
//    public void deleteAppoitment(Appointment appointment) {
//    }
//
//    public void deleteAllAppoitments() {
//    }
//
//    public LiveData<List<Appointment>> getAllAppoitments() {
//        return allAppointments;
//    }

    private static class InsertPatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private ClinicDao clinicDao;

        private InsertPatientAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            clinicDao.insertPatient(patients[0]);
            return null;
        }
    }

    private static class UpdatePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private ClinicDao clinicDao;

        private UpdatePatientAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            clinicDao.updatePatient(patients[0]);
            return null;
        }
    }

    private static class DeletePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private ClinicDao clinicDao;

        private DeletePatientAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            clinicDao.deletePatient(patients[0]);
            return null;
        }
    }

    private static class DeleteAllPatientsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteAllPatientsAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clinicDao.deleteAllPatients();
            return null;
        }
    }

    private static class InsertDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private InsertDoctorAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.insertDoctor(doctors[0]);
            return null;
        }
    }

    private static class UpdateDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private UpdateDoctorAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.updateDoctor(doctors[0]);
            return null;
        }
    }

    private static class DeleteDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteDoctorAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.deleteDoctor(doctors[0]);
            return null;
        }
    }

    private static class DeleteAllDoctorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteAllDoctorsAsyncTask(ClinicDao clinicDao){
            this.clinicDao=clinicDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clinicDao.deleteAllDoctors();
            return null;
        }
    }



}
