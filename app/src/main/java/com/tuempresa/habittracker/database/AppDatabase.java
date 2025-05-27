package com.tuempresa.habittracker.database;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tuempresa.habittracker.dao.DiaDao;
import com.tuempresa.habittracker.dao.HabitoDao;
import com.tuempresa.habittracker.dao.HabitoDiaDao;
import com.tuempresa.habittracker.model.Dia;
import com.tuempresa.habittracker.model.Habito;
import com.tuempresa.habittracker.model.HabitoDia;

@Database(entities = {Habito.class, Dia.class, HabitoDia.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instancia;

    public abstract HabitoDao habitoDao();
    public abstract DiaDao diaDao();
    public abstract HabitoDiaDao habitoDiaDao();

    public static synchronized AppDatabase obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "habit_tracker_db")
                    .fallbackToDestructiveMigration() // ← Esta línea evita errores si cambias las entidades
                    .allowMainThreadQueries()          // ← Solo para apps simples o en pruebas
                    .build();
        }
        return instancia;
    }
}


