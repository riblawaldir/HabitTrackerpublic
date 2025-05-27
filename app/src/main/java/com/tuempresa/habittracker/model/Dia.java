package com.tuempresa.habittracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dia {
    @PrimaryKey(autoGenerate = true)
    public int id_dia;

    @NonNull
    public String fecha;  // Ej: 2025-05-27

    public String hora;   // NUEVO campo, Ej: 07:30

    public String estado_animo;
    public String comentario;
}
