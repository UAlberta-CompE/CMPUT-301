package com.example.emotilog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * In-memory store for all LogEntry objects; provides query helpers and daily summaries
 *
 * Longer context: Maintains a singleton list that backs the home screen, the event list, and the
 * summary view without any on-disk persistence. The UI layers ask this class for data, but it does
 * not handle rendering or Android lifecycle concerns
 *
 * Assumptions: One process-wide singleton is sufficient for the assignment flows
 *
 * Limitations:
 * - No persistence layer, so logs vanish when the process is killed
 * - Same-day detection relies on deprecated java.util.Date APIs and ignores time zones
 * - Lacks a dedicated remove(LogEntry) helper, so deletions in EventListActivity are not yet permanent
 */
public class LogManager {
    private static LogManager instance; // reference used so every Activity shares the same in-memory state
    private final List<LogEntry> logs; // list of all logs

    private LogManager() {
        logs = new ArrayList<>();
    }

    /**
     * Returns the singleton instance, creating it lazily on first access
     */
    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    /**
     * Stores a new log entry in the in-memory list
     */
    public void addLog(String emoji, Date timestamp) {
        logs.add(new LogEntry(emoji, timestamp));
    }

    /**
     * Retrieves all logs whose timestamps fall on the same local calendar day as the given date
     */
    public List<LogEntry> getLogsByDate(Date date) {
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry entry : logs) {
            if (isSameDay(entry.getTimestamp(), date)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Builds a frequency table (emoji -> count) for all logs that fall on the given date
     * Empty map indicates no logs that day
     */
    public Map<String, Integer> getSummaryByDate(Date date) {
        Map<String, Integer> counts = new HashMap<>();
        for (LogEntry entry : getLogsByDate(date)) {
            counts.put(entry.getEmoji(), counts.getOrDefault(entry.getEmoji(), 0) + 1);
        }
        return counts;
    }

    /**
     * Determines whether two Date instances represent the same calendar day
     */
    private boolean isSameDay(Date d1, Date d2) {
        return d1.getYear() == d2.getYear() &&
                d1.getMonth() == d2.getMonth() &&
                d1.getDate() == d2.getDate();
    }
}
