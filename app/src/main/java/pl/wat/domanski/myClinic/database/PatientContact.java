package pl.wat.domanski.myClinic.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient_contact")
public class PatientContact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int patientId;
    private String phone;
    private String email;

    public PatientContact(int patientId,  String phone,  String email) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}

