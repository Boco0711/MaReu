package com.example.mareu.events;

import com.example.mareu.model.Meeting;

public class AddMeetingEvent {
    public Meeting meeting;

    public AddMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}