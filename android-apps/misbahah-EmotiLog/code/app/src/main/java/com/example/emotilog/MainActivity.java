package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

/**
 * Home screen showing a 3x3 emoji grid and navigation shortcuts to other screens
 *
 * Longer context: Dynamically creates nine emoji buttons, logs taps to LogManager, and exposes
 * buttons that route to the event history and summary screens.
 *
 * Assumptions: Immediate, in-memory logging is sufficient; no persistence required on the landing screen
 *
 * Limitations: Layout relies on a simple LinearLayout/GridLayout combination; no landscape-specific tweaks.
 */
public class MainActivity extends AppCompatActivity {
    private final String[] emojis = {"😊","😢","😡","🤩","😴","😱","😐","😍","😭"};

    /**
     * Inflates the main layout, wires emoji buttons, and sets up navigation actions.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout grid = findViewById(R.id.emojiGrid);
        for (final String emoji : emojis) {
            Button btn = new Button(this);
            btn.setText(emoji);
            btn.setTextSize(24);
            btn.setBackgroundResource(R.drawable.bg_emoji);
            btn.setTextColor(getResources().getColor(R.color.text_primary));
            btn.setAllCaps(false);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogManager.getInstance().addLog(emoji, new Date());
                    Toast.makeText(MainActivity.this, "Logged " + emoji, Toast.LENGTH_SHORT).show();
                }
            });
            grid.addView(btn);
        }

        Button btnEvents = findViewById(R.id.btnEvents);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventListActivity.class));
            }
        });

        Button btnSummary = findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SummaryActivity.class));
            }
        });
    }
}
