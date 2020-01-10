package pl.wat.domanski.myClinic.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "doctor")
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String phone;
    @NonNull
    private String email;
    @NonNull
    private String specialization;
    @NonNull
    private String pwzNumber;
    @NonNull
    private long createDate;


    public Doctor( @NonNull String firstName, @NonNull String lastName, @NonNull String phone, @NonNull String email, @NonNull String specialization, @NonNull String pwzNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
        this.pwzNumber = pwzNumber;
        this.createDate = new Date().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
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

    @NonNull
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(@NonNull String specialization) {
        this.specialization = specialization;
    }

    @NonNull
    public String getPwzNumber() {
        return pwzNumber;
    }

    public void setPwzNumber(@NonNull String pwzNumber) {
        this.pwzNumber = pwzNumber;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
