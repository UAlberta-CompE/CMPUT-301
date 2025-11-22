package com.example.emotilog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Screen for choosing a date and viewing/editing/deleting entries from that day
 *
 * Longer context: Presents a date picker and a ListView bound via EventListAdapter; editing updates
 * the in-memory LogEntry, while deletion currently only affects the visible list
 *
 * Assumptions: Users expect a simple day filter with local time semantics
 *
 * Limitations:
 * - Delete path does not propagate to LogManager yet, so entries return after refresh
 * - No undo or confirmation beyond the dialog choice
 */
public class EventListActivity extends AppCompatActivity {
    private ListView listView; // ListView displaying entries for the selected date
    private EventListAdapter adapter; // Adapter binding LogEntry objects to row views
    private Date selectedDate = new Date(); // Day currently being inspected; defaults to "today"

    /**
     * Wires the layout, loads the initial day's logs, and prepares the date picker button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        listView = findViewById(R.id.eventListView);
        loadLogsForDate(selectedDate);

        Button btnPickDate = findViewById(R.id.btnPickDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * Opens a DatePickerDialog to let the user choose which day's logs to view
     */
    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar chosen = Calendar.getInstance();
                chosen.set(year, month, day);
                selectedDate = chosen.getTime();
                loadLogsForDate(selectedDate);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Refreshes the ListView contents to match the selected day.
     *
     * @param date Day whose logs should be displayed.
     */
    private void loadLogsForDate(Date date) {
        final List<LogEntry> logs = LogManager.getInstance().getLogsByDate(date);
        adapter = new EventListAdapter(this, logs);
        listView.setAdapter(adapter);

        // Long-press a row to edit or delete that specific entry.
        // Edit: user picks a replacement emoji; we update in-place and refresh.
        // Delete: remove from LogManager (source of truth) and from the current list view.
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            final LogEntry entry = logs.get(position);
            new AlertDialog.Builder(EventListActivity.this)
                    .setTitle("Edit or Delete?")
                    .setPositiveButton("Edit", (d, which) -> showEditDialog(entry))
                    .setNegativeButton("Delete", (d, which) -> {
                        logs.remove(entry);
                        adapter.notifyDataSetChanged();
                    })
                    .show();
            return true;
        });
    }

    /**
     * Displays an emoji picker dialog for editing a log entry
     *
     * @param entry Entry being modified
     */
    private void showEditDialog(final LogEntry entry) {
        final String[] emojis = {"😊","😢","😡","🤩","😴","😱","😐","😍","😭"};
        new AlertDialog.Builder(this)
                .setTitle("Pick new emoji")
                .setItems(emojis, (d, which) -> {
                    entry.setEmoji(emojis[which]);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Updated entry", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
