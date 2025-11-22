package com.example.emotilog;

import java.util.Date;

/**
 * Immutable-ish record for a single emoji log with timestamp
 *
 * Longer context: stores the emoji a user selects alongside the capture time; does not persist
 * anything to disk and is consumed by LogManager, EventListAdapter, and SummaryAdapter
 *
 * Assumptions: Timestamps are generated at log time and remain unchanged even if the emoji is edited
 *
 * Limitations: Relies on java.util.Date, so it is not fully time-zone aware or immutable
 */
public class LogEntry {
    private String emoji; // Emoji string representing the logged mood
    private Date timestamp; // Timestamp captured when the log was created

    public LogEntry(String emoji, Date timestamp) {
        this.emoji = emoji;
        this.timestamp = timestamp;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
