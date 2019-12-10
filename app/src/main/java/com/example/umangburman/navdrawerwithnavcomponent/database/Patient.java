package com.example.umangburman.navdrawerwithnavcomponent.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient")
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private Long birthDate;

    private String note;

    public Patient(@NonNull String firstName, @NonNull String lastName, Long birthDate, String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.note = note;
    }

    //    public Patient( @NonNull String firstName, @NonNull String lastName, String birthDate, String note) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.birthDate = birthDate;
//        this.note = note;
//    }

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


    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", note='" + note + '\'' +
                '}';
    }
}
