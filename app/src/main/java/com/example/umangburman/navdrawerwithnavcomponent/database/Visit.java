package com.example.umangburman.navdrawerwithnavcomponent.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "visits")
public class Visit {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private int patientId;
    @NonNull
    private int doctorId;
    @NonNull
    private long date;
    @NonNull
    private long timeStart;
    @NonNull
    private long timeEnd;
    private String description;

    public Visit( int patientId, int doctorId, @NonNull long date, @NonNull long timeStart, @NonNull long timeEnd, String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @NonNull
    public long getDate() {
        return date;
    }

    public void setDate(@NonNull long date) {
        this.date = date;
    }

    @NonNull
    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(@NonNull long timeStart) {
        this.timeStart = timeStart;
    }

    @NonNull
    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(@NonNull long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", date=" + date +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", description='" + description + '\'' +
                '}';
    }
}
