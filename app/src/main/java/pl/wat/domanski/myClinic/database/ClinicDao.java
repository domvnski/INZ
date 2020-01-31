package pl.wat.domanski.myClinic.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClinicDao {

    @Insert
    long insertPatient(Patient patient);

    @Update
    void updatePatient(Patient patient);

    @Delete
    void deletePatient(Patient patient);

    @Query("DELETE FROM patient")
    void deleteAllPatients();

    @Query("SELECT * FROM patient ORDER BY lastName ASC")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patient Where id = :id")
    Patient getPatientById(int id);

    @Insert
    void insertPatientAddress(PatientAddress patientAddress);

    @Update
    void updatePatientAddress(PatientAddress patientAddress);

    @Delete
    void deletePatientAddress(PatientAddress patientAddress);

    @Query("SELECT * FROM patient_address Where patientId=:patientId")
    PatientAddress getPatientAddressByPatientId(int patientId);

    @Insert
    void insertPatientContact(PatientContact patientContact);

    @Update
    void updatePatientContact(PatientContact patientContact);

    @Delete
    void deletePatientContact(PatientContact patientContact);

    @Query("SELECT * FROM patient_contact Where patientId=:patientId")
    PatientContact getPatientContactByPatientId(int patientId);

    @Insert
    void insertDoctor(Doctor doctor);

    @Update
    void updateDoctor(Doctor doctor);

    @Delete
    void deleteDoctor(Doctor doctor);

    @Query("DELETE FROM Doctor")
    void deleteAllDoctors();

    @Query("SELECT * FROM doctor ORDER BY lastName ASC")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM doctor Where id = :id")
    Doctor getDoctorById(int id);

    @Insert
    void insertVisit(Visit visit);

    @Update
    void updateVisit(Visit visit);

    @Delete
    void deleteVisit(Visit visit);

    @Query("DELETE FROM visits")
    void deleteAllVisits();

    @Query("SELECT * FROM visits Where id = :id")
    Visit getVisitById(int id);

    @Query("SELECT * FROM visits Where patientId = :id ORDER BY date, timeStart, timeEnd ASC")
    LiveData<List<Visit>> getVisitsByPatientId(int id);

    @Query("SELECT * FROM visits Where doctorId = :id ORDER BY date, timeStart, timeEnd ASC")
    LiveData<List<Visit>> getVisitsByDoctorId(int id);

    @Query("SELECT * FROM visits WHERE date = :date ORDER BY date, timeStart, timeEnd ASC")
    LiveData<List<Visit>> getVisitsByDate(long date);

    @Query("SELECT * FROM visits ORDER BY date, timeStart, timeEnd ASC ")
    LiveData<List<Visit>> getAllVisits();

    @Query("SELECT * FROM visits where date = :date and (timeStart BETWEEN :timeStart and :timeEnd or timeEnd BETWEEN :timeStart and :timeEnd) and (doctorId = :doctorId)")
    List<Visit> checkIfVisitExistInTime(long date, long timeStart, long timeEnd, int doctorId);
}
