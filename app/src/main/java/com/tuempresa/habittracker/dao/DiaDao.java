package com.tuempresa.habittracker.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tuempresa.habittracker.model.Dia;

import java.util.List;

@Dao
public interface DiaDao {
    @Insert
    void insertar(Dia dia);

    @Update
    void actualizar(Dia dia);

    @Delete
    void eliminar(Dia dia);

    @Query("SELECT * FROM Dia")
    List<Dia> obtenerTodos();
    @Query("SELECT COUNT(*) FROM Dia")
    int contar();

}

