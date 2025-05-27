package com.tuempresa.habittracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Habito;

import java.util.List;

public class HabitoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitoAdapter adapter;
    private Button btnAgregar;
    private AppDatabase db;
    private List<Habito> listaHabitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habito);

        recyclerView = findViewById(R.id.recyclerHabitos);
        btnAgregar = findViewById(R.id.btnAgregarHabito);

        db = AppDatabase.obtenerInstancia(this);
        listaHabitos = db.habitoDao().obtenerTodos();

        adapter = new HabitoAdapter(this, listaHabitos, db);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAgregar.setOnClickListener(view -> mostrarDialogoAgregar());
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo Hábito");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_nuevo_habito, null);
        builder.setView(dialogView);

        EditText edtNombre = dialogView.findViewById(R.id.edtNombre);
        EditText edtDescripcion = dialogView.findViewById(R.id.edtDescripcion);
        EditText edtPrioridad = dialogView.findViewById(R.id.edtPrioridad);
        EditText edtFrecuencia = dialogView.findViewById(R.id.edtFrecuencia);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            Habito nuevo = new Habito();
            nuevo.nombre = edtNombre.getText().toString();
            nuevo.descripcion = edtDescripcion.getText().toString();
            nuevo.prioridad = edtPrioridad.getText().toString();
            nuevo.frecuencia = edtFrecuencia.getText().toString();

            db.habitoDao().insertar(nuevo);
            recargarLista();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }


    private void recargarLista() {
        listaHabitos.clear();
        listaHabitos.addAll(db.habitoDao().obtenerTodos());
        adapter.notifyDataSetChanged();
    }
}
