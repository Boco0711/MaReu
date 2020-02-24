package com.example.mareu.events;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(int i) {
        MeetingApiService mApiService = DI.getMeetingApiService();
        this.meeting = mApiService.getMeetingById(i);
    }
}