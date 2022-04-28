package com.example.project4_pi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // przycisk do czyszczenia ekranu
        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(v->{
            DrawingSurface drawingSurface = findViewById(R.id.drawingSurface);
            drawingSurface.invalidate();
        });
    }
}