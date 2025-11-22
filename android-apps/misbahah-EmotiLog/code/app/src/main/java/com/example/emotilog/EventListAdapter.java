package com.example.emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Binds LogEntry items to the event row layout (emoji plus timestamp)
 *
 * Longer context: Used by EventListActivity's ListView to convert each LogEntry into a simple
 * "emoji 2025-09-30 14:35" style row. It does not manage data mutation; it only formats content
 * for display
 *
 * Assumptions: Caller passes a list that reflects the desired day filter
 *
 * Limitations: No ViewHolder pattern; acceptable for the short prototype lists
 */
public class EventListAdapter extends ArrayAdapter<LogEntry> {
    private final LayoutInflater inflater; // Inflates row layouts for the ListView
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()); // Formats timestamps as "yyyy-MM-dd HH:mm" using the device locale

    /**
     * Creates the adapter with the day's entries.
     */
    public EventListAdapter(Context context, List<LogEntry> entries) {
        super(context, 0, entries);
        inflater = LayoutInflater.from(context);
    }

    /**
     * Provides the view for a given position, inflating and binding emoji + timestamp
     * @param position Zero-based index of the row
     * @param convertView Recycled view if available
     * @param parent Parent ListView requesting the row
     * @return View populated with the entry data
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogEntry entry = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_event, parent, false);
        }

        TextView emojiView = convertView.findViewById(R.id.textEmoji);
        TextView timeView = convertView.findViewById(R.id.textTime);

        emojiView.setText(entry.getEmoji());
        timeView.setText(sdf.format(entry.getTimestamp()));

        return convertView;
    }
}
