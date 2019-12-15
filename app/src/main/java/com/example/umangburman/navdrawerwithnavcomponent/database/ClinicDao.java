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

    @Insert
    void insertDoctor(Doctor doctor);

    @Insert
    void insertVisit(Visit visit);

    @Update
    void updatePatient(Patient patient);

    @Update
    void updateDoctor(Doctor doctor);

    @Update
    void updateVisit(Visit visit);

    @Delete
    void deletePatient(Patient patient);

    @Delete
    void deleteDoctor(Doctor doctor);

    @Delete
    void deleteVisit(Visit visit);

    @Query("DELETE FROM patient")
    void deleteAllPatients();

    @Query("DELETE FROM Doctor")
    void deleteAllDoctors();

    @Query("DELETE FROM visits")
    void deleteAllVisits();

    @Query("SELECT * FROM patient ORDER BY lastName ASC")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patient Where id=:id")
    LiveData<Patient> getPatientById(int id);

    @Query("SELECT * FROM doctor ORDER BY lastName ASC")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM visits e ORDER BY date DESC")
    LiveData<List<Visit>> getAllVisits();

    @Query("SELECT * FROM visits WHERE date>:date ORDER BY date DESC")
    LiveData<List<Visit>> getAllVisitsAfter(long date);

    @Query("SELECT * FROM visits ORDER BY date<:date DESC")
    LiveData<List<Visit>> getAllVisitsBefore(long date);


}
