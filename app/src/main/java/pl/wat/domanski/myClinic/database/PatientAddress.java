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

    private String zipCode;

    private String city;


    public PatientAddress(int patientId, @NonNull String address, String zipCode, String city) {
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


    @Override
    public String
    toString() {
        return "PatientAddress{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}