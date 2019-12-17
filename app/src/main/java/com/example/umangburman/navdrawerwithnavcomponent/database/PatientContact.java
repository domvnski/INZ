package com.example.umangburman.navdrawerwithnavcomponent.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient_contact")
public class PatientContact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private int patientId;
    @NonNull
    private String phone;
    @NonNull
    private String email;

    public PatientContact(int patientId, @NonNull String phone, @NonNull String email) {
        this.patientId = patientId;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}

