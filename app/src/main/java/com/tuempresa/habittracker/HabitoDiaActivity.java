package com.tuempresa.habittracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    EditText edtNota;
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
        edtNota = findViewById(R.id.edtNotaDia);
        btnGuardar = findViewById(R.id.btnGuardarRelacion);
        recyclerView = findViewById(R.id.recyclerRelaciones);

        db = AppDatabase.obtenerInstancia(this);

        cargarDatos();

        btnGuardar.setOnClickListener(v -> {
            HabitoDia relacion = new HabitoDia();
            relacion.id_habito = habitos.get(spinnerHabito.getSelectedItemPosition()).id_habito;
            relacion.id_dia = dias.get(spinnerDia.getSelectedItemPosition()).id_dia;
            relacion.estado = checkCompletado.isChecked() ? "completado" : "pendiente";
            relacion.nota_dia = edtNota.getText().toString();

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
        RelacionAdapter adapter = new RelacionAdapter(this, relaciones, habitos, dias, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
