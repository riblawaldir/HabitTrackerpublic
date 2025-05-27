package com.tuempresa.habittracker.model;


import androidx.room.Entity;

@Entity(primaryKeys = {"habitoId", "diaId"})
public class HabitoDia {
    public int habitoId;
    public int diaId;

    public boolean completado;
}
