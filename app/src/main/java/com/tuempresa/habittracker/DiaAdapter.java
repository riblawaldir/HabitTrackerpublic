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
import com.tuempresa.habittracker.model.Dia;

import java.util.List;

public class DiaAdapter extends RecyclerView.Adapter<DiaAdapter.DiaViewHolder> {

    private List<Dia> listaDias;
    private Context context;
    private AppDatabase db;

    public DiaAdapter(Context context, List<Dia> listaDias, AppDatabase db) {
        this.context = context;
        this.listaDias = listaDias;
        this.db = db;
    }

    @NonNull
    @Override
    public DiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new DiaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaViewHolder holder, int position) {
        Dia dia = listaDias.get(position);
        holder.txtFecha.setText(dia.fecha);  // ← CORREGIDO

        holder.itemView.setOnClickListener(v -> mostrarDialogoEditar(dia));
        holder.itemView.setOnLongClickListener(v -> {
            mostrarDialogoEliminar(dia);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaDias.size();
    }

    public static class DiaViewHolder extends RecyclerView.ViewHolder {
        TextView txtFecha;

        public DiaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }

    private void mostrarDialogoEditar(Dia dia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Día");

        EditText edt = new EditText(context);
        edt.setText(dia.fecha);  // ← CORREGIDO
        builder.setView(edt);

        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            dia.fecha = edt.getText().toString();  // ← CORREGIDO
            db.diaDao().actualizar(dia);
            recargar();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoEliminar(Dia dia) {
        new AlertDialog.Builder(context)
                .setTitle("¿Eliminar Día?")
                .setMessage("¿Seguro que deseas eliminar este día?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    db.diaDao().eliminar(dia);
                    recargar();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void recargar() {
        listaDias.clear();
        listaDias.addAll(db.diaDao().obtenerTodos());
        notifyDataSetChanged();
    }
}
