package com.tuempresa.habittracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Habito {
    @PrimaryKey(autoGenerate = true)
    public int habitoId;

    @NonNull
    public String nombre;

    public String descripcion;
}
