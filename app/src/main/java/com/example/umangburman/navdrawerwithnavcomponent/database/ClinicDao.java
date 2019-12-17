package com.example.umangburman.navdrawerwithnavcomponent.database;

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
    void insertPatient(Patient patient);

    @Update
    void updatePatient(Patient patient);

    @Delete
    void deletePatient(Patient patient);

    @Query("DELETE FROM patient")
    void deleteAllPatients();

    @Query("SELECT * FROM patient ORDER BY lastName ASC")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patient Where id=:id")
    LiveData<Patient> getPatientById(int id);

    @Insert
    void insertPatientAddress(PatientAddress patientAddress);

    @Update
    void updatePatientAddress(PatientAddress patientAddress);

    @Delete
    void deletePatientAddress(PatientAddress patientAddress);

//    @Query("SELECT * FROM patient_address Where patientId=:id")
//    LiveData<PatientAddress> getPatientAddressByPatientId(int id);

    @Insert
    void insertPatientContact(PatientContact patientContact);

    @Update
    void updatePatientContact(PatientContact patientContact);

    @Delete
    void deletePatientContact(PatientContact patientContact);

//    @Query("SELECT * FROM patient_address Where patientId=:id")
//    LiveData<PatientContact> getPatientContactByPatientId(int id);

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

    @Insert
    void insertVisit(Visit visit);

    @Update
    void updateVisit(Visit visit);

    @Delete
    void deleteVisit(Visit visit);

    @Query("DELETE FROM visits")
    void deleteAllVisits();

    @Query("SELECT * FROM visits e ORDER BY date DESC")
    LiveData<List<Visit>> getAllVisits();

    @Query("SELECT * FROM visits WHERE date>:date ORDER BY date DESC")
    LiveData<List<Visit>> getAllVisitsAfter(long date);

    @Query("SELECT * FROM visits ORDER BY date<:date DESC")
    LiveData<List<Visit>> getAllVisitsBefore(long date);


}
