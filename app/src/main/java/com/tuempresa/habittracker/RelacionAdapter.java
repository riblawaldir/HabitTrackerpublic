package com.tuempresa.habittracker;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.database.AppDatabase;
import com.tuempresa.habittracker.model.Dia;
import com.tuempresa.habittracker.model.Habito;
import com.tuempresa.habittracker.model.HabitoDia;

import java.util.List;

public class RelacionAdapter extends RecyclerView.Adapter<RelacionAdapter.ViewHolder> {

    private List<HabitoDia> relaciones;
    private List<Habito> habitos;
    private List<Dia> dias;
    private AppDatabase db;
    private Context context;

    public RelacionAdapter(Context context, List<HabitoDia> relaciones, List<Habito> habitos, List<Dia> dias, AppDatabase db) {
        this.context = context;
        this.relaciones = relaciones;
        this.habitos = habitos;
        this.dias = dias;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HabitoDia relacion = relaciones.get(position);
        String nombreHabito = buscarNombreHabito(relacion.id_habito);
        String fechaDia = buscarFechaDia(relacion.id_dia);

        holder.text1.setText("📝 " + nombreHabito + " | 📅 " + fechaDia);
        holder.text2.setText("Estado: " + relacion.estado + " | Nota: " + relacion.nota_dia);

        // ✔️ Marcar como completado tocando
        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Actualizar Estado")
                    .setMessage("¿Marcar como COMPLETADO este hábito?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        relacion.estado = "completado";
                        db.habitoDiaDao().actualizar(relacion);
                        notifyItemChanged(position);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // 🗑️ Eliminar con pulsación larga
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Relación")
                    .setMessage("¿Seguro que deseas eliminar esta relación?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        db.habitoDiaDao().eliminar(relacion);
                        relaciones.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return relaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }

    private String buscarNombreHabito(int id) {
        for (Habito h : habitos) {
            if (h.id_habito == id) return h.nombre;
        }
        return "";
    }

    private String buscarFechaDia(int id) {
        for (Dia d : dias) {
            if (d.id_dia == id) return d.fecha;
        }
        return "";
    }
}
