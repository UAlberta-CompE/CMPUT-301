package com.example.lonelytwitter;
import java.util.Date;

public class HappyMood extends Mood {
    public HappyMood() {
        super();
    }

    public HappyMood(Date date) {
        super(date);
    }

    @Override
    public String getMoodString() {
        return "Happy :)";
    }
}
