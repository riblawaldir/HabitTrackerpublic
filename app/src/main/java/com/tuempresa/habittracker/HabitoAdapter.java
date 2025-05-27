package com.tuempresa.habittracker;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Habito;

import java.util.List;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private List<Habito> listaHabitos;
    private Context context;
    private AppDatabase db;

    public HabitoAdapter(Context context, List<Habito> listaHabitos, AppDatabase db) {
        this.context = context;
        this.listaHabitos = listaHabitos;
        this.db = db;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habito, parent, false);
        return new HabitoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = listaHabitos.get(position);
        holder.txtNombre.setText(habito.nombre);
        holder.txtDescripcion.setText(habito.descripcion);

        holder.itemView.setOnClickListener(v -> mostrarDialogoEditar(habito));
        holder.itemView.setOnLongClickListener(v -> {
            mostrarDialogoEliminar(habito);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaHabitos.size();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDescripcion;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        }
    }

    private void mostrarDialogoEditar(Habito habito) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Hábito");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nuevo_habito, null);
        EditText edtNombre = dialogView.findViewById(R.id.edtNombre);
        EditText edtDescripcion = dialogView.findViewById(R.id.edtDescripcion);

        edtNombre.setText(habito.nombre);
        edtDescripcion.setText(habito.descripcion);

        builder.setView(dialogView);
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            habito.nombre = edtNombre.getText().toString();
            habito.descripcion = edtDescripcion.getText().toString();

            db.habitoDao().actualizar(habito);
            recargar();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoEliminar(Habito habito) {
        new AlertDialog.Builder(context)
                .setTitle("¿Eliminar Hábito?")
                .setMessage("¿Seguro que deseas eliminar este hábito?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    db.habitoDao().eliminar(habito);
                    recargar();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void recargar() {
        listaHabitos.clear();
        listaHabitos.addAll(db.habitoDao().obtenerTodos());
        notifyDataSetChanged();
    }
}
