package com.example.project4_pi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawingSurface drawingSurface = findViewById(R.id.drawingSurface);
        drawingSurface.setVisibility(View.VISIBLE);
        drawingSurface.setDrawingCacheEnabled(true);
        drawingSurface.setEnabled(true);
        drawingSurface.invalidate();

        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(v->{

            drawingSurface.clearScreen();
        });

        // change color to green
        Button changeGreen = findViewById(R.id.green);
        changeGreen.setOnClickListener(v->{
            drawingSurface.setColor(Color.GREEN);
        });
        // change color to red
        Button changeRed = findViewById(R.id.red);
        changeRed.setOnClickListener(v->{
            drawingSurface.setColor(Color.RED);
        });
        // change color to blue
        Button changeBlue = findViewById(R.id.blue);
        changeBlue.setOnClickListener(v->{
            drawingSurface.setColor(Color.BLUE);
        });
        // change color to yellow
        Button changeYellow = findViewById(R.id.yellow);
        changeYellow.setOnClickListener(v->{
            drawingSurface.setColor(Color.YELLOW);
        });

    }
}