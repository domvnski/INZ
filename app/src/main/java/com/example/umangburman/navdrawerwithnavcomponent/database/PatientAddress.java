package com.example.umangburman.navdrawerwithnavcomponent.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient_address")
public class PatientAddress {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private int patientId;
    @NonNull
    private String addressLine1;
    @NonNull
    private String addressLine2;

    private String zipCode;

    private String city;

    private String district;

    private String description;

    public PatientAddress(int patientId, @NonNull String addressLine1, @NonNull String addressLine2, String zipCode, String city, String district, String description) {
        this.patientId = patientId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
        this.city = city;
        this.district = district;
        this.description = description;
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
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(@NonNull String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @NonNull
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(@NonNull String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String
    toString() {
        return "PatientAddress{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}