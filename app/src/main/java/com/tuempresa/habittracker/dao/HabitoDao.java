package com.tuempresa.habittracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tuempresa.habittracker.model.Habito;

import java.util.List;

@Dao
public interface HabitoDao {
    @Insert
    void insertar(Habito habito);

    @Update
    void actualizar(Habito habito);

    @Delete
    void eliminar(Habito habito);

    @Query("SELECT * FROM Habito")
    List<Habito> obtenerTodos();
}
