package com.tuempresa.habittracker.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dia {
    @PrimaryKey(autoGenerate = true)
    public int diaId;

    @NonNull
    public String fecha; // Formato: "2025-05-26"
}