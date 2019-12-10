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

//    @Insert
//    void insertAppointment(Appointment appointment);

    @Update
    void updatePatient(Patient patient);

    @Update
    void updateDoctor(Doctor doctor);

//    @Update
//    void updateAppointment(Appointment appointment);

    @Delete
    void deletePatient(Patient patient);

    @Delete
    void deleteDoctor(Doctor doctor);

//    @Delete
//    void deleteAppointment(Appointment appointment);

    @Query("DELETE FROM patient")
    void deleteAllPatients();

    @Query("DELETE FROM Doctor")
    void deleteAllDoctors();

//    @Query("DELETE FROM appointment")
//    void deleteAllAppointments();

    @Query("SELECT * FROM patient ORDER BY lastName ASC")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM doctor ORDER BY lastName ASC")
    LiveData<List<Doctor>> getAllDoctors();

//    @Query("SELECT * FROM appointment ORDER BY date DESC")
//    LiveData<List<Appointment>> getAllAppoitments();


}
