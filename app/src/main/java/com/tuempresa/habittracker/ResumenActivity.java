package com.tuempresa.habittracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Habito;
import com.tuempresa.habittracker.model.HabitoDia;

public class ResumenActivity extends AppCompatActivity {

    private TextView txtResumen;
    private EditText edtBuscarHabitoId;
    private Button btnEliminar;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        txtResumen = findViewById(R.id.txtResumen);
        edtBuscarHabitoId = findViewById(R.id.edtBuscarHabitoId);
        btnEliminar = findViewById(R.id.btnEliminarHabito);

        db = AppDatabase.obtenerInstancia(this);

        cargarResumen();

        btnEliminar.setOnClickListener(v -> {
            String input = edtBuscarHabitoId.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Ingresa un ID de hábito válido", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(input);
            Habito habito = db.habitoDao().buscarPorId(id);
            if (habito == null) {
                Toast.makeText(this, "No existe un hábito con ese ID", Toast.LENGTH_SHORT).show();
                return;
            }

            db.habitoDiaDao().eliminarPorHabitoId(id); // eliminar relaciones
            db.habitoDao().eliminar(habito); // eliminar hábito
            Toast.makeText(this, "Hábito y relaciones eliminados", Toast.LENGTH_SHORT).show();
            cargarResumen(); // actualizar conteo
        });
    }

    private void cargarResumen() {
        int habitos = db.habitoDao().contar();
        int dias = db.diaDao().contar();
        int relaciones = db.habitoDiaDao().contar();

        String resumen = "📋 Registros actuales:\n\n"
                + "• Hábito: " + habitos + "\n"
                + "• Día: " + dias + "\n"
                + "• HabitoDia: " + relaciones;

        txtResumen.setText(resumen);
    }
}
