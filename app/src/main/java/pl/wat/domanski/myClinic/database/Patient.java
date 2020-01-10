package pl.wat.domanski.myClinic.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "patient")
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Long birthDate;
    @NonNull
    private String pesel;
    private String note;
    @NonNull
    private long createDate;

    public Patient(@NonNull String firstName, @NonNull String lastName, Long birthDate, @NonNull String pesel, String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pesel = pesel;

        if (note.length() == 0 || note == null) this.note = "brak";
        else this.note = note;

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
    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NonNull Long birthDate) {
        this.birthDate = birthDate;
    }

    @NonNull
    public String getPesel() {
        return pesel;
    }

    public void setPesel(@NonNull String pesel) {
        this.pesel = pesel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
