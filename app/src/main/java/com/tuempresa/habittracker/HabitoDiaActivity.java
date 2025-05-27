package com.tuempresa.habittracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Dia;
import com.tuempresa.habittracker.model.Habito;
import com.tuempresa.habittracker.model.HabitoDia;

import java.util.List;

public class HabitoDiaActivity extends AppCompatActivity {

    Spinner spinnerHabito, spinnerDia;
    CheckBox checkCompletado;
    Button btnGuardar;
    RecyclerView recyclerView;

    AppDatabase db;
    List<Habito> habitos;
    List<Dia> dias;
    List<HabitoDia> relaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habito_dia);

        spinnerHabito = findViewById(R.id.spinnerHabito);
        spinnerDia = findViewById(R.id.spinnerDia);
        checkCompletado = findViewById(R.id.checkCompletado);
        btnGuardar = findViewById(R.id.btnGuardarRelacion);
        recyclerView = findViewById(R.id.recyclerRelaciones);

        db = AppDatabase.obtenerInstancia(this);

        cargarDatos();

        btnGuardar.setOnClickListener(v -> {
            HabitoDia relacion = new HabitoDia();
            relacion.habitoId = habitos.get(spinnerHabito.getSelectedItemPosition()).habitoId;
            relacion.diaId = dias.get(spinnerDia.getSelectedItemPosition()).diaId;
            relacion.completado = checkCompletado.isChecked();

            db.habitoDiaDao().insertar(relacion);
            cargarRelaciones();
        });
    }

    private void cargarDatos() {
        habitos = db.habitoDao().obtenerTodos();
        dias = db.diaDao().obtenerTodos();

        ArrayAdapter<String> adapterHabitos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (Habito h : habitos) adapterHabitos.add(h.nombre);
        spinnerHabito.setAdapter(adapterHabitos);

        ArrayAdapter<String> adapterDias = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (Dia d : dias) adapterDias.add(d.fecha);
        spinnerDia.setAdapter(adapterDias);

        cargarRelaciones();
    }

    private void cargarRelaciones() {
        relaciones = db.habitoDiaDao().obtenerTodas();
        RelacionAdapter adapter = new RelacionAdapter(relaciones, habitos, dias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
