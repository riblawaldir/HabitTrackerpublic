package com.tuempresa.habittracker.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tuempresa.habittracker.model.HabitoDia;

import java.util.List;

@Dao
public interface HabitoDiaDao {
    @Insert
    void insertar(HabitoDia habitoDia);

    @Update
    void actualizar(HabitoDia habitoDia);

    @Delete
    void eliminar(HabitoDia habitoDia);

    @Query("SELECT * FROM HabitoDia")
    List<HabitoDia> obtenerTodas();

    @Query("SELECT COUNT(*) FROM HabitoDia")
    int contar();

    @Query("DELETE FROM HabitoDia WHERE id_habito = :habitoId")
    void eliminarPorHabitoId(int habitoId);

}

