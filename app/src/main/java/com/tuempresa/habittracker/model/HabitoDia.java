package com.tuempresa.habittracker.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"id_habito", "id_dia"})
public class HabitoDia {
    public int id_habito;
    public int id_dia;

    public String estado;     // "pendiente", "completado", etc.
    public String nota_dia;
}
