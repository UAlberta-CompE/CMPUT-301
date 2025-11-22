package com.example.emotilog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Screen for choosing a date and viewing emoji frequencies plus percentages
 *
 * Longer context: Fetches per-day summaries from LogManager and displays them via SummaryAdapter,
 * showing an empty-state TextView when no data is available.
 *
 * Assumptions: Summary only needs to reflect the currently selected local date
 *
 * Limitations: None I can think of
 */
public class SummaryActivity extends AppCompatActivity {
    private Date selectedDate = new Date(); // Day currently being summarized; defaults to "today"
    private ListView summaryList; // ListView that renders emoji counts when data exists
    private TextView noEntries; // TextView that explains when no entries were logged

    /**
     * Inflates the layout, loads the initial summary, and prepares the date picker button
     *
     * @param savedInstanceState Standard Android lifecycle bundle (unused).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        summaryList = findViewById(R.id.summaryListView);
        noEntries = findViewById(R.id.noEntriesText);

        loadSummary(selectedDate);

        Button btnPickDateSummary = findViewById(R.id.btnPickDateSummary);
        btnPickDateSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * Opens a DatePickerDialog so the user can choose which day's summary to view
     */
    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar chosen = Calendar.getInstance();
                chosen.set(year, month, day);
                selectedDate = chosen.getTime();
                loadSummary(selectedDate);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Updates the UI to reflect the summary for the given day
     *
     * @param date Day to summarize
     */
    private void loadSummary(Date date) {
        Map<String, Integer> summary = LogManager.getInstance().getSummaryByDate(date);

        if (summary.isEmpty()) {
            noEntries.setText("No entries made that day :(");
            noEntries.setVisibility(View.VISIBLE);
            summaryList.setAdapter(null);
            return;
        }

        int total = 0;
        for (int count : summary.values()) {
            total += count;
        }

        SummaryAdapter adapter = new SummaryAdapter(this, summary, total);
        summaryList.setAdapter(adapter);
        noEntries.setVisibility(View.GONE);
    }
}
