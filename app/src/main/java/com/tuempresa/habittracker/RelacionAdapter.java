package com.tuempresa.habittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.habittracker.model.Habito;
import com.tuempresa.habittracker.model.Dia;
import com.tuempresa.habittracker.model.HabitoDia;

import java.util.List;

public class RelacionAdapter extends RecyclerView.Adapter<RelacionAdapter.ViewHolder> {

    private List<HabitoDia> relaciones;
    private List<Habito> habitos;
    private List<Dia> dias;

    public RelacionAdapter(List<HabitoDia> relaciones, List<Habito> habitos, List<Dia> dias) {
        this.relaciones = relaciones;
        this.habitos = habitos;
        this.dias = dias;
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
        String nombreHabito = buscarNombreHabito(relacion.habitoId);
        String fechaDia = buscarFechaDia(relacion.diaId);
        holder.text1.setText("Hábito: " + nombreHabito + " | Día: " + fechaDia);
        holder.text2.setText("Completado: " + (relacion.completado ? "Sí" : "No"));
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
            if (h.habitoId == id) return h.nombre;
        }
        return "";
    }

    private String buscarFechaDia(int id) {
        for (Dia d : dias) {
            if (d.diaId == id) return d.fecha;
        }
        return "";
    }
}
