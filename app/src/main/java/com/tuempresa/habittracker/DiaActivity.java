package com.tuempresa.habittracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Dia;

import java.util.List;

public class DiaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiaAdapter adapter;
    private Button btnAgregar;
    private AppDatabase db;
    private List<Dia> listaDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia);

        recyclerView = findViewById(R.id.recyclerDias);
        btnAgregar = findViewById(R.id.btnAgregarDia);

        db = AppDatabase.obtenerInstancia(this);
        listaDias = db.diaDao().obtenerTodos();

        adapter = new DiaAdapter(this, listaDias, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAgregar.setOnClickListener(view -> mostrarDialogoAgregar());
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo Día");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_nuevo_dia, null);
        builder.setView(dialogView);

        EditText edtFecha = dialogView.findViewById(R.id.edtFecha);
        EditText edtHora = dialogView.findViewById(R.id.edtHora);
        EditText edtEstado = dialogView.findViewById(R.id.edtEstadoAnimo);
        EditText edtComentario = dialogView.findViewById(R.id.edtComentario);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            Dia nuevo = new Dia();
            nuevo.fecha = edtFecha.getText().toString();
            nuevo.hora = edtHora.getText().toString();  // Nuevo campo
            nuevo.estado_animo = edtEstado.getText().toString();
            nuevo.comentario = edtComentario.getText().toString();

            db.diaDao().insertar(nuevo);
            recargarLista();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }



    private void recargarLista() {
        listaDias.clear();
        listaDias.addAll(db.diaDao().obtenerTodos());
        adapter.notifyDataSetChanged();
    }
}
