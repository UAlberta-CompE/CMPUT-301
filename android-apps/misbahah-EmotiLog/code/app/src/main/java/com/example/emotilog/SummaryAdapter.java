package com.example.emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Binds emoji-to-count summary entries to the summary row layout
 *
 * Longer context: Receives daily frequency data from SummaryActivity and renders each emoji with
 * its count and percentage. It has no knowledge of how the data is collected
 *
 * Assumptions: Caller guarantees total > 0 when constructing the adapter
 *
 * Limitations: Uses List.copyOf, which depends on newer Java APIs; should fall back to ArrayList for SDK 24
 */
public class SummaryAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {
    private final LayoutInflater inflater; // Inflater used to create summary row views
    private final int total; // Total log count for the selected day

    /**
     * Constructs the summary adapter by copying the summary map entries so the adapter holds a
     * stable snapshot
     *
     * @param context Hosting context (SummaryActivity)
     * @param summary Map of emoji text to daily counts
     * @param total Sum of all counts for the day; assumed positive
     */
    public SummaryAdapter(Context context, Map<String, Integer> summary, int total) {
        super(context, 0, List.copyOf(summary.entrySet())); // turn map into list
        this.inflater = LayoutInflater.from(context);
        this.total = total;
    }

    /**
     * Populates a summary row with the emoji, count, and rounded percentage
     *
     * @param position Zero-based row index
     * @param convertView Recycled view if available
     * @param parent Parent ListView
     * @return View showing "emoji count (%)"
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map.Entry<String, Integer> entry = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_summary, parent, false);
        }

        TextView emojiView = convertView.findViewById(R.id.textEmojiSummary);
        TextView countView = convertView.findViewById(R.id.textCountSummary);

        String emoji = entry.getKey();
        int count = entry.getValue();
        double percent = (count * 100.0) / total;

        emojiView.setText(emoji);
        countView.setText(String.format(Locale.getDefault(), "%d (%.0f%%)", count, percent));

        return convertView;
    }
}
