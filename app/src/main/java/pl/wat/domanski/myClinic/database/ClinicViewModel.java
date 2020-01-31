package pl.wat.domanski.myClinic.database;

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

    public void insertPatient(Patient patient, PatientAddress patientAddress, PatientContact patientContact) {
        clinicRepository.insertPatient(patient, patientAddress, patientContact);
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

    public void getPatientById(int id, ClinicRepository.OnPatientLoaded onResultsLoaded) {
        clinicRepository.getPatientById(id, onResultsLoaded);
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public void updatePatientAddress(PatientAddress patientAddress) {
        clinicRepository.updatePatientAddress(patientAddress);
    }

    public void deletePatientAddress(PatientAddress patientAddress) {
        clinicRepository.deletePatientAddress(patientAddress);
    }

    public void getPatientAddressByPatientId(int id, ClinicRepository.OnPatientAddressLoaded onResultsLoaded) {
        clinicRepository.getPatientAddressByPatientId(id, onResultsLoaded);
    }

    public void updatePatientContact(PatientContact patientContact) {
        clinicRepository.updatePatientContact(patientContact);
    }

    public void deletePatientContact(PatientContact patientContact) {
        clinicRepository.deletePatientContact(patientContact);
    }

    public void getPatientContactByPatientId(int id, ClinicRepository.OnPatientContactLoaded onResultsLoaded) {
        clinicRepository.getPatientContactByPatientId(id, onResultsLoaded);
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

    public void getDoctorById(int id, ClinicRepository.OnDoctorLoaded onResultsLoaded) {
        clinicRepository.getDoctorById(id, onResultsLoaded);
    }

    public void insertVisit(Visit visit, ClinicRepository.OnVisitExecuted onVisitExecuted) {
        clinicRepository.insertVisit(visit, onVisitExecuted);
    }

    public void updateVisit(Visit visit, ClinicRepository.OnVisitExecuted onVisitExecuted) {
        clinicRepository.updateVisit(visit, onVisitExecuted);
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

    public void getVisitById(int id, ClinicRepository.OnVisitLoaded onResultsLoaded) {
        clinicRepository.getVisitById(id, onResultsLoaded);
    }

    public LiveData<List<Visit>> getVisitsByPatientId(int id) {
        return clinicRepository.getVisitsByPatientId(id);
    }

    public LiveData<List<Visit>> getVisitsByDoctorId(int id) {
        return clinicRepository.getVisitsByDoctorId(id);
    }

    public LiveData<List<Visit>> getVisitsByDate(long date) {
        return clinicRepository.getVisitsByDate(date);
    }
}
