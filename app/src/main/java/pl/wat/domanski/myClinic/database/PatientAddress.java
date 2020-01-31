package pl.wat.domanski.myClinic.database;

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
    private String address;
    @NonNull
    private String zipCode;
    @NonNull
    private String city;

    public PatientAddress( int patientId, @NonNull String address, @NonNull String zipCode, @NonNull String city) {
        this.patientId = patientId;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
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
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NonNull String zipCode) {
        this.zipCode = zipCode;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }
}