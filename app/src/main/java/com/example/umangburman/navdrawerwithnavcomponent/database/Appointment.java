package com.example.umangburman.navdrawerwithnavcomponent.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "appointment")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time_start;
    @NonNull
    private LocalTime time_end;
}
