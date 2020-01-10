package pl.wat.domanski.myClinic.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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
    @NonNull
    private String description;
    @NonNull
    private long createDate;

    public Visit(@NonNull int patientId, @NonNull int doctorId, @NonNull long date, @NonNull long timeStart, @NonNull long timeEnd, @NonNull String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
        this.createDate = new Date().getTime();
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
