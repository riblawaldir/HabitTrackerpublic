package com.tuempresa.habittracker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnHabitos, btnDias, btnHabitoDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHabitos = findViewById(R.id.btnHabitos);
        btnDias = findViewById(R.id.btnDias);
        btnHabitoDia = findViewById(R.id.btnHabitoDia);

        btnHabitos.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, HabitoActivity.class));
        });

        btnDias.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DiaActivity.class));
        });

        btnHabitoDia.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, HabitoDiaActivity.class));
        });
    }
}
